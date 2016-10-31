package com.qf.liwushuo.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.qf.liwushuo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NetLookActivity extends AppCompatActivity {

    @BindView(R.id.web_net)
    WebView webNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_look);
        ButterKnife.bind(this);
        WebSettings settings = webNet.getSettings();
        settings.setJavaScriptEnabled(true);
        webNet.setWebViewClient(new WebViewClient());
        webNet.loadUrl("file:///android_asset/cdncheck/cdn-diagnosis.html");
    }
}
