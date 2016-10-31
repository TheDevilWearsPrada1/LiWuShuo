package com.qf.liwushuo.ui.fragment;

import android.view.View;
import android.widget.ListView;
import com.google.gson.Gson;
import com.qf.liwushuo.R;
import com.qf.liwushuo.adapter.MyBaseAdapter;
import com.qf.liwushuo.bean.ListCommnetBean;
import com.qf.liwushuo.bean.MRTJBean;
import com.qf.liwushuo.utils.UrlUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Bill on 2016/10/26.
 */

public class CommentFragment extends BaseFragment1 {

    private List<ListCommnetBean.DataBean.CommentsBean> list=new ArrayList<>();
    private ListView lv_pinglun;
    MyBaseAdapter adapter;
    private String item_list = "";
    ListCommnetBean listCommnetBean;
    PtrFrameLayout ptr;

    @Override
    protected int getContentView() {
        return R.layout.list_comment;
    }

    @Override
    protected void init(View view) {
        super.init(view);

        ptr = (PtrFrameLayout) view.findViewById(R.id.ptr);
        lv_pinglun= (ListView) view.findViewById(R.id.lv_pinglun);
        adapter=new MyBaseAdapter(list,getActivity());
        lv_pinglun.setAdapter(adapter);

        item_list = UrlUtil.Webs_List + getActivity().getIntent().getStringExtra("id_list")+UrlUtil.Comment_List;
        loadData();
        setupRefresh();
    }

    //下拉刷新
    private void setupRefresh() {
        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                adapter.notifyDataSetChanged();
                ptr.refreshComplete();
            }
        });
    }

    private void loadData() {
        RequestParams params = new RequestParams(item_list);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                listCommnetBean = new Gson().fromJson(result, ListCommnetBean.class);
                list.clear();
                list.addAll(listCommnetBean.getData().getComments());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }
            @Override
            public void onFinished() {

            }
        });
    }

}
