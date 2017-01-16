package com.atguigu.mobiletest.util;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by 万里洋 on 2017/1/13.
 */

public class CacheUtils {
    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("atguigu",context.MODE_PRIVATE);

        return sp.getString(key,"");
    }

    public static void putString(Context context, String key, String result) {
        SharedPreferences sp = context.getSharedPreferences("atguigu",context.MODE_PRIVATE);
        sp.edit().putString(key,result).commit();
    }
}
