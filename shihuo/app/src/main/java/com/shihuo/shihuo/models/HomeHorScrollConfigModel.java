package com.shihuo.shihuo.models;

import java.util.ArrayList;

/**
 * Created by cm_qiujiaheng on 2016/11/5.
 * 首页顶部横向导航栏的配置数据对象
 */

public class HomeHorScrollConfigModel {
    //商品类型对象
    public ArrayList<HorScrollItemModel> goodsScrolls;
    //商品类型对象
    public ArrayList<HorScrollItemModel> shopsScrolls;

    public HomeHorScrollConfigModel() {
        super();
    }

    public HomeHorScrollConfigModel(String jsonStr) {
        //TODO
        goodsScrolls = new ArrayList<>();
        shopsScrolls = new ArrayList<>();



    }

    public static class HorScrollItemModel {
        public String Id;//商品类型id/商铺类型id
        public String imageUrl;
        public String name;

        public HorScrollItemModel(String id, String imageUrl, String name) {
            Id = id;
            this.imageUrl = imageUrl;
            this.name = name;
        }
    }

    public static HomeHorScrollConfigModel getTestDatas() {
        HomeHorScrollConfigModel homeHorScrollConfigModel = new HomeHorScrollConfigModel();
        ArrayList<HorScrollItemModel> horScrollItemModelsGoods = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            horScrollItemModelsGoods.add(new HorScrollItemModel("" + i, "", "商品类型" + i));
        }
        ArrayList<HorScrollItemModel> horScrollItemModelsShops = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            horScrollItemModelsShops.add(new HorScrollItemModel("" + i, "", "商铺类型" + i));
        }
        homeHorScrollConfigModel.goodsScrolls = horScrollItemModelsGoods;
        homeHorScrollConfigModel.shopsScrolls = horScrollItemModelsShops;

        return homeHorScrollConfigModel;
    }

}
