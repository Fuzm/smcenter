package com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.base;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 连接池接口类,符合接口的连接池类均摊可为连接池供应商
 * @author 李广明
 * @version 1.0
 * @date 2007-10-16
 *
 */
public interface IConnPool {
	
	/**
	 * 获取连接
	 * @return
	 */
	public Connection getConnection() throws SQLException;
	
	/**
	 * 获取连接总数(在用的与空闲的)
	 * @return
	 */
	public int getConnectionCount();
	
	/**
	 * 释放连接
	 * @param conn
	 */
	public void releaseConnection(Connection conn);
	
	/**
	 * 清除并关闭连接池中的连接
	 */
	public void clearAndFree();
	
}
