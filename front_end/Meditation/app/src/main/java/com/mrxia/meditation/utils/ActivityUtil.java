package com.mrxia.meditation.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.mrxia.meditation.bean.Notification;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ActivityUtil {
    public static void showToast(Context context, String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }

    public static int dip2px(float dip, Context context) {
        float v = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
        return (int) (v + 0.5f);
    }

    public static String[] strToIdArray(String str){
        return str.split(",");
    }
}
