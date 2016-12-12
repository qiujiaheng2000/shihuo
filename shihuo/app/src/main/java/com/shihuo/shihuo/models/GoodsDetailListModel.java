
package com.shihuo.shihuo.models;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by lishuai on 16/12/13.
 */

public class GoodsDetailListModel {
    public List<GoodsDetailModel> resultList;

    public static List<GoodsDetailModel> parseStrJson(String strJson) {
        Gson gson = new Gson();
        List<GoodsDetailModel> list = (List<GoodsDetailModel>)gson.fromJson(strJson,
                LoginModel.class);
        return list;
    }

}
