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

import com.mrxia.meditation.R;
import com.mrxia.meditation.utils.LoadingView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class MusicPlayActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer = null;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private boolean isChanging=false;//互斥变量，防止定时器与SeekBar拖动时进度冲突

    //声明布局文件
    private TextView MusicName;
    private TextView MusicArtist;
    private ImageView imagePlay;   //播放和暂停按钮
    private ImageView imageNext;   //下一首歌
    private ImageView imagePre;     //上一首歌
    private ImageView imageSet;     //音乐设置
    private TextView currentTimeText;   //当前时间View
    private TextView totalTimeText;   //歌曲总时间View
    private SeekBar seekBar;     //歌曲播放进度

    public static boolean SEEK_BAR_STATE = true; //默认不是滑动状态
    private String musicPath;
    private String backgroundImgUrl;
    private LoadingView loadingView;
    private ImageView background;

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
        musicPath = intent.getStringExtra("path");
        backgroundImgUrl = intent.getStringExtra("imgUrl");
        mediaPlayer = new MediaPlayer();

        initView();
        registerListener();
        play();
        setHalfTransparent();
    }

    private void initView() {
        background = findViewById(R.id.music_background);
        Picasso
                .with(this)
                .load(backgroundImgUrl)
                .into(background);
        imagePlay = findViewById(R.id.imagePlay);
        imageNext = findViewById(R.id.imageNext);
        imagePre = findViewById(R.id.imagePre);
        imageSet = findViewById(R.id.imageSet);
        currentTimeText = findViewById(R.id.currentText);
        totalTimeText = findViewById(R.id.totalText);
        seekBar = findViewById(R.id.seekBar);
        findViewById(R.id.rl_SeekBar).setFocusableInTouchMode(true);
        loadingView = new LoadingView(this, R.style.CustomDialog);
    }

    private void registerListener() {
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
            //Log.d("mrxiaa", "start!");
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
        pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        pause();
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

    public String timeToStr(int duration){
        int musicTime = duration / 1000;
        String mins = musicTime/60<=10?"0"+musicTime/60:""+musicTime/60;
        String seconds = musicTime%60<=10?"0"+musicTime%60:""+musicTime%60;

        return mins+":"+seconds;
    }
}

