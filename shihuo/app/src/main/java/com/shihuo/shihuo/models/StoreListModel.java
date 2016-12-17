package com.shihuo.shihuo.models;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lishuai on 16/12/15.
 */

public class StoreListModel {
    public static List<StoreDetailModel> parseStrJson(String strJson) {
        List<StoreDetailModel> list = new ArrayList<>();
        if (TextUtils.isEmpty(strJson)) {
            return list;
        }
        Gson gson = new Gson();
        try {
            JSONArray array = new JSONArray(strJson);
            for (int i = 0; i < array.length(); i++) {
                StoreDetailModel model = gson.fromJson(array.getString(i), StoreDetailModel.class);
                list.add(model);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
