package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cm_qiujiaheng on 2016/11/4.
 * 我的收获地址对象
 */

public class MyAddressModel implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.addressId);
        dest.writeString(this.receiverName);
        dest.writeString(this.addressZone);
        dest.writeString(this.addressDetail);
        dest.writeString(this.receiverPhoneNum);
        dest.writeString(this.isDefaultAddress);
    }

    protected MyAddressModel(Parcel in) {
        this.userId = in.readString();
        this.addressId = in.readString();
        this.receiverName = in.readString();
        this.addressZone = in.readString();
        this.addressDetail = in.readString();
        this.receiverPhoneNum = in.readString();
        this.isDefaultAddress = in.readString();
    }

    public static final Parcelable.Creator<MyAddressModel> CREATOR = new Parcelable.Creator<MyAddressModel>() {
        @Override
        public MyAddressModel createFromParcel(Parcel source) {
            return new MyAddressModel(source);
        }

        @Override
        public MyAddressModel[] newArray(int size) {
            return new MyAddressModel[size];
        }
    };
}
