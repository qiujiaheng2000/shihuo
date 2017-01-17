package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * Created by cm_qiujiaheng on 2017/1/17.
 * 提现记录模型
 */
public class ReflectHistoryModel implements Parcelable {
    public String createTime;
    public int amount;
    public int status;


    public static ReflectHistoryModel parseFromJsonStr(String jsonStr){
        Gson gson = new Gson();
        ReflectHistoryModel reflectHistoryModel = gson.fromJson(jsonStr, ReflectHistoryModel.class);
        return reflectHistoryModel;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createTime);
        dest.writeInt(this.amount);
        dest.writeInt(this.status);
    }

    public ReflectHistoryModel() {
    }

    protected ReflectHistoryModel(Parcel in) {
        this.createTime = in.readString();
        this.amount = in.readInt();
        this.status = in.readInt();
    }

    public static final Parcelable.Creator<ReflectHistoryModel> CREATOR = new Parcelable.Creator<ReflectHistoryModel>() {
        @Override
        public ReflectHistoryModel createFromParcel(Parcel source) {
            return new ReflectHistoryModel(source);
        }

        @Override
        public ReflectHistoryModel[] newArray(int size) {
            return new ReflectHistoryModel[size];
        }
    };
}
