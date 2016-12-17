package com.shihuo.shihuo.models;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cm_qiujiaheng on 2016/11/3.
 * 店铺模型
 */

public class ShopsModel {
    public String shopName;//店铺名称
    public String shopDesc;//店铺描述
    public String shopSales;//店铺销量
    public String shopIcon;//店铺logo
    public String shopAdd;//店铺地址


    public static List<ShopsModel> parseStrJson(String strJson) {
        List<ShopsModel> list = new ArrayList<>();
        if (TextUtils.isEmpty(strJson)) {
            return list;
        }
        Gson gson = new Gson();
        try {
            JSONArray array = new JSONArray(strJson);
            for (int i = 0; i < array.length(); i++) {
                ShopsModel model = gson.fromJson(array.getString(i), ShopsModel.class);
                list.add(model);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
