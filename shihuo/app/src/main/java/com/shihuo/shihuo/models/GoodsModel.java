package com.shihuo.shihuo.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jiahengqiu on 2016/10/23.
 * 商品
 */

public class GoodsModel implements Serializable{
    public String goodsId;
    public String goodsTitle;//商品名称
    public String goodsDesc;//商品描述
    public String goodsOriginPrice;//原价
    public String goodsNewPrice;//折后价
    public String salesNum;//数量
    public String goodsDiscount;//折扣
    public String goodsImage;

    public ArrayList<String> capacities;//此商品支持的能力（正品保证、极速退款、七天退换...）
    public ArrayList<String> images;//图片列表
    public String area;//所属区域

    public GoodsModel() {
        super();
    }

    public GoodsModel(String goodsId, String goodsTitle, String goodsDesc, String goodsOriginPrice,
                      String goodsNewPrice, String salesNum, String goodsDiscount, String goodsImage) {
        this.goodsId = goodsId;
        this.goodsTitle = goodsTitle;
        this.goodsDesc = goodsDesc;
        this.goodsOriginPrice = goodsOriginPrice;
        this.goodsNewPrice = goodsNewPrice;
        this.salesNum = salesNum;
        this.goodsDiscount = goodsDiscount;
        this.goodsImage = goodsImage;
    }
}
