package com.mrxia.meditation.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mrxia.meditation.R;

public class FavoriteFragment extends Fragment {
    public static FavoriteFragment newInstance() {
        FavoriteFragment frag = new FavoriteFragment();
        return frag;
    }

    private ViewPager mViewPager;

    private FavoriteCardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;

    private boolean mShowingFragments = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 1. 加载布局，第三个参数必须为`false`，否则会加载两次布局并且抛出异常！！
        View view = inflater.inflate(R.layout.fragment_profile_favorite, container, false);
        mViewPager = view.findViewById(R.id.viewPager);

        mCardAdapter = new FavoriteCardPagerAdapter();
        mCardAdapter.addCardItem(new FavoriteCardItem(R.string.title_1, R.string.text_1));
        mCardAdapter.addCardItem(new FavoriteCardItem(R.string.title_2, R.string.text_1));
        mCardAdapter.addCardItem(new FavoriteCardItem(R.string.title_3, R.string.text_1));
        mCardAdapter.addCardItem(new FavoriteCardItem(R.string.title_4, R.string.text_1));

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);


        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
        return view;
    }

}
