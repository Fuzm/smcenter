package com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.impl;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.base.IConnPool;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 封装针对C3P0的连接管理 Adapter for C3P0
 * 
 * @author guangming.li
 * @version 1.0
 * @date 2014-05-13
 */
public class ConnPoolForC3P0 implements IConnPool {

	private String jdbcUrl;
	private String user;
	private String password;
	private String driverClass = "com.mysql.jdbc.Driver";
	private int maxPoolSize = 100;
	private int minPoolSize = 10;
	private int initialPoolSize = 3;
	private int maxIdleTime = 20;
	private int acquireIncrement = 2;
	private ComboPooledDataSource dataSource;

	/**
	 * 构造C3P0连接池
	 * 
	 * @param jdbcUrl
	 * @param user
	 * @param password
	 * @param driverClass
	 * @param maxPoolSize
	 * @param minPoolSize
	 * @param initialPoolSize
	 * @param maxIdleTime
	 * @param acquireIncrement
	 */
	public ConnPoolForC3P0(String jdbcUrl, String user, String password, String driverClass, Integer maxPoolSize,
			Integer minPoolSize, Integer initialPoolSize, Integer maxIdleTime, Integer acquireIncrement) {
		initC3p0Properties(jdbcUrl, user, password, driverClass, maxPoolSize, minPoolSize, initialPoolSize, maxIdleTime,
				acquireIncrement);
		start();
	}

	/**
	 * 启动连接池
	 */
	private void start() {
		dataSource = new ComboPooledDataSource();
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setUser(user);
		dataSource.setPassword(password);
		try {
			dataSource.setDriverClass(driverClass);
		} catch (PropertyVetoException e) {
			dataSource = null;
			System.err.println("C3p0Plugin start error");
			throw new RuntimeException(e);
		}
		dataSource.setMaxPoolSize(maxPoolSize);
		dataSource.setMinPoolSize(minPoolSize);
		dataSource.setInitialPoolSize(initialPoolSize);
		dataSource.setMaxIdleTime(maxIdleTime);
		dataSource.setAcquireIncrement(acquireIncrement);
	}

	/**
	 * 初始化C3P0连接池的属性
	 * 
	 * @param jdbcUrl
	 * @param user
	 * @param password
	 * @param driverClass
	 * @param maxPoolSize
	 * @param minPoolSize
	 * @param initialPoolSize
	 * @param maxIdleTime
	 * @param acquireIncrement
	 */
	private void initC3p0Properties(String jdbcUrl, String user, String password, String driverClass,
			Integer maxPoolSize, Integer minPoolSize, Integer initialPoolSize, Integer maxIdleTime,
			Integer acquireIncrement) {
		this.jdbcUrl = jdbcUrl;
		this.user = user;
		this.password = password;
		this.driverClass = driverClass != null ? driverClass : this.driverClass;
		this.maxPoolSize = maxPoolSize != null ? maxPoolSize : this.maxPoolSize;
		this.minPoolSize = minPoolSize != null ? minPoolSize : this.minPoolSize;
		this.initialPoolSize = initialPoolSize != null ? initialPoolSize : this.initialPoolSize;
		this.maxIdleTime = maxIdleTime != null ? maxIdleTime : this.maxIdleTime;
		this.acquireIncrement = acquireIncrement != null ? acquireIncrement : this.acquireIncrement;
	}

	/**
	 * 获取一个连接,保证线程安全
	 */
	public Connection getConnection() throws SQLException {
		synchronized (dataSource) {
			return this.dataSource.getConnection();
		}
		
	}

	/**
	 * 连接池中的连接个数
	 */
	public int getConnectionCount() {
		try {
			return this.dataSource.getNumConnections();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void releaseConnection(Connection conn) {
		synchronized (dataSource) {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 清除并释放连接池
	 */
	public void clearAndFree() {
		this.dataSource.close();
	}

}
