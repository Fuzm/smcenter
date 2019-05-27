package com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.impl;

import java.sql.Connection;
import java.sql.SQLException;

import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.base.IConnPool;

/**
/**
 * 封装针对IMdbcp的连接管理
 * 该连接池可智能管理(监控未释放的连接及资源)
 * 可在测试阶段使用此连接池
 * Adapter for IMdbcp
 * @author guangming.li
 * @version 1.0
 * @date 2014-05-13
 */
public class ConnPoolForIMdbcp implements IConnPool{

	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getConnectionCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void releaseConnection(Connection conn) {
		// TODO Auto-generated method stub
		
	}

	public void clearAndFree() {
		// TODO Auto-generated method stub
		
	}

}
