package com.qf.liwushuo.ui.fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.ToxicBakery.viewpager.transforms.BackgroundToForegroundTransformer;
import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.qf.liwushuo.R;
import com.qf.liwushuo.bean.BannerBean;
import com.qf.liwushuo.presenter.BannersPresenter;
import com.qf.liwushuo.ui.view.BannersView;
import com.qf.liwushuo.utils.NetworkImageHolderView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/24 0024.
 */

public class SelectedFragmet extends BaseFragment implements BannersView{
    ConvenientBanner mConvenientBanner;//顶部广告栏控件
    List<BannerBean.DataBean.BannersBean> imageDatas;
    BannersPresenter presenter=new BannersPresenter();
    @Override
    public void setHolder() {
        presenter.getData(getContext());
        presenter.attach(this);
        View view=LayoutInflater.from(getContext()).inflate(R.layout.recy_item,null);
        mConvenientBanner = (ConvenientBanner) view.findViewById(R.id.cb);
        mConvenientBanner.setMinimumHeight(400);
    }

    //设置顶部图片轮播
    private void setupConvenientBanner() {
        mConvenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, imageDatas)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                //设置点击事件
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                    }
                })
        ;
        try {
            //设置广告轮播的样式
            Class cls = Class.forName("com.ToxicBakery.viewpager.transforms." + BackgroundToForegroundTransformer.class.getSimpleName());
            ABaseTransformer transforemer = (ABaseTransformer) cls.newInstance();
            mConvenientBanner.getViewPager().setPageTransformer(true, transforemer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将广告轮播添加到ListView的第一行
        //lv.addHeaderView(mConvenientBanner);
        RecyclerViewHeader header = RecyclerViewHeader.fromXml(getContext(), R.layout.recy_item);
        header.attachTo(recycler);
    }
    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        mConvenientBanner.stopTurning();
    }

/*
    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        mConvenientBanner.startTurning(3000);
    }*/

    @Override
    public void getData(BannerBean bannerBean) {
        imageDatas=new ArrayList<>();
        Log.e("自定义控件", "getData() called with: bannerBean = [" + bannerBean.getData().getBanners().size() + "]");
        imageDatas.addAll(bannerBean.getData().getBanners());
        setupConvenientBanner();

    }
}
