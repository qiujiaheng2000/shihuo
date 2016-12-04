package com.shihuo.shihuo.Activities.shop.models;

/**
 * Created by cm_qiujiaheng on 2016/12/4.
 * 商铺分类管理列表模型
 */

public class ShopTypeModel {

    public String typeId;
    public String storeId;
    public String typeName;

    public ShopTypeModel(String typeId, String storeId, String typeName) {
        this.typeId = typeId;
        this.storeId = storeId;
        this.typeName = typeName;
    }
}
