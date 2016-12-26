
package com.shihuo.shihuo.models;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * Created by lishuai on 16/12/26.
 */

public class SearchModel extends BaseModel {
    /**
     * 搜索历史item
     */
    public final static int ITEM_TYPE_TITLE = 0;

    /**
     * 清除搜索历史
     */
    public final static int ITEM_TYPE_TITLE_CLEAR = 1;

    /**
     * 热门搜索
     */
    public final static int ITEM_TYPE_HOT = 2;

    /**
     * 搜索结果title
     */
    public final static int ITEM_TYPE_TITLE_RESULTE = 3;

    /**
     * 店铺
     */
    public final static int ITEM_TYPE_STORE = 4;

    /**
     * 商品
     */
    public final static int ITEM_TYPE_GOODS = 5;

    public int item_type;

    /**
     * title名称
     */
    public String item_type_title;

    /**
     * 热门搜索
     */
    public List<String> dataList;

    /**
     * 商铺model
     */
    public StoreDetailModel shStores;

    /**
     * 商品model
     */
    public BaseGoodsModel shGoods;

    /**
     * 商铺列表
     */
    public List<StoreDetailModel> shStoresList;

    /**
     * 商品列表
     */
    public List<GoodsModel> shGoodsList;

    public static List<String> parseJsonHotKeyWords(String jsonStr) {
        List<String> list = new ArrayList<>();
        if (!TextUtils.isEmpty(jsonStr)) {
            try {
                JSONObject object = new JSONObject(jsonStr);
                JSONArray array = object.getJSONArray("dataList");
                for (int i = 0; i < array.length(); i++) {
                    list.add(array.getString(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static SearchModel parseJsonStoreAndGoods(String jsonStr) {
        SearchModel model = new SearchModel();
        List<StoreDetailModel> shStoresList = new ArrayList<>();
        List<GoodsModel> shGoodsList = new ArrayList<>();
        if (!TextUtils.isEmpty(jsonStr)) {
            try {
                JSONObject object = new JSONObject(jsonStr);
                // 店铺
                JSONArray arrayStore = object.getJSONArray("shStoresList");
                for (int i = 0; i < arrayStore.length(); i++) {
                    JSONObject objectModel = (JSONObject)arrayStore.get(i);
                    StoreDetailModel storeModel = StoreDetailModel.parseJsonStr(objectModel);
                    shStoresList.add(storeModel);
                }

                // 商品
                JSONArray arrayGoods = object.getJSONArray("shGoodsList");
                for (int i = 0; i < arrayGoods.length(); i++) {
                    JSONObject objectModel = (JSONObject)arrayGoods.get(i);
                    GoodsModel storeModel = GoodsModel.parseJsonStr(objectModel);
                    shGoodsList.add(storeModel);
                }
                model.shStoresList = shStoresList;
                model.shGoodsList = shGoodsList;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return model;
    }

}
