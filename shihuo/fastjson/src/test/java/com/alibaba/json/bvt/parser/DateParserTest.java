package com.alibaba.json.bvt.parser;

import java.util.Date;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.json.JSON;
import com.alibaba.json.JSONException;
import com.alibaba.json.JSONObject;
import com.alibaba.json.parser.DefaultExtJSONParser;
import com.alibaba.json.parser.Feature;
import com.alibaba.json.parser.ParserConfig;

public class DateParserTest extends TestCase {

    public void test_date_0() throws Exception {
        DefaultExtJSONParser parser = new DefaultExtJSONParser("1294552193254");

        Date date = parser.parseObject(Date.class);

        Assert.assertEquals(new Date(1294552193254L), date);
    }

    public void test_date_1() throws Exception {
        int featrues = JSON.DEFAULT_PARSER_FEATURE;
        featrues = Feature.config(featrues, Feature.AllowISO8601DateFormat, true);
        DefaultExtJSONParser parser = new DefaultExtJSONParser("\"2011-01-09T13:49:53.254\"", ParserConfig.getGlobalInstance(), featrues);

        Date date = parser.parseObject(Date.class);

        Assert.assertEquals(new Date(1294552193254L), date);
    }

    public void test_date_2() throws Exception {
        int featrues = JSON.DEFAULT_PARSER_FEATURE;
        DefaultExtJSONParser parser = new DefaultExtJSONParser("new Date(1294552193254)", ParserConfig.getGlobalInstance(), featrues);

        Date date = parser.parseObject(Date.class);

        Assert.assertEquals(new Date(1294552193254L), date);
    }

    public void test_date_3() throws Exception {
        Date date = JSON.parseObject("\"2011-01-09T13:49:53\"", Date.class, Feature.AllowISO8601DateFormat);

        Assert.assertEquals(new Date(1294552193000L), date);
    }

    public void test_date_4() throws Exception {
        int featrues = JSON.DEFAULT_PARSER_FEATURE;
        featrues = Feature.config(featrues, Feature.AllowISO8601DateFormat, true);
        DefaultExtJSONParser parser = new DefaultExtJSONParser("\"2011-01-09\"", ParserConfig.getGlobalInstance(), featrues);

        Date date = parser.parseObject(Date.class);

        Assert.assertEquals(new Date(1294502400000L), date);
    }

    public void test_date_5() throws Exception {
        JSONObject object = JSON.parseObject("{d:'2011-01-09T13:49:53'}", Feature.AllowISO8601DateFormat);
        Assert.assertEquals(new Date(1294552193000L), object.get("d"));
    }

    public void test_date_6() throws Exception {
        int featrues = JSON.DEFAULT_PARSER_FEATURE;
        featrues = Feature.config(featrues, Feature.AllowISO8601DateFormat, true);

        Date date = JSON.parseObject("{d:\"2011-01-09T13:49:53\"}", Entity.class, Feature.AllowISO8601DateFormat).getD();

        Assert.assertEquals(new Date(1294552193000L), date);
    }

    public void test_date_7() throws Exception {
        Entity entity = JSON.parseObject("{d:'2011-01-09T13:49:53'}", Entity.class, Feature.AllowISO8601DateFormat);
        Date date = entity.getD();

        Assert.assertEquals(new Date(1294552193000L), date);
    }

    public void test_date_error_0() throws Exception {

        JSONException error = null;
        try {
            DefaultExtJSONParser parser = new DefaultExtJSONParser("true");

            parser.parseObject(Date.class);
        } catch (JSONException e) {
            error = e;
        }
        Assert.assertNotNull(error);
    }

    public static class Entity {

        private Date d;

        public Date getD() {
            return d;
        }

        public void setD(Date d) {
            this.d = d;
        }

    }
}
