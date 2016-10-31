package com.qf.liwushuo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.qf.liwushuo.bean.DanpinItemsBean;
import com.qf.liwushuo.presenter.DanpinItemsPresenter;
import com.qf.liwushuo.ui.view.DanpinItemsView;

/**
 * Created by Administrator on 2016/10/27 0027.
 */

public class DanpinItemsFragment extends MySuperFragment<DanpinItemsPresenter> implements DanpinItemsView {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.getDanpinItemsData(7,0,0);
    }

    @Override
    public void showView(DanpinItemsBean danpinItemsBean) {
        Log.e("zq",danpinItemsBean.getData().getItems().get(0).getName());
    }

    @Override
    public DanpinItemsPresenter creatersenter() {
        return new DanpinItemsPresenter();
    }
}
