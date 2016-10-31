package com.qf.liwushuo.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.mitnick.util.NetUtils;
import com.qf.liwushuo.application.MyApplication;
import com.qf.liwushuo.bean.HomeItemBean;
import com.qf.liwushuo.bean.TabBean;
import com.qf.liwushuo.model.HomeItemModel;
import com.qf.liwushuo.ui.view.HomeFragmentView;

import org.json.JSONObject;

import ASimpleCache.org.afinal.simplecache.ACache;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/10/24 0024.
 */

public class
HomeFragmentPresenter extends MyBacePresenter<HomeFragmentView, HomeItemModel> {
    public void getData(final int id, int offset, Context context) {
        try {
            int networkState = NetUtils.getNetworkState(context);
            if (networkState == NetUtils.NETWORK_NONE) {
                JSONObject object = MyApplication.getaCache().getAsJSONObject("homeItemBean" + id);
                HomeItemBean homeItemBean = new Gson().fromJson(object.toString(), HomeItemBean.class);
                if (homeItemBean != null) {
                    getView().showView(homeItemBean);
                }
            } else {
                model.londData(new Action1<HomeItemBean>() {
                    @Override
                    public void call(HomeItemBean homeItemBean) {
                        if (homeItemBean != null) {
                            getView().showView(homeItemBean);
                            MyApplication.getaCache().put("homeItemBean" + id, new Gson().toJson(homeItemBean));
                        }

                    }
                }, id, offset, context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public HomeItemModel createModle() {
        return new HomeItemModel();
    }
}
