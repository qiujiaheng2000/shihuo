package com.shihuo.shihuo.Activities.shop.models;

import com.google.gson.Gson;

/**
 * Created by cm_qiujiaheng on 2016/12/5.
 * 商品属性规格模型
 */

public class GoodsPropertyModel {
    public float prePrice;//原价
    public float curPrice;//现价
    public int stockNum;//库存
    public String specName;//规格名称

    public GoodsPropertyModel(float prePrice, float curPrice, int stockNum, String specName) {
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
