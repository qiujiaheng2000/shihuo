package com.alibaba.json.bvt;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.json.JSON;


public class PrettyFormatTest extends TestCase {
    public void test_prettyFormat() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", 123);
        map.put("name", "jobs");
        Assert.assertEquals("{\n\t\"id\":123,\n\t\"name\":\"jobs\"\n}", JSON.toJSONString(map, true));
    }
}
