package com.qf.liwushuo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qf.liwushuo.R;
import com.qf.liwushuo.bean.HomeItemBean;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/24 0024.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    List<HomeItemBean.DataBean.ItemsBean> list;

    public MyAdapter(List<HomeItemBean.DataBean.ItemsBean> list) {
        this.list = list;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.tvCount.setText(list.get(position).getLikes_count() + "");
        Picasso.with(holder.ivItem.getContext())
                .load(list.get(position).getCover_webp_url())
                .into(holder.ivItem);
        holder.tvTitle.setText(list.get(position).getTitle() + "");
        holder.tvInfo.setText(list.get(position).getIntroduction() + "");
        if(list.get(position).getColumn()!=null) {
            holder.tvName.setText(list.get(position).getColumn().getTitle() + "");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(position);
            }
        });
    }

    HotAdapter.OnClickListener onClickListener;

    public void setOnClickListener(HotAdapter.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_item)
        ImageView ivItem;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_info)
        TextView tvInfo;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_count)
        TextView tvCount;


        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
