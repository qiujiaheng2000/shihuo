package com.shihuo.shihuo.Activities;

<<<<<<< HEAD
=======
import android.app.Activity;
>>>>>>> 566b4431ddfc7f89919abc61be6ac79ed02e8038
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
<<<<<<< HEAD
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.util.AppUtils;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MapActivity  extends BaseActivity {
    private String url;
    private WebView mWebView;

    @BindView(R.id.imag_left)
    ImageView imagLeft;

    @BindView(R.id.title)
    TextView title;

=======

import com.shihuo.shihuo.R;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;


public class MapActivity extends Activity {
    private String url;
    private WebView mWebView;

>>>>>>> 566b4431ddfc7f89919abc61be6ac79ed02e8038
    public static void start(Context context, String url) {
        Intent starter = new Intent(context, MapActivity.class);
        starter.putExtra("url", url);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        AppUtils.fullScreenColor(this);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
=======
        setContentView(R.layout.activity_map);
>>>>>>> 566b4431ddfc7f89919abc61be6ac79ed02e8038
        url = getIntent().getStringExtra("url");
        if (TextUtils.isEmpty(url)) {
            finish();
            return;
        }
        mWebView = (WebView) findViewById(R.id.webView);
        initWebViewSettings();
<<<<<<< HEAD

        imagLeft.setVisibility(View.VISIBLE);
        title.setText("运城识货购物网");
    }

    @OnClick({
            R.id.imag_left
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
        }
    }

    @Override
    public void initViews() {

=======
>>>>>>> 566b4431ddfc7f89919abc61be6ac79ed02e8038
    }

    private void initWebViewSettings() {
        WebSettings webSetting = mWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        //webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        //webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        //webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

        // this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
        // settings 的设计
        mWebView.loadUrl(url);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }
}
