package com.qf.liwushuo.ui.view;

import com.qf.liwushuo.bean.GonglueBean;
import com.qf.liwushuo.bean.GonglueHeaderBean;

/**
 * Created by Administrator on 2016/10/24 0024.
 */

public interface StrategyView extends MvpView {
    void showView(GonglueBean gonglueBean);
    void showHeaderView(GonglueHeaderBean gonglueHeaderBean);
}
