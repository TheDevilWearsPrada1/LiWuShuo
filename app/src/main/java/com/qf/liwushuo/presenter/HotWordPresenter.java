package com.qf.liwushuo.presenter;

import android.content.Context;
import android.util.Log;

import com.mitnick.util.NetUtils;
import com.qf.liwushuo.bean.BannerBean;
import com.qf.liwushuo.bean.SearchHotWordBean;
import com.qf.liwushuo.model.BannersModel;
import com.qf.liwushuo.model.HotMoldel;
import com.qf.liwushuo.ui.view.BannersView;
import com.qf.liwushuo.ui.view.HotView;

import rx.functions.Action1;

/**
 * Created by Administrator on 2016/10/24 0024.
 */

public class HotWordPresenter extends MyBacePresenter<HotView, HotMoldel> {
    public void getData(Context context) {
        try {
            int networkState = NetUtils.getNetworkState(context);
            if (networkState == NetUtils.NETWORK_NONE) {
                return;
            } else {
                model.loadData(new Action1<SearchHotWordBean>() {
                    @Override
                    public void call(SearchHotWordBean searchHotWordBean) {
                        getView().showView(searchHotWordBean);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public HotMoldel createModle() {
        return new HotMoldel();
    }
}
