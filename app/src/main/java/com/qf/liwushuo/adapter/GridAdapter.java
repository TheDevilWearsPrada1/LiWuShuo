package com.qf.liwushuo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qf.liwushuo.R;
import com.qf.liwushuo.bean.GonglueBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/10/25 0025.
 */

public class GridAdapter extends BaseAdapter {
    List<GonglueBean.DataBean.ChannelGroupsBean.ChannelsBean> list;

    public GridAdapter(List<GonglueBean.DataBean.ChannelGroupsBean.ChannelsBean> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : 6;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gonglue_item2, parent,false);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
            Picasso.with(parent.getContext()).load(list.get(position).getCover_image_url()).into(viewHolder.image);
        return convertView;
    }

    class ViewHolder {
        ImageView image;
    }
}
