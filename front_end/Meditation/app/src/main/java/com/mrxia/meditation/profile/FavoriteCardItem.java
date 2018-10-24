package com.mrxia.meditation.profile;

public class FavoriteCardItem {

    private int mTextResource;
    private int mTitleResource;

    public FavoriteCardItem(int title, int text) {
        mTitleResource = title;
        mTextResource = text;
    }

    public int getText() {
        return mTextResource;
    }

    public int getTitle() {
        return mTitleResource;
    }
}
