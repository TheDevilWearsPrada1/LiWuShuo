package com.qf.liwushuo.model;

import android.content.Context;

import com.qf.liwushuo.bean.HomeItemBean;
import com.qf.liwushuo.bean.TabBean;
import com.qf.liwushuo.service.MyService;
import com.qf.liwushuo.service.RetrofitInstance;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.qf.liwushuo.R.id.tab;

/**
 * Created by Administrator on 2016/10/24 0024.
 */

public class HomeItemModel implements MvpModel {
    public void londData(Action1<HomeItemBean> action1, int id, int offset, Context context) {
        Observable<HomeItemBean> item = RetrofitInstance
                .getService(MyService.class)
                .getData(id, 2, 1, 2, 20, offset);
        item.observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .subscribe(action1);
    }
}
