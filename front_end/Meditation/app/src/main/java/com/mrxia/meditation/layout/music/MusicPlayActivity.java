package com.mrxia.meditation.layout.music;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.mrxia.meditation.MyApplication;
import com.mrxia.meditation.R;
import com.mrxia.meditation.layout.LoginActivity;
import com.mrxia.meditation.layout.RegisterActivity;
import com.mrxia.meditation.utils.ActivityUtil;
import com.mrxia.meditation.utils.HttpUtil;
import com.mrxia.meditation.utils.LoadingView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.mrxia.meditation.MyApplication.urlStarter;


public class MusicPlayActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer = null;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private boolean isChanging=false;//互斥变量，防止定时器与SeekBar拖动时进度冲突

    //声明布局文件
    private TextView MusicName;
    private TextView MusicArtist;
    private ImageView imagePlay;   //播放和暂停按钮
    private ImageView likeBtn;
    private TextView currentTimeText;   //当前时间View
    private TextView totalTimeText;   //歌曲总时间View
    private SeekBar seekBar;     //歌曲播放进度

    public static boolean SEEK_BAR_STATE = true; //默认不是滑动状态
    private String musicPath;
    private String backgroundImgUrl;
    private String contentStr;
    private String type;
    private LoadingView loadingView;
    private ImageView background;
    private TextView content;
    private Boolean like = false;
    private String musicId;

    @Override
    public void takeKeyEvents(boolean get) {
        super.takeKeyEvents(get);
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        Intent intent = getIntent();
        musicId = intent.getStringExtra("id");
        musicPath = intent.getStringExtra("path");
        backgroundImgUrl = intent.getStringExtra("imgUrl");
        contentStr = intent.getStringExtra("content");
        type = intent.getStringExtra("type");
        mediaPlayer = new MediaPlayer();

        initView();
        registerListener();
        play();
        setHalfTransparent();
    }

    private void initView() {
        likeBtn = findViewById(R.id.likeBtn);
        if (MyApplication.isLogin) {
            String[] favorites = MyApplication.userInfo.getFavorite().split(",");
            for (String favorId : favorites) {
                if (musicId.equals(favorId)) {
                    like = true;
                }
            }
            if (like) {
                likeBtn.setImageResource(R.mipmap.like);
            } else {
                likeBtn.setImageResource(R.mipmap.unlike);
            }
        } else {
            likeBtn.setImageResource(R.mipmap.unlike);
        }

        background = findViewById(R.id.music_background);
        Picasso
                .with(this)
                .load(MyApplication.themeImageUrl)
                .into(background);
        imagePlay = findViewById(R.id.imagePlay);
        currentTimeText = findViewById(R.id.currentText);
        totalTimeText = findViewById(R.id.totalText);
        seekBar = findViewById(R.id.seekBar);
        findViewById(R.id.rl_SeekBar).setFocusableInTouchMode(true);
        loadingView = new LoadingView(this, R.style.CustomDialog);
        content = findViewById(R.id.LrcViewId);
        content.setText(contentStr);
    }

    private void registerListener() {
        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!MyApplication.isLogin){
                    ActivityUtil.showToast(MusicPlayActivity.this, "请先登录哦");
                    Intent intent=new Intent(MusicPlayActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    String[] favorites = MyApplication.userInfo.getFavorite().split(",");
                    String newFavorites = "";
                    if (like){
                        like = false;
                        likeBtn.setImageResource(R.mipmap.unlike);

                        int index = 0;
                        for (String favorId: favorites){
                            if (index==0 && !musicId.equals(favorId)){
                                newFavorites = newFavorites+favorId;
                            } else if (!musicId.equals(favorId)){
                                newFavorites = newFavorites+","+favorId;
                            }
                            index++;
                        }
                    } else {
                        like = true;
                        likeBtn.setImageResource(R.mipmap.like);
                        if (MyApplication.userInfo.getFavorite().equals("")){
                            newFavorites = musicId;
                        } else {
                            newFavorites = MyApplication.userInfo.getFavorite()+","+musicId;
                        }
                    }
                    updateNewFavorites(newFavorites);
                }

            }
        });

        imagePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause();
            }
        });
        mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            public void onSeekComplete(MediaPlayer m) {
                currentTimeText.setText(timeToStr(mediaPlayer.getDuration()));
                m.start();
                if (loadingView!=null) {
                    loadingView.dismiss();
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isChanging=true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
                isChanging=false;
                if (loadingView!=null) {
                    loadingView.show();
                }
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // 装载完毕 开始播放流媒体
                //Log.d("mrxiaa", "prepare over");
                totalTimeText.setText(timeToStr(mediaPlayer.getDuration()));

                mediaPlayer.start();
                seekBar.setMax(mediaPlayer.getDuration());//设置进度条
                setTimer();
                if (loadingView!=null) {
                    loadingView.dismiss();
                }
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // 在播放完毕被回调
                //Log.d("mrxiaa", (type.length()-1)+"");
                if (type!=null && type.contains("lesson")){
                    int index = Integer.valueOf(type.substring(type.length()-1, type.length()));
                    if (index < 6) {
                        if (MyApplication.travelLock[index] == 0) { //actual index == index -1.So next index == index.
                            ActivityUtil.showToast(MusicPlayActivity.this, "恭喜完成第" + index + "课" + ",解锁第" + (index + 1) + "课");
                            MyApplication.travelLock[index] = 1;
                        }
                    }
                }
            }
        });

        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // 如果发生错误，重新播放
                //Log.d("mrxiaa", "error playing...replay");
                //Log.d("mrxiaa", "OnError - Error code: " + what + " Extra code: " + extra);
                replay();
                return false;
            }
        });
    }

    private void updateNewFavorites(String newFavorites){
        MyApplication.userInfo.setFavorite(newFavorites);
        String url = urlStarter + "/updateUserInfo";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", MyApplication.userInfo.getUserId());
        jsonObject.put("userName", MyApplication.userInfo.getUserName());
        jsonObject.put("password", MyApplication.userInfo.getPassword());
        jsonObject.put("favorites", newFavorites);
        jsonObject.put("imgUrl", MyApplication.userInfo.getImgUrl());
        String parameters = JSONObject.toJSONString(jsonObject);

        Log.d("mrxiaa", "using url " + url);
        HttpUtil.postJson_asynch(url, parameters, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("mrxiaa", e.getMessage());
                e.printStackTrace();
                MusicPlayActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ActivityUtil.showToast(MusicPlayActivity.this, "操作失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String resultStr = response.body().string();
                    Log.d("mrxiaa", "info:"+resultStr);
                    final JSONObject resJsonObj = JSONObject.parseObject(resultStr);

                    if (resJsonObj.getString("status").equals("true")){
                        MusicPlayActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ActivityUtil.showToast(MusicPlayActivity.this, "更新成功");

                            }
                        });
                    } else {
                        MusicPlayActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ActivityUtil.showToast(MusicPlayActivity.this, "操作失败");
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    MusicPlayActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ActivityUtil.showToast(MusicPlayActivity.this, "操作失败");
                        }
                    });
                }

            }
        });
    }

    protected void play() {
        //Log.d("mrxiaa", "start playing");
        mediaPlayer.reset();
        // 设置指定的流媒体地址
        try {
            mediaPlayer.setDataSource(musicPath);
        } catch (IOException e) {
            //Log.d("mrxiaa", "set wrong");
            e.printStackTrace();
        }
        // 设置音频流的类型
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        // 通过异步的方式装载媒体资源
        loadingView.show();
        mediaPlayer.prepareAsync();
        //Log.d("mrxiaa", "start prepare");
        // 设置循环播放
        // mediaPlayer.setLooping(true);
    }

    protected void setTimer(){
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                if(isChanging) {
                    return;
                }
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    final String musicTimeStr = timeToStr(mediaPlayer.getCurrentPosition());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            currentTimeText.setText(musicTimeStr);
                        }
                    });
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                }
            }
        };
        mTimer.schedule(mTimerTask, 0, 10);
    }

    protected void pause() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            //Log.d("mrxiaa", "start in music play!");
            imagePlay.setImageResource(R.mipmap.music_pause);
            //Log.d("mrxiaa", "to pause");

            return;
        }
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            //Log.d("mrxiaa", "pause!");
            imagePlay.setImageResource(R.mipmap.music_play);
            //Log.d("mrxiaa", "to play");
        }
    }

    protected void replay() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(0);
            return;
        }
        play();
    }

    protected void stop() {
        if (mTimerTask!=null) {
            mTimerTask.cancel();
        }
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        // 在activity结束的时候回收资源
        super.onDestroy();
        stop();

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            //Log.d("mrxiaa", "pause!");
            imagePlay.setImageResource(R.mipmap.music_play);
            //Log.d("mrxiaa", "to play");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    protected void setHalfTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public static String timeToStr(int duration){
        int musicTime = duration / 1000;
        String mins = musicTime/60<10?"0"+musicTime/60:""+musicTime/60;
        String seconds = musicTime%60<10?"0"+musicTime%60:""+musicTime%60;

        return mins+":"+seconds;
    }
}

