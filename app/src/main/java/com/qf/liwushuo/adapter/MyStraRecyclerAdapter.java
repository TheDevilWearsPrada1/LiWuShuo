package com.qf.liwushuo.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.qf.liwushuo.R;
import com.qf.liwushuo.bean.GonglueBean;
import com.qf.liwushuo.ui.activity.CategoryActivity;
import com.qf.liwushuo.ui.activity.GonglueItemActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/25 0025.
 */

public class MyStraRecyclerAdapter extends BaseRecyclerAdapter<GonglueBean.DataBean.ChannelGroupsBean> implements View.OnClickListener {
    GridAdapter adapter ;
    List<GonglueBean.DataBean.ChannelGroupsBean.ChannelsBean> datas;
    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gonglue_item1, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBind(final RecyclerView.ViewHolder viewHolder, int RealPosition, final GonglueBean.DataBean.ChannelGroupsBean data) {
        if (viewHolder instanceof MyHolder) {
            ((MyHolder) viewHolder).title.setText(data.getName());
            ((MyHolder) viewHolder).chakan.setTag(data);
            datas.clear();
            datas.addAll(data.getChannels());
            adapter.notifyDataSetChanged();
            ((MyHolder) viewHolder).gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.e("zq","position:"+position+"id:"+data.getChannels().get(position).getId());
                    Intent intent = new Intent(((MyHolder) viewHolder).gridView.getContext(), GonglueItemActivity.class);
                    intent.putExtra("id",data.getChannels().get(position).getId());
                    intent.putExtra("name",data.getChannels().get(position).getName());
                    ((MyHolder) viewHolder).gridView.getContext().startActivity(intent);
                }
            });
            ((MyHolder) viewHolder).chakan.setOnClickListener(this);
            if(data.getChannels().size() <=6){
                ((MyHolder) viewHolder).chakan.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        GonglueBean.DataBean.ChannelGroupsBean data = (GonglueBean.DataBean.ChannelGroupsBean) v.getTag();
        Intent intent = new Intent();
        intent.setClass(v.getContext(), CategoryActivity.class);
        //intent.putExtra("position",position);
        intent.putExtra("ChannelGroupsBean", (Serializable) data);
        v.getContext().startActivity(intent);
    }


    class MyHolder extends BaseRecyclerAdapter.Holder  {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.chakan)
        TextView chakan;
        @BindView(R.id.gridView)
        GridView gridView;
        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            datas = new ArrayList<>();
            adapter = new GridAdapter(datas);
            gridView.setAdapter(adapter);
        }

    }
}
