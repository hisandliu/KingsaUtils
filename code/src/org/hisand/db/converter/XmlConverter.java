package org.hisand.db.converter;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class XmlConverter implements RowConverter<String> {
	
	public XmlConverter() {
		super();
	}
	
	public XmlConverter(boolean onlyFirstItem, String listTagname, String itemTagname, String[] keys) {
		super();
		this.onlyFirstItem = onlyFirstItem;
		this.listTagname = listTagname;
		this.itemTagname = itemTagname;
		this.onlyFirstItem = onlyFirstItem;
		this.onlyFirstItem = onlyFirstItem;
		this.keys = keys;
	}
	
	public XmlConverter(boolean onlyFirstItem) {
		super();
		this.onlyFirstItem = onlyFirstItem;
	}
	
	public XmlConverter(String[] keys) {
		super();
		this.keys = keys;
	}
	
	@Override
	public String handler(ResultSet rs) throws SQLException {
		return buildXml(rs);
	}
	
	private boolean onlyFirstItem = false;
	private String listTagname = "items";
	private String itemTagname = "item";
	private String[] cdataTags = null;
	@SuppressWarnings("unused")
	private String[] keys;
	
	private String buildXml(ResultSet rs) throws SQLException {
		if (rs == null) return "";

		try {
			StringBuffer sb = new StringBuffer();
			
			if (listTagname != null && listTagname.trim().length() > 0) {
				sb.append("<" + listTagname + ">");
				sb.append("\n");
			}

			while (rs.next()) {
				StringBuffer line = new StringBuffer();
				ResultSetMetaData meta = rs.getMetaData();
				int count = meta.getColumnCount();
				for (int i = 1; i <= count; i++) {
					String tagname = meta.getColumnName(i);
					if (tagname == null) continue;
					
					tagname = tagname.toLowerCase();
					
					Object value = rs.getObject(i);
					String valuestr = value == null ? "" : value.toString();
					if (cdataTags != null && codeIn(cdataTags, tagname)) {
						valuestr = "<![CDATA[" + valuestr + "]]>";
					}
					else {
						valuestr = handleString2Xml(valuestr);
					}
				
					line.append("<" + tagname + ">");
					line.append(valuestr);
					line.append("</" + tagname + ">");
				}
				
				if (itemTagname != null && itemTagname.trim().length() > 0) {
					sb.append("<" + itemTagname + ">");
					sb.append("\n");
				}
				
				sb.append(line);
				sb.append("\n");
				
				if (itemTagname != null && itemTagname.trim().length() > 0) {
					sb.append("</" + itemTagname + ">");
					sb.append("\n");
				}
				
				if (onlyFirstItem) break;
			}
			
			if (listTagname != null && listTagname.trim().length() > 0) {
				sb.append("</" + listTagname + ">");
			}
			
			return sb.toString().trim();
		}
		catch (SQLException e) {
			throw e;
		}
	}
	
	private String handleString2Xml(String value) {
		return value;
	}
	
	private Boolean codeIn(String[] list, String code) {
		if (list == null) return false;
		if (code == null) code = "";
		for (String s : list) {
			if (code.equals(s)) {
				return true;
			}
		}
		return false;
	}
}
