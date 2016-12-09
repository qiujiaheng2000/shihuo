
package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lishuai on 16/12/8.
 */

public class GoodsDetailModel implements Parcelable {

    public static GoodsDetailModel parseStrJson(String strJson) {
        Gson gson = new Gson();
        GoodsDetailModel model = gson.fromJson(strJson, GoodsDetailModel.class);
        return model;
    }

    public static String parseToJson(GoodsDetailModel modle) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(modle, GoodsDetailModel.class);
        return jsonStr;
    }

    public String goodsId;

    public String isValid;

    public String csPhoneNum;

    public int salesNum;

    public int isFav;

    public int takeGoods;

    public String storeId;

    public String score;

    public int courierDelivery;

    public int goodsTypeId;

    public int noShipFees;

    public int sysGoodsTypeId;

    public String goodsRichTextDetail;

    public String circleName;

    public String goodsName;

    public float curPrice;

    public float prePrice;

    public String goodsDetail;

    public List<GoodsSpecListEntity> goodsSpecList;

    public List<GoodsPicsListEntity> goodsPicsList;

    public List<GoodsDetailPicsListEntity> goodsDetailPicsList;

    public static class GoodsSpecListEntity {

        public int curPrice;

        public String goodsId;

        public int prePrice;

        public int specId;

        public String specName;

        public int stockNum;
    }

    public static class GoodsPicsListEntity {

        public String goodsId;

        public int picId;

        public int picType;

        public String picUrl;
    }

    public static class GoodsDetailPicsListEntity {

        public String goodsId;

        public int picId;

        public int picType;

        public String picUrl;

    }

    public GoodsDetailModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.goodsId);
        dest.writeString(this.isValid);
        dest.writeString(this.csPhoneNum);
        dest.writeInt(this.salesNum);
        dest.writeInt(this.isFav);
        dest.writeInt(this.takeGoods);
        dest.writeString(this.storeId);
        dest.writeString(this.score);
        dest.writeInt(this.courierDelivery);
        dest.writeInt(this.goodsTypeId);
        dest.writeInt(this.noShipFees);
        dest.writeInt(this.sysGoodsTypeId);
        dest.writeString(this.goodsRichTextDetail);
        dest.writeString(this.circleName);
        dest.writeString(this.goodsName);
        dest.writeFloat(this.curPrice);
        dest.writeFloat(this.prePrice);
        dest.writeString(this.goodsDetail);
        dest.writeList(this.goodsSpecList);
        dest.writeList(this.goodsPicsList);
        dest.writeList(this.goodsDetailPicsList);
    }

    protected GoodsDetailModel(Parcel in) {
        this.goodsId = in.readString();
        this.isValid = in.readString();
        this.csPhoneNum = in.readString();
        this.salesNum = in.readInt();
        this.isFav = in.readInt();
        this.takeGoods = in.readInt();
        this.storeId = in.readString();
        this.score = in.readString();
        this.courierDelivery = in.readInt();
        this.goodsTypeId = in.readInt();
        this.noShipFees = in.readInt();
        this.sysGoodsTypeId = in.readInt();
        this.goodsRichTextDetail = in.readString();
        this.circleName = in.readString();
        this.goodsName = in.readString();
        this.curPrice = in.readFloat();
        this.prePrice = in.readFloat();
        this.goodsDetail = in.readString();
        this.goodsSpecList = new ArrayList<GoodsSpecListEntity>();
        in.readList(this.goodsSpecList, GoodsSpecListEntity.class.getClassLoader());
        this.goodsPicsList = new ArrayList<GoodsPicsListEntity>();
        in.readList(this.goodsPicsList, GoodsPicsListEntity.class.getClassLoader());
        this.goodsDetailPicsList = new ArrayList<GoodsDetailPicsListEntity>();
        in.readList(this.goodsDetailPicsList, GoodsDetailPicsListEntity.class.getClassLoader());
    }

    public static final Creator<GoodsDetailModel> CREATOR = new Creator<GoodsDetailModel>() {
        @Override
        public GoodsDetailModel createFromParcel(Parcel source) {
            return new GoodsDetailModel(source);
        }

        @Override
        public GoodsDetailModel[] newArray(int size) {
            return new GoodsDetailModel[size];
        }
    };
}
