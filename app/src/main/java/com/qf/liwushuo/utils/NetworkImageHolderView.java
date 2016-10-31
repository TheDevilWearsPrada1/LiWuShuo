package com.qf.liwushuo.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.qf.liwushuo.R;
import com.qf.liwushuo.bean.BannerBean;
import com.squareup.picasso.Picasso;

/**
 * Created by Sai on 15/8/4.
 * 网络图片加载例子
 */
public class NetworkImageHolderView implements Holder<BannerBean.DataBean.BannersBean> {
    private ImageView imageView;
    View view;

    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, BannerBean.DataBean.BannersBean data) {
        //imageView.setImageResource(R.drawable.ic_default_adimage);
        //ImageLoader.getInstance().displayImage(data,imageView);
        Log.e("自定义控件", "UpdateUI() called with: context = [" + context + "], position = [" + position + "], data = [" + data + "]");
        Picasso.with(context).load(data.getImage_url()).into(imageView);
        //设置点击事件
    }


}
