package com.qf.liwushuo.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qf.liwushuo.R;
import com.qf.liwushuo.bean.SearchHotWordBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/25 0025.
 */

public class HotAdapter extends RecyclerView.Adapter<HotAdapter.MyHolder> {
    List<SearchHotWordBean.DataBean.HotWordsBean> list;


    public HotAdapter(List<SearchHotWordBean.DataBean.HotWordsBean> list) {
        this.list = list;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hold_item_title, null);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        if (getItemViewType(position)==0){
            holder.tvHold.setTextColor(Color.BLACK);
            holder.tvHold.setText(list.get(position).getWord());
        }else {
            holder.tvHold.setTextColor(Color.RED);
            holder.tvHold.setText(list.get(position).getWord());
        }
        holder.tvHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(position);
            }
        });
    }
    OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener{
        void onClick(int position);
    }
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getStyle();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_hold)
        TextView tvHold;
        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
