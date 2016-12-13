package com.shihuo.shihuo.network;

import android.util.Log;

import com.shihuo.shihuo.application.BaseApplication;
import com.shihuo.shihuo.util.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cm_qiujiaheng on 2016/11/19.
 * 返回结果
 */
public class ShiHuoResponse {
    public static final int SUCCESS = 0;
    public static final int FAILED_PARSEERROR = 1001;//数据解析错误
    public int code;
    public String data;
    public String msg;

    public String resultList;

    public static ShiHuoResponse parseResponse(String jsonStr) {
        ShiHuoResponse response = new ShiHuoResponse();
        try {
            Log.d("network", "jsonStr = " + jsonStr);
            JSONObject jsonObject = new JSONObject(jsonStr);
            response.code = jsonObject.getInt("code");
            response.data = jsonObject.getString("data");
            if (response.code != SUCCESS) {
                response.msg = jsonObject.getJSONObject("data").getString("msg");
                AppUtils.showToast(BaseApplication.app, response.msg);

            }

            // 适用于list
            JSONObject objectList = new JSONObject(response.data);
            if (objectList.has("page")) {
                JSONObject objectPage = (JSONObject)objectList.get("page");
                if (objectPage.has("resultList")) {
                    response.resultList = objectPage.getString("resultList");
                }
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
