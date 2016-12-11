package com.shihuo.shihuo.models;

import com.google.gson.Gson;

/**
 * Created by cm_qiujiaheng on 2016/11/27.\
 * 用户登录信息
 */
public class UserInfoModel {
    public String avatarPicUrl;

    public int isValid;

    public String lastLoginIp;

    public String lastLoginTime;

    public String phoneNum;

    public String regTime;

    public String userId;

    public String userName;

    public String storeId;

    public String token;

    public static UserInfoModel parseJson(String userInfoStr) {
        Gson gson = new Gson();
        return gson.fromJson(userInfoStr, UserInfoModel.class);
    }

    public static String getStringFromObject(UserInfoModel userInfoModel) {
        Gson gson = new Gson();
        return gson.toJson(userInfoModel, UserInfoModel.class);
    }

}
