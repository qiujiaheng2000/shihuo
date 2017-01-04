package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * Created by cm_qiujiaheng on 2016/11/3.
 * 我的订单
 */
public class OrderModel implements Parcelable {
    //订单状态
    public static final int ORDER_STATUS_UNSHIP = 1;//待发货
    public static final int ORDER_STATUS_SHIPED = 2;//待收货
    public static final int ORDER_STATUS_COMPLETED = 3;//已完成
    public static final int ORDER_STATUS_BACK = 4;//退货中
    public static final int ORDER_STATUS_BACKED = 5;//已退货
    public static final int ORDER_STATUS_PROCESSING = 6;//处理中
    public static final int ORDER_STATUS_CLOSED = 7;//已关闭

    public String orderId;//
    public String picUrl;
    public String goodsName;
    public String goodsDetail;
    public float goodsPrice;
    public float goodsAmount;
    public int status;

    public String address;
    public String createTime;
    public String goodsId;
    public int goodsNum;
    public int goodsTypeId;
    public String goodsTypeName;
    public String  isReturnMoney;
    public float orderPrice;
    public int paymentMethod;
    public String paymentNum;
    public String paymentTime;
    public String receiverName;
    public String receiverPhoneNum;
    public String refundReason;
    public String refundTime;
    public String refuseReason;
    public String refuseTime;
    public String score;
    public String shipMethod;
    public String storeId;
    public int sysGoodsTypeId;
    public String sysGoodsTypeName;
    public String trackingNum;
    public String userId;

    public int specId;
    public String specName;

    public OrderModel() {
        super();
    }


    public static OrderModel fromJson(String jsonStr) {
        Gson gson = new Gson();
//
        OrderModel orderModel = gson.fromJson(jsonStr, OrderModel.class);
        return orderModel;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderId);
        dest.writeString(this.picUrl);
        dest.writeString(this.goodsName);
        dest.writeString(this.goodsDetail);
        dest.writeFloat(this.goodsPrice);
        dest.writeFloat(this.goodsAmount);
        dest.writeInt(this.status);
        dest.writeString(this.address);
        dest.writeString(this.createTime);
        dest.writeString(this.goodsId);
        dest.writeInt(this.goodsNum);
        dest.writeInt(this.goodsTypeId);
        dest.writeString(this.goodsTypeName);
        dest.writeString(this.isReturnMoney);
        dest.writeFloat(this.orderPrice);
        dest.writeInt(this.paymentMethod);
        dest.writeString(this.paymentNum);
        dest.writeString(this.paymentTime);
        dest.writeString(this.receiverName);
        dest.writeString(this.receiverPhoneNum);
        dest.writeString(this.refundReason);
        dest.writeString(this.refundTime);
        dest.writeString(this.refuseReason);
        dest.writeString(this.refuseTime);
        dest.writeString(this.score);
        dest.writeString(this.shipMethod);
        dest.writeString(this.storeId);
        dest.writeInt(this.sysGoodsTypeId);
        dest.writeString(this.sysGoodsTypeName);
        dest.writeString(this.trackingNum);
        dest.writeString(this.userId);
        dest.writeInt(this.specId);
        dest.writeString(this.specName);
    }

    protected OrderModel(Parcel in) {
        this.orderId = in.readString();
        this.picUrl = in.readString();
        this.goodsName = in.readString();
        this.goodsDetail = in.readString();
        this.goodsPrice = in.readInt();
        this.goodsAmount = in.readInt();
        this.status = in.readInt();
        this.address = in.readString();
        this.createTime = in.readString();
        this.goodsId = in.readString();
        this.goodsNum = in.readInt();
        this.goodsTypeId = in.readInt();
        this.goodsTypeName = in.readString();
        this.isReturnMoney = in.readString();
        this.orderPrice = in.readInt();
        this.paymentMethod = in.readInt();
        this.paymentNum = in.readString();
        this.paymentTime = in.readString();
        this.receiverName = in.readString();
        this.receiverPhoneNum = in.readString();
        this.refundReason = in.readString();
        this.refundTime = in.readString();
        this.refuseReason = in.readString();
        this.refuseTime = in.readString();
        this.score = in.readString();
        this.shipMethod = in.readString();
        this.storeId = in.readString();
        this.sysGoodsTypeId = in.readInt();
        this.sysGoodsTypeName = in.readString();
        this.trackingNum = in.readString();
        this.userId = in.readString();
        this.specId = in.readInt();
        this.specName = in.readString();
    }

    public static final Parcelable.Creator<OrderModel> CREATOR = new Parcelable.Creator<OrderModel>() {
        @Override
        public OrderModel createFromParcel(Parcel source) {
            return new OrderModel(source);
        }

        @Override
        public OrderModel[] newArray(int size) {
            return new OrderModel[size];
        }
    };
}


