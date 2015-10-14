package org.hisand.core;

import org.junit.Assert;
import org.junit.Test;

public class Base64Test {
	
	@Test
	public void testEncodeAndDecode() throws Exception {
		String value = "中央人民政府";
		String result = "";
		
		result = Base64.encode(value.getBytes("utf-8"));
		
		byte[] bs = Base64.decode(result);
		
		String result2 = new String(bs, "utf-8");
		
		Assert.assertEquals(result2, "中央人民政府");
		Assert.assertEquals(value, result2);
		
		System.out.println(value);
	}
}
