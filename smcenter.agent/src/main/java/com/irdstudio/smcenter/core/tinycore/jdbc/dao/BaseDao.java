package com.irdstudio.smcenter.core.tinycore.jdbc.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.irdstudio.smcenter.core.tinycore.jdbc.session.CustomTransaction;

/**
 * 基础DAO类
 * @author guangming.li
 * @version 1.0
 * @date 2013-10-10
 */
public class BaseDao {

	private Statement st = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	/*数据库会话*/
	protected CustomTransaction dbsession = null;
	
	public BaseDao(CustomTransaction dbsession){
		this.dbsession = dbsession;
	}

	/**
	 * 关闭连接资源
	 * @param theRs
	 * @param theStmt
	 * @param thePs
	 */
	protected void close(ResultSet theRs, Statement theStmt,
			PreparedStatement thePs) {
		try {
			if (theRs != null)
				theRs.close();
			if (theStmt != null)
				theStmt.close();
			if (thePs != null)
				thePs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 析构函数(JVM不一定执行)
	 */
	public void finalize() {
		this.close(rs, st, ps);
	}
}
