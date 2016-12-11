package com.shihuo.shihuo.Activities.shop.models;

import com.google.gson.Gson;
import com.shihuo.shihuo.models.LoginModel;

/**
 * Created by cm_qiujiaheng on 2016/12/9.
 * 商铺管理信息
 */

public class ShopManagerInfo {
    public String storeLogoPicUrl;//logo
    public String storeDetail;//商铺描述
    public String storeAnnouncement;//商铺公告
    public String businessTime;//营业时间
    public String storeName;//商铺名称
    public String storeId;//商铺id
    public String circleName;//商铺所属商圈名称
    public String distributionTime;//发货时间
    public String storeFreeShippingPrice;//免邮价格
    public String storeAddress;//店铺地址
    public String csPhoneNum;//客服电话
    public int isRecommended;//是否推荐 0：普通 1：推荐
    public int validateHaveGoodsType;//严重是否含有商铺类型  1：有  0：无   1的时候才能发布新品
    public int isFav;//是否被收藏，1：收藏  0：未收藏
    public int browseNum;//浏览量
    public int sysGoodTypeId;//系统类别


    public static ShopManagerInfo parseFormJsonStr(String jsonStr) {
        Gson gson = new Gson();
        ShopManagerInfo shopManagerInfo = gson.fromJson(jsonStr, ShopManagerInfo.class);
        return shopManagerInfo;
    }

    public static String parseToJson(ShopManagerInfo shopManagerInfo) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(shopManagerInfo, ShopManagerInfo.class);
        return jsonStr;
    }
}
