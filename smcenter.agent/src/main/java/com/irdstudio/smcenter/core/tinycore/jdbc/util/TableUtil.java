package com.irdstudio.smcenter.core.tinycore.jdbc.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.irdstudio.smcenter.core.tinycore.jdbc.executor.SafeReleaseUtil;

/**
 * 数据表操作辅助类
 * @author guangming.li
 * @version 1.0
 * @date 2014-04-29
 */
public class TableUtil {
	
	/**
	 * 数据表是否存在于数据
	 * @param conn
	 * @param name
	 * @return
	 */
	public static boolean isTableExist(Connection conn,String name){
		return false;
	}
	
	/**
	 * 建表操作
	 * @return
	 */
	public static boolean createTable(Connection conn, String ddl) {
		boolean flag = false;
		Statement st = null;
		try {
			st = conn.createStatement();
			st.execute(ddl);
			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SafeReleaseUtil.close(st);
		}
		return flag;
	}
}
