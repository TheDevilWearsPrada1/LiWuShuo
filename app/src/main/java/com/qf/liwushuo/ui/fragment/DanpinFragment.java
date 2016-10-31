package com.qf.liwushuo.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.qf.liwushuo.R;
import com.qf.liwushuo.adapter.DanpinAdapter;
import com.qf.liwushuo.bean.DanpinBean;
import com.qf.liwushuo.presenter.DanpinPresenter;
import com.qf.liwushuo.ui.view.DanpinView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/24 0024.
 */

public class DanpinFragment extends MySuperFragment<DanpinPresenter> implements DanpinView, View.OnClickListener {

    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.listView1)
    ListView listView1;
    DanpinAdapter adapter1;
    List<DanpinBean.DataBean.CategoriesBean> list;
    int screenheight = 0;
    int selectIndex;
    int index;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.danping, container, false);
        ButterKnife.bind(this, view);
        list = new ArrayList<>();
        adapter1 = new DanpinAdapter(list);
        listView1.setAdapter(adapter1);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getDanpinData(getContext());
    }

    @Override
    public void getDanpinData(DanpinBean danpinBean) {
        //Log.e("zq", danpinBean.getData().getCategories().get(0).getSubcategories().get(0).getName());
        list.addAll(danpinBean.getData().getCategories());
        adapter1.notifyDataSetChanged();
        listView1.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE || scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    for (int i = 0; i < ll.getChildCount(); i++) {
                        TextView childAt = (TextView) ll.getChildAt(i);
                        ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                        if (i == index) {
                            childAt.setTextColor(Color.RED);
                            childAt.setBackgroundResource(R.drawable.scroll_selecte);
                            scrollView.scrollTo(0, (layoutParams.height) * index);
                        } else {
                            childAt.setTextColor(Color.BLACK);
                            childAt.setBackgroundResource(R.drawable.scroll_normal);
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //Log.e("zq","firstVisibleItem:"+firstVisibleItem);
                index = firstVisibleItem;
            }
        });
        initTitles(danpinBean);
    }

    private void initTitles(DanpinBean danpinBean) {
        int size = danpinBean.getData().getCategories().size();
        List<DanpinBean.DataBean.CategoriesBean> categories = danpinBean.getData().getCategories();
        //获取屏幕高度
        screenheight = getResources().getDisplayMetrics().heightPixels;
        //新建一个布局属性
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, screenheight / 14);
        for (int i = 0; i < size; i++) {
            TextView tv = new TextView(getContext());
            tv.setText(categories.get(i).getName());
            tv.setLayoutParams(params);
            tv.setTag(i);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.BLACK);
            tv.setBackgroundResource(R.drawable.scroll_normal);
            if (i == 0) {
                tv.setTextColor(Color.RED);
                tv.setBackgroundResource(R.drawable.scroll_selecte);
            }

            tv.setOnClickListener(this);
            ll.addView(tv);
        }
    }

    @Override
    public DanpinPresenter creatersenter() {
        return new DanpinPresenter();
    }

    //textview的点击事件
    @Override
    public void onClick(View v) {
        selectIndex = (int) v.getTag();
        listView1.setSelection(selectIndex);
        for (int i = 0; i < ll.getChildCount(); i++) {
            TextView childAt = (TextView) ll.getChildAt(i);
            if (selectIndex == i ) {
                if(selectIndex == ll.getChildCount()-1){
                    TextView childAt1 = (TextView) ll.getChildAt(selectIndex - 1);
                    childAt1.setBackgroundResource(R.drawable.scroll_selecte);
                    childAt1.setTextColor(Color.RED);
                    return;
                }
                childAt.setTextColor(Color.RED);
                childAt.setBackgroundResource(R.drawable.scroll_selecte);
            } else {
                childAt.setTextColor(Color.BLACK);
                childAt.setBackgroundResource(R.drawable.scroll_normal);
            }
        }
        //Log.e("zq", "点击了" + index);
    }

}
