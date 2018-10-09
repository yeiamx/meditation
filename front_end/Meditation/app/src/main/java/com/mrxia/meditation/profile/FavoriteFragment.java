package com.mrxia.meditation.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mrxia.meditation.R;

public class FavoriteFragment extends Fragment {
    public static FavoriteFragment newInstance() {
        FavoriteFragment frag = new FavoriteFragment();
        return frag;
    }

    private CardGroupView mCardGroupView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 1. 加载布局，第三个参数必须为`false`，否则会加载两次布局并且抛出异常！！
        View view = inflater.inflate(R.layout.fragment_profile_favorite, container, false);
        initView(view);
        registerListener();
        initEvent();
        addCard();
        return view;
    }

    public void registerListener(){

    }

    public void initView(View view){
        mCardGroupView = view.findViewById(R.id.card);
        mCardGroupView.setLoadSize(3);
        mCardGroupView.setMargin(0.15);
    }

    private void initEvent() {
        mCardGroupView.setLoadMoreListener(new CardGroupView.LoadMore() {
            @Override
            public void load() {
                mCardGroupView.addView(getCard());
                mCardGroupView.addView(getCard());
                mCardGroupView.addView(getCard());
                mCardGroupView.addView(getCard());
                mCardGroupView.addView(getCard());
                mCardGroupView.addView(getCard());
                mCardGroupView.addView(getCard());
                mCardGroupView.addView(getCard());
            }
        });
        mCardGroupView.setLeftOrRightListener(new CardGroupView.LeftOrRight() {
            @Override
            public void leftOrRight(boolean left) {
                if (left) {
                    Toast.makeText(getContext(),"向左划喜欢",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "向右滑不喜欢！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addCard() {
        mCardGroupView.addView(getCard());
        mCardGroupView.addView(getCard());
        mCardGroupView.addView(getCard());
        mCardGroupView.addView(getCard());
        mCardGroupView.addView(getCard());
        mCardGroupView.addView(getCard());
        mCardGroupView.addView(getCard());
        mCardGroupView.addView(getCard());
    }

    private View getCard() {
        View card = LayoutInflater.from(getContext()).inflate(R.layout.layout_card, null);
        View view = card.findViewById(R.id.remove);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCardGroupView.removeTopCard(true);
            }
        });
        return card;
    }

}
