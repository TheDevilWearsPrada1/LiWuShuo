package com.qf.liwushuo.presenter;

import com.qf.liwushuo.bean.DanpinItemsBean;
import com.qf.liwushuo.model.DanpinItemsModel;
import com.qf.liwushuo.ui.view.DanpinItemsView;

import rx.functions.Action1;

/**
 * Created by Administrator on 2016/10/27 0027.
 */

public class DanpinItemsPresenter extends MyBacePresenter<DanpinItemsView,DanpinItemsModel> {

    public void getDanpinItemsData(int id,int limit,int affset){
        model.loadDanpinItemsData(new Action1<DanpinItemsBean>() {
            @Override
            public void call(DanpinItemsBean danpinItemsBean) {
                getView().showView(danpinItemsBean);
            }
        }, id, limit, affset);
    }

    @Override
    public DanpinItemsModel createModle() {
        return new DanpinItemsModel();
    }
}
