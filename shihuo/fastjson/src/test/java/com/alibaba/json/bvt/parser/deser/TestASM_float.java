package com.alibaba.json.bvt.parser.deser;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.json.JSON;

public class TestASM_float extends TestCase {

    public void test_asm() throws Exception {
        V0 v = new V0();
        String text = JSON.toJSONString(v);
        V0 v1 = JSON.parseObject(text, V0.class);
        
        Assert.assertEquals(v.getValue(), v1.getValue());
    }

    public static class V0 {

        private float value = 32.5F;

        public float getValue() {
            return value;
        }

        public void setValue(float i) {
            this.value = i;
        }

    }
}