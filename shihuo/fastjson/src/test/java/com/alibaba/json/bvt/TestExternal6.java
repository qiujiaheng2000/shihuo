package com.alibaba.json.bvt;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Method;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.commons.io.IOUtils;

import com.alibaba.json.JSON;
import com.alibaba.json.JSONObject;
import com.alibaba.json.serializer.SerializerFeature;

public class TestExternal6 extends TestCase {

    public void test_0() throws Exception {
        ExtClassLoader classLoader = new ExtClassLoader();
        Class<?> clazz = classLoader.loadClass("org.mule.esb.model.tcc.result.EsbResultModel");
        Method[] methods = clazz.getMethods();
        Method method = clazz.getMethod("setReturnValue", new Class[] { Serializable.class });

        Object obj = clazz.newInstance();
//        method.invoke(obj, "AAAA");

        {
            String text = JSON.toJSONString(obj);
            System.out.println(text);
        }

        String text = JSON.toJSONString(obj, SerializerFeature.WriteClassName, SerializerFeature.WriteMapNullValue);
        System.out.println(text);
        JSON.parseObject(text, clazz);
        JSONObject jsonObj = JSON.parseObject(text);
        Assert.assertEquals(jsonObj.getString("@type"), "org.mule.esb.model.tcc.result.EsbResultModel");
    }

    public static class ExtClassLoader extends ClassLoader {

        public ExtClassLoader() throws IOException{
            super(Thread.currentThread().getContextClassLoader());

            {
                byte[] bytes;
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("external/EsbResultModel.clazz");
                bytes = IOUtils.toByteArray(is);
                is.close();

                super.defineClass("org.mule.esb.model.tcc.result.EsbResultModel", bytes, 0, bytes.length);
            }
            {
                byte[] bytes;
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("external/EsbListBean.clazz");
                bytes = IOUtils.toByteArray(is);
                is.close();
                
                super.defineClass("org.esb.crm.tools.EsbListBean", bytes, 0, bytes.length);
            }
            {
                byte[] bytes;
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("external/EsbHashMapBean.clazz");
                bytes = IOUtils.toByteArray(is);
                is.close();
                
                super.defineClass("org.esb.crm.tools.EsbHashMapBean", bytes, 0, bytes.length);
            }
        }
    }
}
