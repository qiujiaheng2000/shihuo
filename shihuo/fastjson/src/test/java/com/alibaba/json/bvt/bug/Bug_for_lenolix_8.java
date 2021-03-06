package com.alibaba.json.bvt.bug;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.alibaba.json.JSON;
import com.alibaba.json.serializer.SerializerFeature;

public class Bug_for_lenolix_8 extends TestCase {

    public void test_for_objectKey() throws Exception {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<Integer, User> map = new HashMap<Integer, User>();

        User user = new User();
        user.setId(1);
        user.setIsBoy(true);
        user.setName("leno.lix");
        user.setBirthDay(simpleDateFormat.parse("2012-03-07 22:38:21"));
        user.setGmtCreate(new java.sql.Date(simpleDateFormat.parse("2012-02-03 22:38:21").getTime()));

        map.put(1, user);

        String mapJson = JSON.toJSONString(map, SerializerFeature.WriteClassName, SerializerFeature.WriteMapNullValue);

        System.out.println(mapJson);

        Object object = JSON.parse(mapJson);

    }

    public static class User implements Serializable {

        /**
             *
             */

        private static final long  serialVersionUID = 6192533820796587011L;

        private Integer            id;
        private String             name;
        private Boolean            isBoy;
        private Date               birthDay;
        private java.sql.Date      gmtCreate;
        private java.sql.Timestamp gmtModified;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Boolean getIsBoy() {
            return isBoy;
        }

        public void setIsBoy(Boolean isBoy) {
            this.isBoy = isBoy;
        }


        public Date getBirthDay() {
            return birthDay;
        }

        public void setBirthDay(Date birthDay) {
            this.birthDay = birthDay;
        }

        public java.sql.Date getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(java.sql.Date gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public java.sql.Timestamp getGmtModified() {
            return gmtModified;
        }

        public void setGmtModified(java.sql.Timestamp gmtModified) {
            this.gmtModified = gmtModified;
        }

    }
}
