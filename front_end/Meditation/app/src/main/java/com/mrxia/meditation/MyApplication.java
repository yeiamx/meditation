package com.mrxia.meditation;

import android.app.Application;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    public static final String resUrlStarter = "http://39.108.226.195:8080/resources";
    public static final String urlStarter = "http://39.108.226.195:8080/meditation";
    //public static final String urlStarter = "http://192.168.199.121:8080/meditation";

    public static boolean isLogin = false;
    public static DisplayImageOptions options;
    public static String themeImageUrl;
    public static int SETTING = 666;

    public static int[] travelLock = new int[7];
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
        initTravel();
    }

    private void initImageLoader(){
        themeImageUrl = MyApplication.resUrlStarter+"/img/xianqi.jpg";

        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .writeDebugLogs() //打印log信息
                .build();
        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);
        //显示图片的配置
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading)
                .showImageOnFail(R.drawable.loading)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    private void initTravel(){
        travelLock[0] = 1;
        for (int i=1; i<7; i++){
            travelLock[i] = 0;
        }
    }
}
