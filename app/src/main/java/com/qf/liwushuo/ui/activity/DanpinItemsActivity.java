package com.qf.liwushuo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qf.liwushuo.R;
import com.qf.liwushuo.adapter.BaseRecyclerAdapter;
import com.qf.liwushuo.adapter.DanpinItemAdapter;
import com.qf.liwushuo.bean.DanpinItemsBean;
import com.qf.liwushuo.presenter.DanpinItemsPresenter;
import com.qf.liwushuo.ui.view.DanpinItemsView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DanpinItemsActivity extends AppCompatActivity implements DanpinItemsView {

    int id;
    DanpinItemsPresenter presenter = new DanpinItemsPresenter();
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.sort)
    ImageView sort;
    @BindView(R.id.danpinRecycler)
    RecyclerView danpinRecycler;
    DanpinItemAdapter danpinItemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danpin_items);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        name.setText(intent.getStringExtra("name"));
        presenter.attach(this);
        presenter.getDanpinItemsData(id, 0, 0);
        danpinItemAdapter = new DanpinItemAdapter();
        //danpinRecycler.setLayoutManager(new GridLayoutManager(this,GridLayoutManager.VERTICAL,2,false));
        danpinRecycler.setLayoutManager(new GridLayoutManager(this,2, LinearLayoutManager.VERTICAL,false));
        danpinRecycler.setAdapter(danpinItemAdapter);

    }

    @Override
    public void showView(DanpinItemsBean danpinItemsBean) {
        Log.e("zq", danpinItemsBean.getData().getItems().get(0).getName());
        danpinItemAdapter.addDatas((ArrayList<DanpinItemsBean.DataBean.ItemsBean>) danpinItemsBean.getData().getItems());
        danpinItemAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<DanpinItemsBean.DataBean.ItemsBean>() {
            @Override
            public void onItemClick(int position, DanpinItemsBean.DataBean.ItemsBean data) {
                Intent intent = new Intent(sort.getContext(),ListActivity.class);
                intent.putExtra("id_list",data.getId()+"");
                sort.getContext().startActivity(intent);
            }
        });
    }

    @OnClick({R.id.back, R.id.sort})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.sort:

                break;
        }
    }
}
