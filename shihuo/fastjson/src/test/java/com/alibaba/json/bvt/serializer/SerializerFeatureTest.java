package com.alibaba.json.bvt.serializer;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.json.serializer.SerializerFeature;


public class SerializerFeatureTest extends TestCase {
    public void test_0 () throws Exception {
        int feature = 0;
        feature = SerializerFeature.config(feature, SerializerFeature.BrowserCompatible, true);
        Assert.assertEquals(true, SerializerFeature.isEnabled(feature, SerializerFeature.BrowserCompatible));
        feature = SerializerFeature.config(feature, SerializerFeature.BrowserCompatible, false);
        Assert.assertEquals(false, SerializerFeature.isEnabled(feature, SerializerFeature.BrowserCompatible));
    }
}
