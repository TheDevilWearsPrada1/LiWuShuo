package com.qf.liwushuo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import com.qf.liwushuo.R;
import com.qf.liwushuo.adapter.LanmuItemsAdapter;
import com.qf.liwushuo.bean.LanmuItemsBean;
import com.qf.liwushuo.presenter.LanmuItemsPresenter;
import com.qf.liwushuo.ui.view.LanmuItemsView;
import com.qf.liwushuo.ui.view.SmoothListView;
import com.qf.liwushuo.utils.ColorUtil;
import com.qf.liwushuo.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/28 0028.
 */

public class LanmuItemsActivity extends AppCompatActivity implements LanmuItemsView, SmoothListView.ISmoothListViewListener {
    LanmuItemsPresenter presenter = new LanmuItemsPresenter();
    int id;
    @BindView(R.id.listView)
    SmoothListView lanmuListView;
    List<LanmuItemsBean.DataBean.PostsBean> datas;
    LanmuItemsAdapter lanmuItemsAdapter;
    @BindView(R.id.rl_bar)
    RelativeLayout rlBar;

    private Context mContext;
    private View itemHeaderAdView; // 从ListView获取的广告子View
    private boolean isScrollIdle = true; // ListView是否在滑动
    private int adViewHeight = 0; // 广告视图的高度
    private int adViewTopSpace; // 广告视图距离顶部的距离
    int titleHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lanmuitems);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        presenter.attach(this);
        presenter.getLanmuItemsData(id, 0, 0);
        init();
    }

    private void init() {
        //头部布局
        View view= LayoutInflater.from(this).inflate(R.layout.item,lanmuListView,false);
        lanmuListView.addHeaderView(view);
        datas = new ArrayList<>();
        lanmuItemsAdapter = new LanmuItemsAdapter(datas);
        lanmuListView.setAdapter(lanmuItemsAdapter);
        mContext=this;
        lanmuListView.setRefreshEnable(false);
        lanmuListView.setLoadMoreEnable(false);
        lanmuListView.setSmoothListViewListener(this);

        lanmuListView.setOnScrollListener(new SmoothListView.OnSmoothScrollListener() {
            @Override
            public void onSmoothScrolling(View view) {
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                isScrollIdle = (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScrollIdle && adViewTopSpace < 0) return;

                // 获取广告头部View、自身的高度、距离顶部的高度
                if (itemHeaderAdView == null) {
                    itemHeaderAdView = lanmuListView.getChildAt(1 - firstVisibleItem);
                    Log.e("自定义控件", "" + (firstVisibleItem));
                }
                if (itemHeaderAdView != null) {
                    adViewTopSpace = DensityUtil.px2dip(mContext, itemHeaderAdView.getTop());
                    adViewHeight = DensityUtil.px2dip(mContext, itemHeaderAdView.getHeight());
                    //获得标题栏的高度
                    titleHeight = DensityUtil.px2dip(mContext, rlBar.getHeight());
                }
                // 处理标题栏颜色渐变
                handleTitleBarColorEvaluate();
            }
        });
    }
    // 处理标题栏颜色渐变
    private void handleTitleBarColorEvaluate() {
        float fraction;
        if (adViewTopSpace > 0) {
            fraction = 1f - adViewTopSpace * 1f / 60;
            if (fraction < 0f) fraction = 0f;
            rlBar.setAlpha(fraction);
            Log.e("自定义控件", "handleTitleBarColorEvaluate()"+fraction);
            return;
        }

        float space = Math.abs(adViewTopSpace) * 1f;
        //Log.e("自定义控件", "handleTitleBarColorEvaluate() called" + space);
        fraction = space / (adViewHeight-titleHeight);
        if (fraction < 0f) fraction = 0f;
        if (lanmuListView.getFirstVisiblePosition() > 1) {
            fraction = 1f;
        }
        rlBar.setAlpha(1f);


        if (fraction >= 1f) {
            //rlBar.setAlpha(1);
            rlBar.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
        } else {
            rlBar.setBackgroundColor(ColorUtil.getNewColorByStartEndColor(mContext, fraction, R.color.transparent, R.color.orange));
            //rlBar.setAlpha(1);
        }
    }

    @Override
    public void showView(LanmuItemsBean lanmuItemsBean) {
        //Log.e("zq", lanmuItemsBean.getData().getId() + "");
        datas.addAll(lanmuItemsBean.getData().getPosts());
        lanmuItemsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        //下拉刷新加载
    }

    @Override
    public void onLoadMore() {
        //上拉加载
    }
}
