package org.hisand.db;

import java.sql.SQLException;

public final class GeneralDAO extends AbstractDAO {
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
