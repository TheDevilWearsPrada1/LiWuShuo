package com.qf.liwushuo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.qf.liwushuo.R;
import com.qf.liwushuo.adapter.BaseRecyclerAdapter;
import com.qf.liwushuo.adapter.StrategyListAdapter;
import com.qf.liwushuo.bean.GonglueBean;
import com.qf.liwushuo.ui.fragment.BaseFragment;
import com.qf.liwushuo.ui.fragment.ItemFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/26 0026.
 */

public class CategoryActivity extends AppCompatActivity implements BaseRecyclerAdapter.OnItemClickListener<GonglueBean.DataBean.ChannelGroupsBean.ChannelsBean> {
    GonglueBean.DataBean.ChannelGroupsBean data;
    @BindView(R.id.pinlei)
    TextView pinlei;
    @BindView(R.id.strategyRecycler)
    RecyclerView strategyRecycler;
    StrategyListAdapter adapter;
    @BindView(R.id.back)
    TextView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.strategy_list);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        data = (GonglueBean.DataBean.ChannelGroupsBean) intent.getSerializableExtra("ChannelGroupsBean");

        adapter = new StrategyListAdapter();
        Log.e("zq", data.getName());
        pinlei.setText(data.getName());
        strategyRecycler.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
        strategyRecycler.setAdapter(adapter);
        adapter.addDatas((ArrayList<GonglueBean.DataBean.ChannelGroupsBean.ChannelsBean>) data.getChannels());
        adapter.setOnItemClickListener(this);
    }


    @OnClick(R.id.back)
    public void onClick() {
        //Log.e("zq", "点击了");
        /*Intent intent = new Intent(CategoryActivity.this, MainActivity.class);
        intent.putExtra("fragment",1);
        startActivity(intent);*/
        //返回直接finish，不用跳转到对应的activity在展示fragment
        finish();
    }

    @Override
    public void onItemClick(int position, GonglueBean.DataBean.ChannelGroupsBean.ChannelsBean data) {
        Log.e("zq","Id:"+data.getId());
        /*BaseFragment baseFragment = new ItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id",data.getId());
        baseFragment.setArguments(bundle);*/
        Intent intent = new Intent(CategoryActivity.this,GonglueItemActivity.class);
        intent.putExtra("id",data.getId());
        intent.putExtra("name",data.getName());
        startActivity(intent);
    }
}
