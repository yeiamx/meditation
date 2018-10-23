package com.mrxia.meditation.layout.article;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mrxia.meditation.R;
import com.mrxia.meditation.bean.Journal;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JournalFragment extends Fragment {

    private JournalAdapter mAdapter;
    private EditText et;
    private RecyclerView rv;
    private DividerItemDecoration mDividerItemDecoration;
    private ImageButton btn;
    private TextView logan;

    private List<Journal> mDataList;

    public static JournalFragment newInstance() {
        JournalFragment frag = new JournalFragment();
        return frag;
    }


    public void initData() {
        this.mDataList = new ArrayList<Journal>();
        for (int i = 0; i < 10; i++) {
            this.mDataList.add(new Journal("" + i, "user" + i, "content" + i, "2018/10/" + (10 - i), R.drawable.think));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 1. 加载布局，第三个参数必须为`false`，否则会加载两次布局并且抛出异常！！
        View view = inflater.inflate(R.layout.fragment_article_journal, container, false);
        initData();
        initView(view);
        registerListener();
        return view;
    }

    public void registerListener() {

    }

    public void initView(View view) {
//        et = view.findViewById(R.id.article_journal_edittext_1);
//        et.setFocusable(false);
//        et.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), ArticleJournalActivity.class);
//                startActivityForResult(intent, 1);
//            }
//        });
        logan = view.findViewById(R.id.article_journal_logan);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/SetoFont.ttf");
        logan.setTypeface(typeface);
        logan.setText("今天心情如何");

        btn = view.findViewById(R.id.article_journal_add_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ArticleJournalActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        rv = view.findViewById(R.id.article_journal_rv);
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        //rv.addItemDecoration(new ContentFragment.MyDecoration());
        mDividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                new LinearLayoutManager(this.getActivity()).getOrientation());
        rv.addItemDecoration(mDividerItemDecoration);
        mAdapter = new JournalAdapter(this.getActivity(), this.mDataList);
        rv.setAdapter(mAdapter);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("xxchu","into");
        if (resultCode == 4) {
                String newJournal = data.getStringExtra("newJournal");
                long time=System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
                SimpleDateFormat format=new SimpleDateFormat("yyyy/M/d");
                Date d1=new Date(time);
                String t1=format.format(d1);
                Journal journal = new Journal("add", "add",newJournal,t1,R.drawable.think);

                mAdapter.addData(0,journal);
                Log.d("xxchu",this.mDataList.get(0).getContent());
            }

    }
}
