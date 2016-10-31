package com.qf.liwushuo.model;


import com.qf.liwushuo.bean.HomeItemInfoBean;
import com.qf.liwushuo.service.MyService;
import com.qf.liwushuo.service.RetrofitInstance;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;



/**
 * Created by Administrator on 2016/10/24 0024.
 */

public class HomeItemInfoModel implements MvpModel {
    public void londData(Action1<HomeItemInfoBean> action1, int id){
        Observable<HomeItemInfoBean> info = RetrofitInstance.getService(MyService.class).getInfo(id);
        info.observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread()).subscribe(action1);
    }
}
