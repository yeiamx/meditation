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
import com.mrxia.meditation.bean.Notification;

import java.util.ArrayList;
import java.util.List;

public class ContentFragment extends Fragment {
    private RecyclerView recyclerView;
    private VerticalRecyclerAdapter adapter;
    private List<Notification> data;

    public static ContentFragment newInstance() {
        ContentFragment frag = new ContentFragment();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 1. 加载布局，第三个参数必须为`false`，否则会加载两次布局并且抛出异常！！
        View view = inflater.inflate(R.layout.fragment_meditation_content, container, false);
        initData();
        initView(view);
        registerListener();
        return view;
    }

    private void initData(){
        data = new ArrayList<>();
        for (int i=0; i<10;i++) {
            data.add(new Notification(null, String.valueOf(i), null, null));
        }
    }

    public void registerListener(){
    }

    public void initView(View view){
        recyclerView = view.findViewById(R.id.meditation_ver_recyclerview);
        adapter = new VerticalRecyclerAdapter(getActivity(), data);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
}
