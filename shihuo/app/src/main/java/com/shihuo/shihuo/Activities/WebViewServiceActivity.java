
package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.application.SettingUtil;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

public class WebViewServiceActivity extends BaseActivity {
    private static final String TAG = "WebViewServiceActivity";

    @BindView(R.id.imag_left)
    ImageView imagLeft;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.rightbtn)
    Button rightbtn;

    private int isFav;

    private int cId;

    // 是否收藏过
    private boolean mIsFav;

    public static void start(Context context, String url, int cId) {
        Intent intent = new Intent(context, WebViewServiceActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("cId", cId);
        context.startActivity(intent);
    }

    private WebView webView;

    private FrameLayout customViewContainer;

    private WebChromeClient.CustomViewCallback customViewCallback;

    private View mCustomView;

    private MyWebChromeClient mWebChromeClient;

    private MyWebViewClient mWebViewClient;

    private String url = SettingUtil.URL_DEFAULT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.fullScreenColor(this);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        cId = getIntent().getIntExtra("cId", 0);
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
        webView.loadUrl(url);

        imagLeft.setVisibility(View.VISIBLE);
        rightbtn.setVisibility(View.VISIBLE);
        rightbtn.setBackground(getResources().getDrawable(R.drawable.selector_collect));
        title.setText("运城识货购物网");
        request();
    }

    @Override
    public void initViews() {

    }

    @OnClick({
            R.id.imag_left, R.id.rightbtn
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.rightbtn: // 收藏
                if (mIsFav) {
                    requestFav(NetWorkHelper.API_POST_BIANMIN_UN_COLLECTION + "?token="
                            + AppShareUitl.getToken(WebViewServiceActivity.this));
                } else {
                    requestFav(NetWorkHelper.API_POST_BIANMIN_COLLECTION + "?token="
                            + AppShareUitl.getToken(WebViewServiceActivity.this));
                }
                break;
        }
    }

    private void request() {
        String url = NetWorkHelper.API_POST_BIANMIN_INFO + "?token="
                + AppShareUitl.getToken(WebViewServiceActivity.this);
        if (!mDialog.isShowing())
            mDialog.show();
        try {
            JSONObject params = new JSONObject();
            params.put("cId", cId+"");
            OkHttpUtils
                    .postString()
                    .url(NetWorkHelper.getApiUrl(url))
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(params.toString())
                    .build()
                    .execute(new ShihuoStringCallback() {
                        @Override
                        public void onResponse(ShiHuoResponse response, int id) {
                            if (response.code == ShiHuoResponse.SUCCESS) {
                                try {
                                    JSONObject object = new JSONObject(response.data);
                                    if (object.has("isFav")) {
                                        isFav = object.getInt("isFav");
                                    }
                                    // 设置收藏信息
                                    if (isFav == 0) {
                                        rightbtn.setSelected(false);
                                        mIsFav = false;
                                    } else {
                                        rightbtn.setSelected(true);
                                        mIsFav = true;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            if (mDialog.isShowing())
                                mDialog.dismiss();
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            if (mDialog.isShowing())
                                mDialog.dismiss();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestFav(String url) {
        if (!mDialog.isShowing())
            mDialog.show();
        JSONObject params = new JSONObject();
        try {
            params.put("cId", cId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            OkHttpUtils
                    .postString()
                    .url(NetWorkHelper.getApiUrl(url))
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(params.toString())
                    .build()
                    .execute(new ShihuoStringCallback() {
                        @Override
                        public void onResponse(ShiHuoResponse response, int id) {
                            if (response.code == ShiHuoResponse.SUCCESS) {
                                if (mIsFav) {
                                    mIsFav = false;
                                    rightbtn.setSelected(false);
                                } else {
                                    mIsFav = true;
                                    rightbtn.setSelected(true);
                                }
                            } else {
                                AppUtils.showToast(WebViewServiceActivity.this, "操作失败");
                            }
                            if (mDialog.isShowing())
                                mDialog.dismiss();
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            if (mDialog.isShowing())
                                mDialog.dismiss();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
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
                LayoutInflater inflater = LayoutInflater.from(WebViewServiceActivity.this);
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

    @Override
    public void finish() {
        ViewGroup view = (ViewGroup)getWindow().getDecorView();
        view.removeAllViews();
        super.finish();
    }
}
