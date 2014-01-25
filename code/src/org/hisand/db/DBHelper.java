package org.hisand.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {

	public static <T> T query(Connection conn, String sql, boolean closeConn,
			ResultSetHandler<T> handler, Object[] values) throws SQLException {
		ResultSet rs = null;
		PreparedStatement ps = null;
		Statement stmt = null;
		try {
			if (values != null && values.length > 0) {
				ps = conn.prepareStatement(sql);
				fillStatement(ps, values);
				rs = ps.executeQuery();
			} else {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
			}
			return handler.handle(rs);
		} catch (SQLException e) {
			throw e;
		}
		finally {
			close(ps, stmt);
			if (closeConn) {
				close(conn);
			}
		}
	}

	public static int update(Connection conn, String sql, boolean closeConn,
			Object[] values) throws SQLException {
		int rows = 0;
		PreparedStatement ps = null;
		Statement stmt = null;
		try {
			if (values != null && values.length > 0) {
				ps = conn.prepareStatement(sql);
				fillStatement(ps, values);
				rows = ps.executeUpdate();
			} else {
				stmt = conn.createStatement();
				rows = stmt.executeUpdate(sql);
			}
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			close(ps, stmt);
			if (closeConn) {
				close(conn);
			}
		}
	}

	public static int updateWithReturnId(Connection conn, String sql,
			boolean closeConn) throws SQLException {
		Object[] values = null;
		return updateWithReturnId(conn, sql, closeConn, values);
	}

	public static int updateWithReturnId(Connection conn, String sql,
			boolean closeConn, Object[] values) throws SQLException {
		PreparedStatement ps = null;
		Statement stmt = null;
		ResultSet rs = null;
		int result = -1;
		try {
			if (values != null && values.length > 0) {
				ps = conn
						.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				fillStatement(ps, values);
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();
			} else {
				stmt = conn.createStatement();
				stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
				rs = stmt.getGeneratedKeys();
			}
			if (rs.next()) {
				result = rs.getInt(1);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			close(rs);
			close(ps, stmt);
			if (closeConn) {
				close(conn);
			}
		}
	}

	public static int updateWithReturnIdSqlite(Connection conn, String sql,
			boolean closeConn, Object[] values) throws SQLException {
		PreparedStatement ps = null;
		Statement stmt = null;
		ResultSet rs = null;
		int result = -1;
		try {
			if (values != null && values.length > 0) {
				ps = conn.prepareStatement(sql);
				fillStatement(ps, values);
				ps.executeUpdate();
			} else {
				stmt = conn.createStatement();
				stmt.executeUpdate(sql);
			}
			if (stmt == null) {
				stmt = conn.createStatement();
			}
			String sql2 = "select last_insert_rowid();";
			rs = stmt.executeQuery(sql2);
			if (rs.next()) {
				result = rs.getInt(1);
			}

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			close(rs);
			close(ps, stmt);
			if (closeConn) {
				close(conn);
			}
		}
	}

	public static void fillStatement(PreparedStatement ps, Object[] values)
			throws SQLException {
		for (int i = 0; i < values.length; i++) {
			Object obj = values[i];
			if ((obj) instanceof java.util.Date) {
				java.util.Date dt = (java.util.Date) obj;
				obj = new java.sql.Timestamp(dt.getTime());
			}
			ps.setObject(i + 1, obj);
		}
	}

	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (Exception e) {
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			}
		}
	}

	public static void close(PreparedStatement ps) {
		close(null, ps, null);
	}

	public static void close(PreparedStatement ps, ResultSet rs) {
		close(null, ps, rs);
	}

	public static void close(PreparedStatement ps, Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (Exception e) {
			}
		}
	}

	public static void rollback(Connection conn) {
		try {
			if (conn != null) {
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}