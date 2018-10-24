package com.mrxia.meditation.layout.meditation;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mrxia.meditation.MyApplication;
import com.mrxia.meditation.R;
import com.mrxia.meditation.bean.Notification;
import com.mrxia.meditation.layout.music.MusicPlayActivity;
import com.mrxia.meditation.utils.ActivityUtil;
import com.mrxia.meditation.utils.HttpUtil;
import com.mrxia.meditation.utils.ItemClickListener;
import com.mrxia.meditation.utils.LoadingView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.mrxia.meditation.MyApplication.urlStarter;

public class TravelFragment extends Fragment {
    private RecyclerView recyclerView;
    private TravelRecyclerAdapter adapter;
    private List<Notification> data;
    private List<Notification> lessons;
    private LoadingView loadingView;

    public static TravelFragment newInstance() {
        TravelFragment frag = new TravelFragment();
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 1. 加载布局，第三个参数必须为`false`，否则会加载两次布局并且抛出异常！！
        View view = inflater.inflate(R.layout.fragment_meditation_travel, container, false);
        initView(view);
        registerListener();
        initData();

        return view;
    }

    private void initData(){
        data = new ArrayList<>();
        loadingView.show();
        getLessons();
    }

    public void registerListener(){
        recyclerView.addOnItemTouchListener(new ItemClickListener(recyclerView, new ItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (MyApplication.travelLock[position]==1) {
                    Bundle bundle = new Bundle();
                    bundle.putString("path", data.get(position).getResUrl());
                    bundle.putString("imgUrl", data.get(position).getImgUrl());
                    Intent intent = new Intent();
                    //绑定需要传递的参数
                    intent.putExtras(bundle);
                    intent.setClass(getActivity(), MusicPlayActivity.class);
                    getActivity().startActivity(intent);
                }else {
                    ActivityUtil.showToast(getActivity(), "一步一个脚印，先去学完之前的课程吧~");
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }

    public void initView(View view){
        loadingView = new LoadingView(getActivity(), R.style.CustomDialog);

        recyclerView = view.findViewById(R.id.meditation_travel_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void getLessons(){
        lessons = new ArrayList<>();
        String url = urlStarter + "/getNotification";
        String type="lesson";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", type);
        String paramaters = JSONObject.toJSONString(jsonObject);
        Log.d("mrxiaa", "using url " + url);

        HttpUtil.postJson_asynch(url, paramaters, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("mrxiaa", e.getMessage());
                e.printStackTrace();
                if (loadingView!=null) {
                    loadingView.dismiss();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resultStr = response.body().string();
                Log.d("mrxiaa", resultStr);
                lessons = JSONArray.parseArray(resultStr, Notification.class);

                if (loadingView!=null) {
                    loadingView.dismiss();
                }

                for (int i=0; i<7; i++){
                    int num = i%lessons.size();
                    data.add(lessons.get(num));
                }

                adapter = new TravelRecyclerAdapter(getActivity(), data);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        });

    }
}
