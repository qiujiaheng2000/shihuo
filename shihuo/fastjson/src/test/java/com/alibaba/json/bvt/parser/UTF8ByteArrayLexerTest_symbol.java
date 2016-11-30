package com.alibaba.json.bvt.parser;

import junit.framework.TestCase;

import com.alibaba.json.JSON;
import com.alibaba.json.JSONObject;

public class UTF8ByteArrayLexerTest_symbol extends TestCase {

    public void test_utf8() throws Exception {
        byte[] bytes = "{\"name\":\"温家宝\", \"name\":\"xx\"}".getBytes("UTF-8");

        JSONObject json = JSON.parseObject(bytes, JSONObject.class);
    }
}
