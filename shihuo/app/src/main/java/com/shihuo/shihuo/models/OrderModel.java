package com.shihuo.shihuo.models;

import java.util.ArrayList;

/**
 * Created by cm_qiujiaheng on 2016/11/3.
 * 我的订单
 */
public class OrderModel {
    //订单状态
    public enum OrderState {
        enum_order_paid,//已付款代发货
        enum_order_sended,//已发货/待收货
        enum_order_completed,//已完成
        enum_order_after_sales//售后
    }

    public String orderId;//
    public String orderImage;
    public String orderTitle;
    public String orderDesc;
    public String orderPrice;
    public String orderNums;
    public OrderState orderState;

    public OrderModel() {
        super();
    }

    public OrderModel(String orderId, String orderImage, String orderTitle, String orderDesc, String orderPrice, String orderNums, OrderState orderState) {
        this.orderId = orderId;
        this.orderImage = orderImage;
        this.orderTitle = orderTitle;
        this.orderDesc = orderDesc;
        this.orderPrice = orderPrice;
        this.orderNums = orderNums;
        this.orderState = orderState;

    }

    /**
     * 获取测试数据
     *
     * @param size
     * @return
     */
    public static ArrayList<OrderModel> getTestDatas(int size) {
        ArrayList<OrderModel> orderModels = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            OrderModel orderModel = new OrderModel("orderId" + i, "orderImageUrl", "可爱大毛衣……，爆款", "可爱大毛衣……，爆款、热卖2w+件，再不抢就没有了……",
                    "280", "3", OrderState.enum_order_after_sales);
            orderModels.add(orderModel);
        }
        return orderModels;
    }

}


