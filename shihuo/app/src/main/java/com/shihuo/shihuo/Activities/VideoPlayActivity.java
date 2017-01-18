
package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.util.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 使用WebView播放视频时需要注意的地方： 1、加网络访问权限（及其他所需要的权限）；
 * 2、WebViewClient中方法shouldOverrideUrlLoading可用来实现点击webView页面的链接；
 * 3、WebView中播放视频需要添加webView.setWebChromeClient(new WebChromeClient())；
 * 4、视频竖屏时，点击全屏，想要切换到横屏全屏的状态，那么必须在Manifest.xml配置文件该Activity的
 * 配置文件中添加android:configChanges="orientation|screenSize"语句。
 * 5、如果视频不能播放，或者播放比较卡，可以采用硬件加速，即在Application，或所在的Activity的配置文件中添加
 * android:hardwareAccelerated="true"即可。
 *
 * @author zhongyao
 */
public class VideoPlayActivity extends BaseActivity {
    private static final String TAG = "VideoPlayActivity";

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, VideoPlayActivity.class);
        intent.putExtra("url", url);
        // Bundle bundle = new Bundle();
        // bundle.putString("url", url);
        // intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private boolean isCollection;

    private String url;

    private boolean isSlected;

    @BindView(R.id.imag_left)
    ImageView leftbtn;

    @BindView(R.id.title)
    TextView title;

    private WebView webView;

    private FrameLayout customViewContainer;

    private WebChromeClient.CustomViewCallback customViewCallback;

    private View mCustomView;

    private MyWebChromeClient mWebChromeClient;

    private MyWebViewClient mWebViewClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.fullScreenColor(this);
        setContentView(R.layout.activity_video_detail);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");

        title.setText("微视频详情");
        leftbtn.setVisibility(View.VISIBLE);

        webView = (WebView)findViewById(R.id.webView);
        customViewContainer = (FrameLayout)findViewById(R.id.customViewContainer);
        mWebViewClient = new MyWebViewClient();
        webView.setWebViewClient(mWebViewClient);

        mWebChromeClient = new MyWebChromeClient();
        webView.setWebChromeClient(mWebChromeClient);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSaveFormData(true);
        if (!TextUtils.isEmpty(url)) {
            // webView.loadUrl("http://player.youku.com/player.php/sid/XMTcxMDE1NzM5Ng==/v.swf");
            // http://v.youku.com/v_show/XMjM2NTk4NzUyOA==.html
            // http://player.youku.com/player.php/sid/XMTkyMTM5MDM5Ng==/v.swf
//            http://player.youku.com/embed/
            String urlFinal = url;
            urlFinal = url.replace("http://v.youku.com/v_show/id_", "");
            urlFinal = urlFinal.replace(".html", "");
            webView.loadUrl("http://player.youku.com/embed/" + urlFinal);
        }
    }

    @Override
    public void initViews() {

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
    protected void onDestroy() {
        super.onDestroy();
        webView.loadUrl("about:blank");
        webView.stopLoading();
        webView.setWebChromeClient(null);
        webView.setWebViewClient(null);
        webView.destroy();
        webView = null;
    }

    public boolean inCustomView() {
        return (mCustomView != null);
    }

    public void hideCustomView() {
        mWebChromeClient.onHideCustomView();
    }

    @Override
    protected void onPause() {
        super.onPause(); // To change body of overridden methods use File |
                         // Settings | File Templates.
        webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume(); // To change body of overridden methods use File |
                          // Settings | File Templates.
        webView.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop(); // To change body of overridden methods use File |
                        // Settings | File Templates.
        if (inCustomView()) {
            hideCustomView();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (inCustomView()) {
                hideCustomView();
                return true;
            }

            if ((mCustomView == null) && webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    class MyWebChromeClient extends WebChromeClient {
        private Bitmap mDefaultVideoPoster;

        private View mVideoProgressView;

        @Override
        public void onShowCustomView(View view, int requestedOrientation,
                CustomViewCallback callback) {
            onShowCustomView(view, callback); // To change body of overridden
                                              // methods use File | Settings |
                                              // File Templates.
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {

            // if a view already exists then immediately terminate the new one
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mCustomView = view;
            webView.setVisibility(View.GONE);
            customViewContainer.setVisibility(View.VISIBLE);
            customViewContainer.addView(view);
            customViewCallback = callback;
        }

        @Override
        public View getVideoLoadingProgressView() {

            if (mVideoProgressView == null) {
                LayoutInflater inflater = LayoutInflater.from(VideoPlayActivity.this);
                mVideoProgressView = inflater.inflate(R.layout.video_progress, null);
            }
            return mVideoProgressView;
        }

        @Override
        public void onHideCustomView() {
            super.onHideCustomView();
            if (mCustomView == null)
                return;

            webView.setVisibility(View.VISIBLE);
            customViewContainer.setVisibility(View.GONE);
            mCustomView.setVisibility(View.GONE);
            customViewContainer.removeView(mCustomView);
            customViewCallback.onCustomViewHidden();

            mCustomView = null;
        }
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
}
