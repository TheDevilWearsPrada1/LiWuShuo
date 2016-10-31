package com.qf.liwushuo.presenter;

import com.qf.liwushuo.bean.HomeItemInfoBean;
import com.qf.liwushuo.model.HomeItemInfoModel;
import com.qf.liwushuo.ui.view.HomeItemInfoView;

import rx.functions.Action1;

/**
 * Created by Administrator on 2016/10/26 0026.
 */

public class HomeItemInfoPresenter extends MyBacePresenter<HomeItemInfoView,HomeItemInfoModel>{
    public void getData(int id){
        model.londData(new Action1<HomeItemInfoBean>() {
            @Override
            public void call(HomeItemInfoBean homeItemInfoBean) {
                getView().getInfo(homeItemInfoBean);
            }
        },id);
    }
    @Override
    public HomeItemInfoModel createModle() {
        return new HomeItemInfoModel();
    }
}
