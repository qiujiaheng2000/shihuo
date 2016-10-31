package com.shihuo.shihuo.models;

/**
 * Created by cm_qiujiaheng on 2016/10/30.
 * 便民服务对象
 */
public class ServiceModel {
    public String serviceTitle;
    public String serviceDesc;
    public String serviceNumbs;
    public String serviceDate;
    public String serviceId;
    public String serviceUrl;
    public String serviceImageUrl;

    public ServiceModel() {
        super();
    }

    public ServiceModel(String serviceId, String serviceImageUrl, String serviceTitle, String serviceDesc, String serviceNumbs, String serviceDate, String serviceUrl) {
        this.serviceTitle = serviceTitle;
        this.serviceDesc = serviceDesc;
        this.serviceNumbs = serviceNumbs;
        this.serviceDate = serviceDate;
        this.serviceId = serviceId;
        this.serviceUrl = serviceUrl;
        this.serviceImageUrl = serviceImageUrl;
    }
}
