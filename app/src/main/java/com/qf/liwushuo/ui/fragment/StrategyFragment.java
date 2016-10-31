package com.qf.liwushuo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qf.liwushuo.R;
import com.qf.liwushuo.adapter.BaseRecyclerAdapter;
import com.qf.liwushuo.adapter.MyStraHeaderAdapter;
import com.qf.liwushuo.adapter.MyStraRecyclerAdapter;
import com.qf.liwushuo.bean.GonglueBean;
import com.qf.liwushuo.bean.GonglueHeaderBean;
import com.qf.liwushuo.presenter.StrategyPresenter;
import com.qf.liwushuo.ui.activity.LanmuItemsActivity;
import com.qf.liwushuo.ui.view.StrategyView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/24 0024.
 */

public class StrategyFragment extends MySuperFragment<StrategyPresenter> implements StrategyView {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    RecyclerView recyclerHeader;
    MyStraRecyclerAdapter myAdapter;
    MyStraHeaderAdapter headerAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_strategy,container,false);
        ButterKnife.bind(this, view);
        myAdapter = new MyStraRecyclerAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(myAdapter);
        setHeader(recycler);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getData(getContext());
    }

    private void setHeader(RecyclerView recycler) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.gonglue_header,recycler,false);
        myAdapter.setHeaderView(view);
        recyclerHeader = (RecyclerView) view.findViewById(R.id.recyclerHeader);
        headerAdapter = new MyStraHeaderAdapter();
        recyclerHeader.setLayoutManager(new GridLayoutManager(getContext(),3, LinearLayoutManager.HORIZONTAL,false));
        recyclerHeader.setAdapter(headerAdapter);

    }

    @Override
    public StrategyPresenter creatersenter() {
        return new StrategyPresenter();
    }

    @Override
    public void showView(GonglueBean gonglueBean) {
        //Log.e("zq",gonglueBean.getData().getChannel_groups().get(0).getName());
        myAdapter.addDatas((ArrayList<GonglueBean.DataBean.ChannelGroupsBean>) gonglueBean.getData().getChannel_groups());
        //myAdapter.setOnItemClickListener(this);
    }

    @Override
    public void showHeaderView(GonglueHeaderBean gonglueHeaderBean) {
        //Log.e("zq",gonglueHeaderBean.getData().getColumns().get(0).getAuthor());
        headerAdapter.addDatas((ArrayList<GonglueHeaderBean.DataBean.ColumnsBean>) gonglueHeaderBean.getData().getColumns());
        headerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<GonglueHeaderBean.DataBean.ColumnsBean>() {
            @Override
            public void onItemClick(int position, GonglueHeaderBean.DataBean.ColumnsBean data) {
                //Log.e("zq","点击了"+position);
                Intent intent = new Intent(getContext(), LanmuItemsActivity.class);
                intent.putExtra("id",data.getId());
                intent.putExtra("title",data.getTitle());
                getContext().startActivity(intent);
            }
        });
    }


}
