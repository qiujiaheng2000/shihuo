package com.alibaba.json.test;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.json.JSON;
import com.alibaba.json.serializer.SerializeConfig;
import com.alibaba.json.serializer.SerializerFeature;

public class IntArrayFieldTest_primitive extends TestCase {

    public void test_array() throws Exception {
        Assert.assertEquals("[1]", JSON.toJSONString(new int[] { 1 }));

    }

    public void test_codec_null() throws Exception {
        V0 v = new V0();

        SerializeConfig mapping = new SerializeConfig();
        mapping.setAsmEnable(false);

        String text = JSON.toJSONString(v, mapping, SerializerFeature.WriteMapNullValue);
        Assert.assertEquals("{\"value\":null}", text);

        V0 v1 = JSON.parseObject(text, V0.class);

        Assert.assertEquals(v1.getValue(), v.getValue());
    }

    public void test_codec_null_1() throws Exception {
        V0 v = new V0();

        SerializeConfig mapping = new SerializeConfig();
        mapping.setAsmEnable(false);
        Assert.assertTrue(!mapping.isAsmEnable());

        String text = JSON.toJSONString(v, mapping, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty);
        Assert.assertEquals("{\"value\":[]}", text);
    }

    public static class V0 {

        private int[] value;

        public int[] getValue() {
            return value;
        }

        public void setValue(int[] value) {
            this.value = value;
        }

    }
}
