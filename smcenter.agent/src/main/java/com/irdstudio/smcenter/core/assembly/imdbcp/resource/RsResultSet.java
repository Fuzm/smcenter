/**
 * <p>Title: ResultSet的监控类</p>
 * <p>Description: 资源监控层</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 北京易初电子技术有限公司</p>
 * @author 黄瑞文
 * @version 1.0
 * 2006-11-22
 */

package com.irdstudio.smcenter.core.assembly.imdbcp.resource;


import java.sql.ResultSet;
import java.sql.SQLException;

import com.irdstudio.smcenter.core.assembly.imdbcp.encode.CustomResultSet;

/**
 * 资源监控层，ResultSet的监控类
 * @author hrw
 */
public class RsResultSet extends CustomResultSet implements RsInterface {
	/** 最后执行SQL */
	private String lastSql = null;
	/** 最后执行SQL时间 */
	private long lastSqlTime = 0;
	/** 连接 */
	private RsConnection conn = null;
	
	/**
	 * 构造函数
	 * @param _rs 原来的ResultSet
	 * @param charSet 字符集
	 * @param encodeType 转码类型
	 */
	public RsResultSet(ResultSet _rs, String charSet,int encodeType) {
		super(_rs, charSet,encodeType);
	}
	
	public void close() throws SQLException {
		conn.removeResource(this);
		super.close();
	}
	
	/**
	 * 最后执行的SQL
	 */
	public String getLastSql() {
		return this.lastSql;
	}
	
	/**
	 * 最后执行的SQL时间
	 */
	public long getLastSqlTime() {
		return this.lastSqlTime;
	}
	
	/**
	 * 设置最后执行的SQL
	 * @param sql
	 */
	public void setLastSql(String sql) {
		this.lastSql = sql;
		this.conn.setLastSql(sql);
		this.lastSqlTime = System.currentTimeMillis();
	}

	/** 连接 */
	public RsConnection getBaseConnnection() {
		return conn;
	}

	/** 连接 */
	public void setBaseConnnection(RsConnection conn) {
		this.conn = conn;
	}
}
