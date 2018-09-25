package com.mrxia.meditation.layout;

import android.app.DownloadManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import com.google.gson.Gson;
import com.mrxia.meditation.R;
import com.mrxia.meditation.bean.Notification;
import com.mrxia.meditation.utils.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;


import static com.mrxia.meditation.MyApplication.urlStarter;


/**
 * Created by Administrator on 2018/9/19.
 */
public class HomeFragment extends Fragment {
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

        Button button = view.findViewById(R.id.notification_1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("mrxiaa", "clicking");
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
                        Log.d("mrxiaa", response.body().string());
                    }
                });
            }
        });

        return view;
    }
}
