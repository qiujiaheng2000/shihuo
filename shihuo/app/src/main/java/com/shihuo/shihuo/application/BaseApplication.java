
package com.shihuo.shihuo.application;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.ApplicationController;
import com.bugtags.library.Bugtags;
import com.shihuo.shihuo.Notification.NotificationManager;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.util.AppUtils;
import com.shihuo.shihuo.util.aliyun.AliyunHelper;
import com.shihuo.shihuo.util.pay.PayHelper;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.common.UmLog;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
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

    //各个平台的配置，建议放在全局Application或者程序入口
    {
        //微信
        PlatformConfig.setWeixin("wxdba996120cc451c4", "df2b584a6b0649c676238957b9203a24");
        //豆瓣RENREN平台目前只能在服务器端配置
        //新浪微博
        PlatformConfig.setSinaWeibo("2272984471", "0d4c39d02b7a6e9c55d6bb21c5d0b8bd");
        PlatformConfig.setQQZone("1105841956", "1EtDzH1F6ddfeMGi");
        PlatformConfig.setAlipay("2088521333250291");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
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
        initShare();
        Bugtags.start("71e0943d0fb012baf363f9ec7d7065ca", this, Bugtags.BTGInvocationEventBubble);
        //初始化支付sdk
        PayHelper.init(this);
        NotificationManager.initNotification(getApplicationContext());

        UMShareAPI.get(this);
    }

    private void initPush() {
        final PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setDebugMode(true);

        //sdk开启通知声音
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        // sdk关闭通知声音
//		mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
        // 通知声音由服务端控制
//		mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER);

//		mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
//		mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);


//        UmengMessageHandler messageHandler = new UmengMessageHandler() {
//            /**
//             * 自定义消息的回调方法
//             * */
//            @Override
//            public void dealWithCustomMessage(final Context context, final UMessage msg) {
//                new Handler().post(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        // TODO Auto-generated method stub
//                        // 对自定义消息的处理方式，点击或者忽略
//                        boolean isClickOrDismissed = true;
//                        if (isClickOrDismissed) {
//                            //自定义消息的点击统计
//                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
//                        } else {
//                            //自定义消息的忽略统计
//                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
//                        }
//                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//
//            /**
//             * 自定义通知栏样式的回调方法
//             * */
//            @Override
//            public Notification getNotification(Context context, UMessage msg) {
//                switch (msg.builder_id) {
//                    case 1:
//                        Notification.Builder builder = new Notification.Builder(context);
//                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
//                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
//                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
//                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
//                        myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context, msg));
//                        builder.setContent(myNotificationView)
//                                .setSmallIcon(getSmallIconId(context, msg))
//                                .setTicker(msg.ticker)
//                                .setAutoCancel(true);
//
//                        return builder.getNotification();
//                    default:
//                        //默认为0，若填写的builder_id并不存在，也使用默认。
//                        return super.getNotification(context, msg);
//                }
//            }
//        };
//        mPushAgent.setMessageHandler(messageHandler);

        /**
         * 自定义行为的回调处理
         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
            }
        };
        //使用自定义的NotificationHandler，来结合友盟统计处理消息通知
        //参考http://bbs.umeng.com/thread-11112-1-1.html
        //CustomNotificationHandler notificationClickHandler = new CustomNotificationHandler();
        mPushAgent.setNotificationClickHandler(notificationClickHandler);


        //注册推送服务 每次调用register都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                UmLog.i(TAG, "device token: " + deviceToken);
                //注册成功会返回device token
                String userId = AppShareUitl.getUserInfo(getApplicationContext()).userInfo.userId;
                mPushAgent.addAlias(userId, "userId", new UTrack.ICallBack() {
                    @Override
                    public void onMessage(boolean b, String s) {
                        Log.d("push", s);
                    }
                });
            }

            @Override
            public void onFailure(String s, String s1) {
                UmLog.i(TAG, "register failed: " + s + " " +s1);
            }
        });
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
