package com.qf.liwushuo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Created by Administrator on 2016/10/24 0024.
 */

public class CategoryViewPagerAdapter extends BaseViewPagerAdapter {
    List<Fragment> list;
    List<String> titleList;

    public CategoryViewPagerAdapter(FragmentManager fm, List<Fragment> list, List<String> titleList) {
        super(fm, list);
        this.list = list;
        this.titleList = titleList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList == null ? null : titleList.get(position);
    }
}
