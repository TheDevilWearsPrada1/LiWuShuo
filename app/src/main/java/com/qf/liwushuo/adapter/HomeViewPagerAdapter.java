package com.qf.liwushuo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Created by Administrator on 2016/10/24 0024.
 */

public class HomeViewPagerAdapter extends BaseViewPagerAdapter {
    List<String> dataTitle;
    public HomeViewPagerAdapter(FragmentManager fm, List<Fragment> list,List<String> dataTitle) {
        super(fm, list);
        this.dataTitle=dataTitle;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return dataTitle==null?null:dataTitle.get(position);
    }
}
