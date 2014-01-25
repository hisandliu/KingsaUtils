package org.hisand.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class SysSettings implements Serializable {

	private static final long serialVersionUID = 1L;

	private SysSettings() {
		super();
	}
	
	private static SysSettings instance;
	public static SysSettings getInstance() {
		if (instance == null) {
			instance = new SysSettings();
		}
		return instance;
	}

	public static SysSettings getInstance(String id) {
		return new SysSettings();
	}
	
	private Properties props;

	public Properties getProps() {
		if (props == null) {
			props = getProp();
		}
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}
	
	public String get(String key) {
		Properties p = getProps();
		if (p == null) return "";
		if (!p.containsKey(key)) {
			String message = "Key '" + key + "' not found!";
			System.out.println(message);
			return "";
		}
		
		String r = p.getProperty(key);
		if (r != null) {
			try {
				r = new String(r.getBytes("ISO-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				return r;
			} 
		}
		
		return r;
	}
	
	private Properties getProp() {
		/*
		String xxxx = Object.class.getResource("/").getPath();
		System.out.println(xxxx);   
		File file = new File("/");
		  System.out.println(file.getPath());   
		ClassLoader cl = getClass().getClassLoader();
		if (cl == null) {
			System.out.println(" getClass().getClassLoader() is null");
			return null;
		}
		URL url = cl.getResource("/");
		if (url == null) {
			System.out.println(" getResource is null");
		}
		else {
			String xx = url.getPath();
			System.out.println(xx);
		}
		*/
		Properties prop = new Properties(); 
        InputStream in = getClass().getResourceAsStream("/config.properties"); 
        try { 
            prop.load(in);
            in.close();
            return prop;
        } catch (IOException e) {
            e.printStackTrace(); 
            throw new RuntimeException(e);
        } 
	}
	

}
