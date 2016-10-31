package com.qf.liwushuo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qf.liwushuo.R;
import com.qf.liwushuo.bean.GonglueBean;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StrategyListAdapter extends BaseRecyclerAdapter<GonglueBean.DataBean.ChannelGroupsBean.ChannelsBean> {


    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gonglue_item2,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, GonglueBean.DataBean.ChannelGroupsBean.ChannelsBean data) {
        if (viewHolder instanceof StrategyListAdapter.MyHolder) {
            Picasso.with(((MyHolder)viewHolder).image.getContext()).load(data.getCover_image_url()).into(((MyHolder) viewHolder).image);
        }
    }

    class MyHolder extends BaseRecyclerAdapter.Holder{
        @BindView(R.id.image)
        ImageView image;
        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
