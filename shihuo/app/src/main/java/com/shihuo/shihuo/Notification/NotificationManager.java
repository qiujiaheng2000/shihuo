package com.shihuo.shihuo.Notification;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.shihuo.shihuo.application.AppShareUitl;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

/**
 * Created by cm_qiujiaheng on 2017/1/8.
 */

public class NotificationManager {




    public static void initNotification(Context context) {
        initUMengPush(context);

    }

    /**
     * 初始化友盟推送
     */
    private static void initUMengPush(final Context context) {
        final PushAgent mPushAgent = PushAgent.getInstance(context);
        // 应用在前台时否显示通知
        mPushAgent.setNotificaitonOnForeground(true);

        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                String userId = AppShareUitl.getUserInfo(context).userInfo.userId;
                mPushAgent.addAlias(userId, "userId", new UTrack.ICallBack() {
                    @Override
                    public void onMessage(boolean b, String s) {
                        Log.d("push", s);
                    }
                });
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });

        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);

//自动以通知view
//        UmengMessageHandler messageHandler = new UmengMessageHandler() {
//            @Override
//            public Notification getNotification(Context context, UMessage msg) {
//                switch (msg.builder_id) {
//                    case 1:
//                        Notification.Builder builder = new Notification.Builder(context);
//                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(),
//                                R.layout.notification_view);
//                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
//                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
//                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon,
//                                getLargeIcon(context, msg));
//                        myNotificationView.setImageViewResource(R.id.notification_small_icon,
//                                getSmallIconId(context, msg));
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
        //设置免打扰23：00-7:00
        mPushAgent.setNoDisturbMode(23, 0, 7, 0);
        //关闭免打扰
//        mPushAgent.setNoDisturbMode(0, 0, 0, 0);
        //设置冷却时间，在一分钟内同一个应用收到多条信息不提示
        mPushAgent.setMuteDurationSeconds(1);
//        参数number可以设置为0~10之间任意整数。当参数为0时，表示不合并通知。
        mPushAgent.setDisplayNotificationNumber(0);

        /**
         * 客户端控制通知到达响铃、震动、呼吸灯
         有三种状态：
         MsgConstant.NOTIFICATIONPLAYSERVER（服务端控制）
         MsgConstant.NOTIFICATIONPLAYSDKENABLE（客户端允许）
         MsgConstant.NOTIFICATIONPLAYSDKDISABLE（客户端禁止）
         */
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER); //声音
        mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SERVER);//呼吸灯
        mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SERVER);//振动


    }
}
