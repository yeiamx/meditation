package com.mrxia.meditation.layout;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.mrxia.meditation.MyApplication;
import com.mrxia.meditation.R;
import com.mrxia.meditation.layout.home.HomeFragment;
import com.mrxia.meditation.layout.meditation.MeditationFragment;
import com.mrxia.meditation.layout.music.MusicFrament;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{

    private List<Fragment> fragments;
    private List<String> fragmentTags;
    private Fragment mContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragments();
        initView();
        setHalfTransparent();
        setFitSystemWindow(false);
    }

    /**
     * 创建视图
     */
    private void initView() {
        BottomNavigationBar navigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        navigationBar.setMode(BottomNavigationBar.MODE_FIXED);

        navigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        navigationBar.setBarBackgroundColor(R.color.transparent);

        navigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Home")
                .setActiveColorResource(R.color.bottom_active))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Music")
                        .setActiveColorResource(R.color.bottom_active))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Meditation")
                        .setActiveColorResource(R.color.bottom_active))
                .setFirstSelectedPosition(0)//默认选择索引为0的菜单
                .initialise();//对导航进行重绘

        navigationBar.setTabSelectedListener(this);

    }

    /**
     * 设置默认显示的fragment
     */
    private void setDefaultFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.id_content, fragments.get(0), fragmentTags.get(0));
        transaction.commit();
        mContent = fragments.get(0);
    }

    /**
     * 获取fragment
     *
     * @return fragment列表
     */
    private void getFragments() {
        fragments = new ArrayList<>();
        fragmentTags = new ArrayList<>();
        fragments.add(HomeFragment.newInstance());
        fragmentTags.add("home");
        fragments.add(MusicFrament.newInstance());
        fragmentTags.add("music");
        fragments.add(MeditationFragment.newInstance());
        fragmentTags.add("meditation");

        setDefaultFragment();
    }

    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            if (position < fragments.size() && mContent!=fragments.get(position)) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                if (!fragments.get(position).isAdded()) {
                    ft.hide(mContent).add(R.id.id_content, fragments.get(position), fragmentTags.get(position)).commit();
                } else {
                    ft.hide(mContent).show(fragments.get(position)).commit();
                }
            }
        mContent = fragments.get(position);
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data.getStringExtra("class").equals("setting")) {
                HomeFragment fragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("home");
                //通过id或者tag可以从manager获取fragment对象，
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    /**
     * 半透明状态栏
     */
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

    /**
     * 如果需要内容紧贴着StatusBar
     * 应该在对应的xml布局文件中，设置根布局fitsSystemWindows=true。
     */
    private View contentViewGroup;

    protected void setFitSystemWindow(boolean fitSystemWindow) {
        if (contentViewGroup == null) {
            contentViewGroup = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        }
        contentViewGroup.setFitsSystemWindows(fitSystemWindow);
    }


}
