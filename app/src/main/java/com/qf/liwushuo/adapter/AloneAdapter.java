package com.qf.liwushuo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qf.liwushuo.R;
import com.qf.liwushuo.bean.AloneBean;
import com.qf.liwushuo.bean.MRTJBean;
import com.qf.liwushuo.ui.activity.ListActivity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by Bill on 2016/10/24.
 */

public class AloneAdapter extends RecyclerView.Adapter<AloneAdapter.MyViewHolder>{

    List<AloneBean.DataBean.RecommendItemsBean> lists;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public AloneAdapter(List<AloneBean.DataBean.RecommendItemsBean> lists) {
        this.lists = lists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.alone_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv1_alone.setText(lists.get(position).getName());
        holder.tv2_alone.setText(lists.get(position).getPrice());
        holder.tv1_alone.setTextColor(Color.BLACK);
        holder.tv2_alone.setTextColor(Color.RED);
        holder.red.setTextColor(Color.RED);
        Glide.with(context).load(lists.get(position).getCover_image_url()).into(holder.iv_alone);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context.getApplicationContext(), ListActivity.class);
                intent.putExtra("id_list",lists.get(position).getId()+"");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class  MyViewHolder extends RecyclerView.ViewHolder {
        @ViewInject(R.id.iv_alone)
        private ImageView iv_alone;
        @ViewInject(R.id.tv1_alone)
        private TextView tv1_alone;
        @ViewInject(R.id.tv2_alone)
        private TextView tv2_alone;
        @ViewInject(R.id.red)
        private TextView red;

        public MyViewHolder(View itemView) {
            super(itemView);

            x.view().inject(this,itemView);
        }
    }
}
