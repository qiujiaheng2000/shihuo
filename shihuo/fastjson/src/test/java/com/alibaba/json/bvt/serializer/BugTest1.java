package com.alibaba.json.bvt.serializer;

import java.util.concurrent.atomic.AtomicReference;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.json.JSON;
import com.alibaba.json.serializer.SerializerFeature;

public class BugTest1 extends TestCase {


    public void test_5() throws Exception {
        Assert.assertEquals("\"java.util.concurrent.atomic.AtomicReference\"", JSON.toJSONString(AtomicReference.class));
    }

    public void test_7() throws Exception {
        Assert.assertEquals("'java.util.concurrent.atomic.AtomicReference'", JSON.toJSONString(AtomicReference.class, SerializerFeature.UseSingleQuotes));
    }
}
