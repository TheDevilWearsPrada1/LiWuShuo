package com.qf.liwushuo.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qf.liwushuo.R;
import com.qf.liwushuo.bean.ListCommnetBean;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.R.attr.format;
import static com.qf.liwushuo.R.id.vi;

/**
 * Created by Bill on 2016/9/2.
 */

public class MyBaseAdapter extends BaseAdapter {

    List<ListCommnetBean.DataBean.CommentsBean> list;
    Context context;

    public MyBaseAdapter(List<ListCommnetBean.DataBean.CommentsBean> list, Context context) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler viewHoler;
        if (convertView==null){
            convertView= LayoutInflater.from(context)
                    .inflate(R.layout.listcomment,null);
            viewHoler=new ViewHoler();
            convertView.setTag(viewHoler);
            viewHoler.iv_comment= (ImageView) convertView.findViewById(R.id.iv_comment);
            viewHoler.tv1_comm= (TextView) convertView.findViewById(R.id.tv1_comm);
            viewHoler.tv2_comm= (TextView) convertView.findViewById(R.id.tv2_comm);
            viewHoler.tv3_comms= (TextView) convertView.findViewById(R.id.tv3_comms);
            viewHoler.tv3_comm= (TextView) convertView.findViewById(R.id.tv3_comm);
            viewHoler.vi= (TextView) convertView.findViewById(vi);
        }else {
            viewHoler= (ViewHoler) convertView.getTag();
        }
        ListCommnetBean.DataBean.CommentsBean beans=list.get(position);
        viewHoler.tv1_comm.setText(beans.getUser().getNickname());

        long time=beans.getCreated_at()*1000;
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss");
        String format1 = format.format(time);
        viewHoler.tv2_comm.setText(format1);

        if (beans.getReplied_user()!=null){
            viewHoler.tv3_comms.setText(beans.getReplied_user().getNickname());
            viewHoler.vi.setVisibility(View.VISIBLE);
        }
        viewHoler.tv3_comm.setText(beans.getContent());
        if (!beans.getUser().getAvatar_url().equals("http://q.qlogo.cn/qqapp/1101487442/61FC5ACAB1E69B9")){
            x.image().bind(viewHoler.iv_comment,beans.getUser().getAvatar_url(),
                    new ImageOptions.Builder().setCircular(true).build());
        }

        return convertView;
    }

    class ViewHoler{
        ImageView iv_comment;
        TextView tv1_comm;
        TextView tv2_comm;
        TextView tv3_comms;
        TextView tv3_comm;
        TextView vi;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
