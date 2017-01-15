package com.shihuo.shihuo.Activities.shop.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * Created by cm_qiujiaheng on 2017/1/16.
 * 每日统计模型
 */

public class StatisticsDayModel implements Parcelable {
    public String date;
    public int ordersCnt;
    public float totalAmount;

    public static StatisticsDayModel parseFromJson(String jsonStr) {
        Gson gson = new Gson();
        StatisticsDayModel statisticsDayModel = gson.fromJson(jsonStr, StatisticsDayModel.class);
        return statisticsDayModel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeInt(this.ordersCnt);
        dest.writeFloat(this.totalAmount);
    }

    public StatisticsDayModel() {
    }

    protected StatisticsDayModel(Parcel in) {
        this.date = in.readString();
        this.ordersCnt = in.readInt();
        this.totalAmount = in.readFloat();
    }

    public static final Parcelable.Creator<StatisticsDayModel> CREATOR = new Parcelable.Creator<StatisticsDayModel>() {
        @Override
        public StatisticsDayModel createFromParcel(Parcel source) {
            return new StatisticsDayModel(source);
        }

        @Override
        public StatisticsDayModel[] newArray(int size) {
            return new StatisticsDayModel[size];
        }
    };
}
