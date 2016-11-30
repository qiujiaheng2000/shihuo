package com.alibaba.json.bvt;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.json.JSON;
import com.alibaba.json.JSONObject;
import com.alibaba.json.serializer.SerializeConfig;
import com.alibaba.json.serializer.SerializerFeature;

public class TabCharTest extends TestCase {

    public void test_0() throws Exception {
        JSONObject json = new JSONObject();
        json.put("hello\t", "World\t!");
        Assert.assertEquals("{\"hello\\t\":\"World\\t!\"}", JSON.toJSONString(json));
        Assert.assertEquals("{\"hello\t\":\"World\t!\"}", JSON.toJSONStringZ(json, SerializeConfig.getGlobalInstance(), SerializerFeature.QuoteFieldNames));
        Assert.assertEquals("{'hello\\t':'World\\t!'}", JSON.toJSONString(json, SerializerFeature.WriteTabAsSpecial, SerializerFeature.UseSingleQuotes));
    }

}
