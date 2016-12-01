
package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lishuai on 16/11/29.
 */

public class BaseModel implements Parcelable {
    public int code;

    public String msg;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.msg);
    }

    public BaseModel() {
    }

    protected BaseModel(Parcel in) {
        this.code = in.readInt();
        this.msg = in.readString();
    }

}
