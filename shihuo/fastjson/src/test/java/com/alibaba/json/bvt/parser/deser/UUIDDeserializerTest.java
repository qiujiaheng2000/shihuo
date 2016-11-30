package com.alibaba.json.bvt.parser.deser;

import java.util.UUID;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.json.JSON;
import com.alibaba.json.JSONException;
import com.alibaba.json.parser.DefaultExtJSONParser;
import com.alibaba.json.parser.JSONToken;
import com.alibaba.json.parser.ParserConfig;
import com.alibaba.json.parser.deserializer.SimpleTypeDeserializer;

public class UUIDDeserializerTest extends TestCase {

    public void test_url() throws Exception {
        UUID id = UUID.randomUUID();
        Assert.assertEquals(id, JSON.parseObject("'" + id.toString() + "'", UUID.class));

        Assert.assertEquals(null, JSON.parseObject("null", UUID.class));

        DefaultExtJSONParser parser = new DefaultExtJSONParser("null", ParserConfig.getGlobalInstance(), JSON.DEFAULT_PARSER_FEATURE);
        Assert.assertEquals(null, SimpleTypeDeserializer.instance.deserialze(parser, null, null));
        Assert.assertEquals(JSONToken.LITERAL_STRING, SimpleTypeDeserializer.instance.getFastMatchToken());

    }

    public void test_url_error() throws Exception {
        JSONException ex = null;
        try {
            JSON.parseObject("'123'", UUID.class);
        } catch (JSONException e) {
            ex = e;
        }
        Assert.assertNotNull(ex);
    }
}
