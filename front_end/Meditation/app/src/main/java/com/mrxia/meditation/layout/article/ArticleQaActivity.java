package com.mrxia.meditation.layout.article;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.mrxia.meditation.R;

public class ArticleQaActivity extends AppCompatActivity {

    private TextView tv_question;
    private TextView tv_head;
    private TextView tv_answer;
    //private ImageView iv_image;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_article_qa);
        View view = getWindow().getDecorView();
        initView(view);
        setHalfTransparent();
    }

    public void initView(View view){
        tv_head = view.findViewById(R.id.article_qa_head);
        Typeface typeface = Typeface.createFromAsset(ArticleQaActivity.this.getAssets(), "fonts/segoe_script.ttf");
        tv_head.setTypeface(typeface);
        tv_head.setText("Q&A");
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String id = extras.getString("id");
        String question = extras.getString("question");
        String answer = extras.getString("answer");
        tv_question = findViewById(R.id.article_qa_q_content);
        tv_question.setText(question);
        tv_answer = findViewById(R.id.article_qa_a_content);
        tv_answer.setText(answer);
//        int image = extras.getInt("image");
//        tv_title = findViewById(R.id.article_content_title);
//        tv_title.setText(title);
//        tv_content = findViewById(R.id.article_content_content);
//        tv_content.setText(content);
//        //tv_content.setMovementMethod(new ScrollingMovementMethod());
//        iv_image = findViewById(R.id.article_content_image);
//        iv_image.setImageResource(image);
        iv_back = findViewById(R.id.article_qa_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
