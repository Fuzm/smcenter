package com.irdstudio.smcenter.core.tinycore.jdbc.executor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 安全释放JDBC资源
 * @author guangming.li
 * @version 1.0
 * @date 2014-07-07
 */
public class SafeReleaseUtil {
	
	/**
	 * 关闭资源
	 * @param theRs
	 * @param theStmt
	 * @param thePs
	 */
	public static void close(ResultSet rs, Statement st, PreparedStatement ps) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (st != null)
				st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (ps != null)
				ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭PreparedStatement资源
	 * @param ps
	 */
	public static void close(PreparedStatement ps) {
		try {
			if (ps != null)
				ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭Statement资源
	 * @param ps
	 */
	public static void close(Statement st) {
		try {
			if (st != null)
				st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭ResetSet
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
}
