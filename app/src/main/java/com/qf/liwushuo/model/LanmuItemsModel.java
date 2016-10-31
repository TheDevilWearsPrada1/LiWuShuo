package com.qf.liwushuo.model;

import com.qf.liwushuo.bean.LanmuItemsBean;
import com.qf.liwushuo.service.MyService;
import com.qf.liwushuo.service.RetrofitInstance;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/28 0028.
 */

public class LanmuItemsModel implements MvpModel {
    public void loadLanmuItemsData(Action1<LanmuItemsBean> action1, int id, int limit, int affset) {
        Observable<LanmuItemsBean> lanmuItemsData = RetrofitInstance.getService(MyService.class).getLanmuItemsData(id, 20, 0);
        lanmuItemsData.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(action1);
    }
}
