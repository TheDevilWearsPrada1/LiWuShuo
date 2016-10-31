package com.qf.liwushuo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qf.liwushuo.R;
import com.qf.liwushuo.db.OldSearchEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/10/27 0027.
 */

public class OldSearchAdapter extends BaseAdapter{
    List<OldSearchEntity> list;
    int pos;

    public OldSearchAdapter(List<OldSearchEntity> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        if (convertView==null){
            holder=new Holder();
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_old_search,parent,false);
            holder.textView= (TextView) convertView.findViewById(R.id.tv);
            holder.tvDelelte= (TextView) convertView.findViewById(R.id.bt_delete);
            convertView.setTag(holder);
        }else {
            holder= (Holder) convertView.getTag();
        }
        holder.textView.setText(list.get(position).getWord());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClicked.onItem(position);
            }
        });
        holder.tvDelelte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClicked.onDelete(position);
            }
        });
        return convertView;
    }

   /* @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv:
                onItemClicked.onItem(pos);
                break;
            case R.id.bt_delete:
                onItemClicked.onDelete(pos);
                break;
        }
    }*/
    OnItemClicked onItemClicked;

    public void setOnItemClicked(OnItemClicked onItemClicked) {
        this.onItemClicked = onItemClicked;
    }

    public interface OnItemClicked{
        void onItem(int position);
        void onDelete(int position);
    }
    class Holder{
        TextView textView,tvDelelte;
    }
}
