package com.shihuo.shihuo.util.pay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.shihuo.shihuo.Activities.ConfirmOrdersActivity;

import java.util.Map;

/**
 * Created by cm_qiujiaheng on 2017/1/3.
 * 支付工具
 */

public class PayHelper {

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

}


