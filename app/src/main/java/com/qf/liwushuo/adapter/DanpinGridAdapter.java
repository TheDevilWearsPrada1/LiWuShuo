package com.qf.liwushuo.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qf.liwushuo.R;
import com.qf.liwushuo.bean.DanpinBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/10/25 0025.
 */

public class DanpinGridAdapter extends BaseAdapter {
    List<DanpinBean.DataBean.CategoriesBean.SubcategoriesBean> list;

    public DanpinGridAdapter(List<DanpinBean.DataBean.CategoriesBean.SubcategoriesBean> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public DanpinBean.DataBean.CategoriesBean.SubcategoriesBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.danpin_item2, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(list.get(position).getName());
        Picasso.with(parent.getContext()).load(list.get(position).getIcon_url()).into(viewHolder.image);
        //Log.e("zq",list.get(position).getIcon_url());
        return convertView;
    }

    class ViewHolder {
        ImageView image;
        TextView name;
    }
}
