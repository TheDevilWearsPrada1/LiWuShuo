package com.qf.liwushuo.presenter;

import com.qf.liwushuo.bean.SearchWordBean;
import com.qf.liwushuo.model.SearchWoedModel;
import com.qf.liwushuo.ui.view.SearchWordView;

import rx.functions.Action1;

/**
 * Created by Administrator on 2016/10/26 0026.
 */

public class SearchWordPresenter extends MyBacePresenter<SearchWordView,SearchWoedModel> {
    public void getData(String keyWord){
        model.loadData(new Action1<SearchWordBean>() {
            @Override
            public void call(SearchWordBean searchWordBean) {
                getView().getData(searchWordBean);
            }
        },keyWord);
    }
    @Override
    public SearchWoedModel createModle() {
        return new SearchWoedModel();
    }
}
