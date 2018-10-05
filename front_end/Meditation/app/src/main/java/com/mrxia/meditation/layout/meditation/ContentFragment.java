package com.mrxia.meditation.layout.meditation;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mrxia.meditation.R;

public class ContentFragment extends Fragment {
    private RecyclerView recyclerView;
    private VerticalRecyclerAdapter adapter;

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
        recyclerView = view.findViewById(R.id.meditation_ver_recyclerview);
        adapter = new VerticalRecyclerAdapter(getActivity(), null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
}
