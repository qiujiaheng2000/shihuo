package com.shihuo.shihuo.models;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cm_qiujiaheng on 2016/11/4.
 * 我的收获地址对象
 */

public class MyAddressModel implements Serializable {

    public String userId;//用户id
    public String addressId;//地址id
    public String receiverName;//地址收货人
    public String addressZone;//收获地址省市区
    public String addressDetail;//收货人具体地址
    public String receiverPhoneNum;//收货人电话

    public String isDefaultAddress;//是否是默认收货人地址

    public MyAddressModel() {
        super();
    }

    public static MyAddressModel parseFormJson(String jsonStr) {
        Gson gson = new Gson();
        MyAddressModel addressModel = gson.fromJson(jsonStr, MyAddressModel.class);
        return addressModel;
    }

}
