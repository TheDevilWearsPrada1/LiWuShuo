package com.qf.liwushuo.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.mitnick.util.NetUtils;
import com.qf.liwushuo.application.MyApplication;
import com.qf.liwushuo.bean.GonglueBean;
import com.qf.liwushuo.bean.GonglueHeaderBean;
import com.qf.liwushuo.bean.HomeItemBean;
import com.qf.liwushuo.model.StrategyModel;
import com.qf.liwushuo.ui.view.StrategyView;

import org.json.JSONObject;

import rx.functions.Action1;

/**
 * Created by Administrator on 2016/10/24 0024.
 */

public class StrategyPresenter extends MyBacePresenter<StrategyView,StrategyModel> {

    public void getData(Context context){
        try {
            int networkState = NetUtils.getNetworkState(context);
            if (networkState == NetUtils.NETWORK_NONE) {
                JSONObject object = MyApplication.getaCache().getAsJSONObject("gonglueBean");
                GonglueBean gonglueBean = new Gson().fromJson(object.toString(), GonglueBean.class);
                if (gonglueBean != null) {
                    getView().showView(gonglueBean);
                }
                JSONObject object1 = MyApplication.getaCache().getAsJSONObject("gonglueHeaderBean");
                GonglueHeaderBean gonglueHeaderBean = new Gson().fromJson(object1.toString(), GonglueHeaderBean.class);
                if (gonglueHeaderBean != null) {
                    getView().showHeaderView(gonglueHeaderBean);
                }
            } else {
                model.loadData(new Action1<GonglueBean>() {
                    @Override
                    public void call(GonglueBean gonglueBean) {
                        getView().showView(gonglueBean);
                        MyApplication.getaCache().put("gonglueBean" , new Gson().toJson(gonglueBean));
                    }
                });
                model.loadGonglueHeaderData(new Action1<GonglueHeaderBean>() {
                    @Override
                    public void call(GonglueHeaderBean gonglueHeaderBean) {
                        getView().showHeaderView(gonglueHeaderBean);
                        MyApplication.getaCache().put("gonglueHeaderBean", new Gson().toJson(gonglueHeaderBean));
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public StrategyModel createModle() {
        return new StrategyModel();
    }
}
