package com.alibaba.json.bvt.serializer;

import java.io.StringWriter;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.json.JSON;


public class AppendableTest extends TestCase {

    public void test_stringbufer() throws Exception {
        Appendable obj = new StringBuffer();
        obj.append("abc");
        
        String text = JSON.toJSONString(obj);
        
        Assert.assertEquals("\"abc\"", text);     
        

    }
    
    public void test_stringwriter() throws Exception {
        Appendable obj = new StringWriter();
        obj.append("abc");
        
        String text = JSON.toJSONString(obj);
        
        Assert.assertEquals("\"abc\"", text);     
    }
}
