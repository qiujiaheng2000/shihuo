package com.shihuo.shihuo.network;

import android.util.Log;

import com.shihuo.shihuo.application.BaseApplication;
import com.shihuo.shihuo.util.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cm_qiujiaheng on 2016/11/19.
 * list返回结果
 */
public class ShiHuoListResponse {
    public static final int SUCCESS = 0;
    public static final int FAILED_PARSEERROR = 1001;//数据解析错误
    public int code;
    public String data;
    public String msg;

    public static ShiHuoListResponse parseResponse(String jsonStr) {
        ShiHuoListResponse response = new ShiHuoListResponse();
        try {
            Log.d("network", "jsonStr = " + jsonStr);
            JSONObject jsonObject = new JSONObject(jsonStr);
            response.code = jsonObject.getInt("code");
            response.data = jsonObject.getString("data");
//            response.data = jsonObject.getString("data");
            if (response.code != SUCCESS) {
                response.msg = jsonObject.getJSONObject("data").getString("msg");
                AppUtils.showToast(BaseApplication.app, response.msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            response.code = FAILED_PARSEERROR;
            response.msg = "数据解析错误";
            AppUtils.showToast(BaseApplication.app, "数据解析错误");
            return response;
        }
        return response;
    }
}
