package com.alibaba.json.bvt.serializer;

import junit.framework.TestCase;

import com.alibaba.json.parser.ParserConfig;

public class ParserConfigTest extends TestCase {

    public void test_0() throws Exception {
        ParserConfig config = new ParserConfig();
        config.getDerializers();
        config.getDefaultSerializer();
    }

    public void test_error_0() throws Exception {
        ParserConfig config = new ParserConfig();

        config.createJavaBeanDeserializer(int.class);

    }
}
