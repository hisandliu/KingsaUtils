package org.hisand.core;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AESTest {

	@BeforeClass
	public static void beforeClass() {
		System.out.println("beforeClass");
	}
	
	@AfterClass
	public static void afterClass() {
		System.out.println("afterClass");
	}
	
	@Before
	public void before() {
		System.out.println("before");
	}
	
	@After
	public void after() {
		System.out.println("after");
	}
	
	@Test
	public void testEncryptAndDecrypt() throws Exception {
		System.out.println("testEncryptAndDecrypt");
		String cleartext = "HISAND12345";
		String result = "";
		
		AES  aes = new AES("hisand21hisand21", "hisand21hisand21");
		result = aes.encrypt(cleartext);
		
		String cleartext2 = aes.decrypt(result);
		
		Assert.assertEquals(cleartext, cleartext2);
	}
}
