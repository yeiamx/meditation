package com.mrxia.meditation.layout.meditation;



import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mrxia.meditation.MyApplication;
import com.mrxia.meditation.R;
import com.mrxia.meditation.layout.music.MusicPlayActivity;
import com.mrxia.meditation.utils.LoadingView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.mrxia.meditation.layout.music.MusicPlayActivity.timeToStr;

public class QuickStartFragment extends Fragment {
    private ImageView circleMusicButton;
    private MediaPlayer mediaPlayer;
    private boolean isComplete = true;
    private LoadingView loadingView;
    private final String musicPath = MyApplication.resUrlStarter+"/music/rainwave_4.mp3";
    private boolean isFirst = true;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private boolean isChanging=false;//互斥变量，防止定时器与SeekBar拖动时进度冲突
    private SeekBar seekBar;
    private TextView currentTimeText;   //当前时间View
    private TextView totalTimeText;   //歌曲总时间View
    private TextView quickTitle;


    public static QuickStartFragment newInstance() {
        QuickStartFragment frag = new QuickStartFragment();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 1. 加载布局，第三个参数必须为`false`，否则会加载两次布局并且抛出异常！！
        View view = inflater.inflate(R.layout.fragment_meditation_quickstart, container, false);
        mediaPlayer = new MediaPlayer();

        initView(view);
        registerListener();
        initData();
        return view;
    }

    public void initView(View view){
        quickTitle = view.findViewById(R.id.quickstart_title);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/segoe_script.ttf");
        quickTitle.setTypeface(typeface);
        quickTitle.setText("开始第一课");
        seekBar = view.findViewById(R.id.quickSeekBar);
        currentTimeText = view.findViewById(R.id.quickCurrentText);
        totalTimeText = view.findViewById(R.id.quickTotalText);
        circleMusicButton = view.findViewById(R.id.circle_music_button);
        loadingView = new LoadingView(getActivity(), R.style.CustomDialog);
    }

    public void registerListener(){
        circleMusicButton.setOnClickListener(new View.OnClickListener() {
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
                    getActivity().runOnUiThread(new Runnable() {
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


    private void initData(){

    }

    protected void pause() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            //Log.d("mrxiaa", "start in QuickStart!");
            circleMusicButton.setImageResource(R.mipmap.circle_pause_button);
            //Log.d("mrxiaa", "to pause");
            return;
        }

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            //Log.d("mrxiaa", "pause!");
            circleMusicButton.setImageResource(R.mipmap.circle_play_button);
            //Log.d("mrxiaa", "to play");
        }

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

    protected void replay() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(0);
            return;
        }
        play();
    }

    protected void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop();

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            //Log.d("mrxiaa", "pause!");
            circleMusicButton.setImageResource(R.mipmap.circle_play_button);
            //Log.d("mrxiaa", "to play");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
