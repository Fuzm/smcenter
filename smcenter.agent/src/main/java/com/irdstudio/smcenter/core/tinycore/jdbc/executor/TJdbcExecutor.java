package com.irdstudio.smcenter.core.tinycore.jdbc.executor;

import java.sql.Connection;

import com.irdstudio.smcenter.core.tinycore.log.ILogger;

/**
 * Top JDBC executor
 * 负责产生基于JDBC的执行者对象
 * 负责回收执行者
 * @author guangming.li
 * @version 1.0
 * @date 2014-07-07
 */
public class TJdbcExecutor {
	
	private static ILogger jdbcLogger = null;
	
	/**
	 * 创建SQL执行者
	 * @param conn
	 */
	public static void newSqlExecutor(Connection conn){
		
	}
	
	
//	public static void createDaoExecutor();
	
	class JdbcExecutorResult{
		boolean isExecuteSuccess = false;
	}
	
	

}
