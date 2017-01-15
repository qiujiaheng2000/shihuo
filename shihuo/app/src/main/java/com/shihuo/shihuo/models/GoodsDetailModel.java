
package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import org.json.JSONObject;

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

    public String isValid;//1-未失效， 2-商品已下架， 3-库存不足 （用于判断商家是否已下架商品或已下架规格或商品无货的情况）

    public String csPhoneNum;//客服电话

    public int isFav;//是否被搜藏

    public int takeGoods;//上门取货

    public String storeId;//商铺id

    public float score;//评分

    public String shipMethod;//配送方式

    public int courierDelivery;//同城配送

    public int goodsTypeId;//商品类型id

    public int noShipFees;//包邮

    public int salesNum;

    public int sysGoodsTypeId;//系统商品类型id
    public int sysGoodsTypeSecondId;//系统二级分类id

    public String goodsRichTextDetail;

    public String circleName;

    public String goodsName;//商品名称

    public float curPrice;//当前价格

    public String picUrl;//图片

    public String specName;//选中的规格名称
    public int amount;//订单数量
    public String createTime;

    public int specId;//规格id

    public float prePrice;//之前的价格

    public String goodsDetail;//商品详情

    public boolean isChecked;//在购物车界面是否被选中
    public boolean isEdit;//在购物车界面是否是编辑模式

    public List<SpecificationModel> goodsSpecList;

    public List<GoodsPicsListEntity> goodsPicsList;

    public List<GoodsDetailPicsListEntity> goodsDetailPicsList;

    public static GoodsDetailModel parseJsonStr(JSONObject jsonObject) {
        Gson gson = new Gson();
        GoodsDetailModel goodsDetailModel = gson.fromJson(jsonObject.toString(), GoodsDetailModel.class);
        return goodsDetailModel;
    }

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
        dest.writeInt(this.isFav);
        dest.writeInt(this.takeGoods);
        dest.writeString(this.storeId);
        dest.writeFloat(this.score);
        dest.writeString(this.shipMethod);
        dest.writeInt(this.courierDelivery);
        dest.writeInt(this.goodsTypeId);
        dest.writeInt(this.noShipFees);
        dest.writeInt(this.salesNum);
        dest.writeInt(this.sysGoodsTypeId);
        dest.writeInt(this.sysGoodsTypeSecondId);
        dest.writeString(this.goodsRichTextDetail);
        dest.writeString(this.circleName);
        dest.writeString(this.goodsName);
        dest.writeFloat(this.curPrice);
        dest.writeString(this.picUrl);
        dest.writeString(this.specName);
        dest.writeInt(this.amount);
        dest.writeString(this.createTime);
        dest.writeInt(this.specId);
        dest.writeFloat(this.prePrice);
        dest.writeString(this.goodsDetail);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isEdit ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.goodsSpecList);
        dest.writeTypedList(this.goodsPicsList);
        dest.writeTypedList(this.goodsDetailPicsList);
    }

    protected GoodsDetailModel(Parcel in) {
        this.goodsId = in.readString();
        this.isValid = in.readString();
        this.csPhoneNum = in.readString();
        this.isFav = in.readInt();
        this.takeGoods = in.readInt();
        this.storeId = in.readString();
        this.score = in.readFloat();
        this.shipMethod = in.readString();
        this.courierDelivery = in.readInt();
        this.goodsTypeId = in.readInt();
        this.noShipFees = in.readInt();
        this.salesNum = in.readInt();
        this.sysGoodsTypeId = in.readInt();
        this.sysGoodsTypeSecondId = in.readInt();
        this.goodsRichTextDetail = in.readString();
        this.circleName = in.readString();
        this.goodsName = in.readString();
        this.curPrice = in.readFloat();
        this.picUrl = in.readString();
        this.specName = in.readString();
        this.amount = in.readInt();
        this.createTime = in.readString();
        this.specId = in.readInt();
        this.prePrice = in.readFloat();
        this.goodsDetail = in.readString();
        this.isChecked = in.readByte() != 0;
        this.isEdit = in.readByte() != 0;
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
