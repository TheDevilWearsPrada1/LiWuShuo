package com.qf.liwushuo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import static android.R.id.list;

/**
 * Created by Administrator on 2016/10/24 0024.
 */

public class BaseViewPagerAdapter extends FragmentStatePagerAdapter{
    List<Fragment> list;
    public BaseViewPagerAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }
}
