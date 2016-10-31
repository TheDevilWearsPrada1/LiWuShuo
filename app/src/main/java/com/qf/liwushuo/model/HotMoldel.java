package com.qf.liwushuo.model;

import com.qf.liwushuo.bean.BannerBean;
import com.qf.liwushuo.bean.SearchHotWordBean;
import com.qf.liwushuo.service.MyService;
import com.qf.liwushuo.service.RetrofitInstance;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by Administrator on 2016/10/25 0025.
 */

public class HotMoldel implements MvpModel {
    public void loadData(Action1<SearchHotWordBean> action1){
        Observable<SearchHotWordBean> hotWord = RetrofitInstance.getService(MyService.class).getHotWord();
        hotWord.observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread()).subscribe(action1);
    }

}
