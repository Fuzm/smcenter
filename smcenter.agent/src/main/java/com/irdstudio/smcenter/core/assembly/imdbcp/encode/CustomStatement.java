/**
 * <p>Title: Statement的转码类</p>
 * <p>Description: 转码层</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 北京易初电子技术有限公司</p>
 * @author 黄瑞文
 * @version 1.0
 * 2006-11-22
 */

package com.irdstudio.smcenter.core.assembly.imdbcp.encode;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

/**
 * 转码层，Statement的转码类
 * @author hrw
 * 2006-11-26
 */
public class CustomStatement implements Statement {
	
	/** 原来的Statement */
	protected Statement st;
	/** 编码 */
	protected String charSet;
	/** 编码方式, 0 不转码，1 转码，2 优化转码*/
	protected int encodeType;
	
	/**
	 * 构造函数
	 * @param _st 原来的Statement
	 * @param charSet 编码
	 * @param encodeType 编码方式, 0 不转码，1 转码，2 优化转码
	 */
	public CustomStatement(Statement _st, String charSet, int encodeType) {
		this.st = _st;
		this.charSet = charSet;
		this.encodeType = encodeType;
	}
	
	public void close() throws SQLException {
		st.close();
	}
	
	public boolean execute(String p0, int p1) 
	throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		
		return st.execute(enCodeX, p1);
	}
	
	public boolean execute(String p0, int[] p1) 
	throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		
		return st.execute(enCodeX, p1);
    }
	
	public boolean execute(String p0, String[] p1) 
	throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		
		return st.execute(enCodeX, p1);
	}
	
	public boolean execute(String p0) throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		
		return st.execute(enCodeX);
	}
	
	/**
	 * 重载过的方法
	 * @param p0
	 * @return
	 * @throws SQLException
	 */
	public ResultSet executeQuery(String p0) 
	throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		ResultSet _rs = st.executeQuery(enCodeX);
		
		return new CustomResultSet(_rs,this.charSet, this.encodeType);
	}
	
	public int executeUpdate(String p0) throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		
		return st.executeUpdate(enCodeX);
	}
	
	public int executeUpdate(String p0, String[] p1) 
	throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		
		return st.executeUpdate(enCodeX, p1);
	}
	
	public int executeUpdate(String p0, int p1) 
	throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		
		return st.executeUpdate(enCodeX, p1);
	}
	
	public int executeUpdate(String p0, int[] p1) 
	throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);

		return st.executeUpdate(enCodeX, p1);
	}
	
	public int getMaxFieldSize() throws SQLException {
		return st.getMaxFieldSize();
	}
	
	public void setMaxFieldSize(int p0) throws SQLException {
		st.setMaxFieldSize(p0);
	}
	
	public int getMaxRows() throws SQLException {
		return st.getMaxRows();
	}
	
	public void setMaxRows(int p0) throws SQLException {
		st.setMaxRows(p0);
	}
	
	public void setEscapeProcessing(boolean p0) throws SQLException {
		st.setEscapeProcessing(p0);
	}
	
	public int getQueryTimeout() throws SQLException {
		return st.getQueryTimeout();
	}
	
	public void setQueryTimeout(int p0) throws SQLException {
		st.setQueryTimeout(p0);
	}
	
	public void cancel() throws SQLException {
		st.cancel();
	}
	
	public SQLWarning getWarnings() throws SQLException {
		return st.getWarnings();
	}
	
	public void clearWarnings() throws SQLException {
		st.clearWarnings();
	}
	
	public void setCursorName(String p0) throws SQLException {
		st.setCursorName(p0);
	}
	
	public ResultSet getResultSet() throws SQLException {
		return new CustomResultSet(st.getResultSet(),this.charSet, this.encodeType);
	}
	
	public int getUpdateCount() throws SQLException {
		return st.getUpdateCount();
	}
	
	public boolean getMoreResults(int p0) throws SQLException {
		return st.getMoreResults(p0);
	}
	
	public boolean getMoreResults() throws SQLException {
		return st.getMoreResults();
	}
	
	public void setFetchDirection(int p0) throws SQLException {
		st.setFetchDirection(p0);
	}
	
	public int getFetchDirection() throws SQLException {
		return st.getFetchDirection();
	}
	
	public void setFetchSize(int p0) throws SQLException {
		st.setFetchSize(p0);
	}
	
	public int getFetchSize() throws SQLException {
		return st.getFetchSize();
	}
	
	public int getResultSetConcurrency() throws SQLException {
		return st.getResultSetConcurrency();
	}
	
	public int getResultSetType() throws SQLException {
		return st.getResultSetType();
	}
	
	public void addBatch(String p0) throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
				
		st.addBatch(enCodeX);
	}
	
	public void clearBatch() throws SQLException {
		st.clearBatch();
	}
	
	public int[] executeBatch() throws SQLException {
		return st.executeBatch();
	}
	
	public Connection getConnection() throws SQLException {
		return st.getConnection();
	}
	
	public ResultSet getGeneratedKeys() throws SQLException {
		return st.getGeneratedKeys();
	}
	
	public int getResultSetHoldability() throws SQLException {
		return st.getResultSetHoldability();
	}

	/** 原来的Statement */
	protected Statement getBaseStatement() {
		return st;
	}

	/** 原来的Statement */
	protected void setBaseStatement(Statement st) {
		this.st = st;
	}

	public Object unwrap(Class iface) throws SQLException {
		return this.st.unwrap(iface);
	}

	public boolean isWrapperFor(Class iface) throws SQLException {
		return this.st.isWrapperFor(iface);
	}

	public boolean isClosed() throws SQLException {
		return this.st.isClosed();
	}

	public void setPoolable(boolean poolable) throws SQLException {
		this.st.setPoolable(poolable);
	}

	public boolean isPoolable() throws SQLException {
		return this.st.isPoolable();
	}

	@Override
	public void closeOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
}
