
package com.shihuo.shihuo.models;

import com.google.gson.Gson;

/**
 * 版本更新 Created by lishuai on 17/1/23.
 */

public class UpdateModel {
    public String versionName;

    public int versionNum;

    public String platform;

    public String createTime;

    public String versionInfo;

    public String downloadUrl;

    public static UpdateModel parseStrJson(String strJson) {
        Gson gson = new Gson();
        UpdateModel model = gson.fromJson(strJson, UpdateModel.class);
        return model;
    }

    public static String parseToJson(UpdateModel model) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(model, UpdateModel.class);
        return jsonStr;
    }

}
