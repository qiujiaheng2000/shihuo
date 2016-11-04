package com.shihuo.shihuo.models;

import java.util.ArrayList;

/**
 * Created by cm_qiujiaheng on 2016/11/4.
 * 我的收获地址对象
 */

public class MyAddressModel {

    public String addressId;//地址id
    public String addressUser;//地址收货人
    public String addressPhone;//收货人电话
    public String addressDesc;//收货人具体地址
    public String isDefaultAddress;//是否是默认收货人地址

    public MyAddressModel() {
        super();
    }

    public MyAddressModel(String addressId, String addressUser,
                          String addressPhone, String addressDesc, String isDefaultAddress) {
        this.addressId = addressId;
        this.addressUser = addressUser;
        this.addressPhone = addressPhone;
        this.addressDesc = addressDesc;
        this.isDefaultAddress = isDefaultAddress;
    }

    public static ArrayList<MyAddressModel> getTestDatas(int size) {
        ArrayList<MyAddressModel> datas = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            MyAddressModel addressModel;
            if (i % 2 == 0) {
                addressModel = new MyAddressModel("" + i, "李帅 " + i, "1890000000" + i
                        , "猪八戒村 高老庄，猪八戒一号院，2号楼5单元608室猪八戒村 高老庄，猪八戒一号院，2号楼5单元608室猪八戒村 高老庄，猪八戒一号院，2号楼5单元608室 " + i, "0");
            } else {
                addressModel = new MyAddressModel("" + i, "李帅 " + i, "1890000000" + i
                        , "猪八戒村 高老庄，猪八戒一号院" +
                        "" + i, "0");
            }
            datas.add(addressModel);
        }
        return datas;
    }
}
