package com.shihuo.shihuo.util.pay;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.shihuo.shihuo.Activities.ConfirmOrdersActivity;
import com.shihuo.shihuo.models.WXResponse;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.Map;

/**
 * Created by cm_qiujiaheng on 2017/1/3.
 * 支付工具
 */

public class PayHelper {

    public static final String WX_APPID = "wxdba996120cc451c4";
    private static IWXAPI msgApi;

    public static void init(Context context) {
        msgApi = WXAPIFactory.createWXAPI(context, null);
        msgApi.registerApp(WX_APPID);
    }

    public static void alipay(final Activity context, final String orderStr, final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(context);
                Map<String, String> result = alipay.payV2(orderStr, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = ConfirmOrdersActivity.SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        }
        ).start();
    }

    public static void weixinPay(String payInfoStr) {
        WXResponse wxResponse = WXResponse.parseFormJsonStr(payInfoStr);
        PayReq request = new PayReq();
        request.appId = wxResponse.appId;
        request.partnerId = wxResponse.partnerId;
        request.prepayId = wxResponse.prepayId;
        request.packageValue = wxResponse.pkg;
        request.nonceStr = wxResponse.nonceStr;
        request.timeStamp = wxResponse.timeStamp;
        request.sign = wxResponse.paySign;
        msgApi.sendReq(request);
    }
}


