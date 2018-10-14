package com.mrxia.meditation.layout.music;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mrxia.meditation.R;

import java.io.IOException;


public class MusicPlayActivity extends Activity {
    private Bundle bundle;
    //取得歌词View对象
    // private LrcView LrcViewId;
    //   //播放音乐对象
    private MediaPlayer mediaPlayer = null;
    //当前播放时间
    public static int currentTime;
    //音乐时常
    private int duration;
    //用来判断播放控制器是否被点击
    private static boolean AUDIO_STATE = false;

    //声明布局文件
    private TextView MusicName;
    private TextView MusicArtist;
    private ImageView imagePlay;   //播放和暂停按钮
    private ImageView imageNext;   //下一首歌
    private ImageView imagePre;     //上一首歌
    private ImageView imageSet;     //音乐设置
    private TextView currentText;   //当前时间View
    private TextView totleText;   //歌曲总时间View
    private SeekBar seekBar;     //歌曲播放进度
    private String url;
    private String songs;
    private String author;
    /*
    设定音乐播放相关属性
     */
    private int durationTime = 0;   ///歌曲 总时间
    //  private int bufferTime = 0;     //歌曲缓存。
    public static boolean SEEK_BAR_STATE = true; //默认不是滑动状态
    private String musicPath;

    @Override
    public void takeKeyEvents(boolean get) {
        super.takeKeyEvents(get);
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_detail_layout);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        Intent intent = getIntent();
        musicPath = intent.getStringExtra("path");

        initView();
        play();
        registerListener();
    }

    private void initView() {
        imagePlay = findViewById(R.id.imagePlay);
        imageNext = findViewById(R.id.imageNext);
        imagePre = findViewById(R.id.imagePre);
        imageSet = findViewById(R.id.imageSet);
        currentText = findViewById(R.id.currentText);
        totleText = findViewById(R.id.totleText);
        seekBar = findViewById(R.id.seekBar);
        findViewById(R.id.rl_SeekBar).setFocusableInTouchMode(true);
    }

    private void registerListener() {
        imagePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause();
            }
        });
    }

    protected void play() {
        mediaPlayer = new MediaPlayer();
        // 设置指定的流媒体地址
        try {
            mediaPlayer.setDataSource(musicPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 设置音频流的类型
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        // 通过异步的方式装载媒体资源
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // 装载完毕 开始播放流媒体
                mediaPlayer.start();
            }
        });
        // 设置循环播放
        // mediaPlayer.setLooping(true);
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
                replay();
                Log.d("mrxiaa", "error playing...");
                return false;
            }
        });
    }

    protected void pause() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            Log.d("mrxiaa", "start!");
            imagePlay.setImageResource(R.mipmap.music_pause);
            Log.d("mrxiaa", "to pause");

            return;
        }
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            Log.d("mrxiaa", "pause!");
            imagePlay.setImageResource(R.mipmap.music_play);
            Log.d("mrxiaa", "to play");
        }
    }

    protected void replay() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(0);
            return;
        }
        play();
    }

    @Override
    protected void onDestroy() {
        // 在activity结束的时候回收资源
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }


    protected void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}

