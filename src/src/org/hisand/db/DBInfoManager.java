package org.hisand.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DBInfoManager {

	DBInfoManager() {
		super();
	}

	private static DBInfoManager instance;

	public static DBInfoManager getInstance() {
		if (instance == null) {
			instance = new DBInfoManager();
		}
		return instance;
	}

	public java.util.Date getServerTime() throws SQLException {
		return getServerTime(null);
	}

	public java.util.Date getServerTime(String connectionId)
			throws SQLException {
		Connection conn = ConnectionHelper.getConnection(connectionId);
		return getSeverTime0(conn, connectionId, true);
	}

	public java.util.Date getServerTime(Connection conn, String connectionId)
			throws SQLException {
		return getSeverTime0(conn, connectionId, false);
	}

	private java.util.Date getSeverTime0(Connection conn, String connectionId,
			boolean closeConn) throws SQLException {
		Dialect dialect = ConnectionHelper.getDialect(connectionId);
		String sql = dialect.getSysDateSQL();
		ScalarHandler handler = new ScalarHandler();
		Object obj = DBHelper.query(conn, sql, closeConn, handler,
				(Object[]) null);
		return castDatetime(obj);
	}
	
	private java.util.Date castDatetime(Object obj) throws SQLException {
		if (obj == null) {
			throw new SQLException("System datetime is null!");
		}
		if (obj instanceof String) {
			String str = (String) obj;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date date;
			try {
				date = df.parse(str);
			} catch (ParseException e) {
				throw new SQLException("System datetime format is error!");
			}
			return date;
		}
		else if (obj instanceof java.util.Date) {
			return (java.util.Date) obj;
		}
		else {
			throw new SQLException("Get system date failed!");
		}
	}
}
