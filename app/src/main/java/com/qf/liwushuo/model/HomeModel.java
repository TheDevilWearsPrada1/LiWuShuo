package com.qf.liwushuo.model;

import android.content.Context;

import com.qf.liwushuo.bean.TabBean;
import com.qf.liwushuo.service.MyService;
import com.qf.liwushuo.service.RetrofitInstance;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/24 0024.
 */

public class HomeModel implements MvpModel {
    public void londData(Action1<TabBean> action1,Context context){
        Observable<TabBean> tab = RetrofitInstance
                .getService(MyService.class)
                .getTab(1, 2);
        tab.observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread()).subscribe(action1);
    }
}
