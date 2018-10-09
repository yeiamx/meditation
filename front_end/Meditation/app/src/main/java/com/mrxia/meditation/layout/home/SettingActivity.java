package com.mrxia.meditation.layout.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mrxia.meditation.MyApplication;
import com.mrxia.meditation.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView winterButton;
    private TextView autumnButton;
    private TextView foreastButton;

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
        foreastButton = findViewById(R.id.setting_foreast);
    }

    public void registerListener(){
        winterButton.setOnClickListener(this);
        autumnButton.setOnClickListener(this);
        foreastButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setting_winter:
                MyApplication.themeImageUrl = MyApplication.resUrlStarter+"/img/xianqi.jpg";
                break;
            case R.id.setting_autumn:
                MyApplication.themeImageUrl = MyApplication.resUrlStarter+"/img/xianqi.jpg";
                break;
            case R.id.setting_foreast:
                MyApplication.themeImageUrl = MyApplication.resUrlStarter+"/img/xianqi.jpg";
                break;
        }
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
