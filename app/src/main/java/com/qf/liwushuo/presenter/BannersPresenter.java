package com.qf.liwushuo.presenter;

import android.content.Context;
import android.util.Log;

import com.qf.liwushuo.bean.BannerBean;
import com.qf.liwushuo.bean.TabBean;
import com.qf.liwushuo.model.BannersModel;
import com.qf.liwushuo.ui.view.BannersView;

import rx.functions.Action1;

import static android.R.attr.banner;

/**
 * Created by Administrator on 2016/10/24 0024.
 */

public class BannersPresenter extends MyBacePresenter<BannersView,BannersModel> {
    public void getData(Context context){
        model.londData(new Action1<BannerBean>() {
            @Override
            public void call(BannerBean bannerBean) {
                try {
                    getView().getData(bannerBean);
                    Log.e("自定义控件", "call() called with: bannerBean = [" + bannerBean + "]");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },context);
    }
    @Override
    public BannersModel createModle() {
        return new BannersModel();
    }
}
