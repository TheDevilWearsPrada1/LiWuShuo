package com.qf.liwushuo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    private List<String> titleList;
    public MyViewPagerAdapter(FragmentManager fm,List<Fragment> list,List<String> titleList) {
        super(fm);
        this.list = list;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
