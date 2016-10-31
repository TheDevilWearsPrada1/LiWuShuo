package com.qf.liwushuo.application;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import java.io.File;

import ASimpleCache.org.afinal.simplecache.ACache;


/**
 * Created by Administrator on 2016/10/27 0027.
 */

public class MyApplication extends Application {

    static ACache aCache;
    @Override
    public void onCreate() {
        super.onCreate();
        aCache =ACache.get(this);
    }
    public static ACache getaCache() {
        return aCache;
    }
}
