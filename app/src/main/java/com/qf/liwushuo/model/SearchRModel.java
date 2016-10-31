package com.qf.liwushuo.model;

import com.qf.liwushuo.bean.SearchRBean;
import com.qf.liwushuo.service.MyService;
import com.qf.liwushuo.service.RetrofitInstance;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by Administrator on 2016/10/26 0026.
 */

public class SearchRModel implements MvpModel {
    public void loadData(Action1<SearchRBean> action1,String sort,int offset,String keyWord){
        Observable<SearchRBean> sR = RetrofitInstance.getService(MyService.class).getSearchInfo(sort,20,offset,keyWord);
        sR.observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread()).subscribe(action1);
    }
}
