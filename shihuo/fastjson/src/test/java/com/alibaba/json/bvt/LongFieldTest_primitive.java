package com.alibaba.json.bvt;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.json.JSON;
import com.alibaba.json.parser.ParserConfig;
import com.alibaba.json.serializer.SerializeConfig;
import com.alibaba.json.serializer.SerializerFeature;

public class LongFieldTest_primitive extends TestCase {

	public void test_codec() throws Exception {
		V0 v = new V0();
		v.setValue(1001L);

		String text = JSON.toJSONString(v);

		V0 v1 = JSON.parseObject(text, V0.class);

		Assert.assertEquals(v1.getValue(), v.getValue());
	}

	public void test_codec_null() throws Exception {
		V0 v = new V0();

		SerializeConfig mapping = new SerializeConfig();
		mapping.setAsmEnable(false);

		String text = JSON.toJSONString(v, mapping,
				SerializerFeature.WriteMapNullValue);
		Assert.assertEquals("{\"value\":123}", text);

		V0 v1 = JSON.parseObject(text, V0.class);

		Assert.assertEquals(v1.getValue(), v.getValue());
	}

	public void test_codec_null_asm() throws Exception {
		V0 v = new V0();

		SerializeConfig mapping = new SerializeConfig();
		mapping.setAsmEnable(true);

		String text = JSON.toJSONString(v, mapping,
				SerializerFeature.WriteMapNullValue);
		Assert.assertEquals("{\"value\":123}", text);

		ParserConfig config = new ParserConfig();
		config.setAsmEnable(false);

		V0 v1 = JSON.parseObject(text, V0.class, config,
				JSON.DEFAULT_PARSER_FEATURE);

		Assert.assertEquals(v1.getValue(), v.getValue());
	}

	public void test_codec_null_1() throws Exception {
		V0 v = new V0();

		SerializeConfig mapping = new SerializeConfig();
		mapping.setAsmEnable(false);

		String text = JSON.toJSONString(v, mapping,
				SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullNumberAsZero);
		Assert.assertEquals("{\"value\":123}", text);

		V0 v1 = JSON.parseObject(text, V0.class);

		Assert.assertEquals(123, v1.getValue());
	}

	public static class V0 {

		private long value = 123L;

		public long getValue() {
			return value;
		}

		public void setValue(long value) {
			this.value = value;
		}

	}
}
