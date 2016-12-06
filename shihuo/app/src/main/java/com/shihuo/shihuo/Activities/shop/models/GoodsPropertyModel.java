package com.shihuo.shihuo.Activities.shop.models;

import com.google.gson.Gson;

/**
 * Created by cm_qiujiaheng on 2016/12/5.
 * 商品属性规格模型
 */

public class GoodsPropertyModel {
    public String prePrice;//原价
    public String curPrice;//现价
    public String stockNum;//库存
    public String specName;//规格名称

    public GoodsPropertyModel(String prePrice, String curPrice, String stockNum, String specName) {
        this.prePrice = prePrice;
        this.curPrice = curPrice;
        this.stockNum = stockNum;
        this.specName = specName;
    }

    public String toJsonStr() {
        Gson gson = new Gson();
        return gson.toJson(this, GoodsPropertyModel.class);
    }
}
