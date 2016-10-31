package com.qf.liwushuo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.qf.liwushuo.R;
import com.qf.liwushuo.adapter.MyViewPagerAdapter;
import com.qf.liwushuo.bean.MRTJBean;
import com.qf.liwushuo.ui.activity.MainActivity;
import com.qf.liwushuo.utils.UrlUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import static com.qf.liwushuo.R.id.iv_head;

/**
 * Created by Administrator on 2016/10/24 0024.
 *
 * 榜单
 */

public class ListFragment extends Fragment{

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> list;
    private MyViewPagerAdapter myViewPagerAdapter;
    private List<String> titleList;
    private Button share_list;
    MRTJBean mrtjBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.Ext.init(getActivity().getApplication());
        x.Ext.setDebug(true);
        x.view().inject(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list1, container, false);

        ShareSDK.initSDK(getActivity().getApplicationContext());//初始化sharesdk
        share_list= (Button) view.findViewById(R.id.share_list);
        mTabLayout = (TabLayout) view.findViewById(R.id.tabs);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);


        loadData();
        share_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnekeyShare oks = new OnekeyShare();
                //关闭sso授权
                oks.disableSSOWhenAuthorize();
                // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
                //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
                // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
                oks.setTitle("标题");
                // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                oks.setTitleUrl("http://sharesdk.cn");
                // text是分享文本，所有平台都需要这个字段
                oks.setText(mrtjBean.getData().getShare_url());
                oks.setSilent(true);   //隐藏编辑页面
                //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
                //oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                // url仅在微信（包括好友和朋友圈）中使用
                oks.setUrl("http://sharesdk.cn");
                // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                oks.setComment("我是测试评论文本");
                // site是分享此内容的网站名称，仅在QQ空间使用
                oks.setSite("ShareSDK");
                // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                oks.setSiteUrl("http://sharesdk.cn");
                // 启动分享GUI
                oks.show(getActivity());

            }
        });

        list = new ArrayList<>();
        list.add(new MRTJFragment());
        list.add(new TOP100Fragment());
        list.add(new DependenceFragment());
        list.add(new NewStarsFragment());
        titleList = new ArrayList<>();
        titleList.add("每日推荐");
        titleList.add("Top100");
        titleList.add("独立原创榜");
        titleList.add("新星榜");
        myViewPagerAdapter = new MyViewPagerAdapter(
                getChildFragmentManager(), list, titleList);
        mViewPager.setAdapter(myViewPagerAdapter);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //mTabLayout居中
        //mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }
    private void loadData() {
        RequestParams params = new RequestParams(UrlUtil.MRTJ);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                mrtjBean = new Gson().fromJson(result, MRTJBean.class);
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
}
