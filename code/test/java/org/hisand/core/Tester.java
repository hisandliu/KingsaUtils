package org.hisand.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Assert;

import org.junit.Test;

public class Tester {
	@Test
	public void test1() throws Exception {
		InputStream stream = Tester.class.getClassLoader().getResourceAsStream(
				"data/readpuretext.txt");
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(stream));
		StringBuffer sb = new StringBuffer();
		String temp = null;
		while ((temp = reader.readLine()) != null) {
			sb.append(temp);
		}
		reader.close();
		Assert.assertEquals("测试读纯文本内容", sb.toString());
	}
}
