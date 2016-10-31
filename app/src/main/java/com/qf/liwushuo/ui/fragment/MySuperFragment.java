package com.qf.liwushuo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.qf.liwushuo.presenter.MyBacePresenter;
import com.qf.liwushuo.ui.view.MvpView;

/**
 * Created by Administrator on 2016/10/14 0014.
 * 用于mvp注入的
 */
public abstract class MySuperFragment<T extends MyBacePresenter> extends Fragment {
    public T presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=creatersenter();
        presenter.attach((MvpView) this);
    }

    public abstract T creatersenter();

    /**
     * 剥离和Activity的关联
     */
    @Override
    public void onDetach() {
        super.onDetach();
        presenter.deAttach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
