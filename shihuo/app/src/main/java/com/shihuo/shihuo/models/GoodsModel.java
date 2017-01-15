
package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by jiahengqiu on 2016/10/23. 商品
 */

public class GoodsModel implements Parcelable {

    public String goodsId;// 商品id

    public String storeId;// 店铺id

    public int sysGoodsTypeId;// 官方分类id

    public int goodsTypeId;// 商品类别id

    public String goodsName;// 商品名称

    public String goodsDetail;// 商品描述

    public int isFav;// 是否被收藏

    public float prePrice;// 原价

    public float curPrice;// 当前价格

    public String csPhoneNum;// 富文本详情

    public float score;// 评分

    public int salesNum;// 销量

    public String goodsRichTextDetail;// 富文本详情

    public int isValid;// 1：上架 0：下架

    public String picUrl;// 商品图片

    public int noShipFees;// 是否包邮 1包邮，0不包邮

    public int takeGoods;// 上面取货

    public int courierDelivery;// 快递配送

    public int inventor;// 库存量

    public List<String> goodsSpecList;// 规格数组列表

    public List<String> goodsPicsList;// 商品图片

    public List<String> goodsDetailPicsList;// 商品详情

    public static GoodsModel parseJsonStr(JSONObject jsonObject) {
        Gson gson = new Gson();
        GoodsModel goodsModel = gson.fromJson(String.valueOf(jsonObject), GoodsModel.class);
        return goodsModel;
    }

    public GoodsModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.goodsId);
        dest.writeString(this.storeId);
        dest.writeInt(this.sysGoodsTypeId);
        dest.writeInt(this.goodsTypeId);
        dest.writeString(this.goodsName);
        dest.writeString(this.goodsDetail);
        dest.writeInt(this.isFav);
        dest.writeFloat(this.prePrice);
        dest.writeFloat(this.curPrice);
        dest.writeString(this.csPhoneNum);
        dest.writeFloat(this.score);
        dest.writeInt(this.salesNum);
        dest.writeString(this.goodsRichTextDetail);
        dest.writeInt(this.isValid);
        dest.writeString(this.picUrl);
        dest.writeInt(this.noShipFees);
        dest.writeInt(this.takeGoods);
        dest.writeInt(this.courierDelivery);
        dest.writeInt(this.inventor);
        dest.writeStringList(this.goodsSpecList);
        dest.writeStringList(this.goodsPicsList);
        dest.writeStringList(this.goodsDetailPicsList);
    }

    protected GoodsModel(Parcel in) {
        this.goodsId = in.readString();
        this.storeId = in.readString();
        this.sysGoodsTypeId = in.readInt();
        this.goodsTypeId = in.readInt();
        this.goodsName = in.readString();
        this.goodsDetail = in.readString();
        this.isFav = in.readInt();
        this.prePrice = in.readFloat();
        this.curPrice = in.readFloat();
        this.csPhoneNum = in.readString();
        this.score = in.readFloat();
        this.salesNum = in.readInt();
        this.goodsRichTextDetail = in.readString();
        this.isValid = in.readInt();
        this.picUrl = in.readString();
        this.noShipFees = in.readInt();
        this.takeGoods = in.readInt();
        this.courierDelivery = in.readInt();
        this.inventor = in.readInt();
        this.goodsSpecList = in.createStringArrayList();
        this.goodsPicsList = in.createStringArrayList();
        this.goodsDetailPicsList = in.createStringArrayList();
    }

    public static final Creator<GoodsModel> CREATOR = new Creator<GoodsModel>() {
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
