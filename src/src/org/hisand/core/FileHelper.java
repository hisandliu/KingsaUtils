package org.hisand.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileHelper {
	public static String readFileAsString(InputStream is)
			throws java.io.IOException {
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,
				"utf-8"));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		is.close();
		reader.close();
		return fileData.toString();
	}
	
	public static String readStringFromClassFile(Class<?> clazz, String filename)
			throws Exception {
		try {
			InputStream is = clazz.getResourceAsStream(filename);
			return readFileAsString(is);
		} catch (Exception e) {
			throw new Exception("File [" + filename + "] not found!");
		}
	}
}
