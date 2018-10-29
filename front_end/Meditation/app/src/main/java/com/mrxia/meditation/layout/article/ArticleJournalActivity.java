package com.mrxia.meditation.layout.article;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mrxia.meditation.MyApplication;
import com.mrxia.meditation.R;
import com.mrxia.meditation.bean.Notification;
import com.mrxia.meditation.layout.LoginActivity;
import com.mrxia.meditation.utils.ActivityUtil;
import com.mrxia.meditation.utils.HttpUtil;
import com.mrxia.meditation.utils.LoadingView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.mrxia.meditation.MyApplication.urlStarter;

public class ArticleJournalActivity extends AppCompatActivity {
    private TextView tv_head;
    private ImageView iv_back;
    private Button btn;
    private EditText et;
    private LoadingView loadingView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_journal);
        View view = getWindow().getDecorView();
        initView(view);
        setHalfTransparent();
    }

    public void initView(View view) {
        loadingView = new LoadingView(ArticleJournalActivity.this, R.style.CustomDialog);
        tv_head = view.findViewById(R.id.article_journal_head);
        Typeface typeface = Typeface.createFromAsset(ArticleJournalActivity.this.getAssets(), "fonts/segoe_script.ttf");
        tv_head.setTypeface(typeface);
        tv_head.setText("journal");

//        int image = extras.getInt("image");
//        tv_title = findViewById(R.id.article_content_title);
//        tv_title.setText(title);
//        tv_content = findViewById(R.id.article_content_content);
//        tv_content.setText(content);
//        //tv_content.setMovementMethod(new ScrollingMovementMethod());
//        iv_image = findViewById(R.id.article_content_image);
//        iv_image.setImageResource(image);
        iv_back = findViewById(R.id.article_journal_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn = findViewById(R.id.article_journal_button);
        et = findViewById(R.id.article_journal_edittext_2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(et.getText())){
                    Toast.makeText(ArticleJournalActivity.this,"请输入日志内容", Toast.LENGTH_SHORT).show();
                }else if (!MyApplication.isLogin){
                    ActivityUtil.showToast(ArticleJournalActivity.this, "请先登录再发表哦");
                    Intent intent=new Intent(ArticleJournalActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    if (loadingView!=null){
                        loadingView.show();
                    }
                    updateJournal();
                   // Toast.makeText(ArticleJournalActivity.this,"jjj", Toast.LENGTH_SHORT).show();
//                    String newJournal = et.getText().toString();
//                    Intent intent = new Intent();
//                    intent.putExtra("newJournal", newJournal);
//                    loadingView = new LoadingView(ArticleJournalActivity.this, R.style.CustomDialog);
//                    loadingView.show();
//                    setResult(4, intent);
//                    finish();
                }
            }
        });

    }

    public void updateJournal(){
        Random ran = new Random(System.currentTimeMillis());
        final String newJournal = et.getText().toString();
        final Notification notification = new Notification();
        String id = "" + ran.nextInt();
        notification.setId(id);
        notification.setTitle(MyApplication.userInfo.getUserName());
        notification.setContent(newJournal);
        notification.setType("journal_test");
        notification.setImgUrl(MyApplication.userInfo.getImgUrl());
        long time=System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
        SimpleDateFormat format=new SimpleDateFormat("yyyy/M/d");
        Date d1=new Date(time);
        String t1=format.format(d1);
        notification.setTime(t1);
        String jsonString = JSON.toJSONString(notification);
        String postUrl = urlStarter + "/updateNotification";

        HttpUtil.postJson_asynch(postUrl, jsonString, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("xxchu", e.getMessage());
                e.printStackTrace();
                if (loadingView!=null) {
                    loadingView.dismiss();
                }
                Toast.makeText(ArticleJournalActivity.this,"发表失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resultStr = response.body().string();
                Log.d("xxchu", resultStr);

                if (loadingView!=null) {
                    loadingView.dismiss();
                }
                Toast.makeText(ArticleJournalActivity.this,"发表成功", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.putExtra("newJournal", notification);

                setResult(4, intent);
                finish();
            }

        });

    }

    protected void setHalfTransparent() {

        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
}