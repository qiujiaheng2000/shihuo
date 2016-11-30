package com.alibaba.json.bvt;

import java.util.Date;

import junit.framework.TestCase;

import com.alibaba.json.JSON;
import com.alibaba.json.annotation.JSONField;
import com.alibaba.json.serializer.SerializerFeature;

public class DateTest extends TestCase {

    public void test_date() throws Exception {
        long millis = 1324138987429L;
        Date date = new Date(millis);

        VO vo = new VO();
        vo.setDate(date);
        
        System.out.println(JSON.toJSONString(date));

        System.out.println(JSON.toJSONString(date, SerializerFeature.WriteDateUseDateFormat));
        System.out.println(JSON.toJSONString(date));
        System.out.println(JSON.toJSONStringWithDateFormat(vo, "yyyy-MM-dd HH:mm:ss.SSS",
                                                           SerializerFeature.PrettyFormat));
    }

    public static class VO {

        private Date date;

        @JSONField(format="yyyy-MM-dd HH:mm:ss.SS")
        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

    }
}
