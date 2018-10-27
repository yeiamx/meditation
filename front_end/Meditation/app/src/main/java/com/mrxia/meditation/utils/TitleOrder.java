package com.mrxia.meditation.utils;

import com.mrxia.meditation.bean.Notification;

import java.util.Comparator;

public class TitleOrder implements Comparator<Notification> {
    @Override
    public int compare(Notification lhs, Notification rhs) {
        // TODO Auto-generated method stub
        return lhs.getTitle().compareTo(rhs.getTitle());
    }

}