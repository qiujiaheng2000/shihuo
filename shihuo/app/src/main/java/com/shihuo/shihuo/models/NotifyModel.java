package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * Created by cm_qiujiaheng on 2017/1/15.
 * 消息中心模型
 */

public class NotifyModel implements Parcelable {

    public String createTime;
    public String schemeUrl;
    public int type;

    public static NotifyModel parseFromJsonStr(String jsonStr) {
        Gson gson = new Gson();
        NotifyModel notifyModel = gson.fromJson(jsonStr, NotifyModel.class);
        return notifyModel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createTime);
        dest.writeString(this.schemeUrl);
        dest.writeInt(this.type);
    }

    public NotifyModel() {
    }

    protected NotifyModel(Parcel in) {
        this.createTime = in.readString();
        this.schemeUrl = in.readString();
        this.type = in.readInt();
    }

    public static final Parcelable.Creator<NotifyModel> CREATOR = new Parcelable.Creator<NotifyModel>() {
        @Override
        public NotifyModel createFromParcel(Parcel source) {
            return new NotifyModel(source);
        }

        @Override
        public NotifyModel[] newArray(int size) {
            return new NotifyModel[size];
        }
    };
}
