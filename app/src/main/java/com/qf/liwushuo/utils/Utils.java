package com.qf.liwushuo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;




/**
 * Created by Administrator on 2016/10/25 0025.
 */

public class Utils {
    /**
     * 判断网络是否可用
     *
     * @param context Context对象
     */
    public static Boolean isNetworkReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }

}

