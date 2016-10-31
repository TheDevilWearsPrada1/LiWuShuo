package com.qf.liwushuo.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qf.liwushuo.R;
import com.qf.liwushuo.bean.LanmuItemsBean;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2016/10/28 0028.
 */

public class LanmuItemsAdapter extends BaseAdapter {
    List<LanmuItemsBean.DataBean.PostsBean> list;
    SimpleDateFormat format;
    public LanmuItemsAdapter(List<LanmuItemsBean.DataBean.PostsBean> list) {
        this.list = list;
        format = new SimpleDateFormat("MM-dd");
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lanmuitems_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(parent.getContext()).load(list.get(position).getCover_image_url()).into(holder.image);
        holder.title.setText(list.get(position).getTitle());
        long time = (list.get(position).getCreated_at())*1000L;
        //Log.e("zq","time:"+ time);
        String timzze = format.format(time);
        //Log.e("zq","timzze:"+ timzze);
        holder.created_at.setText(timzze);
        holder.likes_count.setText(list.get(position).getLikes_count() + "");
        holder.nickname.setText(list.get(position).getAuthor().getNickname());

        return convertView;
    }

    class ViewHolder {
        TextView title, created_at, nickname, likes_count;
        ImageView image;

        public ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.title);
            created_at = (TextView) view.findViewById(R.id.created_at);
            nickname = (TextView) view.findViewById(R.id.nickname);
            likes_count = (TextView) view.findViewById(R.id.likes_count);
            image = (ImageView) view.findViewById(R.id.image);
        }
    }
}

