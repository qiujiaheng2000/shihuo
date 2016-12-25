
package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 商品规格 Created by lishuai on 16/12/11.
 * 也用用系统分类，和推荐店铺
 */

public class SpecificationModel implements Parcelable {

    public int curPrice;

    public String goodsId;

    public int prePrice;

    public int specId;

    public String specName;

    public int stockNum;

    public String storeName;

    public int circleId;

    public String circleName;

    public String storeId;

    public SpecificationModel() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.curPrice);
        dest.writeString(this.goodsId);
        dest.writeInt(this.prePrice);
        dest.writeInt(this.specId);
        dest.writeString(this.specName);
        dest.writeInt(this.stockNum);
        dest.writeString(this.storeName);
        dest.writeInt(this.circleId);
        dest.writeString(this.circleName);
        dest.writeString(this.storeId);
    }

    protected SpecificationModel(Parcel in) {
        this.curPrice = in.readInt();
        this.goodsId = in.readString();
        this.prePrice = in.readInt();
        this.specId = in.readInt();
        this.specName = in.readString();
        this.stockNum = in.readInt();
        this.storeName = in.readString();
        this.circleId = in.readInt();
        this.circleName = in.readString();
        this.storeId = in.readString();
    }

    public static final Creator<SpecificationModel> CREATOR = new Creator<SpecificationModel>() {
        @Override
        public SpecificationModel createFromParcel(Parcel source) {
            return new SpecificationModel(source);
        }

        @Override
        public SpecificationModel[] newArray(int size) {
            return new SpecificationModel[size];
        }
    };
}
