package com.qf.liwushuo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qf.liwushuo.R;
import com.qf.liwushuo.adapter.HotAdapter;
import com.qf.liwushuo.adapter.MyAdapter;
import com.qf.liwushuo.bean.HomeItemBean;
import com.qf.liwushuo.presenter.HomeFragmentPresenter;
import com.qf.liwushuo.ui.activity.HomeitemActivity;
import com.qf.liwushuo.ui.view.HomeFragmentView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by Administrator on 2016/10/24 0024.
 */

public abstract class BaseFragment extends MySuperFragment<HomeFragmentPresenter> implements HomeFragmentView, BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    MyAdapter adapter;
    List<HomeItemBean.DataBean.ItemsBean> list;
    @BindView(R.id.rl_modulename_refresh)
    BGARefreshLayout rlModulenameRefresh;
    int id;
    int offset = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        id = bundle.getInt("id");

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getData(id, offset, getContext());
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_item, container, false);
        ButterKnife.bind(this, view);
        initData();
        initRefreshLayout(rlModulenameRefresh);
        return view;
    }

    private void initData() {
        list = new ArrayList<>();
        adapter = new MyAdapter(list);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);
        adapter.setOnClickListener(new HotAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getContext(), HomeitemActivity.class);
                intent.putExtra("id", list.get(position).getId());
                startActivity(intent);
            }
        });
    }

    private void initRefreshLayout(BGARefreshLayout refreshLayout) {
        // 为BGARefreshLayout设置代理
        rlModulenameRefresh.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getActivity(), true);
        // 设置下拉刷新和上拉加载更多的风格

        // 为了增加下拉刷新头部和加载更多的通用性，提供了以下可选配置选项  -------------START
        // 设置正在加载更多时不显示加载更多控件
        rlModulenameRefresh.setIsShowLoadingMoreView(true);
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
        offset = 0;
        presenter.getData(id, offset, getContext());
    }

    /**
     * @param refreshLayout
     * @return 是否显示加载更多
     */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        offset += 20;
        presenter.getData(id, offset, getContext());
        return true;
    }


    // 通过代码方式控制进入正在刷新状态。应用场景：某些应用在activity的onStart方法中调用，自动进入正在刷新状态获取最新数据
    public void beginRefreshing() {
        rlModulenameRefresh.beginRefreshing();
    }

    public abstract void setHolder();

    @Override
    public HomeFragmentPresenter creatersenter() {
        return new HomeFragmentPresenter();
    }

    @Override
    public void showView(HomeItemBean homeItemBean) {
        list.addAll(homeItemBean.getData().getItems());
        adapter.notifyDataSetChanged();
        rlModulenameRefresh.endRefreshing();
        rlModulenameRefresh.endLoadingMore();
    }
}
