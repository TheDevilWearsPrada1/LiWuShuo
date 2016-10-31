package com.qf.liwushuo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qf.liwushuo.R;
import com.qf.liwushuo.bean.GonglueHeaderBean;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/25 0025.
 */

public class MyStraHeaderAdapter extends BaseRecyclerAdapter<GonglueHeaderBean.DataBean.ColumnsBean> {

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gonglue_header_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, GonglueHeaderBean.DataBean.ColumnsBean data) {
        if (viewHolder instanceof MyHolder) {
            ((MyHolder) viewHolder).header_author.setText(data.getAuthor());
            ((MyHolder) viewHolder).header_title.setText(data.getTitle());
            Picasso.with(((MyHolder) viewHolder).header_imager.getContext()).load(data.getCover_image_url()).into(((MyHolder) viewHolder).header_imager);
        }
    }

    class MyHolder extends BaseRecyclerAdapter.Holder {
        @BindView(R.id.header_imager)
        ImageView header_imager;
        @BindView(R.id.header_title)
        TextView header_title;
        @BindView(R.id.header_author)
        TextView header_author;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
