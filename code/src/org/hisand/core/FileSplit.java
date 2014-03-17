package org.hisand.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileSplit {

	private static void writeFile(byte[] buf, String fileDestination)
			throws IOException {
		FileOutputStream fos = new FileOutputStream(fileDestination, true);
		fos.write(buf);
		fos.close();
	}

	// 读取文件
	public static void splitFile(String sourceFile, String targetPath, int size)
			throws IOException {
		if (targetPath == null) {
			targetPath = "";
		}
		if (!targetPath.endsWith("/")) {
			targetPath = targetPath + "/";
		}

		FileInputStream fis = new FileInputStream(sourceFile);
		// 获取后缀
		// String prefix = sourceFile.substring(sourceFile.lastIndexOf(".") +
		// 1);
		// String extension =
		// sourceFile.substring(sourceFile.lastIndexOf('/'),sourceFile.indexOf("."));
		// String trueName = extension.replace("/", "");
		int last = sourceFile.lastIndexOf("/");
		String filename = sourceFile.substring(last + 1, sourceFile.length());
		// int size = 5632 * 200;
		// System.out.println(filename);
		// available()方法可以知道文件具体有多少个字节。
		int filelen = fis.available();
		fis.close();

		// int size = 0;
		//
		// if (isSize) {
		// size = sizeOrCount;
		// } else {
		// size = filelen / (sizeOrCount - 1);
		// }

		int fl = filelen / size;
		byte[][] buf = new byte[fl + 1][size];
		for (int i = 0; i < fl; i++) {
			// System.out.println(i+":"+buf[i]);
			FileInputStream fis2 = new FileInputStream(sourceFile);
			fis2.skip(i * size);
			fis2.read(buf[i], 0, size);
			String name = filename + "." + padLeft((i + 1) + "", 3, "0");
			String fullname = targetPath + name;
			writeFile(buf[i], fullname);
			fis2.close();
		}

		FileInputStream fis2 = new FileInputStream(sourceFile);
		fis2.skip(fl * size);
		buf[fl] = new byte[filelen % size];
		fis2.read(buf[fl], 0, filelen % size);
		String name = filename + "." + padLeft((fl + 1) + "", 3, "0");
		String fullname = targetPath + name;
		writeFile(buf[fl], fullname);
		fis2.close();

		fis.close();
	}

	// 读取文件
	public static void splitFileByCount(String sourceFile, String targetPath,
			int count) throws IOException {
		if (targetPath == null) {
			targetPath = "";
		}
		if (!targetPath.endsWith("/")) {
			targetPath = targetPath + "/";
		}

		FileInputStream fis = new FileInputStream(sourceFile);
		// 获取后缀
		// String prefix = sourceFile.substring(sourceFile.lastIndexOf(".") +
		// 1);
		// String extension =
		// sourceFile.substring(sourceFile.lastIndexOf('/'),sourceFile.indexOf("."));
		// String trueName = extension.replace("/", "");
		int last = sourceFile.lastIndexOf("/");
		String filename = sourceFile.substring(last + 1, sourceFile.length());
		// int size = 5632 * 200;
		// System.out.println(filename);
		// available()方法可以知道文件具体有多少个字节。
		int filelen = fis.available();
		fis.close();

		int size = filelen / count;
		int lv = filelen % count;
		int lastsize = size + lv;

		byte[][] buf = new byte[count][size];
		buf[count - 1] = new byte[lastsize];
		for (int i = 0; i < count; i++) {
			int s = buf[i].length;
			FileInputStream fis2 = new FileInputStream(sourceFile);
			fis2.skip(i * size);
			fis2.read(buf[i], 0, s);
			String name = filename + "." + padLeft((i + 1) + "", 3, "0");
			String fullname = targetPath + name;
			writeFile(buf[i], fullname);
			fis2.close();
		}

		fis.close();
	}

	private static String padLeft(String text, int size, String replace) {
		if (text.length() == 2)
			return "0" + text;
		if (text.length() == 1)
			return "00" + text;
		return text;
	}

	@SuppressWarnings("unused")
	private static String padRight(String text, int size, String replace) {
		StringBuilder builder = new StringBuilder(text);
		while (builder.length() < size) {
			builder.append(replace);
		}
		return builder.toString();
	}

	public static void main(String[] args) {
		try {
			String sourceFile = "/work/huadict/data/cidian_sqlite/android_tw_standard/zidian_tw";
			String targetPath = "/work/huadict/data/cidian_sqlite/android_tw_standard/split";
			// splitFile(sourceFile, targetPath, 1024 * 960, true);
			splitFileByCount(sourceFile, targetPath, 15);
		} catch (IOException e) {
		}
	}

}
