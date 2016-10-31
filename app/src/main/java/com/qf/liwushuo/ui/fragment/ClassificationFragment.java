package com.qf.liwushuo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qf.liwushuo.R;
import com.qf.liwushuo.adapter.CategoryViewPagerAdapter;
import com.qf.liwushuo.ui.activity.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.qf.liwushuo.R.id.lv;

/**
 * Created by Administrator on 2016/10/24 0024.
 * <p>
 * 分类
 */

public class ClassificationFragment extends Fragment implements View.OnClickListener {
    /* @BindView(R.id.searchView)
     SearchView searchView;*/
    @BindView(R.id.strategy_tab)
    TabLayout strategy_tab;
    @BindView(R.id.categoryViewPager)
    ViewPager categoryViewPager;
    List<Fragment> fragments;
    CategoryViewPagerAdapter categoryAdapter;
    List<String> titleList;
    @BindView(R.id.ly)
    LinearLayout ly;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragments = new ArrayList<>();
        titleList = new ArrayList<>();
        titleList.add("攻略");
        titleList.add("单品");
        fragments.add(new StrategyFragment());
        fragments.add(new DanpinFragment());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, null);
        ButterKnife.bind(this, view);
        categoryAdapter = new CategoryViewPagerAdapter(getChildFragmentManager(), fragments, titleList);
        categoryViewPager.setAdapter(categoryAdapter);
        strategy_tab.setupWithViewPager(categoryViewPager);
        ly.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getContext(), SearchActivity.class);
        startActivity(intent);
    }
}
