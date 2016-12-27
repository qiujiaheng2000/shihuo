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
    public enum OrderState {
        enum_order_paid,//已付款代发货
        enum_order_sended,//已发货/待收货
        enum_order_completed,//已完成
        enum_order_after_sales//售后
    }

    public String orderId;//
    public String picUrl;
    public String goodsName;
    public String goodsDetail;
    public String goodsPrice;
    public String goodsAmount;
    public OrderState status;

    public OrderModel() {
        super();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static OrderModel fromJson(String jsonStr) {
        Gson gson = new Gson();
        OrderModel orderModel = gson.fromJson(jsonStr, OrderModel.class);
        return orderModel;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderId);
        dest.writeString(this.picUrl);
        dest.writeString(this.goodsName);
        dest.writeString(this.goodsDetail);
        dest.writeString(this.goodsPrice);
        dest.writeString(this.goodsAmount);
        dest.writeInt(this.status == null ? -1 : this.status.ordinal());
    }

    protected OrderModel(Parcel in) {
        this.orderId = in.readString();
        this.picUrl = in.readString();
        this.goodsName = in.readString();
        this.goodsDetail = in.readString();
        this.goodsPrice = in.readString();
        this.goodsAmount = in.readString();
        int tmpStatus = in.readInt();
        this.status = tmpStatus == -1 ? null : OrderState.values()[tmpStatus];
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


