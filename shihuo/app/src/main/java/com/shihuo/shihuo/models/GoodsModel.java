
package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jiahengqiu on 2016/10/23. 商品
 */

public class GoodsModel implements Parcelable {

    public int courierDelivery;

    public int curPrice;

    public String goodsDetail;

    public String goodsId;

    public String goodsName;

    public String goodsRichTextDetail;

    public int goodsTypeId;

    public int isValid;

    public int noShipFees;

    public String picUrl;

    public int prePrice;

    public int salesNum;

    public int score;

    public String storeId;

    public int sysGoodsTypeId;

    public int takeGoods;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.courierDelivery);
        dest.writeInt(this.curPrice);
        dest.writeString(this.goodsDetail);
        dest.writeString(this.goodsId);
        dest.writeString(this.goodsName);
        dest.writeString(this.goodsRichTextDetail);
        dest.writeInt(this.goodsTypeId);
        dest.writeInt(this.isValid);
        dest.writeInt(this.noShipFees);
        dest.writeString(this.picUrl);
        dest.writeInt(this.prePrice);
        dest.writeInt(this.salesNum);
        dest.writeInt(this.score);
        dest.writeString(this.storeId);
        dest.writeInt(this.sysGoodsTypeId);
        dest.writeInt(this.takeGoods);
    }

    public GoodsModel() {
    }

    protected GoodsModel(Parcel in) {
        this.courierDelivery = in.readInt();
        this.curPrice = in.readInt();
        this.goodsDetail = in.readString();
        this.goodsId = in.readString();
        this.goodsName = in.readString();
        this.goodsRichTextDetail = in.readString();
        this.goodsTypeId = in.readInt();
        this.isValid = in.readInt();
        this.noShipFees = in.readInt();
        this.picUrl = in.readString();
        this.prePrice = in.readInt();
        this.salesNum = in.readInt();
        this.score = in.readInt();
        this.storeId = in.readString();
        this.sysGoodsTypeId = in.readInt();
        this.takeGoods = in.readInt();
    }

    public static final Parcelable.Creator<GoodsModel> CREATOR = new Parcelable.Creator<GoodsModel>() {
        @Override
        public GoodsModel createFromParcel(Parcel source) {
            return new GoodsModel(source);
        }

        @Override
        public GoodsModel[] newArray(int size) {
            return new GoodsModel[size];
        }
    };
}
