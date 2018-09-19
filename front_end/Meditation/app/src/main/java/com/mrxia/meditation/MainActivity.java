package com.mrxia.meditation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{

    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //我这里用到了谷歌官方的一个工具叫Databinding，又不熟悉的可以自己去百度
        setContentView(R.layout.activity_main);
        fragments = new ArrayList<>();
        initView();
    }

    @Override
    public void setTitle(CharSequence title) {
        //设置标题
        super.setTitle(title);
    }

    /**
     * 创建视图
     */
    private void initView() {

        //得到BottomNavigationBar控件
        BottomNavigationBar navigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        //设置BottomNavigationBar的模式，会在下面详细讲解
        navigationBar.setMode(BottomNavigationBar.MODE_DEFAULT);
        //设置BottomNavigationBar的背景风格，后面详细讲解
        navigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        //我这里增加了3个Fragment
        //BottomNavigationItem("底部导航ico","底部导航名字")
        navigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "首页")
                .setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "发现")
                        .setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "我的")
                        .setActiveColorResource(R.color.colorPrimary))
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
        transaction.replace(R.id.id_content, Fragment1.newInstance());
        transaction.commit();
    }

    /**
     * 获取fragment
     *
     * @return fragment列表
     */
    private List<Fragment> getFragments() {

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(Fragment1.newInstance());
        fragments.add(Fragment2.newInstance());
        fragments.add(Fragment3.newInstance());
        return fragments;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle("测试Demo");
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
}
