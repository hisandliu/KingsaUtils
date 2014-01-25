package org.hisand.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;


public class WebCatcher {
	/**
	 * 
	 */
	public String getOnePageHtml2(String url, String encoding)
			throws IOException {
		try {
			StringBuffer sb = new StringBuffer();
			String line;
			URL urlObj = new URL(url);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlObj.openStream(), encoding));
			while ((line = in.readLine()) != null) {
				sb.append(line + "\n");
			}
			in.close();
			return sb.toString();
		} catch (MalformedURLException e) {
			System.out.println("失敗！!");
			e.printStackTrace();
			throw e;
		} catch (final IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public String getOnePageHtml(String url, String encoding)
			throws IOException {
		try {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8087)); 
			URL httpurl = new URL(url);
			HttpURLConnection httpConn = (HttpURLConnection) httpurl
					.openConnection(proxy);
			httpConn.setDoInput(true);
			httpConn.setConnectTimeout(60000);// 毫秒
			httpConn.setReadTimeout(60000);// 毫秒
			httpConn.setRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (Windows; U; Windows NT 5.2; zh-TW; rv:1.9.0.8) Gecko/2009032609 Firefox/13.0.8");
			//httpConn.setHeader("User-Agent",
			//		"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					httpConn.getInputStream(), encoding));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line + "\n");
			}
			in.close();
			return sb.toString();
		} catch (MalformedURLException e) {
			System.out.println("失敗！!");
			e.printStackTrace();
			throw e;
		} catch (final IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public String getOnePageHtml3(String url, String encoding)
			throws IOException {
		try {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8087)); 
			URL httpurl = new URL(url);
			
			HttpURLConnection httpConn = (HttpURLConnection) httpurl
					.openConnection(proxy);
			httpConn.setDoInput(true);
			httpConn.setConnectTimeout(60000);// 毫秒
			httpConn.setReadTimeout(60000);// 毫秒
//			httpConn.setRequestProperty(
//					"User-Agent",
//					"Mozilla/5.0 (Windows; U; Windows NT 5.2; zh-TW; rv:1.9.0.8) Gecko/2009032609 Firefox/13.0.8");
			httpConn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					httpConn.getInputStream(), encoding));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line + "\n");
			}
			in.close();
			return sb.toString();
		} catch (MalformedURLException e) {
			System.out.println("失敗！!");
			e.printStackTrace();
			throw e;
		} catch (final IOException e) {
			e.printStackTrace();
			throw e;
		}
	}


	/*
	public String getOnePageHtmlXXXX(String url, String encoding)
			throws IOException {
		HttpClient httpclient = new DefaultHttpClient(); // 实例化一个HttpClient
		String html = "";
		HttpGet httpget = new HttpGet(url);

		System.out.println("executing request " + httpget.getURI());
		// 查看默认request头部信息
		System.out.println("Accept-Charset:"
				+ httpget.getFirstHeader("Accept-Charset"));
		httpget.setHeader("User-Agent",
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)");
		// 用逗号分隔显示可以同时接受多种编码
		httpget.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
		httpget.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
		// 验证头部信息设置生效
		System.out.println("Accept-Charset:"
				+ httpget.getFirstHeader("Accept-Charset").getValue());

		HttpResponse response = httpclient.execute(httpget);// 执行
		HttpEntity entity = response.getEntity(); // 返回服务器响应

		System.out.println("----------------------------------------");
		System.out.println(response.getStatusLine()); // 服务器返回状态
		if (entity != null) {
			Header[] headers = response.getAllHeaders(); // 返回的HTTP头信息
			for (int i = 0; i < headers.length; i++) {
				System.out.println(headers[i]);
			}

			System.out.println("Response content length: "
					+ entity.getContentLength());
			System.out.println("----------------------------------------");
			System.out.println("Response content: ");
			// String responseString =
			// EntityUtils.toString(response.getEntity());//返回服务器响应的HTML代码
			// responseString = new
			// String(responseString.getBytes("gb2312"),"gbk");//转换中文
			// System.out.println(responseString); //打印出服务器响应的HTML代码

			// 将源码流保存在一个byte数组当中，因为可能需要两次用到该流
			// 注，如果上面的EntityUtils.toString(response.getEntity())执行了后，就不能再用下面的语句拿数据了，直接用上面的数据
			byte[] bytes = EntityUtils.toByteArray(entity);
			String charSet = encoding;

			// 如果头部Content-Type中包含了编码信息，那么我们可以直接在此处获取
			charSet = EntityUtils.getContentCharSet(entity);

			System.out.println("Last get: " + charSet);
			// 至此，我们可以将原byte数组按照正常编码专成字符串输出（如果找到了编码的话）
			
			html = new String(bytes, charSet);
			System.out.println("Encoding string is: " + html);

		}
		System.out.println("----------------------------------------");

		// Do not feel like reading the response body
		// Call abort on the request object
		httpget.abort();

		// When HttpClient instance is no longer needed,
		// shut down the connection manager to ensure
		// immediate deallocation of all system resources
		httpclient.getConnectionManager().shutdown();
		return html;
	}

	public void test(String[] args) throws Exception {
		HttpClient httpclient = new DefaultHttpClient(); // 实例化一个HttpClient

		HttpGet httpget = new HttpGet("http://www.baidu.com/");

		System.out.println("executing request " + httpget.getURI());
		// 查看默认request头部信息
		System.out.println("Accept-Charset:"
				+ httpget.getFirstHeader("Accept-Charset"));
		httpget.setHeader("User-Agent",
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)");
		// 用逗号分隔显示可以同时接受多种编码
		httpget.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
		httpget.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
		// 验证头部信息设置生效
		System.out.println("Accept-Charset:"
				+ httpget.getFirstHeader("Accept-Charset").getValue());

		HttpResponse response = httpclient.execute(httpget);// 执行
		HttpEntity entity = response.getEntity(); // 返回服务器响应

		System.out.println("----------------------------------------");
		System.out.println(response.getStatusLine()); // 服务器返回状态
		if (entity != null) {
			Header[] headers = response.getAllHeaders(); // 返回的HTTP头信息
			for (int i = 0; i < headers.length; i++) {
				System.out.println(headers[i]);
			}

			System.out.println("Response content length: "
					+ entity.getContentLength());
			System.out.println("----------------------------------------");
			System.out.println("Response content: ");
			// String responseString =
			// EntityUtils.toString(response.getEntity());//返回服务器响应的HTML代码
			// responseString = new
			// String(responseString.getBytes("gb2312"),"gbk");//转换中文
			// System.out.println(responseString); //打印出服务器响应的HTML代码

			// 将源码流保存在一个byte数组当中，因为可能需要两次用到该流
			// 注，如果上面的EntityUtils.toString(response.getEntity())执行了后，就不能再用下面的语句拿数据了，直接用上面的数据
			byte[] bytes = EntityUtils.toByteArray(entity);
			String charSet = "";

			// 如果头部Content-Type中包含了编码信息，那么我们可以直接在此处获取
			charSet = EntityUtils.getContentCharSet(entity);

			System.out.println("In header: " + charSet);
			// 如果头部中没有，那么我们需要 查看页面源码，这个方法虽然不能说完全正确，因为有些粗糙的网页编码者没有在页面中写头部编码信息
			if (charSet == null || charSet == "") {
				String regEx = "<meta.*charset=['|\"]?([[a-z]|[A-Z]|[0-9]|-]*)['|\"]?";
				Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
				Matcher m = p.matcher(new String(bytes)); // 默认编码转成字符串，因为我们的匹配中无中文，所以串中可能的乱码对我们没有影响
				if (m.find()) {
					charSet = m.group(1);
				} else {
					charSet = "";
				}
			}

			if (charSet == null || charSet.equals("")) {
				charSet = "utf-8";
			}

			System.out.println("Last get: " + charSet);
			// 至此，我们可以将原byte数组按照正常编码专成字符串输出（如果找到了编码的话）
			System.out.println("Encoding string is: "
					+ new String(bytes, charSet));

		}
		System.out.println("----------------------------------------");

		// Do not feel like reading the response body
		// Call abort on the request object
		httpget.abort();

		// When HttpClient instance is no longer needed,
		// shut down the connection manager to ensure
		// immediate deallocation of all system resources
		httpclient.getConnectionManager().shutdown();
	}
	*/

}
