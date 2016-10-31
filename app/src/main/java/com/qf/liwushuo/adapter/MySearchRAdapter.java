package com.qf.liwushuo.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qf.liwushuo.R;
import com.qf.liwushuo.bean.SearchRBean;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/10/26 0026.
 */

public class MySearchRAdapter extends RecyclerView.Adapter<MySearchRAdapter.MySearchHolder> {
    List<SearchRBean.DataBean.ItemsBean> list;

    public MySearchRAdapter(List<SearchRBean.DataBean.ItemsBean> list) {
        this.list = list;
    }

    @Override
    public MySearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mrtj, parent, false);
        return new MySearchHolder(view);
    }

    @Override
    public void onBindViewHolder(MySearchHolder holder, final int position) {
        Picasso.with(holder.ivMrtj.getContext())
                .load(list.get(position).getCover_image_url())
                .into(holder.ivMrtj);
        holder.tvMrtj.setText(list.get(position).getShort_description());

        holder.tvMrtj2.setText(list.get(position).getName());
        holder.tvMrtj3.setText(list.get(position).getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
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

    public interface OnClickListener {
        void onClick(int position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class MySearchHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_mrtj)
        ImageView ivMrtj;
        @BindView(R.id.tv_mrtj)
        TextView tvMrtj;
        @BindView(R.id.tv_mrtj2)
        TextView tvMrtj2;
        @BindView(R.id.tv_mrtj3)
        TextView tvMrtj3;

        public MySearchHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
