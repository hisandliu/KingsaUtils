package org.hisand.db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * 数据库的链接信息
 * 
 * @author Administrator
 * 
 */
public class BasicDataSource implements DataSource {
	private String driverName;
	private String url;
	private String userName;
	private String password;
	private String dialectName;
	private int maxIdleTime;
	private int maxPoolSize;
	
	public BasicDataSource() {
		super();
	}
	
	public BasicDataSource(DataSource defaultDataSource) {
		super();
		this.defaultDataSource = defaultDataSource;
	}
	
	private DataSource defaultDataSource;

	public DataSource getDefaultDataSource() {
		return defaultDataSource;
	}

	public void setDefaultDataSource(DataSource defaultDataSource) {
		this.defaultDataSource = defaultDataSource;
	}

	public int getMaxIdleTime() {
		return maxIdleTime;
	}

	public void setMaxIdleTime(int maxIdleTime) {
		this.maxIdleTime = maxIdleTime;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public String getDialectName() {
		return dialectName;
	}

	public void setDialectName(String dialectName) {
		this.dialectName = dialectName;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private PrintWriter logWriter = null;

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		if (getDefaultDataSource() != null) {
			return getDefaultDataSource().getLogWriter();
		}
		return logWriter;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		if (getDefaultDataSource() != null) {
			getDefaultDataSource().setLogWriter(out);
			return;
		}
		logWriter = out;
	}

	private int loginTimeout;

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		if (getDefaultDataSource() != null) {
			getDefaultDataSource().setLoginTimeout(seconds);
			return;
		}
		loginTimeout = seconds;
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		if (getDefaultDataSource() != null) {
			return getDefaultDataSource().getLoginTimeout();
		}
		return loginTimeout;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		if (getDefaultDataSource() != null) {
			return getDefaultDataSource().unwrap(iface);
		}
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		if (getDefaultDataSource() != null) {
			return getDefaultDataSource().isWrapperFor(iface);
		}
		return false;
	}

	@Override
	public Connection getConnection() throws SQLException {
		return getConnection(getUserName(), getPassword());
	}

	@Override
	public Connection getConnection(String username, String password)
			throws SQLException {
		if (getDefaultDataSource() != null) {
			return getDefaultDataSource().getConnection();
		}
		try {
			Class.forName(getDriverName());
		} catch (ClassNotFoundException e) {
			throw new SQLException(e);
		}
		Connection conn = null;
		if (username != null && username.length() > 0) {
			conn = DriverManager.getConnection(getUrl(), username, password);
		} else {
			conn = DriverManager.getConnection(getUrl());
		}
		return conn;
	}

	public Dialect getDialect() {
		try {
			Class<?> clazz = Class.forName(getDialectName());
			Object obj = clazz.newInstance();
			return (Dialect) obj;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}
}
