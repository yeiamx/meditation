package com.mrxia.meditation.layout.article;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mrxia.meditation.R;
import com.mrxia.meditation.bean.Notification;
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
    private RecyclerView rv;
    private DividerItemDecoration mDividerItemDecoration;
    private ContentAdapter mAdapter;
    private List<Notification> mDataList;
    private List<Notification> articles;
    private LoadingView loadingView;

    public static ContentFragment newInstance() {
        ContentFragment frag = new ContentFragment();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 1. 加载布局，第三个参数必须为`false`，否则会加载两次布局并且抛出异常！！
        View view = inflater.inflate(R.layout.fragment_article_content, container, false);
        initView(view);
        registerListener();
        initData();

        return view;
    }

    public void initData(){
        mDataList = new ArrayList<Notification>();
        loadingView.show();
        getArticles();
    }

    public void registerListener(){

    }

    public void initView(View view){
        loadingView = new LoadingView(getActivity(), R.style.CustomDialog);

        rv = view.findViewById(R.id.article_content_rv);
        rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rv.addItemDecoration(new MyDecoration());
    }

    public void getArticles(){
        articles = new ArrayList<>();

        String url = urlStarter + "/getNotification";
        String type="article";
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
                articles = JSONArray.parseArray(resultStr, Notification.class);
                if (loadingView!=null)
                    loadingView.dismiss();

                for (int i=0; i<20; i++){
                    int num = i%articles.size();
                    mDataList.add(articles.get(num));
                }
                mAdapter = new ContentAdapter(getActivity(), mDataList);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rv.setAdapter(mAdapter);
                    }
                });
            }
        });

    }

    static class MyDecoration extends RecyclerView.ItemDecoration{

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(15,15,15,15);
        }
    }
}
