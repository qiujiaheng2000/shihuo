
package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lishuai on 16/11/29.
 */

public class BusinessTypeModel implements Parcelable {
    public int adId;

    public String adName;

    public String adPicUrl;

    public int adType;

    public String redirectUrl;

    public int sort;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.adId);
        dest.writeString(this.adName);
        dest.writeString(this.adPicUrl);
        dest.writeInt(this.adType);
        dest.writeString(this.redirectUrl);
        dest.writeInt(this.sort);
    }

    public BusinessTypeModel() {
    }

    protected BusinessTypeModel(Parcel in) {
        this.adId = in.readInt();
        this.adName = in.readString();
        this.adPicUrl = in.readString();
        this.adType = in.readInt();
        this.redirectUrl = in.readString();
        this.sort = in.readInt();
    }

    public static final Parcelable.Creator<BusinessTypeModel> CREATOR = new Parcelable.Creator<BusinessTypeModel>() {
        @Override
        public BusinessTypeModel createFromParcel(Parcel source) {
            return new BusinessTypeModel(source);
        }

        @Override
        public BusinessTypeModel[] newArray(int size) {
            return new BusinessTypeModel[size];
        }
    };
}
