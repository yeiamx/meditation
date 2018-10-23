package com.mrxia.meditation.profile;

import android.support.v7.widget.CardView;

public interface FavoriteCardAdapter {

    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}
