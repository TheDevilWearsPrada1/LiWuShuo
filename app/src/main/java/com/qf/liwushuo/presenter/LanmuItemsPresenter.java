package com.qf.liwushuo.presenter;

import com.qf.liwushuo.bean.LanmuItemsBean;
import com.qf.liwushuo.model.LanmuItemsModel;
import com.qf.liwushuo.ui.view.LanmuItemsView;

import rx.functions.Action1;

/**
 * Created by Administrator on 2016/10/28 0028.
 */

public class LanmuItemsPresenter extends MyBacePresenter<LanmuItemsView,LanmuItemsModel> {

    public void getLanmuItemsData(int id,int limit,int offset){
        model.loadLanmuItemsData(new Action1<LanmuItemsBean>() {
            @Override
            public void call(LanmuItemsBean lanmuItemsBean) {
                getView().showView(lanmuItemsBean);
            }
        },id,limit,offset);
    }

    @Override
    public LanmuItemsModel createModle() {
        return new LanmuItemsModel();
    }
}
