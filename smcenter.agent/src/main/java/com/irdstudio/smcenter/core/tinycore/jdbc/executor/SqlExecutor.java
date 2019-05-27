package com.irdstudio.smcenter.core.tinycore.jdbc.executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.irdstudio.smcenter.core.util.vo.UniKeyValueObject;

/**
 * SQL执行者,用于执行常用的SQL操作
 * 		从多个类中整理而来
 * @author guangming.li
 * @version 1.0
 * @date 2014-10-22
 */
public class SqlExecutor {
	
	/* 查询时最大允许的返回记录数 */
	private final static int MAX_QUERY_SIZE = 10000;
	
	/**
	 * 返回单条记录
	 * 返回的数据不可以用于并发环境下访问
	 * @param sql 查询语句
	 * @return
	 * @throws SQLException 
	 */
	public static UniKeyValueObject querySingleResult(Connection conn,String sql) throws SQLException{	
		return getSingleResult(conn,sql,false);
	}
	
	/**
	 * 返回单条记录
	 * 返回的数据可用于并发环境下访问
	 * @param sql 查询语句
	 * @return
	 * @throws SQLException 
	 */
	public static UniKeyValueObject querySingleResultEx(Connection conn,String sql) throws SQLException{	
		return getSingleResult(conn,sql,true);
	}
	
	
	/**
	 * 返回多条记录的值
	 * 以List的方法返回,返回的数据不可以用于并发环境
	 * 关注查询可能返回的记录条数,最多返回一万条记录的结果集
	 * @param conn
	 * @param sql
	 * @return
	 * @throws SQLException 
	 */
	public static List<UniKeyValueObject> queryMultipleResult(Connection conn,String sql) throws SQLException{
		return getMultipleResult(conn,sql,false);
		
	}

	/**
	 * 返回多条记录的值
	 * 以List的方法返回,返回的数据可以用于并发环境
	 * 关注查询可能返回的记录条数,最多返回一万条记录的结果集
	 * @param conn
	 * @param sql
	 * @return
	 * @throws SQLException 
	 */
	public static List<UniKeyValueObject> queryMultipleResultEx(Connection conn,String sql) throws SQLException{
		return getMultipleResult(conn,sql,true);		
	}
	
	/**
	 * 执行SQL语句，返回第一条记录的第一个字段结果
	 * @param conn
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public static Object queryOnlyOneValue(Connection conn, String sql)
			throws SQLException {
		ResultSet rs = null;
		Object result = null;
		Statement st = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if (rs != null && rs.next()) {
				result = rs.getString(1);
			}
		} finally {
			SafeReleaseUtil.close(rs, st, null);
		}
		return result;
	}	
	
	/**
	 * 返回多条记录
	 * @param conn 连接
	 * @param sql  查询语句
	 * @param flag 结果对象是否线程安全
	 * @return
	 * @throws SQLException
	 */
	private static UniKeyValueObject getSingleResult(Connection conn,
			String sql, boolean flag) throws SQLException {
		UniKeyValueObject uvo = null;
		ResultSet rs = null;
		Statement st = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			int index = 0;
			if (rs.next() && index < SqlExecutor.MAX_QUERY_SIZE) {
				index++;
				uvo = new UniKeyValueObject(flag);
				ResultSetMetaData rsmd = rs.getMetaData();
				int numCols = rsmd.getColumnCount();
				for (int i = 1; i <= numCols; i++) {
					uvo.addVariable(rsmd.getColumnLabel(i), rs.getObject(i));
				}
			}
		} finally {
			SafeReleaseUtil.close(rs, st, null);
		}
		return uvo;
	}
	
	/**
	 * 返回多条记录的值
	 * @param conn 连接
	 * @param sql  查询语句
	 * @param flag 结果对象是否线程安全
	 * @return
	 * @throws SQLException 
	 */
	private static List<UniKeyValueObject> getMultipleResult(Connection conn,
			String sql, boolean flag) throws SQLException {
		List<UniKeyValueObject> uvoList = new ArrayList<UniKeyValueObject>();
		ResultSet rs = null;
		Statement st = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs != null && rs.next()) {
				UniKeyValueObject uvo = new UniKeyValueObject(flag);
				ResultSetMetaData rsmd = rs.getMetaData();
				int numCols = rsmd.getColumnCount();
				for (int i = 1; i <= numCols; i++) {
					uvo.addVariable(rsmd.getColumnLabel(i), rs.getObject(i));
				}
				uvoList.add(uvo);
			}
		} finally {
			SafeReleaseUtil.close(rs, st, null);
		}
		return uvoList;
	}
}
