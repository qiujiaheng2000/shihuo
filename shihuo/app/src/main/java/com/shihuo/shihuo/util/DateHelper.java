package com.shihuo.shihuo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cm_qiujiaheng on 2017/1/15.
 * 日期帮助工具
 */

public class DateHelper {

    /**
     * 获取现在日期
     *
     * @return 返回短时间字符串格式yyyyMMdd
     */
    public static String getStringDateDay() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取现在月
     *
     * @return 返回短时间字符串格式yyyyMM
     */
    public static String getStringDateMonth() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取现在年
     *
     * @return 返回短时间字符串格式yyyy
     */
    public static String getStringDateYear() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
}
