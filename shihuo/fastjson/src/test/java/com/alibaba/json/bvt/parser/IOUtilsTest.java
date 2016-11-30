package com.alibaba.json.bvt.parser;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.json.util.IOUtils;
import com.alibaba.json.util.ThreadLocalCache;

public class IOUtilsTest extends TestCase {

    public void test_error_0() throws Exception {
        Exception error = null;
        try {
            IOUtils.decode(ThreadLocalCache.getUTF8Decoder(), ByteBuffer.wrap("abc".getBytes("UTF-8")),
                           CharBuffer.wrap(new char[0]));
        } catch (Exception ex) {
            error = ex;
        }
        Assert.assertNotNull(error);
    }

    public void test_close() throws Exception {
        IOUtils.close((Closeable) null);
    }

    public void test_close1() throws Exception {
        IOUtils.close(new Closeable() {

            public void close() throws IOException {

            }

        });
    }

    public void test_close_error() throws Exception {
        IOUtils.close(new Closeable() {

            public void close() throws IOException {
                throw new IOException();
            }

        });
    }

}
