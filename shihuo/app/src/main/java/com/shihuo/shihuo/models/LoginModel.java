package com.shihuo.shihuo.models;

import com.google.gson.Gson;

/**
 * Created by cm_qiujiaheng on 2016/11/27.
 */

public class LoginModel {
    public UserInfoModel userInfo;
    public String token;

    public static LoginModel parseStrJson(String strJson) {
        Gson gson = new Gson();
        LoginModel loginModel = gson.fromJson(strJson, LoginModel.class);
        return loginModel;
    }

    public static String parseToJson(LoginModel loginModel) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(loginModel, LoginModel.class);
        return jsonStr;
    }

}
