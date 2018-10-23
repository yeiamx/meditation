package com.mrxia.meditation.layout.article;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mrxia.meditation.R;
import com.mrxia.meditation.bean.Qa;

import java.util.ArrayList;
import java.util.List;

public class QaFragment extends Fragment {


    private TextView q;
    private TextView a;
    private RecyclerView rv;
    private DividerItemDecoration mDividerItemDecoration;
    private QaAdapter mAdapter;
    private List<Qa> mDataList;

    public static QaFragment newInstance() {
        QaFragment frag = new QaFragment();
        return frag;
    }


    public void initData(){
        this.mDataList = new ArrayList<Qa>();
        for(int i=0;i<3;i++){
            if(i==0)
                mDataList.add(new Qa(""+i, getResources().getString(R.string.question_1), getResources().getString(R.string.answer_1)));
            else if(i==1)
                mDataList.add(new Qa(""+i, getResources().getString(R.string.question_2), getResources().getString(R.string.answer_2)));
            else if(i==2)
                mDataList.add(new Qa(""+i, getResources().getString(R.string.question_3), getResources().getString(R.string.answer_3)));

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 1. 加载布局，第三个参数必须为`false`，否则会加载两次布局并且抛出异常！！
        initData();
        View view = inflater.inflate(R.layout.fragment_article_qa, container, false);
        View item_view = inflater.inflate(R.layout.layout_article_qa_item, container, false);
        initView(view);
        registerListener();
        return view;
    }

    public void registerListener(){

    }

    public void initView(View view){

//        q = item_view.findViewById(R.id.article_qa_q);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/segoe_script.ttf");
//        q.setTypeface(typeface);
//        q.setText("Q:");
//
//        a = item_view.findViewById(R.id.article_qa_a);
//        a.setTypeface(typeface);

        rv = view.findViewById(R.id.article_qa_rv);
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        mDividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                new LinearLayoutManager(this.getActivity()).getOrientation());
        rv.addItemDecoration(mDividerItemDecoration);
        mAdapter = new QaAdapter(this.getActivity(), mDataList, typeface);
        rv.setAdapter(mAdapter);
    }
}
