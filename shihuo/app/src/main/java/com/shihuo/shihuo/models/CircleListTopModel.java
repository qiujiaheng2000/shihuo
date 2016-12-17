
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
 * Created by lishuai on 16/12/15.
 */

public class CircleListTopModel {

    // banner
    public List<GoodsTypeModel> shAdvertisingList;

    // 商圈分区
    public List<SpecificationModel> shSysStoreCircleList;

    // 推荐店铺
    public List<SpecificationModel> shStoresList;

    public static CircleListTopModel parseStrJson(String strJson) {
        CircleListTopModel model = new CircleListTopModel();
        List<SpecificationModel> listAre = new ArrayList<>();
        List<GoodsTypeModel> listBanner = new ArrayList<>();
        List<SpecificationModel> listLabel = new ArrayList<>();
        Gson gson = new Gson();
        try {
            JSONObject object = new JSONObject(strJson);
            // 解析商圈分区
            JSONArray arrayAre = new JSONArray(object.getString("shSysStoreCircleList"));
            for (int i = 0; i < arrayAre.length(); i++) {
                SpecificationModel areModel = gson.fromJson(arrayAre.getString(i), SpecificationModel.class);
                listAre.add(areModel);
            }

            // 解析推荐店铺
            JSONArray arrayStore = new JSONArray(object.getString("shStoresList"));
            for (int i = 0; i < arrayStore.length(); i++) {
                SpecificationModel labelModel = gson.fromJson(arrayStore.getString(i), SpecificationModel.class);
                listLabel.add(labelModel);
            }

            // 解析banner
            JSONArray arrayBanner = new JSONArray(object.getString("shAdvertisingList"));
            for (int i = 0; i < arrayBanner.length(); i++) {
                GoodsTypeModel bannerModel = gson.fromJson(arrayBanner.getString(i), GoodsTypeModel.class);
                listBanner.add(bannerModel);
            }

            model.shAdvertisingList = listBanner;
            model.shSysStoreCircleList = listAre;
            model.shStoresList = listLabel;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return model;
    }
}
