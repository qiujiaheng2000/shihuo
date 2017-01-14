package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * Created by cm_qiujiaheng on 2016/10/30.
 * 便民服务对象
 */
public class ServiceModel implements Parcelable {
    public String cName;
    public String cDetail;
    public String browseNum;
    public String createTime;
    public int cId;
    public int isFav;
    public int cTypeId;
    public String linkUrl;
    public String imgUrl;


    public static ServiceModel parseFromJsonStr(String jsonStr) {
        Gson gson = new Gson();
        ServiceModel serviceModel = gson.fromJson(jsonStr, ServiceModel.class);
        return serviceModel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cName);
        dest.writeString(this.cDetail);
        dest.writeString(this.browseNum);
        dest.writeString(this.createTime);
        dest.writeInt(this.cId);
        dest.writeInt(this.isFav);
        dest.writeInt(this.cTypeId);
        dest.writeString(this.linkUrl);
        dest.writeString(this.imgUrl);
    }

    public ServiceModel() {
    }

    protected ServiceModel(Parcel in) {
        this.cName = in.readString();
        this.cDetail = in.readString();
        this.browseNum = in.readString();
        this.createTime = in.readString();
        this.cId = in.readInt();
        this.isFav = in.readInt();
        this.cTypeId = in.readInt();
        this.linkUrl = in.readString();
        this.imgUrl = in.readString();
    }

    public static final Parcelable.Creator<ServiceModel> CREATOR = new Parcelable.Creator<ServiceModel>() {
        @Override
        public ServiceModel createFromParcel(Parcel source) {
            return new ServiceModel(source);
        }

        @Override
        public ServiceModel[] newArray(int size) {
            return new ServiceModel[size];
        }
    };
}
