package com.shihuo.shihuo.models;

/**
 * Created by cm_qiujiaheng on 2016/12/4.
 * 商铺版主页列表model
 */

public class ShopMainGridModel {
    public String id;
    public int iconUrlResid;
    public String name;

    public ShopMainGridModel() {
    }

    public ShopMainGridModel(String id, int iconUrlResid, String name) {
        this.id = id;
        this.iconUrlResid = iconUrlResid;
        this.name = name;
    }
}
