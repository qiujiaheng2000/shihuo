package com.alibaba.json.bvt.serializer.indent;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.json.JSON;
import com.alibaba.json.serializer.JSONSerializer;
import com.alibaba.json.serializer.SerializerFeature;

public class PrettyFormatTest extends TestCase {

    public void test_0() throws Exception {
        Assert.assertEquals(0, new JSONSerializer().getIndentCount());
        
        Assert.assertEquals("[\n\t{},\n\t{}\n]", JSON.toJSONString(new Object[] { new Object(), new Object() }, SerializerFeature.PrettyFormat));
    }
}
