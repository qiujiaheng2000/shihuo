package com.shihuo.shihuo.models;

/**
 * Created by cm_qiujiaheng on 2016/10/30.
 * 视频对象
 */
public class VideoModel {
    public String videoTitle;
    public String videoDesc;
    public String videoNumbs;
    public String videoDate;
    public String videoId;
    public String videoUrl;
    public String videoImageUrl;

    public VideoModel() {
        super();
    }

    public VideoModel(String videoId, String videoImageUrl, String videoTitle, String videoDesc, String videoNumbs, String videoDate, String videoUrl) {
        this.videoTitle = videoTitle;
        this.videoDesc = videoDesc;
        this.videoNumbs = videoNumbs;
        this.videoDate = videoDate;
        this.videoId = videoId;
        this.videoUrl = videoUrl;
        this.videoImageUrl = videoImageUrl;
    }
}
