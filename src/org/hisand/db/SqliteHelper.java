package org.hisand.db;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SqliteHelper {
	public final static String DATE_FORMAT = "yyyy-MM-dd";
	public final static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static String getDateTimeString(java.util.Date date) {
		if (date == null) return "";
		DateFormat df = new SimpleDateFormat(DATETIME_FORMAT);
		String r = df.format(date);
		return r;
	}
	
	public static String getDateString(java.util.Date date) {
		if (date == null) return "";
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		String r = df.format(date);
		return r;
	}

	public static java.util.Date getDate(String dateString) {
		try {
			if (dateString == null || dateString.length() == 0) return null;
			DateFormat df = new SimpleDateFormat(DATE_FORMAT);
			java.util.Date date = df.parse(dateString);
			return date;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static java.util.Date getDateTime(String dateString) {
		try {
			if (dateString == null || dateString.length() == 0) return null;
			DateFormat df = new SimpleDateFormat(DATETIME_FORMAT);
			java.util.Date date = df.parse(dateString);
			return date;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static java.sql.Timestamp getDateStamp(String dateString) {
		try {
			java.util.Date date = getDate(dateString);
			if (date == null) return null;
			java.sql.Timestamp time = new Timestamp(date.getTime());
			return time;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static java.sql.Timestamp getDateTimeStamp(String dateString) {
		try {
			java.util.Date date = getDateTime(dateString);
			if (date == null) return null;
			java.sql.Timestamp time = new Timestamp(date.getTime());
			return time;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
