package com.shihuo.shihuo.models;

/**
 * Created by cm_qiujiaheng on 2016/12/4.
 * 商铺版主页列表model
 */

public class ShopMainGridModel {
    public String id;
    public String iconUrl;
    public String name;

    public ShopMainGridModel() {
    }

    public ShopMainGridModel(String id, String iconUrl, String name) {
        this.id = id;
        this.iconUrl = iconUrl;
        this.name = name;
    }
}
