package com.mrxia.meditation.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mrxia.meditation.R;

/**
 * Created by Administrator on 2018/9/19.
 */
public class MeditationFragment extends Fragment{
    public static MeditationFragment newInstance() {
        MeditationFragment frag = new MeditationFragment();
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 1. 加载布局，第三个参数必须为`false`，否则会加载两次布局并且抛出异常！！
        return inflater.inflate(R.layout.fragment_meditation, container, false);
    }
}
