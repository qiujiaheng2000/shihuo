package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * Created by cm_qiujiaheng on 2016/10/30.
 * 视频对象
 * <p>
 * url:/shihuo/app/microVideo/getMicroVideoList?pageNum=1&typeId=0
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
 * "browseNum": 11,
 * "createTime": "2017-01-08 12:40:07",
 * "imgUrl": "11",
 * "isFav": 0,
 * "mDetail": "11",
 * "mId": 1,
 * "mName": "11",
 * "mTypeId": 2,
 * "imgUrl": "11"
 * }
 * ],
 * "totalCount": 1,
 * "totalPage": 1
 * }
 * }
 * }
 */
public class VideoModel implements Parcelable {
    public String browseNum;
    public String createTime;
    public String imgUrl;
    public int isFav;
    public String mDetail;
    public int mId;
    public String mName;
    public int mTypeId;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.browseNum);
        dest.writeString(this.createTime);
        dest.writeString(this.imgUrl);
        dest.writeInt(this.isFav);
        dest.writeString(this.mDetail);
        dest.writeInt(this.mId);
        dest.writeString(this.mName);
        dest.writeInt(this.mTypeId);
    }

    public VideoModel() {
    }

    protected VideoModel(Parcel in) {
        this.browseNum = in.readString();
        this.createTime = in.readString();
        this.imgUrl = in.readString();
        this.isFav = in.readInt();
        this.mDetail = in.readString();
        this.mId = in.readInt();
        this.mName = in.readString();
        this.mTypeId = in.readInt();
    }

    public static final Parcelable.Creator<VideoModel> CREATOR = new Parcelable.Creator<VideoModel>() {
        @Override
        public VideoModel createFromParcel(Parcel source) {
            return new VideoModel(source);
        }

        @Override
        public VideoModel[] newArray(int size) {
            return new VideoModel[size];
        }
    };

    public static VideoModel parseFromJsonStr(String jsonStr) {
        Gson gson = new Gson();
        VideoModel videoModel = gson.fromJson(jsonStr, VideoModel.class);
        return videoModel;
    }
}
