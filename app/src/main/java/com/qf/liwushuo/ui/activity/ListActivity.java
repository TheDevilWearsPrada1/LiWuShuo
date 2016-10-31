package com.qf.liwushuo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.qf.liwushuo.R;
import com.qf.liwushuo.adapter.MyViewPagerAdapter;
import com.qf.liwushuo.bean.ListItemBean;
import com.qf.liwushuo.bean.MRTJBean;
import com.qf.liwushuo.ui.fragment.AloneFragment;
import com.qf.liwushuo.ui.fragment.CommentFragment;
import com.qf.liwushuo.ui.fragment.DetailsFragment;
import com.qf.liwushuo.utils.UrlUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import static com.qf.liwushuo.R.id.btn;
import static com.qf.liwushuo.R.id.start;

/**
 * Created by Bill on 2016/10/26.
 */

public class ListActivity extends AppCompatActivity {

    @ViewInject(R.id.btn)
    private Button btn;
    @ViewInject(R.id.btnshop)
    private Button btnshop;
    @ViewInject(R.id.back_list)
    private Button back_list;
    @ViewInject(R.id.tab_item)
    private TabLayout mTabLayout;
    @ViewInject(R.id.viewpagers)
    private ViewPager mViewPager;

    private List<Fragment> list;
    private MyViewPagerAdapter myViewPagerAdapter;
    private List<String> titleList;
    private String item_list="";
    ListItemBean listItemBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        x.Ext.init(getApplication());
        x.Ext.setDebug(true);
        x.view().inject(this);

        item_list = UrlUtil.Webs_List + getIntent().getStringExtra("id_list");
        ShareSDK.initSDK(getApplicationContext());//初始化sharesdk

        list = new ArrayList<>();
        list.add(new AloneFragment());
        list.add(new DetailsFragment());
        list.add(new CommentFragment());
        titleList = new ArrayList<>();
        titleList.add("单品");
        titleList.add("详情");
        titleList.add("评论");
        myViewPagerAdapter = new MyViewPagerAdapter(
                getSupportFragmentManager(), list, titleList);
        mViewPager.setAdapter(myViewPagerAdapter);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        mTabLayout.setupWithViewPager(mViewPager);

        back_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loadData();
        btn.setOnClickListener(new View.OnClickListener() {
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
                oks.setText(listItemBean.getData().getUrl());
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
                oks.show(ListActivity.this);
            }
        });
        btnshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ListActivity.this,TaobaoActivity.class);
                intent.putExtra("taobao",listItemBean.getData().getPurchase_url());
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        RequestParams params = new RequestParams(item_list);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                listItemBean = new Gson().fromJson(result, ListItemBean.class);
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
