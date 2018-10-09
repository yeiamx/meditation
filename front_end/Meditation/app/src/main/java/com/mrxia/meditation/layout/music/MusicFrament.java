package com.mrxia.meditation.layout.music;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.mrxia.meditation.R;
import com.mrxia.meditation.bean.MusicInfo;
import com.mrxia.meditation.service.MusicPlayerService;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicFrament extends Fragment {

    private Button play;
    public static MusicFrament newInstance() {
        MusicFrament frag = new MusicFrament();
        return frag;
    }

    @Override
    @SuppressLint("NewApi")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 1. 加载布局，第三个参数必须为`false`，否则会加载两次布局并且抛出异常！！
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        play = (Button) view.findViewById(R.id.buttonPlay);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                //bundle.putInt("key",);
                bundle.putString("songs","LJQNB");
                bundle.putString("author","LJQ");
                Intent intent = new Intent();
                //绑定需要传递的参数
                intent.putExtras(bundle);
                intent.setClass(getActivity(), MusicPlayActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }



}
