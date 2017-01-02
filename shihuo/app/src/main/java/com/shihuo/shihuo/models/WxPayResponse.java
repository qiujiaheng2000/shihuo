package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cm_qiujiaheng on 2017/1/2.
 */

public class WxPayResponse implements Parcelable {
    public String appId;
    public String partnerId;
    public String prepayId;
    public String timeStamp;
    public String nonceStr;
    public String pkg;
    public String paySign;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.appId);
        dest.writeString(this.partnerId);
        dest.writeString(this.prepayId);
        dest.writeString(this.timeStamp);
        dest.writeString(this.nonceStr);
        dest.writeString(this.pkg);
        dest.writeString(this.paySign);
    }

    public WxPayResponse() {
    }

    protected WxPayResponse(Parcel in) {
        this.appId = in.readString();
        this.partnerId = in.readString();
        this.prepayId = in.readString();
        this.timeStamp = in.readString();
        this.nonceStr = in.readString();
        this.pkg = in.readString();
        this.paySign = in.readString();
    }

    public static final Parcelable.Creator<WxPayResponse> CREATOR = new Parcelable.Creator<WxPayResponse>() {
        @Override
        public WxPayResponse createFromParcel(Parcel source) {
            return new WxPayResponse(source);
        }

        @Override
        public WxPayResponse[] newArray(int size) {
            return new WxPayResponse[size];
        }
    };
}
