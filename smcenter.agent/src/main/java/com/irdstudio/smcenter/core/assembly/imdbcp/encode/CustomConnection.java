/**
 * <p>Title: Connection的转码类</p>
 * <p>Description: 转码层</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 北京易初电子技术有限公司</p>
 * @author 黄瑞文
 * @version 1.0
 * 2006-11-22
 */
package com.irdstudio.smcenter.core.assembly.imdbcp.encode;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * 转码层，Connection的转码类
 * @author hrw
 * 2006-11-26
 */
public class CustomConnection implements Connection {
	
	/** 原来的Connection */
	protected Connection con;
	/** 编码 */
	protected String charSet;
	/** 编码方式, 0 不转码，1 转码，2 优化转码*/
	protected int encodeType;

	/**
	 * 构造函数
	 * @param _con 原来的Connection
	 * @param charSet 编码
	 * @param encodeType 编码方式, 0 不转码，1 转码，2 优化转码
	 */
	public CustomConnection(Connection _con, String charSet, int encodeType) {
		this.con = _con;
		this.charSet = charSet;
		this.encodeType = encodeType;
	}
	
	public void setReadOnly(boolean p0) throws SQLException {
		con.setReadOnly(p0);
	}
	
	public boolean isReadOnly() throws SQLException {
		return con.isReadOnly();
	}
	
	public void close() throws SQLException {
		//Debug.writeLog("Closed");
		con.close();
	}
	
	public boolean isClosed() throws SQLException {
		return con.isClosed();
	}
	
	public Statement createStatement(int p0, int p1, int p2) 
	throws SQLException {
		Statement _stmt = con.createStatement(p0, p1, p2);
		return new CustomStatement(_stmt,this.charSet,this.encodeType);
	}
	
	public Statement createStatement() throws SQLException {
		Statement _stmt = con.createStatement();
		return new CustomStatement(_stmt,this.charSet,this.encodeType);
	}
	
	public Statement createStatement(int p0, int p1) 
	throws SQLException {
		Statement _stmt = con.createStatement(p0, p1);
		return new CustomStatement(_stmt,this.charSet,this.encodeType);
	}
	
	public PreparedStatement prepareStatement(String p0,int p1) 
	throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		PreparedStatement _stmt = con.prepareStatement(enCodeX, p1);
		
		return new CustomPreparedStatement(_stmt, this.charSet,this.encodeType);
	}
	
	public PreparedStatement prepareStatement(String p0,String[] p1) 
	throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		PreparedStatement _stmt = con.prepareStatement(enCodeX, p1);
		
		return new CustomPreparedStatement(_stmt, this.charSet,this.encodeType);
	}
	
	public PreparedStatement prepareStatement(String p0,int p1, int p2, int p3) 
	throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		PreparedStatement _stmt = con.prepareStatement(enCodeX, p1, p2, p3);
		
		return new CustomPreparedStatement(_stmt, this.charSet,this.encodeType);
	}
	
	public PreparedStatement prepareStatement(String p0) 
	throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		PreparedStatement _stmt = con.prepareStatement(enCodeX);
		
		return new CustomPreparedStatement(_stmt, this.charSet,this.encodeType);
	}
	
	public PreparedStatement prepareStatement(String p0,int[] p1) 
	throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		PreparedStatement _stmt = con.prepareStatement(enCodeX, p1);
		
		return new CustomPreparedStatement(_stmt, this.charSet,this.encodeType);
	}
	
	public PreparedStatement prepareStatement(String p0,int p1, int p2) 
	throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		PreparedStatement _stmt = con.prepareStatement(enCodeX, p1, p2);
		
		return new CustomPreparedStatement(_stmt, this.charSet,this.encodeType);
	}
	
	public CallableStatement prepareCall(String p0, int p1,int p2) 
	throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		CallableStatement _cs = con.prepareCall(enCodeX, p1, p2);
		
		return new CustomCallableStatement(_cs,this.charSet,this.encodeType);
	}
	
	public CallableStatement prepareCall(String p0) 
	throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		CallableStatement _cs = con.prepareCall(enCodeX);
		
		return new CustomCallableStatement(_cs,this.charSet,this.encodeType);
	}
	
	public CallableStatement prepareCall(String p0, int p1,int p2, int p3) 
	throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		CallableStatement _cs = con.prepareCall(enCodeX, p1, p2, p3);
		
		return new CustomCallableStatement(_cs,this.charSet,this.encodeType);
	}
	
	public String nativeSQL(String p0) 
	throws SQLException {
		return con.nativeSQL(p0);
	}
	
	public void setAutoCommit(boolean p0) throws SQLException {
		con.setAutoCommit(p0);
	}
	
	public boolean getAutoCommit() throws SQLException {
		return con.getAutoCommit();
	}
	
	public void commit() throws SQLException {
		con.commit();
	}
	
	public void rollback(Savepoint p0) throws SQLException {
		con.rollback(p0);
	}
	
	public void rollback() throws SQLException {
		con.rollback();
	}
	
	public DatabaseMetaData getMetaData() throws SQLException {
		return con.getMetaData();
	}
	
	public void setCatalog(String p0) throws SQLException {
		con.setCatalog(p0);
	}
	
	public String getCatalog() throws SQLException {
		return con.getCatalog();
	}
	
	public void setTransactionIsolation(int p0) throws SQLException {
		con.setTransactionIsolation(p0);
	}
	
	public int getTransactionIsolation() throws SQLException {
		return con.getTransactionIsolation();
	}
	
	public SQLWarning getWarnings() throws SQLException {
		return con.getWarnings();
	}
	
	public void clearWarnings() throws SQLException {
		con.clearWarnings();
	}
	
	public Map getTypeMap() throws SQLException {
		return con.getTypeMap();
	}
	
	public void setTypeMap(Map p0) throws SQLException {
		con.setTypeMap(p0);
	}
	
	public void setHoldability(int p0) throws SQLException {
		con.setHoldability(p0);
	}
	
	public int getHoldability() throws SQLException {
		return con.getHoldability();
	}
	
	public Savepoint setSavepoint() throws SQLException {
		return con.setSavepoint();
	}
	
	public Savepoint setSavepoint(String p0) throws SQLException {
		return con.setSavepoint(p0);
	}
	
	public void releaseSavepoint(Savepoint p0) throws SQLException {
		con.releaseSavepoint(p0);
	}

	/** 原来的Connection */
	protected Connection getBaseConnnection() {
		return con;
	}

	/** 原来的Connection */
	protected void setBaseConnection(Connection con) {
		this.con = con;
	}

	public Object unwrap(Class iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isWrapperFor(Class iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public Clob createClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Blob createBlob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public NClob createNClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public SQLXML createSQLXML() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isValid(int timeout) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public void setClientInfo(String name, String value)
			throws SQLClientInfoException {
		// TODO Auto-generated method stub
		
	}

	public void setClientInfo(Properties properties)
			throws SQLClientInfoException {
		// TODO Auto-generated method stub
		
	}

	public String getClientInfo(String name) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Properties getClientInfo() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Array createArrayOf(String typeName, Object[] elements)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Struct createStruct(String typeName, Object[] attributes)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSchema(String schema) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSchema() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void abort(Executor executor) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNetworkTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
}
