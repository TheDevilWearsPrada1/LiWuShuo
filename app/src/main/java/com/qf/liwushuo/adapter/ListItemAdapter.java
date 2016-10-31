package com.qf.liwushuo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.qf.liwushuo.bean.ListItemBean;
import java.util.List;

/**
 * Created by Bill on 2016/10/26.
 */

public class ListItemAdapter extends RecyclerView.Adapter<MRTJAdapter.MyViewHolder>{

    List<ListItemBean> lists;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public ListItemAdapter(List<ListItemBean> lists) {
        this.lists = lists;
    }

    @Override
    public MRTJAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MRTJAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
