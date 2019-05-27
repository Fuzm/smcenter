package com.irdstudio.smcenter.core.tinycore.jdbc.session;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库会话
 * @author guangming.li
 * @version 1.0
 * @date 2013-10-10
 */
public class CustomTransaction {

	/*数据库连接对象*/
	private Connection conn = null;
	
	/**
	 * 实例化数据库会话(必须由工厂类来创建)
	 * @param conn
	 */
	protected CustomTransaction(Connection conn){
		this.conn = conn;
		if (conn != null)
			try {
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * 获取连接对象
	 * @return
	 */
	public Connection getConnection(){
		return this.conn;
	}
	
	/**
	 * 执行新增、删除、修改
	 * @param sql
	 * @return
	 * @throws SQLException 
	 */
	public boolean executeSql(String sql) throws SQLException {
		boolean flag = false;
		Statement st = null;
		try {
			st = conn.createStatement();
			st.executeUpdate(sql);
			flag = true;
		} finally {
			if (st != null)
				try {
					st.close();
					st = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return flag;
	}
	
	/**
	 * 回滚事务
	 * 如果需要的话
	 * @throws SQLException 
	 */
	public void rollback() throws SQLException{
		if (conn != null)
			conn.rollback();
	}
	
	/**
	 * 提交事务并结束
	 * 如果需要的话
	 * @throws SQLException 
	 */
	public void commit() throws SQLException {
		if (conn != null)
			conn.commit();
		
	}
}
