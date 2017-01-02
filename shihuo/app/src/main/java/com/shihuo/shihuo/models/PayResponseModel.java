package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * Created by cm_qiujiaheng on 2017/1/2.
 */

public class PayResponseModel implements Parcelable {
    public String paymentNum;//支付流水号，需记录以备支付完成后与服务器确认支付情况

    public String paySign;//支付宝需要的签名，供支付宝前端支付sdk使用
    public WxPayResponse wxPayResponse;//微信支付需要的字段，详情见微信支付sdk


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.paymentNum);
        dest.writeString(this.paySign);
        dest.writeParcelable(this.wxPayResponse, flags);
    }

    public PayResponseModel() {
    }

    protected PayResponseModel(Parcel in) {
        this.paymentNum = in.readString();
        this.paySign = in.readString();
        this.wxPayResponse = in.readParcelable(WxPayResponse.class.getClassLoader());
    }

    public static final Parcelable.Creator<PayResponseModel> CREATOR = new Parcelable.Creator<PayResponseModel>() {
        @Override
        public PayResponseModel createFromParcel(Parcel source) {
            return new PayResponseModel(source);
        }

        @Override
        public PayResponseModel[] newArray(int size) {
            return new PayResponseModel[size];
        }
    };

    public static PayResponseModel parseFromJsonObjStr(String jsonObjStr) {
        Gson gson = new Gson();
        PayResponseModel payResponseModel = gson.fromJson(jsonObjStr, PayResponseModel.class);
        return payResponseModel;
    }
}
