package org.hisand.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;

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
	
	/**
	 * 复制文件(以超快的速度复制文件)
	 * 
	 * @param srcFile
	 *            源文件File
	 * @param destDir
	 *            目标目录File
	 * @param newFileName
	 *            新文件名
	 * @return 实际复制的字节数，如果文件、目录不存在、文件为null或者发生IO异常，返回-1
	 */
	public static long copyFile(File srcFile, File destDir, String newFileName) {
		long copySizes = 0;
		if (!srcFile.exists()) {
			System.out.println("源文件不存在");
			copySizes = -1;
		} else if (!destDir.exists()) {
			System.out.println("目标目录不存在");
			copySizes = -1;
		} else if (newFileName == null) {
			System.out.println("文件名为null");
			copySizes = -1;
		} else {
			try {
				@SuppressWarnings("resource")
				FileChannel fcin = new FileInputStream(srcFile).getChannel();
				@SuppressWarnings("resource")
				FileChannel fcout = new FileOutputStream(new File(destDir,
						newFileName)).getChannel();
				long size = fcin.size();
				fcin.transferTo(0, fcin.size(), fcout);
				fcin.close();
				fcout.close();
				copySizes = size;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return copySizes;
	}

}
