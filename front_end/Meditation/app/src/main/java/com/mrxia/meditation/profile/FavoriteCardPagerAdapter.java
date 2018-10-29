package com.mrxia.meditation.profile;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mrxia.meditation.MyApplication;
import com.mrxia.meditation.R;
import com.mrxia.meditation.bean.Notification;
import com.mrxia.meditation.layout.music.MusicPlayActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavoriteCardPagerAdapter extends PagerAdapter implements FavoriteCardAdapter {

    private List<CardView> mViews;
    private List<Notification> mData;
    private float mBaseElevation;
    private Context context;

    public FavoriteCardPagerAdapter(Context context, List<Notification> mData) {
        this.context = context;
        this.mData = mData;
        mViews = new ArrayList<>();
        for (int i=0; i<mData.size(); i++) mViews.add(null);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.fragment_profile_favorite_adapter,container,false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = view.findViewById(R.id.cardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("path", mData.get(position).getResUrl());
                bundle.putString("imgUrl", mData.get(position).getImgUrl());
                bundle.putString("content", "");
                bundle.putString("id", mData.get(position).getId());
                Intent intent = new Intent();
                //绑定需要传递的参数
                intent.putExtras(bundle);
                intent.setClass(context, MusicPlayActivity.class);
                context.startActivity(intent);
            }
        });
        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(Notification item, View view) {
        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        TextView contentTextView = (TextView) view.findViewById(R.id.contentTextView);
        ImageView background = view.findViewById(R.id.iv_cover);
        titleTextView.setText(item.getTitle());
        contentTextView.setText(item.getType());
        Picasso
                .with(context)
                .load(item.getImgUrl())
                .into(background);
    }

}

