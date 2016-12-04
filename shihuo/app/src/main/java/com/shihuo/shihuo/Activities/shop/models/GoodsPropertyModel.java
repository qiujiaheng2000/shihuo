package com.shihuo.shihuo.Activities.shop.models;

/**
 * Created by cm_qiujiaheng on 2016/12/5.
 * 商品属性规格模型
 */

public class GoodsPropertyModel {
    public String original;//原价
    public String current;//现价
    public String repertory;//库存
    public String standard;//规格

    public GoodsPropertyModel(String original, String current, String repertory, String standard) {
        this.original = original;
        this.current = current;
        this.repertory = repertory;
        this.standard = standard;
    }
}
