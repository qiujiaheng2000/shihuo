
package com.shihuo.shihuo.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.ProgressWebView;
import com.shihuo.shihuo.application.SettingUtil;

public class WebViewActivity extends Activity {

    private ProgressWebView mWebView;

    private String url = SettingUtil.URL_DEFAULT;

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        mWebView = (ProgressWebView) findViewById(R.id.view_webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        url = getIntent().getStringExtra("url");
        mWebView.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.loadUrl("blank");
    }
}
