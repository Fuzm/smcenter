package com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.irdstudio.smcenter.agent.utils.AnsiLog;
import com.irdstudio.smcenter.agent.utils.SpringContextUtils;
import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.base.IConnPool;

public class ConnPoolForDruid implements IConnPool {

	private DruidDataSource druidDataSource;

	public ConnPoolForDruid() {
		druidDataSource = (DruidDataSource) SpringContextUtils.getBean("dataSource");
	}

	public Connection getConnection() throws SQLException {
		AnsiLog.info("ActiveCount:"+druidDataSource.getActiveCount());
		Set<DruidPooledConnection> conns = druidDataSource.getActiveConnections();
		List<String> list = druidDataSource.getActiveConnectionStackTrace();

		for (String string : list) {
			AnsiLog.info("ActiveConnectionStackTrace:"+string);
		}
		for (DruidPooledConnection conn : conns) {
			AnsiLog.info("ActiveConnections：" + conn.getConnectStackTrace());
		}
		synchronized (druidDataSource) {
			return druidDataSource.getConnection();
		}
	}

	@Override
	public int getConnectionCount() {
		return (int) druidDataSource.getConnectCount();
	}

	@Override
	public void releaseConnection(Connection conn) {
		synchronized (druidDataSource) {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void clearAndFree() {
		druidDataSource.close();
	}
}
