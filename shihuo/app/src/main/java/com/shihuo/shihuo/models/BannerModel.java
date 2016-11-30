
package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * banner图model
 * Created by lishuai on 16/11/29.
 */

public class BannerModel implements Parcelable {

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

    public BannerModel() {
    }

    protected BannerModel(Parcel in) {
        this.adId = in.readInt();
        this.adName = in.readString();
        this.adPicUrl = in.readString();
        this.adType = in.readInt();
        this.redirectUrl = in.readString();
        this.sort = in.readInt();
    }

    public static final Parcelable.Creator<BannerModel> CREATOR = new Parcelable.Creator<BannerModel>() {
        @Override
        public BannerModel createFromParcel(Parcel source) {
            return new BannerModel(source);
        }

        @Override
        public BannerModel[] newArray(int size) {
            return new BannerModel[size];
        }
    };
}
