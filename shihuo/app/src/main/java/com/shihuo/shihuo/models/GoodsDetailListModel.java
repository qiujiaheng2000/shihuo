
package com.shihuo.shihuo.models;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lishuai on 16/12/13.
 */

public class GoodsDetailListModel {
    public static List<GoodsDetailModel> parseStrJson(String strJson) {
        List<GoodsDetailModel> list = new ArrayList<>();
        if (TextUtils.isEmpty(strJson)) {
            return list;
        }
        Gson gson = new Gson();
        try {
            JSONArray array = new JSONArray(strJson);
            for (int i = 0; i < array.length(); i++) {
                GoodsDetailModel model = gson.fromJson(array.getString(i), GoodsDetailModel.class);
                list.add(model);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

}
