package com.alibaba.json.bvt;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.json.JSON;
import com.alibaba.json.annotation.JSONField;
import com.alibaba.json.serializer.SerializeConfig;
import com.alibaba.json.serializer.SerializerFeature;

public class FeaturesTest6 extends TestCase {

    public void test_0() throws Exception {
        SerializeConfig config = new SerializeConfig();
        config.setAsmEnable(false);
        
        String text = JSON.toJSONString(new Entity(), config);
        Assert.assertEquals("{\"value\":[]}", text);
    }
    
    public void test_1() throws Exception {
        SerializeConfig config = new SerializeConfig();
        config.setAsmEnable(true);
        
        String text = JSON.toJSONString(new Entity(), config);
        Assert.assertEquals("{\"value\":[]}", text);
    }

    public static class Entity {

        private List value;

        @JSONField(serialzeFeatures = { SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty })
        public List getValue() {
            return value;
        }


    }
}
