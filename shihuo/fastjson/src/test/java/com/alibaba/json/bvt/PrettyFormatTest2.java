package com.alibaba.json.bvt;

import com.alibaba.json.JSON;

import junit.framework.Assert;
import junit.framework.TestCase;

public class PrettyFormatTest2 extends TestCase {

    public void test_prettyFormat() throws Exception {
        VO vo = new VO();
        vo.setId(123);
        vo.setName("jobs");
        
        Assert.assertEquals("{\n\t\"id\":123,\n\t\"name\":\"jobs\"\n}", JSON.toJSONString(vo, true));
    }

    public static class VO {

        private int    id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}
