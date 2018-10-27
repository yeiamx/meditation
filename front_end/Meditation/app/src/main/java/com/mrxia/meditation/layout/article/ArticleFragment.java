package com.mrxia.meditation.layout.article;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mrxia.meditation.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/19.
 */
public class ArticleFragment extends Fragment{
    private TabLayout mTabLayout;
    private TextView title;
    private ViewPager viewPager;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments;
    public static ArticleFragment newInstance() {
        ArticleFragment frag = new ArticleFragment();
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 1. 加载布局，第三个参数必须为`false`，否则会加载两次布局并且抛出异常！！
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        initView(view);
        registerListener();
        return view;
    }

    public void registerListener(){
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void initView(View view){
        title = view.findViewById(R.id.article_title);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/segoe_script.ttf");
        title.setTypeface(typeface);
        title.setText("Meditation");

        titles.add("文章");titles.add("短日记");titles.add("Q&A");
        mTabLayout = view.findViewById(R.id.article_tablayout);
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));

        viewPager = view.findViewById(R.id.article_viewpager);
        mTabLayout.setupWithViewPager(viewPager);
        fragments = new ArrayList<>();
        fragments.add(ContentFragment.newInstance());
        fragments.add(JournalFragment.newInstance());
        fragments.add(QaFragment.newInstance());
        ArticleFragmentAdapter adapter = new ArticleFragmentAdapter(getChildFragmentManager(),fragments, titles);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("xxchu", "into article");
        if(resultCode==4){
            fragments.get(1).onActivityResult(requestCode, resultCode, data);
        }
    }
}
