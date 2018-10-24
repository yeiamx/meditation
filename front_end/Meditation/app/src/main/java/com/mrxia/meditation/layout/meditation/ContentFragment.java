package com.mrxia.meditation.layout.meditation;


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
import com.mrxia.meditation.layout.article.ContentAdapter;
import com.mrxia.meditation.utils.HttpUtil;
import com.mrxia.meditation.utils.LoadingView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.mrxia.meditation.MyApplication.urlStarter;

public class ContentFragment extends Fragment {
    private RecyclerView recyclerView;
    private ContentRecyclerAdapter adapter;
    private List<Notification> data;
    private List<Notification> music;
    private LoadingView loadingView;

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
        initData();

        return view;
    }

    private void initData(){
        if (data==null) {
            data = new ArrayList<>();
            loadingView.show();
            getMusics();
        } else {
            adapter = new ContentRecyclerAdapter(getActivity(), data);
            recyclerView.setAdapter(adapter);
        }
    }

    public void registerListener(){
    }

    public void getMusics(){
        music = new ArrayList<>();
        String url = urlStarter + "/getNotification";
        String type="music";
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
                music = JSONArray.parseArray(resultStr, Notification.class);
                if (loadingView!=null) {
                    loadingView.dismiss();
                }

                for (int i=0; i<5; i++){
                    int num = i%music.size();
                    data.add(music.get(num));
                }
                adapter = new ContentRecyclerAdapter(getActivity(), data);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        });
    }


    public void initView(View view){
        loadingView = new LoadingView(getActivity(), R.style.CustomDialog);

        recyclerView = view.findViewById(R.id.meditation_ver_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SectionDecoration(getActivity(), new SectionDecoration.DecorationCallback() {
            @Override
            public long getGroupId(int position) {
                return position;    //一行为一类
            }

            @Override
            public String getGroupFirstLine(int position) {
                return "类别"+String.valueOf(position);
            }
        }));
    }
}
