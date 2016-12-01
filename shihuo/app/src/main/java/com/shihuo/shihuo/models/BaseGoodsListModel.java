
package com.shihuo.shihuo.models;

/**
 * 列表model基类 Created by lishuai on 16/12/1.
 */

public class BaseGoodsListModel extends BaseModel {
    public DataEntity data;

    public class DataEntity {
        public GoodsListModel page;
    }
}
