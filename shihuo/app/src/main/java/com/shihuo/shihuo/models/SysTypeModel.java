
package com.shihuo.shihuo.models;

import java.util.List;

/**
 * Created by lishuai on 16/11/29.
 */

public class SysTypeModel extends BaseModel {

    public DataEntity data;

    private class DataEntity {
        public List<BannerModel> shAdvertisingList;

        public List<DiscountModel> shSysDiscountTypeList;

        public List<GoodsTypeModel> shSysGoodsTypeList;

        public List<BusinessTypeModel> shSysStoreCircleList;
    }
}
