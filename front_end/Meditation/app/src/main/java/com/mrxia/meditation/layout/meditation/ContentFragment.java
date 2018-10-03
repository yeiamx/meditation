package com.mrxia.meditation.layout.meditation;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mrxia.meditation.R;

public class ContentFragment extends Fragment {
    public static ContentFragment newInstance() {
        ContentFragment frag = new ContentFragment();
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 1. 加载布局，第三个参数必须为`false`，否则会加载两次布局并且抛出异常！！
        View view = inflater.inflate(R.layout.fragment_meditation_content, container, false);
        initView(view);
        registerListener();
        return view;
    }

    public void registerListener(){

    }

    public void initView(View view){

    }
}
