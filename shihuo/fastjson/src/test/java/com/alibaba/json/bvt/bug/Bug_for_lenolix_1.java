package com.alibaba.json.bvt.bug;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.alibaba.json.JSON;
import com.alibaba.json.JSONObject;
import com.alibaba.json.TypeReference;
import com.alibaba.json.serializer.SerializerFeature;

public class Bug_for_lenolix_1 extends TestCase {

    public void test_0() throws Exception {
        Map<String, User> matcherMap = new HashMap<String, User>();
        String matcherMapString = JSON.toJSONString(matcherMap, SerializerFeature.WriteClassName,
                                                    SerializerFeature.WriteMapNullValue);
        
        System.out.println(matcherMapString);
        
        matcherMap = JSONObject.parseObject(matcherMapString, new TypeReference<Map<String, User>>() {
        });
    }

    public static class User {

    }
}
