
package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lishuai on 16/11/30.
 */

public class HomeModel implements Parcelable {

    public static int ITEM_TYPE = 0;

    public static int ITEM_TYPE_GOODS = 1;

    public int item_type;

    public SysTypeModel typeData;

    public BaseGoodsModel baseGoodsModel;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.item_type);
        dest.writeParcelable(this.typeData, flags);
        dest.writeParcelable(this.baseGoodsModel, flags);
    }

    public HomeModel() {
    }

    protected HomeModel(Parcel in) {
        this.item_type = in.readInt();
        this.typeData = in.readParcelable(SysTypeModel.class.getClassLoader());
        this.baseGoodsModel = in.readParcelable(BaseGoodsModel.class.getClassLoader());
    }

    public static final Parcelable.Creator<HomeModel> CREATOR = new Parcelable.Creator<HomeModel>() {
        @Override
        public HomeModel createFromParcel(Parcel source) {
            return new HomeModel(source);
        }

        @Override
        public HomeModel[] newArray(int size) {
            return new HomeModel[size];
        }
    };
}
