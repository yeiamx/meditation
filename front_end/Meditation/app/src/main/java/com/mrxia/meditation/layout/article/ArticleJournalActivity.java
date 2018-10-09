package com.mrxia.meditation.layout.article;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mrxia.meditation.R;

public class ArticleJournalActivity extends AppCompatActivity {
    private TextView tv_head;
    private ImageView iv_back;
    private Button btn;
    private EditText et;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_journal);
        View view = getWindow().getDecorView();
        initView(view);
        setHalfTransparent();
    }

    public void initView(View view) {
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
                }else{
                   // Toast.makeText(ArticleJournalActivity.this,"jjj", Toast.LENGTH_SHORT).show();
                    String newJournal = et.getText().toString();
                    Intent intent = new Intent();
                    intent.putExtra("newJournal", newJournal);
                    setResult(4, intent);
                    finish();
                }
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