package com.alibaba.json.bvt.bug;

import junit.framework.TestCase;

import com.alibaba.json.JSON;
import com.alibaba.json.serializer.SerializerFeature;

public class Bug_for_qqdwll2012 extends TestCase {

    public void test_for_x() throws Exception {
        VO vo = new VO();
        vo.setValue("<a href=\"http://www.baidu.com\"> 问题链接 </a> |");
        
        String text = JSON.toJSONString(vo, SerializerFeature.WriteSlashAsSpecial);
        System.out.println(text);
    }

    public static class VO {

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }
}