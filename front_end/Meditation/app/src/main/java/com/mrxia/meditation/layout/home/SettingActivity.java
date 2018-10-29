package com.mrxia.meditation.layout.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mrxia.meditation.MyApplication;
import com.mrxia.meditation.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView winterButton;
    private TextView autumnButton;
    private TextView summerButton;
    private TextView springButton;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        registerListener();
    }

    public void initView(){
        winterButton = findViewById(R.id.setting_winter);
        autumnButton = findViewById(R.id.setting_autumn);
        summerButton = findViewById(R.id.setting_summer);
        springButton = findViewById(R.id.setting_spring);
        backButton = findViewById(R.id.setting_back);
    }

    public void registerListener(){
        winterButton.setOnClickListener(this);
        autumnButton.setOnClickListener(this);
        summerButton.setOnClickListener(this);
        springButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setting_back:
                finish();
                break;
            case R.id.setting_winter:
                MyApplication.themeImageUrl = MyApplication.resUrlStarter+"/img/winter.jpg";
                MyApplication.settingType = "winter";
                break;
            case R.id.setting_autumn:
                MyApplication.themeImageUrl = MyApplication.resUrlStarter+"/img/autumn.jpg";
                MyApplication.settingType = "autumn";
                break;
            case R.id.setting_spring:
                MyApplication.themeImageUrl = MyApplication.resUrlStarter+"/img/spring.jpg";
                MyApplication.settingType = "spring";
                break;
            case R.id.setting_summer:
                MyApplication.themeImageUrl = MyApplication.resUrlStarter+"/img/summer.jpg";
                MyApplication.settingType = "summer";
                break;
        }
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        intent.putExtra("class", "setting");
        finish();
    }
}
