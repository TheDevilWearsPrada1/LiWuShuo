package com.qf.liwushuo.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qf.liwushuo.R;
import com.qf.liwushuo.bean.DanpinItemsBean;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/28 0028.
 */

public class DanpinItemAdapter extends BaseRecyclerAdapter<DanpinItemsBean.DataBean.ItemsBean> {
    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.danpin_item_list,parent,false);
        return new DanpinHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, DanpinItemsBean.DataBean.ItemsBean data) {
        if(viewHolder instanceof DanpinHolder){
            DanpinHolder danpinHolder= (DanpinHolder) viewHolder;
            Picasso.with(danpinHolder.image.getContext()).load(data.getCover_image_url()).into(danpinHolder.image);
            danpinHolder.name.setText(data.getName());
            danpinHolder.price.setText(data.getPrice());
            danpinHolder.price.setTextColor(Color.parseColor("#e71717"));
            danpinHolder.money.setTextColor(Color.parseColor("#e71717"));
            if(!data.getShort_description().equals("")){
                danpinHolder.short_description.setVisibility(View.VISIBLE);
                danpinHolder.short_description.setText(data.getShort_description());
                danpinHolder.name.setTextColor(Color.parseColor("#686666"));
            }else {
                danpinHolder.short_description.setVisibility(View.GONE);
                //Log.e("zq",1111+"");
            }
        }
    }

    class DanpinHolder extends BaseRecyclerAdapter.Holder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.money)
        TextView money;
        @BindView(R.id.short_description)
        TextView short_description;
        public DanpinHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
