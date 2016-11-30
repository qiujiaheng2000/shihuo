
package com.shihuo.shihuo.models;

import java.util.List;

/**
 * Created by lishuai on 16/11/29.
 */

public class SysTypeModel extends BaseModel {

    public DataEntity data;

    public class DataEntity {
        public List<GoodsTypeModel> shAdvertisingList;

        public List<GoodsTypeModel> shSysDiscountType1List;

        public List<GoodsTypeModel> shSysDiscountType2List;

        public List<GoodsTypeModel> shSysGoodsTypeList;

        public List<GoodsTypeModel> shSysStoreCircleList;
    }
}
