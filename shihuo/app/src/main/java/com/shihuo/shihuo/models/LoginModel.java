package com.shihuo.shihuo.models;

import com.google.gson.Gson;

/**
 * Created by cm_qiujiaheng on 2016/11/27.
 */

public class LoginModel {
    public static final String SHOPLOCATED_VALIDATED = "1";//认证通过
    public static final String SHOPLOCATED_WAITED = "2";//待认证
    public static final String SHOPLOCATED_REJECTED = "3";//被拒绝


    public UserInfoModel userInfo;
    public String token;
    /**
     * 1.入驻成功，2审核中，3被拒绝
     */
    public int isValid;
    public String storeId;

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
