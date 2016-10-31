package com.qf.liwushuo.presenter;

import com.qf.liwushuo.bean.SearchRBean;
import com.qf.liwushuo.model.SearchRModel;
import com.qf.liwushuo.ui.view.SerachRFragmetView;

import rx.functions.Action1;

/**
 * Created by Administrator on 2016/10/26 0026.
 */

public class SearchRPresenter extends MyBacePresenter<SerachRFragmetView,SearchRModel> {
    public void getData(String sort,int offset,String keyWord){
        model.loadData(new Action1<SearchRBean>() {
            @Override
            public void call(SearchRBean searchRBean) {
                getView().getData(searchRBean);
            }
        },sort,offset,keyWord);
    }
    @Override
    public SearchRModel createModle() {
        return new SearchRModel();
    }
}
