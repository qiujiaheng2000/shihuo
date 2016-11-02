package com.shihuo.shihuo.models;

/**
 * Created by jiahengqiu on 2016/10/23.
 * 商品
 */

public class GoodsModel {
    public String goodsId;
    public String goodsTitle;//商品名称
    public String goodsDesc;//商品描述
    public String goodsOriginPrice;//原价
    public String goodsNewPrice;//折后价
    public String salesNum;//数量
    public String goodsDiscount;//折扣
    public String goodsImag;

    public GoodsModel() {
        super();
    }

    public GoodsModel(String goodsId, String goodsTitle, String goodsDesc, String goodsOriginPrice,
                      String goodsNewPrice, String salesNum, String goodsDiscount, String goodsImag) {
        this.goodsId = goodsId;
        this.goodsTitle = goodsTitle;
        this.goodsDesc = goodsDesc;
        this.goodsOriginPrice = goodsOriginPrice;
        this.goodsNewPrice = goodsNewPrice;
        this.salesNum = salesNum;
        this.goodsDiscount = goodsDiscount;
        this.goodsImag = goodsImag;
    }
}
