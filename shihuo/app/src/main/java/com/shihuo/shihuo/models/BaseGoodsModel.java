
package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lishuai on 16/11/30.
 */

public class BaseGoodsModel implements Parcelable {
    public GoodsModel goodsLeftModel;

    public GoodsModel goodsRightModel;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.goodsLeftModel, flags);
        dest.writeParcelable(this.goodsRightModel, flags);
    }

    public BaseGoodsModel() {
    }

    protected BaseGoodsModel(Parcel in) {
        this.goodsLeftModel = in.readParcelable(GoodsModel.class.getClassLoader());
        this.goodsRightModel = in.readParcelable(GoodsModel.class.getClassLoader());
    }

    public static final Parcelable.Creator<BaseGoodsModel> CREATOR = new Parcelable.Creator<BaseGoodsModel>() {
        @Override
        public BaseGoodsModel createFromParcel(Parcel source) {
            return new BaseGoodsModel(source);
        }

        @Override
        public BaseGoodsModel[] newArray(int size) {
            return new BaseGoodsModel[size];
        }
    };
}
