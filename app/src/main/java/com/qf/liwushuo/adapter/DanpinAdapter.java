package com.qf.liwushuo.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.qf.liwushuo.R;
import com.qf.liwushuo.bean.DanpinBean;
import com.qf.liwushuo.ui.activity.DanpinItemsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/26 0026.
 */

public class DanpinAdapter extends BaseAdapter {
    DanpinGridAdapter danpinGridAdapter;
    List<DanpinBean.DataBean.CategoriesBean> list;
    List<DanpinBean.DataBean.CategoriesBean.SubcategoriesBean> datas;
    public DanpinAdapter(List<DanpinBean.DataBean.CategoriesBean> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.danpin_item,parent,false);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
        }else {
             holder = (MyViewHolder) convertView.getTag();
        }
        holder.name.setText(list.get(position).getName());
        datas = new ArrayList<>();
        datas.addAll(list.get(position).getSubcategories());
        danpinGridAdapter = new DanpinGridAdapter(datas);
        holder.gridView.setAdapter(danpinGridAdapter);

        final MyViewHolder finalHolder = holder;
        holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position2, long id) {
                //Log.e("zq",data.getSubcategories().get(position).getId()+"");
                Intent intent = new Intent(finalHolder.gridView.getContext(), DanpinItemsActivity.class);
                intent.putExtra("id",list.get(position).getSubcategories().get(position2).getId());
                intent.putExtra("name",list.get(position).getSubcategories().get(position2).getName());
                finalHolder.gridView.getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    class MyViewHolder{
        TextView name;
        GridView gridView;

        public MyViewHolder(View view) {
            this.gridView = (GridView) view.findViewById(R.id.gridView);
            this.name = (TextView) view.findViewById(R.id.name);
        }
    }
}
