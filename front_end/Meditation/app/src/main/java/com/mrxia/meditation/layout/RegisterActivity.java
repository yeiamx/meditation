package com.mrxia.meditation.layout;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mrxia.meditation.MyApplication;
import com.mrxia.meditation.R;
import com.mrxia.meditation.utils.ActivityUtil;
import com.mrxia.meditation.utils.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.mrxia.meditation.MyApplication.urlStarter;

public class RegisterActivity extends AppCompatActivity {
    private EditText emailET;
    private EditText userNameET;
    private EditText passwordET;
    private EditText confirmPasswordET;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        registerListener();
    }


    public void registerListener(){
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register(){
        String userId = emailET.getText().toString();
        String userName = userNameET.getText().toString();
        String password = passwordET.getText().toString();
        String confirmPassword = confirmPasswordET.getText().toString();
        String url = urlStarter + "/register";

        if (userName.equals("")||password.equals("")||userId.equals("")){
            ActivityUtil.showToast(RegisterActivity.this, "请输入注册信息");
        }else if (!password.equals(confirmPassword)){
            ActivityUtil.showToast(RegisterActivity.this, "密码不一致");
        }else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", userId);
            jsonObject.put("userName", userName);
            jsonObject.put("password", password);
            String parameters = JSONObject.toJSONString(jsonObject);

            Log.d("mrxiaa", "using url " + url);
            HttpUtil.postJson_asynch(url, parameters, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("mrxiaa", e.getMessage());
                    e.printStackTrace();
                    RegisterActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ActivityUtil.showToast(RegisterActivity.this, "注册失败");
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        final String resultStr = response.body().string();
                        Log.d("mrxiaa", "info:"+resultStr);
                        final JSONObject resJsonObj = JSONObject.parseObject(resultStr);

                        if (resJsonObj.getString("status").equals("true")){
                            RegisterActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ActivityUtil.showToast(RegisterActivity.this, "注册成功");
                                    finish();
                                }
                            });
                        } else {
                            RegisterActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ActivityUtil.showToast(RegisterActivity.this, "注册失败");
                                }
                            });
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        RegisterActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ActivityUtil.showToast(RegisterActivity.this, "注册失败");
                            }
                        });
                    }

                }
            });
        }
    }

    public void initView(){
        emailET = findViewById(R.id.register_email);
        userNameET = findViewById(R.id.register_username);
        passwordET = findViewById(R.id.register_password_1);
        confirmPasswordET = findViewById(R.id.register_password_2);
        registerButton = findViewById(R.id.register_button);

        TextView title = findViewById(R.id.register_title);
        Typeface typeface = Typeface.createFromAsset(RegisterActivity.this.getAssets(), "fonts/segoe_script.ttf");
        title.setTypeface(typeface);
        title.setText("Meditation");
    }
}
