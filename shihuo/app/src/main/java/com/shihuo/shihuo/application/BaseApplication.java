
package com.shihuo.shihuo.application;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.ApplicationController;
import com.bugtags.library.Bugtags;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.util.AppUtils;
import com.shihuo.shihuo.util.aliyun.AliyunHelper;
import com.shihuo.shihuo.util.pay.PayHelper;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by jiahengqiu on 2016/10/22.
 */
public class BaseApplication extends ApplicationController {

    private static final String TAG = "BaseApplication";

    public static BaseApplication app;

    private Toast mToast;

    public static BaseApplication getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        app = this;
        AppUtils.initFresco(this);
        initOkHttp();
        initAliyun();
        ZXingLibrary.initDisplayOpinion(this);
        initShare();
        Bugtags.start("71e0943d0fb012baf363f9ec7d7065ca", this, Bugtags.BTGInvocationEventBubble);
        //初始化支付sdk
        PayHelper.init(this);
    }

    private void initAliyun() {
        //初始化阿里云图片上传
        AliyunHelper.getInstance().init(this);
    }

    private void initShare() {
//        PlatformConfig.setWeixin(SettingUtil.WEIXIN_APP_ID, SettingUtil.WEIXIN_APP_SECRET);
//        PlatformConfig.setSinaWeibo(SettingUtil.WEIBO_APP_ID, SettingUtil.WEIBO_APP_SECRET);
//        PlatformConfig.setQQZone(SettingUtil.QQ_APP_ID, SettingUtil.QQ_APP_SECRET);
    }


    private void initOkHttp() {
        // 初始化okhttp
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("okhttp"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS).build();
        OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * 遗弃在小米没有浮窗权限时无法显示的SuperToast,改用系统Toast
     *
     * @param message
     * @param duration
     */
    public void showToast(Object message, int duration) {
        if (message == null || TextUtils.isEmpty(message.toString()))
            return;
        // 用成员变量保存引用,避免多次点击会叠加Toast问题
        if (mToast == null || mToast.getView() == null) {
            mToast = new Toast(getApplicationContext());
            mToast.setView(LayoutInflater.from(getApplicationContext()).inflate(
                    R.layout.view_toast, null));
        }
        ((TextView) mToast.getView().findViewById(R.id.toast_text)).setText(message.toString());
        mToast.setDuration(duration);
        mToast.show();
    }

}
