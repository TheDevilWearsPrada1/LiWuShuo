package com.qf.liwushuo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qf.liwushuo.R;
import com.qf.liwushuo.adapter.HomeViewPagerAdapter;
import com.qf.liwushuo.bean.TabBean;
import com.qf.liwushuo.presenter.HomePresenter;
import com.qf.liwushuo.ui.activity.SearchActivity;
import com.qf.liwushuo.ui.view.HomeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/24 0024.
 * <p>
 * 首页
 */

public class HomeFragment extends MySuperFragment<HomePresenter> implements HomeView {
    @BindView(R.id.giftremind)
    ImageView giftremind;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.vp_home)
    ViewPager vpHome;

    List<String> dataTitle;
    HomeViewPagerAdapter homeViewPagerAdapter;
    List<Fragment> list;
    @BindView(R.id.ly)
    LinearLayout ly;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataTitle = new ArrayList<>();
        list = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        homeViewPagerAdapter = new HomeViewPagerAdapter(getChildFragmentManager(), list, dataTitle);
        vpHome.setAdapter(homeViewPagerAdapter);
        tab.setupWithViewPager(vpHome);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getData(getContext());
    }

    @Override
    public HomePresenter creatersenter() {
        return new HomePresenter();
    }

    @Override
    public void showView(TabBean tabBean) {
        //Log.e("hehe",tabBean.getData().getCandidates().size()+"");
        initData(tabBean);

    }

    private void initData(TabBean tabBean) {

        for (int i = 0; i < tabBean.getData().getChannels().size(); i++) {
            Log.e("hehe",tabBean.getData().getCandidates().size()+"");
            Log.e("自定义控件", "initData() called with: tabBean = [" + tabBean.getData().getChannels().get(i) .getName()+ "]");
            tab.addTab(tab.newTab().setText(tabBean.getData().getChannels().get(i).getName()));
            dataTitle.add(tabBean.getData().getChannels().get(i).getName());
            BaseFragment baseFragment = null;
          /*  if (i != 0) {
                baseFragment = new ItemFragment();

            }else {
                baseFragment=new SelectedFragmet();
            }*/
            baseFragment = new ItemFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id", tabBean.getData().getChannels().get(i).getId());
            baseFragment.setArguments(bundle);
            list.add(baseFragment);
        }
        homeViewPagerAdapter.notifyDataSetChanged();
    }


    @OnClick(R.id.ly)
    public void onClick() {
        Intent intent=new Intent(getContext(), SearchActivity.class);
        startActivity(intent);
    }
}
