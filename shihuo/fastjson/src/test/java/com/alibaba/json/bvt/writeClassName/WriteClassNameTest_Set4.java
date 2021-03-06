package com.alibaba.json.bvt.writeClassName;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.json.JSON;
import com.alibaba.json.serializer.SerializerFeature;

public class WriteClassNameTest_Set4 extends TestCase {

    public void test_list() throws Exception {
        A a = new A();
        LinkedHashSet<B> set = new LinkedHashSet<B>();
        set.add(new B());
        set.add(new B1());
        a.setList(set);
        String text = JSON.toJSONString(a, SerializerFeature.WriteClassName);
        System.out.println(text);
        Assert.assertEquals("{\"@type\":\"com.alibaba.json.bvt.writeClassName.WriteClassNameTest_Set4$A\",\"list\":[{},{\"@type\":\"com.alibaba.json.bvt.writeClassName.WriteClassNameTest_Set4$B1\"}]}",
                            text);

        A a1 = (A) JSON.parse(text);

        Assert.assertEquals(2, a1.getList().size());
        Assert.assertTrue(new ArrayList<B>(a1.getList()).get(0) instanceof B);
        Assert.assertTrue(new ArrayList<B>(a1.getList()).get(1) instanceof B1);
    }

    public static class A {

        private LinkedHashSet<B> list;

        public LinkedHashSet<B> getList() {
            return list;
        }

        public void setList(LinkedHashSet<B> list) {
            this.list = list;
        }

    }

    public static class B {

    }

    public static class B1 extends B {

    }
}
