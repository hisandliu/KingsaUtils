package org.hisand.core;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
	
	@Test 
	public void testMock() {
	
		//创建mock对象，参数可以是类，也可以是接口  
        @SuppressWarnings("unchecked")
		List<String> list = mock(List.class);  
          
        //设置方法的预期返回值  
        when(list.get(0)).thenReturn("helloworld");  
      
        String result = list.get(0);  
          
        //验证方法调用(是否调用了get(0))  
        verify(list).get(0);  
          
        //junit测试  
        Assert.assertEquals("helloworld", result);
	}
}
