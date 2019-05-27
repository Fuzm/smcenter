package com.irdstudio.smcenter.core.tinycore.jdbc.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * 数据库表与表之前的数据复制或移动
 * @author guangming.li
 * @version 1.0
 * @date 2014-04-21
 */
public class DataMoveUtil {
	
	/**
	 * 将查询出来的数据复制到表
	 * @param conn
	 * @param insertSql
	 * @param querySql
	 * @return
	 * @throws SQLException
	 */
	public static boolean executeCustomCopy(Connection conn, String insertSql,
			String querySql){
		boolean flag = true;
		StringBuffer sql = new StringBuffer(insertSql);
		sql.append(" ").append(querySql);
		Statement st = null;
		try{
			st = conn.createStatement();
			st.execute(sql.toString());
		} catch(SQLException e){
			e.printStackTrace();
			flag = false;
		} finally{			
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	/**
	 * 将查询出来的数据迁移到表并删除原数据
	 * @param conn
	 * @param insertSql
	 * @param querySql
	 * @param queryCond
	 * @param cutSql
	 * @return
	 * @throws SQLException
	 */
	public static boolean executeCustomCut(Connection conn, String insertSql,
			String querySql,String cutSql) throws SQLException{
		boolean flag = executeCustomCopy(conn,insertSql,querySql);
		if (flag) {
			// 执行删除原数据的操作
			String sqls[] = cutSql.split(";");
			for (int i = 0; i < sqls.length; i++) {
				Statement st = null;
				try{
					st = conn.createStatement();
					st.execute(sqls[i]);
				} catch(SQLException e){
					e.printStackTrace();
					flag = false;
				} finally{			
					try {
						if (st != null)
							st.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}			
		}
		return true;
	}	
	
	/**
	 * 建立从表迁移到表的类实例
	 * @param conn
	 * @param srcTableName
	 * @param destTableName
	 * @return
	 * @throws SQLException
	 */
	public synchronized static TableMove createTableMoveInst(Connection conn,
			String srcTableName, String destTableName) throws SQLException {
		return new TableMove(conn, srcTableName,destTableName);
	}
	
}
