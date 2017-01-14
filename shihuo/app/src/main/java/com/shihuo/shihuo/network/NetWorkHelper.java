package com.shihuo.shihuo.network;

import android.util.Log;

/**
 * Created by cm_qiujiaheng on 2016/11/15.
 * 网络请求帮助类
 */

public class NetWorkHelper {

    public static final String ApiHost = "http://59.110.10.19:8080";//外网
    //        public static final String ApiHost = "http://192.168.0.130:8080";
    public static final String ApiPath = "/shihuo/app";
    //获取验证码接口 POST
    public static final String API_VERIFY_CODE = "/user/verifyCode/";
    /**
     * 手机号注册 POST
     */
    public static final String API_REGISTER = "/user/register";
    /**
     * 忘记密码 POST
     */
    public static final String API_FORGET_PASSWORD_POST = "/user/forgotPassword";
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
    public static final String API_BASICINFO = "/userInfo/basicInfo";
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
     * 商铺管理信息 GET
     */
    public static final String API_GET_STOREINFO = "/userInfo/storeInfo";

    /**
     * 商品发布 POST
     */
    public static final String API_PUBLISHGOOD = "/goods/publishGoods";

    /**
     * 首页获取商圈、商品分类，banner，折扣区 GET
     */
    public static final String API_GET_SYS_TYPE = "/homePage/getTypeList";
    /**
     * 获取购物车列表
     */
    public static final String API_GET_CART_LIST = "/cart/list";
    /**
     * 添加商品到购物车
     */
    public static final String API_GET_CART_ADD = "/cart/add";

    /**
     * 首页获取热销商品
     */
    public static final String API_GET_HOT_GOODS = "/homePage/getHotGoodsList";
    /**
     * 修改密码
     */
    public static final String API_FIX_PASSWORD = "/user/updatePassword";

    /**
     * 我要入驻 -获取系统商品分类
     */
    public static final String API_GET_SYSTEM_GOODSTYPES = "/sysGoodsType/getSysGoodsTypeList";

    /**
     * 批量删除购物车列表商品
     */
    public static final String API_POST_DELETE_SHOPPINGCAR_GOODS = "/cart/delete";
    /**
     * 批量删除购物车列表商品
     */
    public static final String API_POST_MODIFY_SHOPPINGCAR_GOODS = "/cart/modify";

    //店铺管理

    /**
     * 获取店铺商品分类
     */
    public static final String API_GET_GOODSTYPELIST = "/goodsType/getGoodsTypeList";
    /**
     * 添加商品分类
     */
    public static final String API_ADD_GOODSTYPELIST = "/goodsType/addGoodsType";

    /**
     * 发布商品
     */
    public static final String API_POST_PUBLISHGOODS = "/goods/publishGoods";

    /**
     * 获取商品详情
     */
    public static final String API_GET_SHOP_DETAIL = "/goods/getGoodsById";

    /**
     * 收藏商品
     */
    public static final String API_POST_FAV_GOODS = "/shUserFavGoods/favGoods";

    /**
     * 取消收藏商品
     */
    public static final String API_POST_UN_FAV_GOODS = "/shUserFavGoods/undoFavGoods";

    /**
     * 收藏店铺
     */
    public static final String API_POST_FAV_STORE = "/shUserFavStores/favStores";

    /**
     * 取消收藏店铺
     */
    public static final String API_POST_UN_FAV_STORE = "/shUserFavStores/undoFavStores";

    /**
     * 根据店铺类别获取商品列表
     */
    public static final String API_GET_GOODS_BYTYPE = "/goods/selectGoodsByType";

    /**
     * 商品修改
     */
    public static final String API_GET_GOODS_UPDATE = "/goods/updateGoodsById";
    /**
     * 商铺信息修改
     */
    public static final String API_POST_SHOPINFO_UPDATE = "/store/updateStoreInfo";

    /**
     * 商铺类别修改
     */
    public static final String API_POST_GOODS_TYPE_UPDATE = "/goodsType/updateGoodsType";
    /**
     * 商铺类别删除
     */
    public static final String API_POST_GOODS_TYPE_DELETE = "/goodsType/delGoodsType";
    /**
     * 商品上架、下架
     */
    public static final String API_POST_GOODS_DELETE = "/goods/delGoods";
    /**
     * 商品上架、下架
     */
    public static final String API_POST_GOODS_UPDATESTATUS = "/goods/updateStatus";

    /**
     * 商品收藏列表
     */
    public static final String API_GET_GOODS_FAV_LIST = "/shUserFavGoods/getFavGoodsList";
    /**
     * 店铺收藏列表
     */
    public static final String API_GET_STORES_FAV_LIST = "/shUserFavStores/getFavStoresList";

    /**
     * 获取商圈二级页面分区、推荐店铺、banner广告
     */
    public static final String API_GET_CIRCLE_LIST_TOP = "/homePage/getSysCircleInfo";

    /**
     * 获取商圈二级页面商铺列表
     */
    public static final String API_GET_CIRCLE_LIST = "/homePage/getStores";

    /**
     * 根据系统分类、店铺id获取商品列表，不传id就是根据系统分类获取列表
     */
    public static final String API_GET_GOODS_LIST_BY_SYSTYPE = "/homePage/getGoodsByStoreId";
    /**
     * 获取系统分类的相关信息：二级分类id
     */
    public static final String API_GET_SYSTYPEINFO = "/homePage/getSysTypeInfo";

    /**
     * 我的收获地址列表
     */
    public static final String API_GET_MY_ADDRESS = "/address/getAddresses";
    /**
     * 新增收货地址
     */
    public static final String API_POST_NEW_ADDRESS = "/address/addAddresses";
    /**
     * 修改收货地址
     */
    public static final String API_POST_UPDATE_ADDRESS = "/address/updateAddresses";
    /**
     * 删除收货地址
     */
    public static final String API_POST_DELETE_ADDRESS = "/address/delAddresses";
    /**
     * 意见反馈
     */
    public static final String API_POST_FEEDBACK = "/feedback/feedback";
    /**
     * 获取搜索关键词
     */
    public static final String API_GET_SEARCH_HOT_KEWWORDS = "/homePage/getKeywordsList";
    /**
     * 获取搜索结果
     */
    public static final String API_GET_SEARCH_RESULT = "/homePage/search";
    /**
     * 根据类型获取我的订单列表
     */
    public static final String API_GET_MYORDERS = "/order/user/list";
    /**
     * 根据类型获取我的商铺的订单列表
     */
    public static final String API_GET_STORE_MYORDERS = "/order/store/list";
    /**
     * 提交订单
     */
    public static final String API_POST_NEWORDERS = "/order/new";
    /**
     * 订单确认收货
     */
    public static final String API_POST_RECEIVE = "/order/user/receive";
    /**
     * 订单发货
     */
    public static final String API_POST_DELIVER_GOODS = "/order/store/deliver";
    /**
     * 订单评价
     */
    public static final String API_POST_EVALUATE_GOODS = "/order/user/rate";
    /**
     * 订单申请退货
     */
    public static final String API_POST_REFUND_GOODS = "/order/user/refund";
    /**
     * 获取订单详情
     */
    public static final String API_GET_ORDER_DETAIL = "/order/user/detail";
    /**
     * 获取商户订单详情
     */
    public static final String API_GET_STORE_ORDER_DETAIL = "/order/store/detail";
    /**
     * 商品订单退货处理
     */
    public static final String API_POST_STORE_ORDERS_REFUND = "/order/store/refund";
    /**
     * 获取视频列表
     */
    public static final String API_GET_VIDEO_LIST = "/microVideo/getMicroVideoList";
    /**
     * 获取服务列表
     */
    public static final String API_GET_SERVICE_LIST = "/convenience/getConvenienceList";
    /**
     * 获取优惠 折扣专区列表
     * /homePage/getGoodsByDiscoutId?discountId=1&pageNum=0
     */
    public static final String API_GET_DISCOUNT_LIST = "/homePage/getGoodsByDiscoutId";
    /**
     * 获取优惠 折扣专区banner
     * /homePage/getDiscountAdv
     */
    public static final String API_GET_DISCOUNT_BANNER = "/homePage/getDiscountAdv";
    /**
     * 获取微视频的类型和banner
     * /microVideo/getMicroVideoRelated
     */
    public static final String API_GET_VIDEO_BANNER = "/microVideo/getMicroVideoRelated";

    /**
     * 获取视频的收藏列表
     */
    public static final String API_GET_VIDOE_FAV_LIST = "/microVideo/selectFavMicroVideoList";


    /**
     * 获取便民服务的类型和banner
     * /microVideo/getMicroVideoRelated
     */
    public static final String API_GET_SERVICE_BANNER = "/convenience/getConvenienceRelated";
    /**
     * 获取便民服务的收藏列表
     */
    public static final String API_GET_SERVICE_FAV_LIST = "/convenience/selectFavConvenienceList";

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
