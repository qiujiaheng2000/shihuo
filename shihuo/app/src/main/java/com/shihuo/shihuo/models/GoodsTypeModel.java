
package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类、商圈分类 Created by lishuai on 16/11/29.
 */

public class GoodsTypeModel implements Parcelable {

    public static List<GoodsTypeModel> parseStrJson(String strJson) {
        List<GoodsTypeModel> list = new ArrayList<>();
        if (TextUtils.isEmpty(strJson)) {
            return list;
        }
        Gson gson = new Gson();
        try {
            JSONArray array = new JSONArray(strJson);
            for (int i = 0; i < array.length(); i++) {
                GoodsTypeModel model = gson.fromJson(array.getString(i), GoodsTypeModel.class);
                list.add(model);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public String createTime;

    public int isValid;

    public String logo;

    public int orderNum;

    public int typeId;

    public int parentId;

    public int circleId;

    public String typeName;

    public String logoPicUrl;

    public String redirectSchemaUrl;

    public String circleName;

    public String typeUrl;

    public int adId;

    public String adName;

    public String adPicUrl;

    public int adType;

    public String redirectUrl;

    public int sort;

    public String detail;

    public int discountId;

    public String discountName;

    public String discountPicUrl;

    public String storeId;

    public int discountType;

    public GoodsTypeModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createTime);
        dest.writeInt(this.isValid);
        dest.writeString(this.logo);
        dest.writeInt(this.orderNum);
        dest.writeInt(this.typeId);
        dest.writeInt(this.parentId);
        dest.writeInt(this.circleId);
        dest.writeString(this.typeName);
        dest.writeString(this.logoPicUrl);
        dest.writeString(this.redirectSchemaUrl);
        dest.writeString(this.circleName);
        dest.writeString(this.typeUrl);
        dest.writeInt(this.adId);
        dest.writeString(this.adName);
        dest.writeString(this.adPicUrl);
        dest.writeInt(this.adType);
        dest.writeString(this.redirectUrl);
        dest.writeInt(this.sort);
        dest.writeString(this.detail);
        dest.writeInt(this.discountId);
        dest.writeString(this.discountName);
        dest.writeString(this.discountPicUrl);
        dest.writeInt(this.discountType);
    }

    protected GoodsTypeModel(Parcel in) {
        this.createTime = in.readString();
        this.isValid = in.readInt();
        this.logo = in.readString();
        this.orderNum = in.readInt();
        this.typeId = in.readInt();
        this.parentId = in.readInt();
        this.circleId = in.readInt();
        this.typeName = in.readString();
        this.logoPicUrl = in.readString();
        this.redirectSchemaUrl = in.readString();
        this.circleName = in.readString();
        this.typeUrl = in.readString();
        this.adId = in.readInt();
        this.adName = in.readString();
        this.adPicUrl = in.readString();
        this.adType = in.readInt();
        this.redirectUrl = in.readString();
        this.sort = in.readInt();
        this.detail = in.readString();
        this.discountId = in.readInt();
        this.discountName = in.readString();
        this.discountPicUrl = in.readString();
        this.discountType = in.readInt();
    }

    public static final Creator<GoodsTypeModel> CREATOR = new Creator<GoodsTypeModel>() {
        @Override
        public GoodsTypeModel createFromParcel(Parcel source) {
            return new GoodsTypeModel(source);
        }

        @Override
        public GoodsTypeModel[] newArray(int size) {
            return new GoodsTypeModel[size];
        }
    };

    public static GoodsTypeModel parseJsonStr(JSONObject jsonObject) {
        Gson gson = new Gson();
        GoodsTypeModel goodsTypeModel = gson.fromJson(jsonObject.toString(),GoodsTypeModel.class);
        return goodsTypeModel;
    }
}
