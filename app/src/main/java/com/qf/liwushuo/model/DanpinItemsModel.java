package com.qf.liwushuo.model;

import com.qf.liwushuo.bean.DanpinItemsBean;
import com.qf.liwushuo.service.MyService;
import com.qf.liwushuo.service.RetrofitInstance;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/27 0027.
 */

public class DanpinItemsModel implements MvpModel {
    public void loadDanpinItemsData(Action1<DanpinItemsBean> action1, int id, int limit, int offset) {
        Observable<DanpinItemsBean> danpinItemsData = RetrofitInstance.getService(MyService.class).getDanpinItemsData(id, 20, 0);
        danpinItemsData.observeOn(AndroidSchedulers.mainThread())//
                .subscribeOn(Schedulers.newThread())//
                .subscribe(action1);

    }
}
