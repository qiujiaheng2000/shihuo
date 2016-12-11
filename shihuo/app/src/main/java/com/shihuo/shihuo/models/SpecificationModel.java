
package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 商品规格
 * Created by lishuai on 16/12/11.
 */

public class SpecificationModel implements Parcelable {

    public int curPrice;

    public String goodsId;

    public int prePrice;

    public int specId;

    public String specName;

    public int stockNum;

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
    }

    public SpecificationModel() {
    }

    protected SpecificationModel(Parcel in) {
        this.curPrice = in.readInt();
        this.goodsId = in.readString();
        this.prePrice = in.readInt();
        this.specId = in.readInt();
        this.specName = in.readString();
        this.stockNum = in.readInt();
    }

    public static final Parcelable.Creator<SpecificationModel> CREATOR = new Parcelable.Creator<SpecificationModel>() {
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
