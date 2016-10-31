package com.qf.liwushuo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import com.j256.ormlite.dao.Dao;
import com.qf.liwushuo.R;
import com.qf.liwushuo.adapter.HotAdapter;
import com.qf.liwushuo.adapter.OldSearchAdapter;
import com.qf.liwushuo.bean.SearchHotWordBean;
import com.qf.liwushuo.db.OldSearchEntity;
import com.qf.liwushuo.db.UserDataBaseOpenHelper;
import com.qf.liwushuo.presenter.HotWordPresenter;
import com.qf.liwushuo.ui.view.HotView;

import org.greenrobot.eventbus.EventBus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/25 0025.
 */

public class HotFragment extends MySuperFragment<HotWordPresenter> implements HotView {
    @BindView(R.id.search_recycler)
    RecyclerView searchRecycler;

    Dao<OldSearchEntity, Integer> userDao;//数据库操作类
    UserDataBaseOpenHelper mOpenHelper;

    HotAdapter hotAdapter;
    List<SearchHotWordBean.DataBean.HotWordsBean> list;
    @BindView(R.id.lv_hot)
    ListView lvHot;

    OldSearchAdapter adapter;
    List<OldSearchEntity> datas;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_hot, container, false);
        ButterKnife.bind(this, view);
        presenter.getData(getContext());
        init();
        initDbView();
        initDao();
        loadData();
        return view;

    }
    //重新加载数据
    public void startData(){
        loadData();
    }

    private void initDbView() {
        datas = new ArrayList<>();
        adapter = new OldSearchAdapter(datas);
        //为listView添加头部
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_holder_old_search, lvHot, false);
        view.findViewById(R.id.bt_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除数据库的数据
                try {
                    userDao.delete(datas);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                //重新查询
                loadData();
            }
        });
        lvHot.addHeaderView(view);
        lvHot.setAdapter(adapter);
        //搜索历史的点击事件
        adapter.setOnItemClicked(new OldSearchAdapter.OnItemClicked() {
            @Override
            public void onItem(int position) {
                EventBus.getDefault().post(datas.get(position).getWord());
                Log.e("自定义控件", "onItem() called with: position = [" + position + "]");
            }

            @Override
            public void onDelete(int position) {
                //删除数据库的数据
                try {
                    userDao.delete(datas.get(position));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                //重新查询
                loadData();
            }

        });
    }

    private void initDao() {
        mOpenHelper = UserDataBaseOpenHelper.getOpenHelper(getContext());
        try {
            userDao = mOpenHelper.getDao(OldSearchEntity.class);//获取数据库操作类
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        try {
            List<OldSearchEntity> users = userDao.queryForAll();
            datas.clear();
            datas.addAll(users);
            lvHot.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();//首先查询数据库，清数据，加载新数据
            if (datas.size() == 0) {
                lvHot.setVisibility(View.GONE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        list = new ArrayList<>();
        hotAdapter = new HotAdapter(list);
        searchRecycler.setLayoutManager(new GridLayoutManager(getContext(), 5));
        searchRecycler.setAdapter(hotAdapter);
        hotAdapter.setOnClickListener(new HotAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                EventBus.getDefault().post(list.get(position).getWord());
            }
        });
    }

    @Override
    public HotWordPresenter creatersenter() {
        return new HotWordPresenter();
    }

    @Override
    public void showView(SearchHotWordBean searchHotWordBean) {
        list.clear();
        list.addAll(searchHotWordBean.getData().getHot_words());
        hotAdapter.notifyDataSetChanged();
    }
}
