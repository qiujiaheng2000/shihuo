
package com.shihuo.shihuo.application;

/**
 * 常量类 Created by lishuai on 16/12/6.
 */

public class Contants {

    /**
     * 图片前缀
     */
    public static final String IMAGE_URL = "http://shihuo-user.oss-cn-beijing.aliyuncs.com/";
    // 店铺状态
    /**
     * 入驻成功
     */
    public static final int STORE_TYPE_SUCCESS = 1;

    /**
     * 审核中
     */
    public static final int STORE_TYPE_CHECK = 2;

    /**
     * 被拒绝，入驻失败
     */
    public static final int STORE_TYPE_FAIL = 3;

    /**
     * 二维码类型，商铺
     */
    public static final String ZXING_TYPE_STORE = "storeDetail";

    /**
     * 二维码类型，商品
     */
    public static final String ZXING_TYPE_GOODS = "goodDetail";

    /**
     * app分享地址
     */
    public static final String SHARE_URL = "http://www.ycshsj.com/xiazai";
}
