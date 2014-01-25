package org.hisand.db;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public final class GeneralMapDAO extends AbstractMapDAO {
	
	public int insertWithReturnId(Map<String, Object> item,
			String tablename, String[] fieldNames) throws SQLException {
		return super.insertWithReturnId(item, tablename, fieldNames);
	}

	public int insert(Map<String, Object> item, String tablename,
			String[] fieldNames) throws SQLException {
		return super.insert(item, tablename, fieldNames);
	}

	public int insert(Map<String, Object> item, String tablename,
			String[] fieldNames, boolean isReturnId) throws SQLException {
		return super.insert(item, tablename, fieldNames, isReturnId);
	}

	public int updateWithReturnId(Map<String, Object> item,
			String tablename, String[] fieldNames,
			Map<String, Object> whereItem, String[] whereFields)
			throws SQLException {
		return super.updateWithReturnId(item, tablename, fieldNames, whereItem, whereFields);
	}

	public int update(Map<String, Object> item, String tablename,
			String[] fieldNames, Map<String, Object> whereItem,
			String[] whereFields) throws SQLException {
		return super.update(item, tablename, fieldNames, whereItem, whereFields);
	}

	public int update(Map<String, Object> item, String tablename,
			String[] fieldNames, Map<String, Object> whereItem,
			String[] whereFields, boolean isReturnId) throws SQLException {
		return super.update(item, tablename, fieldNames, whereItem, whereFields, isReturnId);
	}

	public int delete(String tablename, Map<String, Object> whereItem,
			String[] whereFields) throws SQLException {
		return super.delete(tablename, whereItem, whereFields);
	}

	public Map<String, Object> queryItem(String sql, Object[] values)
			throws SQLException {
		return super.queryItem(sql, values);
	}
	
	public List<Map<String, Object>> query(String sql, Object[] values)
			throws SQLException {
		return super.query(sql, values);
	}

	public List<Map<String, Object>> query(String tablename,
			String[] fieldNames, Map<String, Object> whereItem,
			String[] whereFields) throws SQLException {
		return super.query(tablename, fieldNames, whereItem, whereFields);
	}
	
	public int updateWithReturnId(String sql, Object[] values)
			throws SQLException {
		return super.updateWithReturnId(sql, values);
	}

	public int update(String sql, Object[] values) throws SQLException {
		return super.update(sql, values);
	}

	public Object scalar(String sql, Object[] values) throws SQLException {
		return super.scalar(sql, values);
	}
	
	public Object scalar(String sql, Object[] values, int columnIndex) throws SQLException {
		return super.scalar(sql, values, columnIndex);
	}
	
	public Object scalar(String sql, Object[] values, String columnName) throws SQLException {
		return super.scalar(sql, values, columnName);
	}
}
