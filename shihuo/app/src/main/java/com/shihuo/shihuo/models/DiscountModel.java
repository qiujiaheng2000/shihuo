
package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 折扣区model Created by lishuai on 16/11/29.
 */

public class DiscountModel implements Parcelable {

    public String detail;

    public int discountId;

    public String discountName;

    public String discountPicUrl;

    public int discountType;

    public int orderNum;

    public String redirectSchemaUrl;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.detail);
        dest.writeInt(this.discountId);
        dest.writeString(this.discountName);
        dest.writeString(this.discountPicUrl);
        dest.writeInt(this.discountType);
        dest.writeInt(this.orderNum);
        dest.writeString(this.redirectSchemaUrl);
    }

    public DiscountModel() {
    }

    protected DiscountModel(Parcel in) {
        this.detail = in.readString();
        this.discountId = in.readInt();
        this.discountName = in.readString();
        this.discountPicUrl = in.readString();
        this.discountType = in.readInt();
        this.orderNum = in.readInt();
        this.redirectSchemaUrl = in.readString();
    }

    public static final Parcelable.Creator<DiscountModel> CREATOR = new Parcelable.Creator<DiscountModel>() {
        @Override
        public DiscountModel createFromParcel(Parcel source) {
            return new DiscountModel(source);
        }

        @Override
        public DiscountModel[] newArray(int size) {
            return new DiscountModel[size];
        }
    };
}
