package com.qf.liwushuo.model;

import com.qf.liwushuo.bean.SearchWordBean;
import com.qf.liwushuo.service.MyService;
import com.qf.liwushuo.service.RetrofitInstance;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by Administrator on 2016/10/26 0026.
 */

public class SearchWoedModel implements MvpModel {
    public void loadData(Action1<SearchWordBean> action1,String keyWord){
        Observable<SearchWordBean> sR = RetrofitInstance.getService(MyService.class).getWord(keyWord);
        sR.observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread()).subscribe(action1);
    }
}
