package com.irdstudio.smcenter.core.util.pub;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.TConnPool;

public class ConnectionUtil {
	
	private static String OPENDAY = null;

	/**
	 * 从连接池中获取连接(缺省使用EMP连接池)
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		return TConnPool.getDefaultPool().getConnection();
	}

	/**
	 * 释放连接进入连接池(缺少为EMP连接池)
	 * 
	 * @param conn
	 */
	public static void releaseConnection(Connection conn) {
		TConnPool.getDefaultPool().releaseConnection(conn);
	}
	
	/**
	 * 获取系统时间
	 * @return
	 */
	public static String getSysTime() {
		return OPENDAY;
	}

	/**
	 * 加载系统营业日期
	 */
	public static void loadSysTime() {

		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			connection = ConnectionUtil.getConnection();
			/** 获取context根节点 */
			stmt = connection.createStatement();

			String sqlStr = "select openday from pub_sys_info";
			rs = stmt.executeQuery(sqlStr);
			if (rs.next()) {
				OPENDAY = rs.getString("openday");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (stmt != null) {
					stmt.close();
				}
				ConnectionUtil.releaseConnection(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
