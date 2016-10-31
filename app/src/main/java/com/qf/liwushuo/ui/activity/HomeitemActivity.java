package com.qf.liwushuo.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.qf.liwushuo.R;
import com.qf.liwushuo.bean.HomeItemInfoBean;
import com.qf.liwushuo.presenter.HomeItemInfoPresenter;
import com.qf.liwushuo.ui.view.HomeItemInfoView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeitemActivity extends AppCompatActivity implements HomeItemInfoView {

    int id;
    HomeItemInfoPresenter presenter=new HomeItemInfoPresenter();
    @BindView(R.id.web_item)
    WebView webItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeitem);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra("id", 0);
        presenter.getData(id);
        presenter.attach(this);
    }

    @Override
    public void getInfo(HomeItemInfoBean homeItemInfoBean) {
        String column_bottom = homeItemInfoBean.getData().getContent_html();
        WebSettings ws = webItem.getSettings();
           /* ws.setUseWideViewPort(true);
            ws.setLoadWithOverviewMode(true);*/
        ws.setBuiltInZoomControls(false);
        ws.setSupportZoom(false);
/*           *
             *  设置网页布局类型：
             *  1、LayoutAlgorithm.NARROW_COLUMNS ： 适应内容大小
             *  2、LayoutAlgorithm.SINGLE_COLUMN: 适应屏幕，内容将自动缩放
             **/
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        ws.setDefaultTextEncodingName("utf-8"); //设置文本编码
        ws.setAppCacheEnabled(true);
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);//设置缓存模式</span>
        //webView.loadData(article_content, "text/html;charset=UTF-8", null);
        webItem.loadDataWithBaseURL(null,column_bottom , "text/html", "utf-8", null);
    }
}
