
package com.shihuo.shihuo.util;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * scheme定义
 * 商品详情页面 shihuo:///goodDetail/{id}
 * 订单详情页面 shihuo:///orderDetail/{id}
 * 系统商品一级分类页面 shihuo:///goodSysOneType/{id}
 * 某个商圈页面 shihuo:///circle/{id}
 * 微视频详情页面 shihuo:///videoDetail/{id}
 * 便民服务详情页面 shihuo:///serviceDetail/{id}
 * 商铺详情页面 shihuo:///storeDetail/{id}
 * 折扣区专场 shihuo:///discount/{id}
 * 优惠区专场 shihuo:///preferential/{id}
 */

public class AppSchemeHelper {
    private static String scheme = "shihuo://";

    public static Intent dealScheme(Context context, String schemeStr) {
        if (TextUtils.isEmpty(schemeStr) && schemeStr.startsWith(scheme)) {
            return null;
        }
        String tempStr = schemeStr.replace(scheme, "");
        String[] schemeArray = null;
        if (tempStr.contains("/")) {
            schemeArray = tempStr.split("/");
        }
        if (schemeArray == null && schemeArray.length == 0) {
            return null;
        } else {
            return dealSchemeGo(context, schemeArray[1], schemeArray[2]);
        }

    }

    /**
     * @param context
     * @param type    跳转页面type
     * @param id      跳转页面id
     * @return
     */
    public static Intent dealSchemeGo(Context context, String type, String id) {
        if (type.equals("goodDetail")) {

        } else if (type.equals("orderDetail")) {

        } else if (type.equals("goodType")) {

        } else if (type.equals("discount")) {// 折扣区

        } else if (type.equals("circle")) {

        } else if (type.equals("videoDetail")) {

        } else if (type.equals("serviceDetail")) {

        } else if (type.equals("storeDetail")) {

        } else if (type.equals("goodSysOneType")) { // 系统商品一级分类页面


        } else if (type.equals("preferential")) { // 优惠区


        }
        return null;
    }
}
