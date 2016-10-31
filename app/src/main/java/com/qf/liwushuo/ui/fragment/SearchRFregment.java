package com.qf.liwushuo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qf.liwushuo.R;
import com.qf.liwushuo.adapter.MySearchRAdapter;
import com.qf.liwushuo.bean.SearchRBean;
import com.qf.liwushuo.presenter.SearchRPresenter;
import com.qf.liwushuo.ui.activity.ListActivity;
import com.qf.liwushuo.ui.view.SerachRFragmetView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by Administrator on 2016/10/26 0026.
 */

public class SearchRFregment extends MySuperFragment<SearchRPresenter> implements SerachRFragmetView, BGARefreshLayout.BGARefreshLayoutDelegate {
    String keyWord;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.rl_modulename_refresh)
    BGARefreshLayout rlModulenameRefresh;

    List<SearchRBean.DataBean.ItemsBean> list;
    MySearchRAdapter adapter;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_sor)
    TextView tvSor;
    int offset = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keyWord = getArguments().getString("keyWord");
        presenter.getData("", offset, keyWord);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        list = new ArrayList<>();
        adapter = new MySearchRAdapter(list);
        recycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recycler.setAdapter(adapter);
        initRefreshLayout(rlModulenameRefresh);
        adapter.setOnClickListener(new MySearchRAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(getContext(), ListActivity.class);
                intent.putExtra("id_list",list.get(position).getId()+"");
                startActivity(intent);
            }
        });
    }

    @Override
    public SearchRPresenter creatersenter() {
        return new SearchRPresenter();
    }

    @Override
    public void getData(SearchRBean searchRBean) {
        tvCount.setText("单品（"+searchRBean.getData().getTotal()+"）");
        list.addAll(searchRBean.getData().getItems());
        adapter.notifyDataSetChanged();
        rlModulenameRefresh.endLoadingMore();
        rlModulenameRefresh.endRefreshing();
    }

    private void initRefreshLayout(BGARefreshLayout refreshLayout) {
        // 为BGARefreshLayout设置代理
        rlModulenameRefresh.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getActivity(), true);
        // 设置下拉刷新和上拉加载更多的风格

        // 为了增加下拉刷新头部和加载更多的通用性，提供了以下可选配置选项  -------------START
        // 设置正在加载更多时不显示加载更多控件
        rlModulenameRefresh.setIsShowLoadingMoreView(false);
        // 设置正在加载更多时的文本
        refreshViewHolder.setLoadingMoreText("正在加载");
        // 设置整个加载更多控件的背景颜色资源id
        //refreshViewHolder.setLoadMoreBackgroundColorRes(Color.BLAC);
        // 设置整个加载更多控件的背景drawable资源id
        //refreshViewHolder.setLoadMoreBackgroundDrawableRes(R.mipmap.bga_refresh_loading02);
        // 设置下拉刷新控件的背景颜色资源id
        //refreshViewHolder.setRefreshViewBackgroundColorRes(refreshViewBackgroundColorRes);
        // 设置下拉刷新控件的背景drawable资源id
        // refreshViewHolder.setRefreshViewBackgroundDrawableRes(R.mipmap.bga_refresh_loading01);
        // 设置自定义头部视图（也可以不用设置）     参数1：自定义头部视图（例如广告位）， 参数2：上拉加载更多是否可用
        //mRefreshLayout.setCustomHeaderView(mBanner, false);
        // 可选配置  -------------END
        rlModulenameRefresh.setRefreshViewHolder(refreshViewHolder);

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        // 在这里加载最新数据
        list.clear();
        offset=0;
        presenter.getData("", offset, keyWord);
    }

    /**
     * @param refreshLayout
     * @return 是否显示加载更多
     */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        offset += 20;
        presenter.getData("", offset, keyWord);
        return true;
    }
}
