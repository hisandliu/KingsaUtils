package org.hisand.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbstractMapDAO extends AbstractDAO {
	protected Object getValue(Map<String, Object> item, String name) {
		return item.get(name);
	}

	protected int insertWithReturnId(Map<String, Object> item,
			String tablename, String[] fieldNames) throws SQLException {
		return insert(item, tablename, fieldNames, true);
	}

	protected int insert(Map<String, Object> item, String tablename,
			String[] fieldNames) throws SQLException {
		return insert(item, tablename, fieldNames, false);
	}

	protected int insert(Map<String, Object> item, String tablename,
			String[] fieldNames, boolean isReturnId) throws SQLException {
		StringBuffer fieldsb = new StringBuffer("");
		StringBuffer expsb = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		for (int i = 0; i < fieldNames.length; i++) {
			String name = fieldNames[i];
			fieldsb.append("," + name);
			expsb.append(",?");
			values.add(getValue(item, name));
		}
		String fieldline = fieldsb.toString().substring(1);
		String expline = expsb.toString().substring(1);
		StringBuffer sqlsb = new StringBuffer("insert into " + tablename + " (");
		sqlsb.append(fieldline + ") values (");
		sqlsb.append(expline + ")");
		String sql = sqlsb.toString();
		int r = 0;
		if (isReturnId) {
			r = updateWithReturnId0(sql, values.toArray());
		} else {
			r = update(sql, values.toArray());
		}
		return r;
	}

	protected int updateWithReturnId(Map<String, Object> item,
			String tablename, String[] fieldNames,
			Map<String, Object> whereItem, String[] whereFields)
			throws SQLException {
		return update(item, tablename, fieldNames, whereItem, whereFields, true);
	}

	protected int update(Map<String, Object> item, String tablename,
			String[] fieldNames, Map<String, Object> whereItem,
			String[] whereFields) throws SQLException {
		return update(item, tablename, fieldNames, whereItem, whereFields,
				false);
	}

	protected int update(Map<String, Object> item, String tablename,
			String[] fieldNames, Map<String, Object> whereItem,
			String[] whereFields, boolean isReturnId) throws SQLException {
		StringBuffer expsb = new StringBuffer();
		StringBuffer wheresb = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		for (int i = 0; i < fieldNames.length; i++) {
			String name = fieldNames[i];
			expsb.append("," + name + "=?");
			values.add(getValue(item, name));
		}
		if (whereItem != null) {
			for (int i = 0; i < whereFields.length; i++) {
				String name = whereFields[i];
				wheresb.append("and " + name + "=? ");
				values.add(getValue(whereItem, name));
			}
		}
		String expline = expsb.toString().substring(1);
		String whereline = wheresb.toString();
		if (whereline.toLowerCase().startsWith("and")) {
			whereline = whereline.substring(3);
		}
		StringBuffer sqlsb = new StringBuffer("update " + tablename + " set ");
		sqlsb.append(expline);
		if (whereline != null && whereline.length() > 0) {
			sqlsb.append("\n where " + whereline);
		}
		String sql = sqlsb.toString();
		int r = 0;
		if (isReturnId) {
			r = updateWithReturnId0(sql, values.toArray());
		} else {
			r = update(sql, values.toArray());
		}
		return r;
	}

	protected int delete(String tablename, Map<String, Object> whereItem,
			String[] whereFields) throws SQLException {

		StringBuffer wheresb = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		if (whereItem != null) {
			for (int i = 0; i < whereFields.length; i++) {
				String name = whereFields[i];
				wheresb.append("and " + name + "=? ");
				values.add(getValue(whereItem, name));
			}
		}
		String whereline = wheresb.toString();
		if (whereline.toLowerCase().startsWith("and")) {
			whereline = whereline.substring(3);
		}
		StringBuffer sqlsb = new StringBuffer("delete from " + tablename + "");
		if (whereline != null && whereline.length() > 0) {
			sqlsb.append("\n where " + whereline);
		}
		String sql = sqlsb.toString();
		return update(sql, values.toArray());
	}

	public Map<String, Object> queryItem(String sql, Object[] values)
			throws SQLException {
		ConnResult rt = getConnResult();
		MapHandler handler = new MapHandler();
		return DBHelper.query(rt.conn, sql, rt.isNewconn, handler, values);
	}

	protected List<Map<String, Object>> query(String sql, Object[] values)
			throws SQLException {
		ConnResult rt = getConnResult();
		MapListHandler handler = new MapListHandler();
		return DBHelper.query(rt.conn, sql, rt.isNewconn, handler, values);
	}

	protected List<Map<String, Object>> query(String tablename,
			String[] fieldNames, Map<String, Object> whereItem,
			String[] whereFields) throws SQLException {
		StringBuffer expsb = new StringBuffer();
		StringBuffer wheresb = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		if (fieldNames == null || fieldNames.length == 0) {
			expsb.append("*");
		} else {
			for (int i = 0; i < fieldNames.length; i++) {
				String name = fieldNames[i];
				expsb.append("," + name);
			}
		}
		if (whereItem != null) {
			for (int i = 0; i < whereFields.length; i++) {
				String name = whereFields[i];
				wheresb.append("and " + name + "=? ");
				values.add(getValue(whereItem, name));
			}
		}
		String expline = expsb.toString();
		if (expline.startsWith(",")) {
			expline = expline.substring(1);
		}
		String whereline = wheresb.toString();
		if (whereline.toLowerCase().startsWith("and")) {
			whereline = whereline.substring(3);
		}
		StringBuffer sqlsb = new StringBuffer("select " + expline + " from "
				+ tablename + "");
		if (whereline != null && whereline.length() > 0) {
			sqlsb.append("\n where " + whereline);
		}
		String sql = sqlsb.toString();
		return query(sql, values.toArray());
	}
	
	private int updateWithReturnId0(String sql, Object[] values)
			throws SQLException {
		ConnResult rt = getConnResult();
		Dialect dialect = getDialect();
		if (dialect instanceof SqliteDialect) {
			return DBHelper.updateWithReturnIdSqlite(rt.conn, sql,
					rt.isNewconn, values);
		} else {
			return DBHelper.updateWithReturnId(rt.conn, sql, rt.isNewconn,
					values);
		}
	}
	
	protected Object[] buildInsertInfo(Map<String, Object> item, String tablename, String[] fields) {
		StringBuffer nameSB = new StringBuffer();
		StringBuffer valueSB = new StringBuffer();
		List<Object> pList = new ArrayList<Object>();
	
		for (String name : fields) {
			if (item.containsKey(name)) {
				nameSB.append("," + name);
				valueSB.append(",?");
				pList.add(item.get(name));
			}
		}
	
		String nameLine = nameSB.toString();
		if (nameLine.length() > 1) {
			nameLine = nameLine.substring(1);
		}
		
		String valueLine = valueSB.toString();
		if (valueLine.length() > 1) {
			valueLine = valueLine.substring(1);
		}
		
		String sql = "insert into " + tablename + " (" + nameLine + ") values (" + valueLine + ")" ;
		
		Object[] r = new Object[] {sql, pList.toArray()};
		
		return r;
	}

}
