
package com.shihuo.shihuo.util;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.shihuo.shihuo.Activities.CircleListActivity;
import com.shihuo.shihuo.Activities.GoodsDetailActivity;
import com.shihuo.shihuo.Activities.GoodsListByTypeActivity;
import com.shihuo.shihuo.Activities.HomeDiscountListActivity;
import com.shihuo.shihuo.Activities.OrderDetailActivity;
import com.shihuo.shihuo.Activities.ShopHomeActivity;
import com.shihuo.shihuo.models.OrderModel;

/**
 * scheme定义
 * 商品详情页面 shihuo:///goodDetail/{id}
 * 商铺详情页面 shihuo:///storeDetail/{id}
 * 订单详情页面 shihuo:///orderDetail/{id}
 * 某个商品分类页面 shihuo:///goodSysOneType/{id}
 * 某个商圈页面 shihuo:///circle/{id}
 * 折扣区专场 shihuo:///discount/{id}
 * 优惠区专场 shihuo:///preferential/{id}
 */

public class AppSchemeHelper {
    private static String scheme = "shihuo://";

    public static Intent dealScheme(Context context, String schemeStr) {
        if (TextUtils.isEmpty(schemeStr) || !schemeStr.startsWith(scheme)) {
            return null;
        }
        String tempStr = schemeStr.replace(scheme, "");
        String[] schemeArray = null;
        if (tempStr.contains("/")) {
            schemeArray = tempStr.split("/");
        }
        if (schemeArray == null || schemeArray.length == 0) {
            return null;
        } else {
            return dealSchemeGo(context, schemeArray[1], schemeArray[2]);
        }

    }

    /**
     * @param context
     * @param type 跳转页面type
     * @param id 跳转页面id
     * @return
     */
    public static Intent dealSchemeGo(Context context, String type, String id) {
        if (type.equals("goodDetail")) {// 商品详情页面
            GoodsDetailActivity.start(context, id);
        } else if (type.equals("storeDetail")) {// 商铺详情页面
            ShopHomeActivity.start(context, id);
        } else if (type.equals("orderDetailUser")) {// 订单详情页面用户
            OrderModel orderModel = new OrderModel();
            orderModel.orderId = id;
            OrderDetailActivity.start(context, orderModel, OrderDetailActivity.ORDER_FROM_USER);
        } else if (type.equals("orderDetailStore")) {// 订单详情页面店铺
            OrderModel orderModel = new OrderModel();
            orderModel.orderId = id;
            OrderDetailActivity.start(context, orderModel, OrderDetailActivity.ORDER_FROM_SHOP);
        } else if (type.equals("orderDetail")) {// 订单详情
            OrderModel orderModel = new OrderModel();
            orderModel.orderId = id;
            OrderDetailActivity.start(context, orderModel, OrderDetailActivity.ORDER_FROM_USER);
        } else if (type.equals("preferential")) { // 优惠区
            HomeDiscountListActivity.start(context, "识货优惠区", id);
        } else if (type.equals("discount")) { // 折扣区
            HomeDiscountListActivity.start(context, "识货折扣区", id);
        } else if (type.equals("goodSysOneType")) { // 某个商品分类
            GoodsListByTypeActivity.start(context, Integer.valueOf(id) - 1);
        } else if (type.equals("circle")) { // 某个商圈
            CircleListActivity.start(context, Integer.valueOf(id) - 1);
        }
        return null;
    }
}
