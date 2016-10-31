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
import com.qf.liwushuo.bean.MRTJBean;
import com.qf.liwushuo.ui.activity.ListActivity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by Bill on 2016/10/24.
 */

public class MRTJAdapter extends RecyclerView.Adapter<MRTJAdapter.MyViewHolder>{

    List<MRTJBean.DataBean.ItemsBean> lists;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public MRTJAdapter(List<MRTJBean.DataBean.ItemsBean> lists) {
        this.lists = lists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mrtj,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_mrtj.setText(lists.get(position).getShort_description());
        holder.tv_mrtj2.setText(lists.get(position).getName());
        holder.tv_mrtj3.setText(lists.get(position).getPrice());
        holder.tv_mrtj.setTextColor(Color.BLACK);
        holder.tv_mrtj3.setTextColor(Color.RED);
        holder.tv_mrtj￥.setTextColor(Color.RED);
        Glide.with(context).load(lists.get(position).getCover_image_url()).into(holder.iv_mrtj);
        //x.image().bind(holder.iv_mrtj,lists.get(position).getCover_image_url());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context.getApplicationContext(), ListActivity.class);
                intent.putExtra("id_list",lists.get(position).getId()+"");
                context.startActivity(intent);
                Log.d("ce",""+position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class  MyViewHolder extends RecyclerView.ViewHolder {
        @ViewInject(R.id.iv_mrtj)
        private ImageView iv_mrtj;
        @ViewInject(R.id.tv_mrtj)
        private TextView tv_mrtj;
        @ViewInject(R.id.tv_mrtj2)
        private TextView tv_mrtj2;
        @ViewInject(R.id.tv_mrtj3)
        private TextView tv_mrtj3;
        @ViewInject(R.id.tv_mrtj￥)
        private TextView tv_mrtj￥;

        public MyViewHolder(View itemView) {
            super(itemView);

            x.view().inject(this,itemView);
        }
    }
}
