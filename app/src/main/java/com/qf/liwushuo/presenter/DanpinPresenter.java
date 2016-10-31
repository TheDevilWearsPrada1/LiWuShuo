package com.qf.liwushuo.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.mitnick.util.NetUtils;
import com.qf.liwushuo.application.MyApplication;
import com.qf.liwushuo.bean.DanpinBean;
import com.qf.liwushuo.bean.HomeItemBean;
import com.qf.liwushuo.model.DanpinModel;
import com.qf.liwushuo.ui.view.DanpinView;

import org.json.JSONObject;

import rx.functions.Action1;

/**
 * Created by Administrator on 2016/10/26 0026.
 */

public class DanpinPresenter extends MyBacePresenter<DanpinView, DanpinModel> {

    public void getDanpinData(Context context) {

        try {
            int networkState = NetUtils.getNetworkState(context);
            if (networkState == NetUtils.NETWORK_NONE) {
                JSONObject object = MyApplication.getaCache().getAsJSONObject("danpinBean");
                DanpinBean danpinBean = new Gson().fromJson(object.toString(), DanpinBean.class);
                if (danpinBean != null) {
                    getView().getDanpinData(danpinBean);
                }
            } else {
                model.loadDanpinData(new Action1<DanpinBean>() {
                    @Override
                    public void call(DanpinBean danpinBean) {
                        if (danpinBean != null) {
                            getView().getDanpinData(danpinBean);
                            MyApplication.getaCache().put("danpinBean", new Gson().toJson(danpinBean));
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


      /* model.loadDanpinData(new Action1<DanpinBean>() {
            @Override
            public void call(DanpinBean danpinBean) {
                getView().getDanpinData(danpinBean);
            }
        });*/
    }

    @Override
    public DanpinModel createModle() {
        return new DanpinModel();
    }
}
