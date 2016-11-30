package com.alibaba.json.bvt.serializer;

import java.util.HashSet;
import java.util.Set;

import com.alibaba.json.JSON;

import junit.framework.TestCase;

public class CircularReferencesTest2 extends TestCase {

    public void test_ref() throws Exception {

        A a = new A();
        
        B b = new B();
        a.setB(b);
        
        b.getListA().add(a);
        
        JSON.toJSONString(a);
    }

    public static class B {

        private Set listA = new HashSet<A>();

        public Set getListA() {
            return listA;
        }

        public void setListA(Set<A> listA) {
            this.listA = listA;
        }

    }

    public static class A implements java.io.Serializable {

        private B b;

        public B getB() {
            return b;
        }

        public void setB(B b) {
            this.b = b;
        }

    }

}
