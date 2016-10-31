package com.qf.liwushuo.ui.fragment;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.qf.liwushuo.R;
import com.qf.liwushuo.adapter.AloneAdapter;
import com.qf.liwushuo.adapter.MRTJAdapter;
import com.qf.liwushuo.adapter.MyPagerAdapter;
import com.qf.liwushuo.bean.AloneBean;
import com.qf.liwushuo.bean.ListItemBean;
import com.qf.liwushuo.bean.MRTJBean;
import com.qf.liwushuo.utils.UrlUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static com.qf.liwushuo.R.id.iv_head;

/**
 * Created by Bill on 2016/10/26.
 */

public class AloneFragment extends BaseFragment1 {

    private TextView tv_list1;
    private TextView tv_list2;
    private TextView tv_list3;
    private TextView tv_list4;
    private TextView tv_list5;
    ViewPager viewpager;
    MyPagerAdapter adapter;
    RecyclerView recyclerView;
    AloneAdapter aloneAdapter;
    List<AloneBean.DataBean.RecommendItemsBean> lists = new ArrayList<>();
    AloneBean aloneBean;

    ArrayList<View> imgs;
    ListItemBean listItemBean;
    private String item_list = "";
    private String item_recycle = "";

    @Override
    protected int getContentView() {
        return R.layout.list_alone;
    }

    @Override
    protected void init(View view) {
        super.init(view);

        tv_list1= (TextView) view.findViewById(R.id.tv_list1);
        tv_list2= (TextView) view.findViewById(R.id.tv_list2);
        tv_list3= (TextView) view.findViewById(R.id.tv_list3);
        tv_list4= (TextView) view.findViewById(R.id.tv_list4);
        tv_list5= (TextView) view.findViewById(R.id.tv_list5);
        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerViews);
        viewpager= (ViewPager) view.findViewById(R.id.viewpager);

        item_list = UrlUtil.Webs_List + getActivity().getIntent().getStringExtra("id_list");
        item_recycle=item_list+UrlUtil.List_recycle;

        imgs=new ArrayList<>();
        loadData();
        adapter=new MyPagerAdapter(imgs);
        viewpager.setAdapter(adapter);

        initview();
        loadData2();
    }

    private void loadData() {
        RequestParams params = new RequestParams(item_list);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                listItemBean = new Gson().fromJson(result, ListItemBean.class);
                tv_list1.setText(listItemBean.getData().getShort_description());
                tv_list1.setTextColor(Color.BLACK);
                tv_list2.setText(listItemBean.getData().getName());
                tv_list2.setTextColor(Color.GRAY);
                tv_list3.setText(listItemBean.getData().getPrice());
                tv_list2.setTextColor(Color.RED);
                tv_list4.setText(listItemBean.getData().getLikes_count()+"");
                tv_list2.setTextColor(Color.GRAY);
                tv_list5.setText(listItemBean.getData().getDescription());
                tv_list2.setTextColor(Color.GRAY);

                int len=listItemBean.getData().getImage_urls().size();
                for (int i = 0; i < len; i++) {
                    ImageView iv=new ImageView(getActivity());
                    iv.setScaleType(ImageView.ScaleType.FIT_XY);
                    Glide.with(getContext()).load(listItemBean.getData().getImage_urls().get(i)).into(iv);
                    imgs.add(iv);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void loadData2() {
        RequestParams params = new RequestParams(item_recycle);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                aloneBean = new Gson().fromJson(result, AloneBean.class);
                lists.addAll(aloneBean.getData().getRecommend_items());
                aloneAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void initview() {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        lists = new ArrayList<>();
        aloneAdapter = new AloneAdapter(lists);
        aloneAdapter.setContext(getActivity());
        recyclerView.setAdapter(aloneAdapter);
    }
}
