package com.shihuo.shihuo.network;

import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by cm_qiujiaheng on 2016/11/19.
 */

public abstract class ShihuoStringCallback extends StringCallback {


    @Override
    public void onResponse(String response, int id) {
        ShiHuoResponse resp = ShiHuoResponse.parseResponse(response);
        onResponse(resp, id);
    }

    public abstract void onResponse(ShiHuoResponse response, int id);
}
