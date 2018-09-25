package com.mrxia.meditation.utils;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.*;

/**
 * Created by admin on 2016/12/6.
 */
public class HttpUtil {
    private final static OkHttpClient client = new OkHttpClient.Builder()
            .connectionPool(new ConnectionPool(20, 5, TimeUnit.MINUTES))
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS).build();
    private final static MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static void postJson_asynch(String url, String jsonStr, Callback callback){
        RequestBody body = RequestBody.create(JSON, jsonStr);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }


}

