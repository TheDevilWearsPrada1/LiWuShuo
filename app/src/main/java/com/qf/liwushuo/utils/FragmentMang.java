package com.qf.liwushuo.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Administrator on 2016/10/25 0025.
 */

public class FragmentMang {
    private FragmentManager fragmentManager;
    private Fragment showFragment;
    public FragmentMang(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
    /**
     * 管理fragment的显示与隐藏
     */
    public void fragmentManager(int resid, Fragment fragment, String tag) {
        //开启一个事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //隐藏正在显示的Fragment对象
        if (showFragment != null) {
            fragmentTransaction.hide(showFragment);
        }

        Fragment mFragment = fragmentManager.findFragmentByTag(tag);
        if (mFragment != null) {
            fragmentTransaction.show(mFragment);
        } else {
            mFragment = fragment;
            fragmentTransaction.add(resid, mFragment, tag);
        }

        showFragment = mFragment;
        fragmentTransaction.commit();
    }

    public void fragmentRemove(Fragment fragment) {
        //开启一个事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }
}
