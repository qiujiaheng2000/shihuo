package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * Created by cm_qiujiaheng on 2016/10/30.
 * 便民服务对象
 */
public class ServiceModel implements Parcelable {
    public String cName;
    public String cDetail;
    public String browseNum;
    public String createTime;
    public int cId;
    public int isFav;
    public int cTypeId;
    public String linkUrl;
    public String imgUrl;


    public static ServiceModel parseFromJsonStr(String jsonStr) {
        Gson gson = new Gson();
        ServiceModel serviceModel = gson.fromJson(jsonStr, ServiceModel.class);
        return serviceModel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cName);
        dest.writeString(this.cDetail);
        dest.writeString(this.browseNum);
        dest.writeString(this.createTime);
        dest.writeInt(this.cId);
        dest.writeInt(this.isFav);
        dest.writeInt(this.cTypeId);
        dest.writeString(this.linkUrl);
        dest.writeString(this.imgUrl);
    }

    public ServiceModel() {
    }

    protected ServiceModel(Parcel in) {
        this.cName = in.readString();
        this.cDetail = in.readString();
        this.browseNum = in.readString();
        this.createTime = in.readString();
        this.cId = in.readInt();
        this.isFav = in.readInt();
        this.cTypeId = in.readInt();
        this.linkUrl = in.readString();
        this.imgUrl = in.readString();
    }

    public static final Parcelable.Creator<ServiceModel> CREATOR = new Parcelable.Creator<ServiceModel>() {
        @Override
        public ServiceModel createFromParcel(Parcel source) {
            return new ServiceModel(source);
        }

        @Override
        public ServiceModel[] newArray(int size) {
            return new ServiceModel[size];
        }
    };
}
/**
 * #####2 分页获取便民列表
 * url:/shihuo/app/convenience/getConvenienceList?pageNum=1&typeId=0
 * method:GET
 * response:
 * {
 * "code": 0,
 * "data": {
 * "page": {
 * "pageNum": 1,
 * "pageSize": 20,
 * "resultList": [
 * {
 * "browseNum": 1,
 * "cDetail": "222",
 * "cId": 2,
 * "cName": "2",
 * "cTypeId": 2,
 * "createTime": "2017-01-07 23:12:14",
 * "imgUrl": "1",
 * "isFav": 0,
 * "linkUrl": "1"
 * },
 * {
 * "browseNum": 1,
 * "cDetail": "111",
 * "cId": 1,
 * "cName": "1",
 * "cTypeId": 1,
 * "createTime": "2017-01-07 23:11:59",
 * "imgUrl": "1",
 * "isFav": 0,
 * "linkUrl": "1"
 * }
 * ],
 * "totalCount": 2,
 * "totalPage": 1
 * }
 * }
 * }
 */