package com.qf.liwushuo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.qf.liwushuo.R;
import com.qf.liwushuo.adapter.LanmuItemsAdapter;
import com.qf.liwushuo.bean.LanmuItemsBean;
import com.qf.liwushuo.presenter.LanmuItemsPresenter;
import com.qf.liwushuo.ui.view.LanmuItemsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/28 0028.
 */

public class LanmuItemsActivity extends AppCompatActivity implements LanmuItemsView {
    LanmuItemsPresenter presenter = new LanmuItemsPresenter();
    int id;
    @BindView(R.id.lanmuListView)
    ListView lanmuListView;
    List<LanmuItemsBean.DataBean.PostsBean> datas;
    LanmuItemsAdapter lanmuItemsAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lanmuitems);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        presenter.attach(this);
        presenter.getLanmuItemsData(id, 0, 0);
        init();
    }

    private void init() {
        datas = new ArrayList<>();
        lanmuItemsAdapter = new LanmuItemsAdapter(datas);
        lanmuListView.setAdapter(lanmuItemsAdapter);
    }

    @Override
    public void showView(LanmuItemsBean lanmuItemsBean) {
        //Log.e("zq", lanmuItemsBean.getData().getId() + "");
        datas.addAll(lanmuItemsBean.getData().getPosts());
        lanmuItemsAdapter.notifyDataSetChanged();
    }
}
