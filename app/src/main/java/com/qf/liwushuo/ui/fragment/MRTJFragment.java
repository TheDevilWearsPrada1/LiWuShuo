package com.qf.liwushuo.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.qf.liwushuo.adapter.MRTJAdapter;
import com.qf.liwushuo.bean.MRTJBean;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;
import com.qf.liwushuo.R;
import com.qf.liwushuo.utils.UrlUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bill on 2016/10/25.
 */

public class MRTJFragment extends BaseFragment1 {

    private XRecyclerView recyclerView;
    private ImageView iv_head;
    MRTJAdapter adapter_mrtj;
    List<MRTJBean.DataBean.ItemsBean> lists = new ArrayList<>();
    com.qf.liwushuo.bean.MRTJBean MRTJBean;

    @Override
    protected int getContentView() {
        return R.layout.mrtj;
    }

    @Override
    protected void init(View view) {
        super.init(view);

        recyclerView= (XRecyclerView) view.findViewById(R.id.recycle_mrtj);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //refresh data here
                adapter_mrtj.notifyDataSetChanged();
                recyclerView.refreshComplete();
            }
            @Override
            public void onLoadMore() {
                // load more data here
                recyclerView.loadMoreComplete();
            }
        });

        View head = LayoutInflater.from(getActivity()).inflate(R.layout.mrtj_head,
                (ViewGroup) view.findViewById(android.R.id.content), false);
        iv_head= (ImageView) head.findViewById(R.id.iv_head);
        recyclerView.addHeaderView(head);

        initview();
        loadData();
    }

    private void loadData() {
        RequestParams params = new RequestParams(UrlUtil.MRTJ);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MRTJBean = new Gson().fromJson(result, com.qf.liwushuo.bean.MRTJBean.class);
                lists.addAll(MRTJBean.getData().getItems());
                Glide.with(getActivity()).load(MRTJBean.getData().getCover_image()).into(iv_head);
                adapter_mrtj.notifyDataSetChanged();
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

    private void initview() {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        lists = new ArrayList<>();
        adapter_mrtj = new MRTJAdapter(lists);
        adapter_mrtj.setContext(getActivity());
        recyclerView.setAdapter(adapter_mrtj);
    }
}
