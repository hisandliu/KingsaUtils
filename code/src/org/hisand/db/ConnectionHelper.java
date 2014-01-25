package org.hisand.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.hisand.core.SysSettings;

/**
 * 
 * @author Hisand Liu
 *
 */
public abstract class ConnectionHelper {

	public ConnectionHelper() {
	}

	public static Connection getConnection() throws SQLException {
		Connection conn = getConnection(null);
		return conn;
	}

	public static Connection getConnection(String connectionId) throws SQLException {
		BasicDataSource ds = getDataSource(connectionId);
		Connection conn = ds.getConnection();
		conn.setAutoCommit(true);
		return conn;
	}
	
	public static BasicDataSource getDataSource() {
		return getDataSource(null);
	}

	public static BasicDataSource getDataSource(String connectionId) {
		if (connectionId == null || connectionId.trim().length() == 0) {
			connectionId = SysSettings.getInstance().get("db.defaultConnectionId");
		}
		BasicDataSource info = new BasicDataSource();
		String p = "db." + connectionId;
		info.setDriverName(g(p + ".driverName"));
		info.setUrl(g(p + ".url"));
		info.setUserName(g(p + ".userName"));
		info.setPassword(g(p + ".password"));
		info.setDialectName(g(p + ".dialectName"));
		return info;
	}
	
	public static BasicDataSource getDataSource333(String connectionId) {
		BasicDataSource info = new BasicDataSource();
		info.setDriverName("org.sqlite.JDBC");
		info.setUrl("jdbc:sqlite:e:/hisand/bible/db/book_2.db");
		info.setUserName("");
		info.setPassword("");
		info.setDialectName("org.hisand.db.SqliteDialect");
		return info;
	}
	
	public static Dialect getDialect() {
		return getDialect(null);
	}
	
	public static Dialect getDialect(String connectionId) {
		BasicDataSource dataSource = getDataSource(connectionId);
		return dataSource.getDialect();
	}
	
	private static String g(String key) {
		return SysSettings.getInstance().get(key);
	}
	
	public static void main(String[] args) {
		String f = "test format %1$s is good %1$s xxx %2$s!";
		String r = String.format(f, "龟山岛", "草拟");
		System.out.println(r);
	}
}
