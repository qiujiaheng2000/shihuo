
package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

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

    public List<SpecificationModel> goodsSpecList;

    public List<GoodsPicsListEntity> goodsPicsList;

    public List<GoodsDetailPicsListEntity> goodsDetailPicsList;

    public static class GoodsPicsListEntity implements Parcelable {

        public String goodsId;

        public int picId;

        public int picType;

        public String picUrl;

        public GoodsPicsListEntity() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.goodsId);
            dest.writeInt(this.picId);
            dest.writeInt(this.picType);
            dest.writeString(this.picUrl);
        }

        protected GoodsPicsListEntity(Parcel in) {
            this.goodsId = in.readString();
            this.picId = in.readInt();
            this.picType = in.readInt();
            this.picUrl = in.readString();
        }

        public static final Creator<GoodsPicsListEntity> CREATOR = new Creator<GoodsPicsListEntity>() {
            @Override
            public GoodsPicsListEntity createFromParcel(Parcel source) {
                return new GoodsPicsListEntity(source);
            }

            @Override
            public GoodsPicsListEntity[] newArray(int size) {
                return new GoodsPicsListEntity[size];
            }
        };
    }

    public static class GoodsDetailPicsListEntity implements Parcelable {

        public String goodsId;

        public int picId;

        public int picType;

        public String picUrl;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.goodsId);
            dest.writeInt(this.picId);
            dest.writeInt(this.picType);
            dest.writeString(this.picUrl);
        }

        public GoodsDetailPicsListEntity() {
        }

        protected GoodsDetailPicsListEntity(Parcel in) {
            this.goodsId = in.readString();
            this.picId = in.readInt();
            this.picType = in.readInt();
            this.picUrl = in.readString();
        }

        public static final Creator<GoodsDetailPicsListEntity> CREATOR = new Creator<GoodsDetailPicsListEntity>() {
            @Override
            public GoodsDetailPicsListEntity createFromParcel(Parcel source) {
                return new GoodsDetailPicsListEntity(source);
            }

            @Override
            public GoodsDetailPicsListEntity[] newArray(int size) {
                return new GoodsDetailPicsListEntity[size];
            }
        };
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
        dest.writeTypedList(this.goodsSpecList);
        dest.writeTypedList(this.goodsPicsList);
        dest.writeTypedList(this.goodsDetailPicsList);
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
        this.goodsSpecList = in.createTypedArrayList(SpecificationModel.CREATOR);
        this.goodsPicsList = in.createTypedArrayList(GoodsPicsListEntity.CREATOR);
        this.goodsDetailPicsList = in.createTypedArrayList(GoodsDetailPicsListEntity.CREATOR);
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
