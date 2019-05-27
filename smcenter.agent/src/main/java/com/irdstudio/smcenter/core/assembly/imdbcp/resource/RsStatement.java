/**
 * <p>Title: Statement的监控类</p>
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
import java.sql.Statement;

import com.irdstudio.smcenter.core.assembly.imdbcp.encode.CustomStatement;
import com.irdstudio.smcenter.core.assembly.imdbcp.encode.Util;

/**
 * 资源监控层，Statement的监控类
 * @author hrw
 */
public class RsStatement extends CustomStatement implements RsInterface 
{
	/** 最后执行SQL */
	private String lastSql = null;
	/** 最后执行SQL时间 */
	private long lastSqlTime = 0;
	/** 连接 */
	private RsConnection conn = null;
	
	public RsStatement(Statement statement, String charSet, int encodeType){
		super(statement,charSet,encodeType);
	}
	
	/**
	 * 关闭Statement
	 * 同时需要清除连接的资源
	 */
	public void close() throws SQLException {
		conn.removeResource(this);
		super.close();
	}
	
	public boolean execute(String p0, int p1) 
	throws SQLException {
		this.setLastSql(p0);
		
		return super.execute(p0, p1);
	}
	
	public boolean execute(String p0, int[] p1) 
	throws SQLException {
		this.setLastSql(p0);
		
		return super.execute(p0, p1);
    }
	
	public boolean execute(String p0, String[] p1) 
	throws SQLException {
		this.setLastSql(p0);
		
		return super.execute(p0, p1);
	}
	
	public boolean execute(String p0) throws SQLException {
		this.setLastSql(p0);
		
		return super.execute(p0);
	}
	
	public ResultSet executeQuery(String p0) 
	throws SQLException {
		this.setLastSql(p0);
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		ResultSet _rs = st.executeQuery(enCodeX);
		RsResultSet rs = new RsResultSet(_rs,this.charSet,this.encodeType);
		conn.addResource(rs);
		rs.setLastSql(lastSql);
		
		return rs;
	}
	
	public int executeUpdate(String p0) throws SQLException {
		this.setLastSql(p0);

		return super.executeUpdate(p0);
	}
	
	public int executeUpdate(String p0, String[] p1) 
	throws SQLException {
		this.setLastSql(p0);
		
		return super.executeUpdate(p0, p1);
	}
	
	public int executeUpdate(String p0, int p1) 
	throws SQLException {
		this.setLastSql(p0);
		
		return super.executeUpdate(p0, p1);
	}
	
	public int executeUpdate(String p0, int[] p1) 
	throws SQLException {
		this.setLastSql(p0);
		
		return super.executeUpdate(p0, p1);
	}
	
	public ResultSet getResultSet() throws SQLException {
		ResultSet _rs = st.getResultSet();
		RsResultSet rs = new RsResultSet(_rs,this.charSet,this.encodeType);
		conn.addResource(rs);
		rs.setLastSql(lastSql);
		
		return rs;
	}
	
	public void addBatch(String p0) throws SQLException {
		this.setLastSql(p0);
		
		super.addBatch(p0);
	}
	
	public ResultSet getGeneratedKeys() throws SQLException {
		ResultSet _rs = st.getGeneratedKeys();
		RsResultSet rs = new RsResultSet(_rs,this.charSet,this.encodeType);
		conn.addResource(rs);
		rs.setLastSql(lastSql);
		
		return rs;
	}

    /** 最后执行的SQL */
	public String getLastSql() {
		return this.lastSql;
	}
	
	/** 最后执行的SQL时间 */
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
