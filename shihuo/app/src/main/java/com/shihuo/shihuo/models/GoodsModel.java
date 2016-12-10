
package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by jiahengqiu on 2016/10/23. 商品
 */

public class GoodsModel implements Parcelable {

    public int courierDelivery;//快递配送

    public float curPrice;//当前价格

    public String goodsDetail;//商品描述

    public String goodsId;//商品id

    public String goodsName;//商品名称

    public String goodsRichTextDetail;//富文本详情

    public int goodsTypeId;//商品类别id

    public int isValid;//1：上架  0：下架

    public int noShipFees;//是否包邮  1包邮，0不包邮

    public String picUrl;//商品图片

    public float prePrice;//原价

    public int salesNum;//销量

    public int score;//评分

    public String storeId;//店铺id

    public int sysGoodsTypeId;//官方分类id

    public int takeGoods;//上面取货

    public int inventor;//库存量

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.courierDelivery);
        dest.writeFloat(this.curPrice);
        dest.writeString(this.goodsDetail);
        dest.writeString(this.goodsId);
        dest.writeString(this.goodsName);
        dest.writeString(this.goodsRichTextDetail);
        dest.writeInt(this.goodsTypeId);
        dest.writeInt(this.isValid);
        dest.writeInt(this.noShipFees);
        dest.writeString(this.picUrl);
        dest.writeFloat(this.prePrice);
        dest.writeInt(this.salesNum);
        dest.writeInt(this.score);
        dest.writeString(this.storeId);
        dest.writeInt(this.sysGoodsTypeId);
        dest.writeInt(this.takeGoods);
        dest.writeInt(this.inventor);
    }

    public GoodsModel() {
    }

    protected GoodsModel(Parcel in) {
        this.courierDelivery = in.readInt();
        this.curPrice = in.readFloat();
        this.goodsDetail = in.readString();
        this.goodsId = in.readString();
        this.goodsName = in.readString();
        this.goodsRichTextDetail = in.readString();
        this.goodsTypeId = in.readInt();
        this.isValid = in.readInt();
        this.noShipFees = in.readInt();
        this.picUrl = in.readString();
        this.prePrice = in.readFloat();
        this.salesNum = in.readInt();
        this.score = in.readInt();
        this.storeId = in.readString();
        this.sysGoodsTypeId = in.readInt();
        this.takeGoods = in.readInt();
        this.inventor = in.readInt();
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

    public static GoodsModel parseJsonStr(JSONObject jsonObject) {
        Gson gson = new Gson();
        GoodsModel goodsModel = gson.fromJson(String.valueOf(jsonObject), GoodsModel.class);
        return goodsModel;
    }
}
