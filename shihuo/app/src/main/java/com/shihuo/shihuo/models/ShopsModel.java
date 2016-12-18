package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;
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

public class ShopsModel implements Parcelable {
    public String userId;//店铺地址
    public String storeName;//店铺名称
    public String storeLogoPicUrl;//店铺log图片地址
    public String storeId;//店铺id
    public String storeDetail;//店铺详情
    public int orderNum;//店铺订单数量
    public int isValid;//店铺是否有效
    public int favstoreId;//收藏的店铺id
    public String favTime;//店铺收藏时间
    public String circleName;//商圈名称
    public int circleId;//商圈id


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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.storeName);
        dest.writeString(this.storeLogoPicUrl);
        dest.writeString(this.storeId);
        dest.writeString(this.storeDetail);
        dest.writeInt(this.orderNum);
        dest.writeInt(this.isValid);
        dest.writeInt(this.favstoreId);
        dest.writeString(this.favTime);
        dest.writeString(this.circleName);
        dest.writeInt(this.circleId);
    }

    public ShopsModel() {
    }

    protected ShopsModel(Parcel in) {
        this.userId = in.readString();
        this.storeName = in.readString();
        this.storeLogoPicUrl = in.readString();
        this.storeId = in.readString();
        this.storeDetail = in.readString();
        this.orderNum = in.readInt();
        this.isValid = in.readInt();
        this.favstoreId = in.readInt();
        this.favTime = in.readString();
        this.circleName = in.readString();
        this.circleId = in.readInt();
    }

    public static final Parcelable.Creator<ShopsModel> CREATOR = new Parcelable.Creator<ShopsModel>() {
        @Override
        public ShopsModel createFromParcel(Parcel source) {
            return new ShopsModel(source);
        }

        @Override
        public ShopsModel[] newArray(int size) {
            return new ShopsModel[size];
        }
    };
}
