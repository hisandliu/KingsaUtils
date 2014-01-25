package org.hisand.db;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractDAO {
	private Connection connection;

	protected class ConnResult {
		public boolean isNewconn;
		public Connection conn;
	}

	protected ConnResult getConnResult() throws SQLException {
		Connection conn = getConnection();
		boolean isNewconn = false;
		if (conn == null) {
			conn = createConnection();
			isNewconn = true;
		}
		ConnResult rt = new ConnResult();
		rt.conn = conn;
		rt.isNewconn = isNewconn;
		return rt;
	}

	/**
	 * 取得 Connection
	 * 
	 * @return
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * 設置 Connection
	 * 
	 * @param connection
	 * @throws AWTException
	 */
	public void setConnection(Connection connection, String connectionId)
			{
		this.connection = connection;
		setConnectionId(connectionId);
	}

	private String connectionId;

	/**
	 * 取得 Connection Id
	 * 
	 * @return
	 */
	public String getConnectionId() {
		return connectionId;
	}

	/**
	 * 設置 Connection Id，如果是空值，代表是默認的資料庫連接
	 * 
	 * @param connectionId
	 */
	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}

	private String defaultConnectionId = null;

	protected String getDefaultConnectionId() {
		return defaultConnectionId;
	}

	protected void setDefaultConnectionId(String defaultConnectionId) {
		this.defaultConnectionId = defaultConnectionId;
	}

	protected String getUsedConnectionId() {
		String id = null;
		if (getConnectionId() != null && getConnectionId().length() > 0) {
			id = getConnectionId();
		} else {
			id = getDefaultConnectionId();
		}
		return id;
	}

	protected Dialect getDialect() {
		return ConnectionHelper.getDialect(getUsedConnectionId());
	}

	/**
	 * 創建新的 Connection
	 * 
	 * @return
	 * @throws SQLException
	 */
	protected Connection createConnection() throws SQLException {
		String id = getUsedConnectionId();
		Connection conn = ConnectionHelper.getConnection(id);
		return conn;
	}

	protected int updateWithReturnId(String sql, Object[] values)
			throws SQLException {
		return updateWithReturnId0(sql, values);
	}

	protected int update(String sql, Object[] values) throws SQLException {
		ConnResult rt = getConnResult();
		return DBHelper.update(rt.conn, sql, rt.isNewconn, values);
	}

	protected Object scalar(String sql, Object[] values) throws SQLException {
		return scalar(sql, values, 1);
	}
	
	protected Object scalar(String sql, Object[] values, int columnIndex) throws SQLException {
		if (columnIndex <= 0) columnIndex = 1;
		ConnResult rt = getConnResult();
		ScalarHandler handler = new ScalarHandler(columnIndex);
		return DBHelper.query(rt.conn, sql, rt.isNewconn, handler, values);
	}
	
	protected Object scalar(String sql, Object[] values, String columnName) throws SQLException {
		ConnResult rt = getConnResult();
		ScalarHandler handler = new ScalarHandler(columnName);
		return DBHelper.query(rt.conn, sql, rt.isNewconn, handler, values);
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

}
