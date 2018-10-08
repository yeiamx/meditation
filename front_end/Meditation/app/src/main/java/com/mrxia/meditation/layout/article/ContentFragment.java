package com.mrxia.meditation.layout.article;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mrxia.meditation.R;
import com.mrxia.meditation.bean.Article;

import java.util.ArrayList;
import java.util.List;

public class ContentFragment extends Fragment {
    private RecyclerView rv;
    private DividerItemDecoration mDividerItemDecoration;
    private ContentAdapter mAdapter;
    private List<Article> mDataList;

    public static ContentFragment newInstance() {
        ContentFragment frag = new ContentFragment();
        return frag;
    }


    public void initData(){
        this.mDataList = new ArrayList<Article>();
        for(int i=0;i<20;i++){
            if(i % 2 == 0){
            mDataList.add(new Article(""+i, getResources().getString(R.string.example_article_title), getResources().getString(R.string.example_article_content), R.drawable.content1));
            }else{
                mDataList.add(new Article(""+i, getResources().getString(R.string.example_article_title_1), getResources().getString(R.string.example_article_content_1), R.drawable.content2));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 1. 加载布局，第三个参数必须为`false`，否则会加载两次布局并且抛出异常！！
        View view = inflater.inflate(R.layout.fragment_article_content, container, false);
        initData();
        initView(view);
        registerListener();
        return view;
    }

    public void registerListener(){

    }

    public void initView(View view){
        rv = view.findViewById(R.id.article_content_rv);
        rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

//        mDividerItemDecoration = new DividerItemDecoration(rv.getContext(),
//                new LinearLayoutManager(this.getActivity()).getOrientation());
//        rv.addItemDecoration(mDividerItemDecoration);
        rv.addItemDecoration(new MyDecoration());
        mAdapter = new ContentAdapter(this.getActivity(), this.mDataList);
        rv.setAdapter(mAdapter);
        //mAdapter.


    }

    static class MyDecoration extends RecyclerView.ItemDecoration{

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(5,5,5,5);
        }
    }
}
