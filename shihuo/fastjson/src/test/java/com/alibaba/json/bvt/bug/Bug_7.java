package com.alibaba.json.bvt.bug;

import java.math.BigInteger;

import junit.framework.TestCase;

import org.junit.Assert;

import com.alibaba.json.JSON;

public class Bug_7 extends TestCase {

    public void test_floatArray() throws Exception {
        float[] a = new float[] { 1, 2 };
        String text = JSON.toJSONString(a);
        Assert.assertEquals("[1,2]", text);
        JSON json = (JSON) JSON.parse(text);
        Assert.assertEquals("[1,2]", json.toJSONString());
    }

    public void test_doubleArray() throws Exception {
        double[] a = new double[] { 1, 2 };
        String text = JSON.toJSONString(a);
        Assert.assertEquals("[1,2]", text);
        JSON json = (JSON) JSON.parse(text);
        Assert.assertEquals("[1,2]", json.toJSONString());
    }

    public void test_bigintegerArray() throws Exception {
        BigInteger[] a = new BigInteger[] { new BigInteger("214748364812"), new BigInteger("2147483648123") };
        String text = JSON.toJSONString(a);
        Assert.assertEquals("[214748364812,2147483648123]", text);
        JSON json = (JSON) JSON.parse(text);
        Assert.assertEquals("[214748364812,2147483648123]", json.toJSONString());
    }

}
   
