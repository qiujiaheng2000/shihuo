
package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 商品分类 Created by lishuai on 16/11/29.
 */

public class GoodsTypeModel implements Parcelable {

    public String createTime;

    public int isValid;

    public String logo;

    public int orderNum;

    public int typeId;

    public String typeName;

    public String typeUrl;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createTime);
        dest.writeInt(this.isValid);
        dest.writeString(this.logo);
        dest.writeInt(this.orderNum);
        dest.writeInt(this.typeId);
        dest.writeString(this.typeName);
        dest.writeString(this.typeUrl);
    }

    public GoodsTypeModel() {
    }

    protected GoodsTypeModel(Parcel in) {
        this.createTime = in.readString();
        this.isValid = in.readInt();
        this.logo = in.readString();
        this.orderNum = in.readInt();
        this.typeId = in.readInt();
        this.typeName = in.readString();
        this.typeUrl = in.readString();
    }

    public static final Parcelable.Creator<GoodsTypeModel> CREATOR = new Parcelable.Creator<GoodsTypeModel>() {
        @Override
        public GoodsTypeModel createFromParcel(Parcel source) {
            return new GoodsTypeModel(source);
        }

        @Override
        public GoodsTypeModel[] newArray(int size) {
            return new GoodsTypeModel[size];
        }
    };
}
