package com.qf.liwushuo.model;

import com.qf.liwushuo.bean.DanpinBean;
import com.qf.liwushuo.service.MyService;
import com.qf.liwushuo.service.RetrofitInstance;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/26 0026.
 */

public class DanpinModel implements MvpModel {
    public void loadDanpinData(Action1<DanpinBean> action1) {
        Observable<DanpinBean> danpinData = RetrofitInstance.getService(MyService.class).getDanpinData();
        danpinData.observeOn(AndroidSchedulers.mainThread())//
                .subscribeOn(Schedulers.newThread())//
                .subscribe(action1);
    }
}
