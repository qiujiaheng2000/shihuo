package com.shihuo.shihuo.network;

import android.util.Log;

/**
 * Created by cm_qiujiaheng on 2016/11/15.
 * 网络请求帮助类
 */

public class NetWorkHelper {

//    public static final String ApiHost = "http://59.110.10.19:8080";
    public static final String ApiHost = "http://192.168.0.105:8080";
    public static final String ApiPath = "/shihuo/app";
    //获取验证码接口 POST
    public static final String API_VERIFY_CODE = "/user/verifyCode/";
    /**
     * 手机号注册 POST
     */
    public static final String API_REGISTER = "/user/register";
    /**
     * 手机号登录 POST
     */
    public static final String API_LOGIN = "/user/login";
    /**
     * 忘记密码 POST
     */
    public static final String API_FORGOTPASSWORD = "/user/forgotPassword";
    /**
     * 我的基本信息 GET
     */
    public static final String API_BASICINFO = "/user/basicInfo";
    /**
     * 商品收藏列表 GET
     */
    public static final String API_FAVGOOD = "/user/favGood";
    /**
     * 店铺收藏列表 GET
     */
    public static final String API_FAVSTORE = "/user/favStore";

    /**
     * 我的订单 GET
     */
    public static final String API_ORDER = "/user/order";

    /**
     * 我的地址 GET
     */
    public static final String API_ADDRESS = "/user/address";
    /**
     * 商家入住 POST
     */
    public static final String API_OPENSHOP = "/userInfo/openShop";

    /**
     * 获取商品分类 GET
     */
    public static final String API_GETGOODSTYPE = "/userInfo/getGoodsType";
    /**
     * 获取商圈分类 GET
     */
    public static final String API_GETCIRCLE = "/userInfo/getCircle";

    /**
     * 获取商圈区域分类 GET
     */
    public static final String API_GETCIRCLEAREA = "/userInfo/getCircleArea";
    /**
     * 商铺管理 POST
     */
    public static final String API_STOREINFO = "/storeInfo";

    /**
     * 商品发布 POST
     */
    public static final String API_PUBLISHGOOD = "/goods/publishGood";

    /**
     * 首页获取商圈、商品分类，banner，折扣区 GET
     */
    public static final String API_GET_SYS_TYPE = "/homePage/getTypeList";

    /**
     * 首页获取热销商品
     */
    public static final String API_GET_HOT_GOODS = "/homePage/getHotGoodsList";

    /**
     * 获取相关APIURL
     *
     * @param url 接口的后缀
     * @return 完整的apiurl
     */
    public static String getApiUrl(String url) {
        Log.d("network", "url = " + ApiHost + ApiPath + url);
        return ApiHost + ApiPath + url;
    }
}
