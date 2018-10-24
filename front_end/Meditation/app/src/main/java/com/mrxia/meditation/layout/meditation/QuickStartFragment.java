package com.mrxia.meditation.layout.meditation;



import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mrxia.meditation.MyApplication;
import com.mrxia.meditation.R;
import com.mrxia.meditation.utils.LoadingView;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class QuickStartFragment extends Fragment {
    private ImageView circleMusicButton;
    private MediaPlayer mediaPlayer;
    private boolean isComplete = true;
    private LoadingView loadingView;
    private final String musicPath = MyApplication.resUrlStarter+"/music/lesson1.mp3";
    private boolean isFirst = true;

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

    public void registerListener(){
        circleMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause();
            }
        });
        mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            public void onSeekComplete(MediaPlayer m) {
                m.start();
                if (loadingView!=null) {
                    loadingView.dismiss();
                }
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // 装载完毕 开始播放流媒体
                //Log.d("mrxiaa", "prepare over");
                if (loadingView!=null) {
                    loadingView.dismiss();
                }
                mediaPlayer.start();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

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

    public void initView(View view){
        circleMusicButton = view.findViewById(R.id.circle_music_button);
        loadingView = new LoadingView(getActivity(), R.style.CustomDialog);
    }

    private void initData(){

    }

    protected void pause() {
        if (isFirst){
            isFirst = false;
            return;
        }

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
