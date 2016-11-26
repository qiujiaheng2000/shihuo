package com.shihuo.shihuo.Commons;

/**
 * Created by cm_qiujiaheng on 2016/11/26.
 */

public class ShihuoSharepreference {

    private ShihuoSharepreference(){}

    public static class ShihuoHolder {
        private static final ShihuoSharepreference INST = new ShihuoSharepreference();
    }

    public static final ShihuoSharepreference getInst() {
        return ShihuoHolder.INST;
    }

}
