package com.alibaba.json.bvt.parser;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.json.JSON;
import com.alibaba.json.parser.DefaultExtJSONParser;
import com.alibaba.json.parser.Feature;
import com.alibaba.json.parser.ParserConfig;

public class DateParserTest_sql extends TestCase {

    public void f_test_date_0() throws Exception {
        DefaultExtJSONParser parser = new DefaultExtJSONParser("1294552193254");

        java.sql.Date date = parser.parseObject(java.sql.Date.class);

        Assert.assertEquals(new java.sql.Date(1294552193254L), date);
    }

    public void test_date_1() throws Exception {
        int featrues = JSON.DEFAULT_PARSER_FEATURE;
        featrues = Feature.config(featrues, Feature.AllowISO8601DateFormat, true);
        DefaultExtJSONParser parser = new DefaultExtJSONParser("\"2011-01-09T13:49:53.254\"", ParserConfig.getGlobalInstance(), featrues);

        java.sql.Date date = parser.parseObject(java.sql.Date.class);

        Assert.assertEquals(new java.sql.Date(1294552193254L), date);
    }

    public void test_date_2() throws Exception {
        int featrues = JSON.DEFAULT_PARSER_FEATURE;
        DefaultExtJSONParser parser = new DefaultExtJSONParser("new Date(1294552193254)", ParserConfig.getGlobalInstance(), featrues);

        java.sql.Date date = parser.parseObject(java.sql.Date.class);

        Assert.assertEquals(new java.sql.Date(1294552193254L), date);
    }

    public void test_date_3() throws Exception {
        int featrues = JSON.DEFAULT_PARSER_FEATURE;
        featrues = Feature.config(featrues, Feature.AllowISO8601DateFormat, true);
        DefaultExtJSONParser parser = new DefaultExtJSONParser("\"2011-01-09T13:49:53\"", ParserConfig.getGlobalInstance(), featrues);

        java.sql.Date date = parser.parseObject(java.sql.Date.class);

        Assert.assertEquals(new java.sql.Date(1294552193000L), date);
    }

    public void test_date_4() throws Exception {
        int featrues = JSON.DEFAULT_PARSER_FEATURE;
        featrues = Feature.config(featrues, Feature.AllowISO8601DateFormat, true);
        DefaultExtJSONParser parser = new DefaultExtJSONParser("\"2011-01-09\"", ParserConfig.getGlobalInstance(), featrues);

        java.sql.Date date = parser.parseObject(java.sql.Date.class);

        Assert.assertEquals(new java.sql.Date(1294502400000L), date);
    }
}
