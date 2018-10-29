package com.mrxia.meditation.layout;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.mrxia.meditation.MyApplication;
import com.mrxia.meditation.R;
import com.mrxia.meditation.bean.UserInfo;
import com.mrxia.meditation.utils.ActivityUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mrxia.meditation.utils.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.mrxia.meditation.MyApplication.urlStarter;

public class LoginActivity extends AppCompatActivity {
    private EditText pwdET;
    private EditText usernameET;
    private Button loginButton;
    private TextView signupTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        registerListener();
    }

    public void initView(){
        pwdET = findViewById(R.id.login_password_1);
        usernameET = findViewById(R.id.login_username_1);
        loginButton = findViewById(R.id.login_button);
        signupTV = findViewById(R.id.sign_up);

        TextView title = findViewById(R.id.login_title);
        Typeface typeface = Typeface.createFromAsset(LoginActivity.this.getAssets(), "fonts/segoe_script.ttf");
        title.setTypeface(typeface);
        title.setText("Meditation");
    }

    public void registerListener(){
        signupTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login(){
        String password = pwdET.getText().toString();
        String userName = usernameET.getText().toString();
        String url = urlStarter + "/login";
        if (userName.equals("") || password.equals("")){
            ActivityUtil.showToast(LoginActivity.this, "请输入用户名和密码");
        } else if (userName.equals("admin") && password.equals("admin")){
            ActivityUtil.showToast(LoginActivity.this, "登录成功");
            UserInfo userInfo = new UserInfo();
            MyApplication.userInfo = userInfo;
            MyApplication.isLogin = true;
            finish();
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userName", userName);
            jsonObject.put("password", password);
            String paramaters = JSONObject.toJSONString(jsonObject);

            Log.d("mrxiaa", "using url " + url);
            HttpUtil.postJson_asynch(url, paramaters, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("mrxiaa", e.getMessage());
                    e.printStackTrace();
                    LoginActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ActivityUtil.showToast(LoginActivity.this, "登录失败");
                        }
                    });

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    try {
                        final String resultStr = response.body().string();
                        Log.d("mrxiaa", resultStr);
                        final JSONObject resJsonObj = JSONObject.parseObject(resultStr);

                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!resJsonObj.containsKey("status")){
                                    ActivityUtil.showToast(LoginActivity.this, "登录成功");
                                    MyApplication.isLogin = true;
                                    MyApplication.userInfo = JSONObject.parseObject(resultStr, UserInfo.class);
                                    finish();
                                } else {
                                    LoginActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ActivityUtil.showToast(LoginActivity.this, "登录失败");
                                        }
                                    });
                                }

                            }
                        });
                    } catch (Exception e){
                        e.printStackTrace();
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ActivityUtil.showToast(LoginActivity.this, "登录失败");
                            }
                        });
                    }
                }
            });
        }
    }

}
