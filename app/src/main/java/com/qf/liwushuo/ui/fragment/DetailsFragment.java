package com.qf.liwushuo.ui.fragment;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.qf.liwushuo.R;
import com.qf.liwushuo.bean.ListItemBean;
import com.qf.liwushuo.utils.UrlUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Bill on 2016/10/26.
 */

public class DetailsFragment extends BaseFragment1 {

    WebView wv;
    ListItemBean listItemBean;
    private String item_list="";

    @Override
    protected int getContentView() {
        return R.layout.detailsweb;
    }

    @Override
    protected void init(View view) {
        super.init(view);

        item_list= UrlUtil.Webs_List+getActivity().getIntent().getStringExtra("id_list");
        wv= (WebView) view.findViewById(R.id.wv);
        loadData();
    }

    private void setupView(String str) {
        WebSettings ws = wv.getSettings();
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
        wv.loadDataWithBaseURL(null,str , "text/html", "utf-8", null);
    }

    private void loadData() {
        RequestParams params = new RequestParams(item_list);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                listItemBean = new Gson().fromJson(result, ListItemBean.class);
                setupView(listItemBean.getData().getDetail_html());
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
