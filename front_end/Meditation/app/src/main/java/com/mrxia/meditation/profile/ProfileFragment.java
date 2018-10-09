package com.mrxia.meditation.profile;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mrxia.meditation.R;


import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    private TabLayout mTabLayout;
    private TextView title;
    private ViewPager viewPager;
    private List<String> titles = new ArrayList<>();
    public static ProfileFragment newInstance() {
        ProfileFragment frag = new ProfileFragment();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 1. 加载布局，第三个参数必须为`false`，否则会加载两次布局并且抛出异常！！
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
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
        title = view.findViewById(R.id.profile_title);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/segoe_script.ttf");
        title.setTypeface(typeface);
        title.setText("Profile");

        titles.add("Dashboard");titles.add("Favorite");
        mTabLayout = view.findViewById(R.id.profile_tablayout);
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));

        viewPager = view.findViewById(R.id.profile_viewpager);
        mTabLayout.setupWithViewPager(viewPager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(DashboardFragment.newInstance());
        fragments.add(FavoriteFragment.newInstance());
        ProfileFragmentAdapter adapter = new ProfileFragmentAdapter(getChildFragmentManager(),fragments, titles);
        viewPager.setAdapter(adapter);

    }
}
