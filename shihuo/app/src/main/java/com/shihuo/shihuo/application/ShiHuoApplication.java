package com.shihuo.shihuo.application;

import com.shihuo.shihuo.util.AppUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import android.app.Application;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by jiahengqiu on 2016/10/22.
 */
public class ShiHuoApplication extends Application {
    public static ShiHuoApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        //初始化okhttp
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("okhttp"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);

        AppUtils.initFresco(this);

    }

    public static ShiHuoApplication getInstance() {
        return app;
    }
}
