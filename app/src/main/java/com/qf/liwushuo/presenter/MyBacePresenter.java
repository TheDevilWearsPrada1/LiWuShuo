package com.qf.liwushuo.presenter;

import com.qf.liwushuo.model.MvpModel;
import com.qf.liwushuo.ui.view.MvpView;

import java.lang.ref.WeakReference;

import ASimpleCache.org.afinal.simplecache.ACache;


/**
 * Created by Administrator on 2016/10/14 0014.
 */

public abstract class MyBacePresenter<V extends MvpView,T extends MvpModel> {
    private WeakReference<V> weakReference;
    public T model;
    public MyBacePresenter() {
        model=createModle();
    }

    public abstract T createModle();

    /**
     * 创建时建立绑定
     * @param mvpView
     */
    public void attach(V mvpView){
        weakReference=new WeakReference<V>(mvpView);
    }

    /**
     * activity 消毁的时候解除绑定
     */
    public void deAttach(){
        if (weakReference != null) {
            weakReference.clear();
            weakReference=null;

        }
    }
    public V getView(){
        return weakReference==null?null:weakReference.get();
    }
}
