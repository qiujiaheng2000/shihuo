
package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by lishuai on 16/12/15.
 */

public class StoreDetailModel implements Parcelable {

    public String bankCardNum;

    public String bankName;

    public int browseNum;

    public String businessTime;

    public int circleId;

    public String circleName;
    public String csPhoneNum;

    public String distributionTime;

    public String holderIdNum;

    public String holderName;

    public String holderPhoneNum;

    public String idCardBackPicUrl;

    public String idCardFrontPicUrl;

    public String idCardSelfieUrl;

    public int isRecommended;

    public int isValid;

    public int orderNum;

    public String storeAddress;

    public String storeAnnouncement;

    public String storeDetail;

    public int storeFreeShippingPrice;

    public String storeId;

    public String storeLogoPicUrl;

    public String storeName;

    public int sysGoodTypeId;

    public StoreDetailModel() {
    }

    public static StoreDetailModel parseJsonStr(JSONObject jsonObject) {
        Gson gson = new Gson();
        StoreDetailModel storeDetailModel = gson.fromJson(jsonObject.toString(), StoreDetailModel.class);
        return storeDetailModel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bankCardNum);
        dest.writeString(this.bankName);
        dest.writeInt(this.browseNum);
        dest.writeString(this.businessTime);
        dest.writeInt(this.circleId);
        dest.writeString(this.circleName);
        dest.writeString(this.csPhoneNum);
        dest.writeString(this.distributionTime);
        dest.writeString(this.holderIdNum);
        dest.writeString(this.holderName);
        dest.writeString(this.holderPhoneNum);
        dest.writeString(this.idCardBackPicUrl);
        dest.writeString(this.idCardFrontPicUrl);
        dest.writeString(this.idCardSelfieUrl);
        dest.writeInt(this.isRecommended);
        dest.writeInt(this.isValid);
        dest.writeInt(this.orderNum);
        dest.writeString(this.storeAddress);
        dest.writeString(this.storeAnnouncement);
        dest.writeString(this.storeDetail);
        dest.writeInt(this.storeFreeShippingPrice);
        dest.writeString(this.storeId);
        dest.writeString(this.storeLogoPicUrl);
        dest.writeString(this.storeName);
        dest.writeInt(this.sysGoodTypeId);
    }

    protected StoreDetailModel(Parcel in) {
        this.bankCardNum = in.readString();
        this.bankName = in.readString();
        this.browseNum = in.readInt();
        this.businessTime = in.readString();
        this.circleId = in.readInt();
        this.circleName = in.readString();
        this.csPhoneNum = in.readString();
        this.distributionTime = in.readString();
        this.holderIdNum = in.readString();
        this.holderName = in.readString();
        this.holderPhoneNum = in.readString();
        this.idCardBackPicUrl = in.readString();
        this.idCardFrontPicUrl = in.readString();
        this.idCardSelfieUrl = in.readString();
        this.isRecommended = in.readInt();
        this.isValid = in.readInt();
        this.orderNum = in.readInt();
        this.storeAddress = in.readString();
        this.storeAnnouncement = in.readString();
        this.storeDetail = in.readString();
        this.storeFreeShippingPrice = in.readInt();
        this.storeId = in.readString();
        this.storeLogoPicUrl = in.readString();
        this.storeName = in.readString();
        this.sysGoodTypeId = in.readInt();
    }

    public static final Creator<StoreDetailModel> CREATOR = new Creator<StoreDetailModel>() {
        @Override
        public StoreDetailModel createFromParcel(Parcel source) {
            return new StoreDetailModel(source);
        }

        @Override
        public StoreDetailModel[] newArray(int size) {
            return new StoreDetailModel[size];
        }
    };
}
