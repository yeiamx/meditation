package com.mrxia.meditation.layout;

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
import com.mrxia.meditation.R;
import com.mrxia.meditation.layout.article.ArticleFragment;
import com.mrxia.meditation.layout.home.HomeFragment;
import com.mrxia.meditation.layout.meditation.MeditationFragment;
import com.mrxia.meditation.layout.music.MusicFrament;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{

    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragments = new ArrayList<>();
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
        navigationBar.setBarBackgroundColor(R.color.transparent_background);

        navigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Home")
                .setActiveColorResource(R.color.bottom_active))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Music")
                        .setActiveColorResource(R.color.bottom_active))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Meditation")
                        .setActiveColorResource(R.color.bottom_active))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Article")
                        .setActiveColorResource(R.color.bottom_active))
                .setFirstSelectedPosition(0)//默认选择索引为0的菜单
                .initialise();//对导航进行重绘

        fragments = getFragments();
        setDefaultFragment();
        navigationBar.setTabSelectedListener(this);

    }

    /**
     * 设置默认显示的fragment
     */
    private void setDefaultFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.id_content, HomeFragment.newInstance());
        transaction.commit();
    }

    /**
     * 获取fragment
     *
     * @return fragment列表
     */
    private List<Fragment> getFragments() {

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance());
        fragments.add(MusicFrament.newInstance());
        fragments.add(MeditationFragment.newInstance());
        fragments.add(ArticleFragment.newInstance());
        return fragments;
    }

    @Override
    public void onTabSelected(int position) {

        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.replace(R.id.id_content, fragment);
                ft.commitAllowingStateLoss();//选择性的提交，和commit有一定的区别，他不保证数据完整传输
            }
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

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
