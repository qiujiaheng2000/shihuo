package com.alibaba.json.bvt.parser;

import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.json.JSON;
import com.alibaba.json.JSONException;
import com.alibaba.json.parser.DefaultJSONParser;
import com.alibaba.json.parser.Feature;

public class DefaultJSONParserTest2 extends TestCase {

    public void test_0() throws Exception {
        String text = "{}";
        Map map = (Map) JSON.parse(text);
        Assert.assertEquals(0, map.size());
    }

    public void test_1() throws Exception {
        JSONException error = null;
        try {
            String text = "{}a";
            Map map = (Map) JSON.parse(text);
            Assert.assertEquals(0, map.size());
        } catch (JSONException e) {
            error = e;
        }
        Assert.assertNotNull(error);
    }

    public void test_2() throws Exception {
        JSONException error = null;
        try {
            DefaultJSONParser parser = new DefaultJSONParser("{'a'3}");
            parser.config(Feature.AllowSingleQuotes, true);
            parser.parse();
        } catch (JSONException e) {
            error = e;
        }
        Assert.assertNotNull(error);
    }

    public void test_3() throws Exception {
        JSONException error = null;
        try {
            DefaultJSONParser parser = new DefaultJSONParser("{a 3}");
            parser.config(Feature.AllowUnQuotedFieldNames, true);
            parser.parse();
        } catch (JSONException e) {
            error = e;
        }
        Assert.assertNotNull(error);
    }

    public void test_4() throws Exception {
        JSONException error = null;
        try {
            DefaultJSONParser parser = new DefaultJSONParser("{");
            parser.config(Feature.AllowUnQuotedFieldNames, true);
            parser.parse();
        } catch (JSONException e) {
            error = e;
        }
        Assert.assertNotNull(error);
    }

    public void test_5() throws Exception {
        DefaultJSONParser parser = new DefaultJSONParser("{}");
        Map map = parser.parseObject();
        Assert.assertEquals(0, map.size());
    }
}
