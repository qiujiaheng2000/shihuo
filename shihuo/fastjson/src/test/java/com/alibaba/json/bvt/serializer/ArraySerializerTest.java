package com.alibaba.json.bvt.serializer;

import junit.framework.TestCase;

import org.junit.Assert;

import com.alibaba.json.serializer.JSONSerializer;
import com.alibaba.json.serializer.SerializeWriter;
import com.alibaba.json.util.IOUtils;

public class ArraySerializerTest extends TestCase {

    public void test_0() throws Exception {
        SerializeWriter out = new SerializeWriter(1);

        JSONSerializer.write(out, new A[] { new A(), null, null });

        Assert.assertEquals("[{},null,null]", out.toString());
    }

    public void test_1() throws Exception {
        SerializeWriter out = new SerializeWriter(1);

        JSONSerializer.write(out, new A[] {});

        Assert.assertEquals("[]", out.toString());

        new IOUtils();
    }

    public void test_2() throws Exception {
        SerializeWriter out = new SerializeWriter(1);

        JSONSerializer.write(out, new A[] { new A() });

        Assert.assertEquals("[{}]", out.toString());

        new IOUtils();
    }

    public static class A {

    }
}
