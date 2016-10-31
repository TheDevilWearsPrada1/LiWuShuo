package com.qf.liwushuo.model;

import com.qf.liwushuo.bean.GonglueBean;
import com.qf.liwushuo.bean.GonglueHeaderBean;
import com.qf.liwushuo.bean.LanmuBean;
import com.qf.liwushuo.service.MyService;
import com.qf.liwushuo.service.RetrofitInstance;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/24 0024.
 */

public class StrategyModel implements MvpModel {
    public void loadData(Action1<GonglueBean> action1) {
        Observable<GonglueBean> gonglueData = RetrofitInstance.getService(MyService.class).getGonglueData();
        gonglueData.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(action1);
    }

    public void loadGonglueHeaderData(Action1<GonglueHeaderBean> action1) {
        Observable<GonglueHeaderBean> gonglueHeaderData = RetrofitInstance.getService(MyService.class).getGonglueHeaderData();
        gonglueHeaderData.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(action1);
    }
}
