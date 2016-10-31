package com.qf.liwushuo.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.mitnick.util.NetUtils;
import com.qf.liwushuo.application.MyApplication;
import com.qf.liwushuo.bean.TabBean;
import com.qf.liwushuo.model.HomeModel;
import com.qf.liwushuo.ui.view.HomeView;

import org.json.JSONObject;

import rx.functions.Action1;

/**
 * Created by Administrator on 2016/10/24 0024.
 */

public class HomePresenter extends MyBacePresenter<HomeView, HomeModel> {
    public void getData(Context context) {
        try {
            int networkState = NetUtils.getNetworkState(context);
            if (networkState == NetUtils.NETWORK_NONE) {
                JSONObject object = MyApplication.getaCache().getAsJSONObject("tabBean");
                TabBean tabBean = new Gson().fromJson(object.toString(), TabBean.class);
                if (tabBean != null) {
                    getView().showView(tabBean);
                }

            } else {
                model.londData(new Action1<TabBean>() {
                    @Override
                    public void call(TabBean tabBean) {
                        //保存数据到内存
                        MyApplication.getaCache().put("tabBean", new Gson().toJson(tabBean));
                        getView().showView(tabBean);
                    }
                }, context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public HomeModel createModle() {
        return new HomeModel();
    }
}
