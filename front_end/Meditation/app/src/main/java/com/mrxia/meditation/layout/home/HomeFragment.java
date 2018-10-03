package com.mrxia.meditation.layout.home;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.mrxia.meditation.R;
import com.mrxia.meditation.layout.LoginActivity;
import com.mrxia.meditation.utils.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


import static com.mrxia.meditation.MyApplication.isLogin;
import static com.mrxia.meditation.MyApplication.urlStarter;


/**
 * Created by Administrator on 2018/9/19.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{
    private TextView title_1;
    private TextView content_1;
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 1. 加载布局，第三个参数必须为`false`，否则会加载两次布局并且抛出异常！！
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TextView title = view.findViewById(R.id.home_title);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/segoe_script.ttf");
        title.setTypeface(typeface);
        title.setText("Meditation");

        title_1 = view.findViewById(R.id.notification_title_1);
        content_1 = view.findViewById(R.id.notification_content_1);
        RelativeLayout notification_layout_1 = view.findViewById(R.id.notification_1);
        notification_layout_1.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (isLogin) {
            getNotification();
        } else {
            Intent intent=new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

    private void getNotification(){
        String url = urlStarter + "/homeNotification";
        Log.d("mrxiaa", "using url " + url);

        HttpUtil.postJson_asynch(url, "{id:0}", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("mrxiaa", e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resultStr = response.body().string();
                Log.d("mrxiaa", resultStr);
                try {
                    final JSONArray jsonArray = JSONArray.parseArray(resultStr);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            title_1.setText(jsonArray.getJSONObject(0).getString("title"));
                            content_1.setText(jsonArray.getJSONObject(0).getString("content"));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
