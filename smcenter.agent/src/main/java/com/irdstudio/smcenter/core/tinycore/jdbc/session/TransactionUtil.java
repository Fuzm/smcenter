package com.irdstudio.smcenter.core.tinycore.jdbc.session;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 事务辅助类
 * @author guangming.li
 * @version 1.1
 * @date 2013-10-10
 * @since 1.0 2014-05-23 封装,所有事务必须由该类进行开启
 */
public class TransactionUtil {
	
	/**
	 * 根据连接创建事务会话
	 * @param conn
	 * @return
	 */
	public static CustomTransaction createTransaction(Connection conn){
		return new CustomTransaction(conn);
	}

	/**
	 * 结束事务(与createTransaction成对出现)
	 */
	public static void releaseTransaction(CustomTransaction tran) {
		try {
			if(!tran.getConnection().getAutoCommit()){
				tran.getConnection().setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
}
