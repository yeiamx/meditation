package com.mrxia.meditation.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mrxia.meditation.MyApplication;
import com.mrxia.meditation.R;
import com.mrxia.meditation.bean.Notification;
import com.mrxia.meditation.layout.LoginActivity;
import com.mrxia.meditation.layout.music.MusicPlayActivity;
import com.mrxia.meditation.utils.ActivityUtil;
import com.mrxia.meditation.utils.HttpUtil;
import com.mrxia.meditation.utils.LoadingView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.mrxia.meditation.MyApplication.urlStarter;

public class FavoriteFragment extends Fragment {
    private ViewPager mViewPager;
    private List<Notification> data;
    private FavoriteCardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private boolean mShowingFragments = false;
    private LoadingView loadingView;
    private int lastFavoriteNum;

    public static FavoriteFragment newInstance() {
        FavoriteFragment frag = new FavoriteFragment();
        return frag;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 1. 加载布局，第三个参数必须为`false`，否则会加载两次布局并且抛出异常！！
        View view = inflater.inflate(R.layout.fragment_profile_favorite, container, false);

        initView(view);
        registerListener();
        initData();

        return view;
    }

    private void initView(View view){
        loadingView = new LoadingView(getActivity(), R.style.CustomDialog);
        mViewPager = view.findViewById(R.id.viewPager);
        mViewPager.setOffscreenPageLimit(1);
    }

    private void registerListener(){

    }

    private void initData(){
        if (MyApplication.isLogin) {
            lastFavoriteNum = MyApplication.userInfo.getFavorite().split(",").length;
            if (loadingView!=null) {
                loadingView.show();
            }
            getFavorites();
        } else {
            Log.d("mrxiaa", "login");
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

    private void getFavorites() {
        String url = urlStarter + "/getNotification";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("favorites", MyApplication.userInfo.getFavorite());
        jsonObject.put("type", "favorite");
        String parameters = JSONObject.toJSONString(jsonObject);
        Log.d("mrxiaa", "using url " + url);
        HttpUtil.postJson_asynch(url, parameters, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (loadingView!=null){
                    loadingView.dismiss();
                }
                Log.d("mrxiaa", e.getMessage());
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ActivityUtil.showToast(getActivity(), "操作失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (loadingView!=null){
                        loadingView.dismiss();
                    }
                    final String resultStr = response.body().string();
                    Log.d("mrxiaa", "info:"+resultStr);
                    final List<Notification> resData = JSONArray.parseArray(resultStr, Notification.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCardAdapter = new FavoriteCardPagerAdapter(getActivity(), resData);
                            mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
                            mViewPager.setAdapter(mCardAdapter);
                            mViewPager.setPageTransformer(false, mCardShadowTransformer);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                    if (loadingView!=null){
                        loadingView.dismiss();
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.isLogin) {
            lastFavoriteNum = MyApplication.userInfo.getFavorite().split(",").length;
            if (loadingView != null) {
                loadingView.show();
            }
            getFavorites();
        }
    }
}
