
package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lishuai on 16/12/2.
 */

public class BaseListModel implements Parcelable {
    public int pageNum;

    public int pageSize;

    public int totalCount;

    public int totalPage;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.pageNum);
        dest.writeInt(this.pageSize);
        dest.writeInt(this.totalCount);
        dest.writeInt(this.totalPage);
    }

    public BaseListModel() {
    }

    protected BaseListModel(Parcel in) {
        this.pageNum = in.readInt();
        this.pageSize = in.readInt();
        this.totalCount = in.readInt();
        this.totalPage = in.readInt();
    }

    public static final Parcelable.Creator<BaseListModel> CREATOR = new Parcelable.Creator<BaseListModel>() {
        @Override
        public BaseListModel createFromParcel(Parcel source) {
            return new BaseListModel(source);
        }

        @Override
        public BaseListModel[] newArray(int size) {
            return new BaseListModel[size];
        }
    };
}
