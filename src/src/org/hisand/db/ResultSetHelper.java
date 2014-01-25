package org.hisand.db;

import java.io.Reader;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;

public class ResultSetHelper {

	public static Double setDoubleDBValue(Double d) {
		if (d == null) return null;
		return d.isNaN() ? null : d;
	}

	public static Object getObjectFromResultSet(ResultSet rs, String rowname,
			boolean needLog) {
		Object obj = null;
		try {
			obj = rs.getObject(rowname);
		} catch (Exception e) {
			if (needLog)
				e.printStackTrace();
		}
		return obj;
	}

	public static Object getObjectFromResultSet(ResultSet rs, String rowname) {
		return getObjectFromResultSet(rs, rowname, true);
	}

	public static HashMap<String, Object> getHashMapFromResultSet(ResultSet rs)
			throws Exception {
		return getHashMapFromResultSet(rs, null);
	}

	public static HashMap<String, Object> getHashMapFromResultSet(ResultSet rs,
			HashMap<String, Object> item) throws Exception {
		if (item == null)
			item = new HashMap<String, Object>();

		ResultSetMetaData rs_meta = rs.getMetaData();
		int count = rs_meta.getColumnCount();
		for (int i = 1; i <= count; i++) {
			// String name1=rs_meta.getCatalogName(i);
			String name2 = getColumnName(rs_meta, i);
			name2 = name2.toLowerCase();
			// String name=name1+"_"+name2;

			Object value = "";
			value = rs.getObject(i);

			if (value instanceof Clob) {
				Clob clob = (Clob) value;
				// rs.getCharacterStream(i);
				char clobVal[] = new char[(int) clob.length()];
				Reader r = clob.getCharacterStream();
				r.read(clobVal);
				value = new String(clobVal);
				r.close();
			}

			item.put(name2, value);
		}

		return item;

	}

	public static ArrayList<HashMap<String, Object>> getHashMapListFromResultSet(
			ArrayList<HashMap<String, Object>> list, ResultSet rs)
			throws Exception {
		// ArrayList<HashMap<String,Object>> list=new
		// ArrayList<HashMap<String,Object>>();
		if (list == null)
			list = new ArrayList<HashMap<String, Object>>();

		while (rs != null && rs.next()) {
			HashMap<String, Object> item = getHashMapFromResultSet(rs);
			list.add(item);
		}

		return list;

	}

	public static ArrayList<HashMap<String, Object>> getHashMapListFromResultSet(
			ResultSet rs) throws Exception {
		return getHashMapListFromResultSet(null, rs);
	}

	// --------------------------------for GWT------------------------------
	public static HashMap<String, String> getStrHashMapFromResultSet(
			ResultSet rs) throws Exception {
		return getStrHashMapFromResultSet(rs, null);
	}

	public static HashMap<String, String> getStrHashMapFromResultSet(
			ResultSet rs, HashMap<String, String> item) throws Exception {
		if (item == null)
			item = new HashMap<String, String>();

		ResultSetMetaData rs_meta = rs.getMetaData();
		int count = rs_meta.getColumnCount();
		for (int i = 1; i <= count; i++) {
			// String name1=rs_meta.getCatalogName(i);
			String name2 = getColumnName(rs_meta, i);
			name2 = name2.toLowerCase();
			// String name=name1+"_"+name2;
			Object value = null;
			value = rs.getObject(i);
			String r = value == null ? null : value.toString();
			item.put(name2, r);
		}

		return item;

	}

	public static ArrayList<HashMap<String, String>> getStrHashMapListFromResultSet(
			ArrayList<HashMap<String, String>> list, ResultSet rs)
			throws Exception {
		// ArrayList<HashMap<String,Object>> list=new
		// ArrayList<HashMap<String,Object>>();
		if (list == null)
			list = new ArrayList<HashMap<String, String>>();

		while (rs != null && rs.next()) {
			HashMap<String, String> item = getStrHashMapFromResultSet(rs);
			list.add(item);
		}

		return list;

	}

	public static ArrayList<HashMap<String, String>> getStrHashMapListFromResultSet(
			ResultSet rs) throws Exception {
		return getStrHashMapListFromResultSet(null, rs);
	}

	public static String getXmlStrFromResultSet(ResultSet rs) throws Exception {
		StringBuilder xml = new StringBuilder();
		ResultSetMetaData rs_meta = rs.getMetaData();
		int count = rs_meta.getColumnCount();
		for (int i = 1; i <= count; i++) {
			// String name1=rs_meta.getCatalogName(i);
			String name2 = rs_meta.getColumnName(i);
			name2 = name2.toLowerCase();
			// String name=name1+"_"+name2;
			Object value = rs.getObject(i);
			String valStr = "";
			if (value != null) {
				valStr = value.toString();
			}
			xml.append("<" + name2 + ">" + e(valStr) + "</" + name2 + ">");
		}

		return xml.toString();
	}

	private static String e(String value) {
		return value;
	}

	// 浣跨敤attribute
	public static String getXmlStrFromResultSet2(ResultSet rs) throws Exception {
		String xml = "";
		ResultSetMetaData rs_meta = rs.getMetaData();
		int count = rs_meta.getColumnCount();
		for (int i = 1; i <= count; i++) {
			// String name1=rs_meta.getCatalogName(i);
			String name2 = rs_meta.getColumnName(i);
			name2 = name2.toLowerCase();
			// String name=name1+"_"+name2;
			Object value = rs.getObject(i);
			String valStr = "";
			if (value != null) {
				valStr = value.toString();
			}
			xml = xml + " " + name2 + "=\"" + e(valStr) + "\"";
		}

		return xml;
	}

	public static String getColumnName(ResultSetMetaData rs_meta, int index) {
		String name2 = null;
		try {
			name2 = rs_meta.getColumnLabel(index);
			if (name2 == null || name2.trim().length() <= 0) {
				name2 = rs_meta.getColumnName(index);
			}
		} catch (Exception e) {

		}
		if (name2 == null)
			name2 = "";
		return name2;
	}
}
