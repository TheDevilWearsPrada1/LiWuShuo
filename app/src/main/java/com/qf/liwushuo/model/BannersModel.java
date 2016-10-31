package com.qf.liwushuo.model;

import android.content.Context;

import com.qf.liwushuo.bean.BannerBean;
import com.qf.liwushuo.service.MyService;
import com.qf.liwushuo.service.RetrofitInstance;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;



/**
 * Created by Administrator on 2016/10/24 0024.
 */

public class BannersModel implements MvpModel {
    public void londData(Action1<BannerBean> action1, Context context){
        Observable<BannerBean> tab = RetrofitInstance.getService(MyService.class).getBannersData();
        tab.observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread()).subscribe(action1);
    }
}
