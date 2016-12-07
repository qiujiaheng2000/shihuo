
package com.shihuo.shihuo.models;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by lishuai on 16/12/8.
 */

public class GoodsDetailModel {

    public static GoodsDetailModel parseStrJson(String strJson) {
        Gson gson = new Gson();
        GoodsDetailModel model = gson.fromJson(strJson, GoodsDetailModel.class);
        return model;
    }

    public static String parseToJson(GoodsDetailModel modle) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(modle, GoodsDetailModel.class);
        return jsonStr;
    }

    public String goodsId;

    public String isValid;

    public String salesNum;

    public int takeGoods;

    public String storeId;

    public String score;

    public int courierDelivery;

    public int goodsTypeId;

    public int noShipFees;

    public int sysGoodsTypeId;

    public String goodsRichTextDetail;

    public String circleName;

    public String goodsName;

    public String goodsDetail;

    public List<GoodsSpecListEntity> goodsSpecList;

    public List<GoodsPicsListEntity> goodsPicsList;

    public List<GoodsDetailPicsListEntity> goodsDetailPicsList;

    public static class GoodsSpecListEntity {

        public int curPrice;

        public String goodsId;

        public int prePrice;

        public int specId;

        public String specName;

        public int stockNum;
    }

    public static class GoodsPicsListEntity {

        public String goodsId;

        public int picId;

        public int picType;

        public String picUrl;
    }

    public static class GoodsDetailPicsListEntity {

        public String goodsId;

        public int picId;

        public int picType;

        public String picUrl;

    }
}
