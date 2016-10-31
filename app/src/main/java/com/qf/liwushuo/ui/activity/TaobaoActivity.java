package com.qf.liwushuo.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.qf.liwushuo.R;

/**
 * Created by Bill on 2016/10/27.
 */

public class TaobaoActivity extends AppCompatActivity {

    //@ViewInject(R.id.wv_taobao)
    private WebView wv_taobao;
    private String taobao="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taobaoweb);

        setupView();
    }

    private void setupView() {
        wv_taobao= (WebView) findViewById(R.id.wv_taobao);
        String url = getIntent().getStringExtra("taobao");

       /* if(checkPackage("com.taobao.taobao")){
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri uri = Uri.parse(url);
            intent.setData(uri);
            startActivity(intent);
        }else{*/
            wv_taobao.setWebViewClient(new WebViewClient());
            wv_taobao.getSettings().setJavaScriptEnabled(true);
            wv_taobao.loadUrl(url);
       /* }*/
    }
    /**

     * 检测该包名所对应的应用是否存在

     * @param packageName

     * @return

     */

    public boolean checkPackage(String packageName)

    {

        if (packageName == null || "".equals(packageName))

            return false;

        try

        {

            this.getPackageManager().getApplicationInfo(packageName, PackageManager
                    .GET_UNINSTALLED_PACKAGES);

            return true;

        }

        catch (PackageManager.NameNotFoundException e)

        {

            return false;

        }

    }
}
