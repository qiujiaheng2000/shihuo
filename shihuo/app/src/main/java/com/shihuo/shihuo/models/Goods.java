package com.shihuo.shihuo.models;

/**
 * Created by jiahengqiu on 2016/10/23.
 * 商品
 */

public class Goods {
    public String goodsId;
    public String goodsTitle;//商品名称
    public String goodsOriginPrice;//原价
    public String goodsNewPrice;//折后价
    public String salesNum;//数量
    public String goodsDiscount;//折扣
    public String goodsImag;

    public Goods() {
        super();
    }

    public Goods(String goodsId, String goodsTitle, String goodsOriginPrice, String goodsNewPrice, String salesNum, String goodsDiscount, String goodsImag) {
        this.goodsId = goodsId;
        this.goodsTitle = goodsTitle;
        this.goodsOriginPrice = goodsOriginPrice;
        this.goodsNewPrice = goodsNewPrice;
        this.salesNum = salesNum;
        this.goodsDiscount = goodsDiscount;
        this.goodsImag = goodsImag;
    }
}
