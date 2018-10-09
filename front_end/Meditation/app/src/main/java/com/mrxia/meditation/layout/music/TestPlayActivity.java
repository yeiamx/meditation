package com.mrxia.meditation.layout.music;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mrxia.meditation.R;

public class TestPlayActivity extends AppCompatActivity {
    private MediaPlayer mp=new MediaPlayer();
    private Button play;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_play);
        play=(Button) findViewById(R.id.play);
        final MediaPlayer mp =MediaPlayer.create(this, R.raw.aaa);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp != null) {
            mp.stop();
            mp.release();
        }
    }
}

