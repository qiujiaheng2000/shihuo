
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

public class NotifyListModel {

    public static List<NotifyModel> parseStrJson(String strJson) {
        List<NotifyModel> list = new ArrayList<>();
        if (TextUtils.isEmpty(strJson)) {
            return list;
        }
        Gson gson = new Gson();
        try {
            JSONArray array = new JSONArray(strJson);
            for (int i = 0; i < array.length(); i++) {
                NotifyModel model = gson.fromJson(array.getString(i), NotifyModel.class);
                list.add(model);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

}
