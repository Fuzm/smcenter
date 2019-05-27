/**
 * <p>Title: PreparedStatement的转码类</p>
 * <p>Description: 转码层</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 北京易初电子技术有限公司</p>
 * @author 黄瑞文
 * @version 1.0
 * 2006-11-22
 */

package com.irdstudio.smcenter.core.assembly.imdbcp.encode;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * 转码层，PreparedStatement的转码类
 * @author hrw
 * 2006-11-26
 */
public class CustomPreparedStatement implements PreparedStatement {
	/** 原来的PreparedStatement */
	protected PreparedStatement ps;
	/** 编码 */
	protected String charSet;
	/** 编码方式, 0 不转码，1 转码，2 优化转码*/
	protected int encodeType;
	
	/**
	 * 构造函数
	 * @param _ps 原来的PreparedStatement
	 * @param charSet 编码
	 * @param encodeType 编码方式, 0 不转码，1 转码，2 优化转码
	 */
	public CustomPreparedStatement(PreparedStatement _ps, String charSet, int encodeType) {
		this.ps = _ps;
		this.charSet = charSet;
		this.encodeType = encodeType;
	}
	
	public void setBoolean(int p0, boolean p1) throws SQLException {
		ps.setBoolean(p0, p1);
	}
	
	public void setByte(int p0, byte p1) throws SQLException {
		ps.setByte(p0, p1);
	}
	
	public void setShort(int p0, short p1) throws SQLException {
		ps.setShort(p0, p1);
	}
	
	public void setInt(int p0, int p1) throws SQLException {
		ps.setInt(p0, p1);
	}
	
	public void setLong(int p0, long p1) throws SQLException {
		ps.setLong(p0, p1);
	}
	
	public void setFloat(int p0, float p1) throws SQLException {
		ps.setFloat(p0, p1);
	}
	
	public void setDouble(int p0, double p1) throws SQLException {
		ps.setDouble(p0, p1);
	}
	
	public void setURL(int p0, URL p1) throws SQLException {
		ps.setURL(p0, p1);
	}
	
	public void setTime(int p0, Time p1) throws SQLException {
		ps.setTime(p0, p1);
	}
	
	public void setTime(int p0, Time p1, Calendar p2) 
	throws SQLException {
		ps.setTime(p0, p1, p2);
	}
	
	public boolean execute() throws SQLException {
		return ps.execute();
	}
	
	/**
	 * 重载过的方法
	 * @return
	 * @throws SQLException
	 */
	public ResultSet executeQuery() throws SQLException {
		ResultSet _rs = ps.executeQuery();
		return new CustomResultSet(_rs,charSet,this.encodeType);
	}
	
	public int executeUpdate() throws SQLException {
		return ps.executeUpdate();
	}
	
	public void setNull(int p0, int p1) throws SQLException {
		ps.setNull(p0, p1);
	}
	
	public void setNull(int p0, int p1, String p2) 
	throws SQLException {
		ps.setNull(p0, p1, p2);
	}
	
	public void setBigDecimal(int p0, BigDecimal p1) 
	throws SQLException {
		ps.setBigDecimal(p0, p1);
	}
	
	/**
	 * 重载过的方法
	 * @param p0
	 * @param p1
	 * @throws SQLException
	 */
	public void setString(int p0, String p1) 
	throws SQLException {
		String enCodeX = Util.encodeString(p1, this.charSet, this.encodeType);
		
		ps.setString(p0, enCodeX);
	}
	
	public void setBytes(int p0, byte[] p1) throws SQLException {
		ps.setBytes(p0, p1);
	}
	
	public void setDate(int p0, Date p1) throws SQLException {
		ps.setDate(p0, p1);
	}
	
	public void setDate(int p0, Date p1, Calendar p2) 
	throws SQLException {
		ps.setDate(p0, p1, p2);
	}
	
	public void setTimestamp(int p0, Timestamp p1, Calendar p2) 
	throws SQLException {
		ps.setTimestamp(p0, p1, p2);
	}
	
	public void setTimestamp(int p0, Timestamp p1) 
	throws SQLException {
		ps.setTimestamp(p0, p1);
	}
	
	public void setAsciiStream(int p0, InputStream p1, int p2) 
	throws SQLException {
		ps.setAsciiStream(p0, p1, p2);
	}
	
	/**
	 * @deprecated
	 */
	public void setUnicodeStream(int p0, InputStream p1, int p2) 
	throws SQLException {
		ps.setUnicodeStream(p0, p1, p2);
	}
	
	public void setBinaryStream(int p0, InputStream p1, int p2) 
	throws SQLException {
		ps.setBinaryStream(p0, p1, p2);
	}
	
	public void clearParameters() throws SQLException {
		ps.clearParameters();
	}
	
	public void setObject(int p0, Object p1, int p2, int p3) 
	throws SQLException {
		Object enCodeX = Util.encodeObject(p1, this.charSet, this.encodeType);
			
		ps.setObject(p0, enCodeX, p2, p3);
	}
	
	public void setObject(int p0, Object p1, int p2) 
	throws SQLException {
		Object enCodeX = Util.encodeObject(p1, this.charSet, this.encodeType);
			
		ps.setObject(p0, enCodeX, p2);
	}
	
	/**
	 * 重载的方法
	 * @param p0
	 * @param p1
	 * @throws SQLException
	 */
	public void setObject(int p0, Object p1) 
	throws SQLException {
		
		Object enCodeX = Util.encodeObject(p1, this.charSet, this.encodeType);
			
		ps.setObject(p0, enCodeX);
	}
	
	public void addBatch() throws SQLException {
		ps.addBatch();
	}
	
	public void setCharacterStream(int p0, Reader p1, int p2) 
	throws SQLException {
		ps.setCharacterStream(p0, p1, p2);
	}
	
	public void setRef(int p0, Ref p1) throws SQLException {
		ps.setRef(p0, p1);
	}
	
	public void setBlob(int p0, Blob p1) throws SQLException {
		ps.setBlob(p0, p1);
	}
	
	public void setClob(int p0, Clob p1) throws SQLException {
		ps.setClob(p0, p1);
	}
	
	public void setArray(int p0, Array p1) throws SQLException {
		ps.setArray(p0, p1);
	}
	
	public ResultSetMetaData getMetaData() throws SQLException {
		return ps.getMetaData();
	}
	
	public ParameterMetaData getParameterMetaData() 
	throws SQLException {
		return ps.getParameterMetaData();
	}
	
	public void close() throws SQLException {
		ps.close();
	}
	
	public boolean execute(String p0, int p1) 
	throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		
		return ps.execute(enCodeX, p1);
	}
	
	public boolean execute(String p0) throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		
		return ps.execute(enCodeX);
	}
	
	public boolean execute(String p0, String[] p1) 
	throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		
		return ps.execute(enCodeX, p1);
	}
	
	public boolean execute(String p0, int[] p1) 
	throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		
		return ps.execute(enCodeX, p1);
	}
	
	public ResultSet executeQuery(String p0) 
	throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		
		return ps.executeQuery(enCodeX);
	}
	
	public int executeUpdate(String p0) 
	throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		
		return ps.executeUpdate(enCodeX);
	}
	
	public int executeUpdate(String p0, String[] p1) 
	throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		
		return ps.executeUpdate(enCodeX, p1);
	}
	
	public int executeUpdate(String p0, int[] p1) 
	throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		
		return ps.executeUpdate(enCodeX, p1);
	}
	
	public int executeUpdate(String p0, int p1) 
	throws SQLException {
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		
		return ps.executeUpdate(enCodeX, p1);
	}
	
	public void addBatch(String p0) throws SQLException {
		ps.addBatch(p0);
	}
	
	public int getMaxFieldSize() throws SQLException {
		return ps.getMaxFieldSize();
	}
	
	public void setMaxFieldSize(int p0) throws SQLException {
		ps.setMaxFieldSize(p0);
	}
	
	public int getMaxRows() throws SQLException {
		return ps.getMaxRows();
	}
	
	public void setMaxRows(int p0) throws SQLException {
		ps.setMaxRows(p0);
	}
	
	public void setEscapeProcessing(boolean p0) throws SQLException {
		ps.setEscapeProcessing(p0);
	}
	
	public int getQueryTimeout() throws SQLException {
		return ps.getQueryTimeout();
	}
	
	public void setQueryTimeout(int p0) throws SQLException {
		ps.setQueryTimeout(p0);
	}
	
	public void cancel() throws SQLException {
		ps.cancel();
	}
	
	public SQLWarning getWarnings() throws SQLException {
		return ps.getWarnings();
	}
	
	public void clearWarnings() throws SQLException {
		ps.clearWarnings();
	}
	
	public void setCursorName(String p0) throws SQLException {
		ps.setCursorName(p0);
	}
	
	public ResultSet getResultSet() throws SQLException {
		ResultSet rs = ps.getResultSet();

		return new CustomResultSet(rs,this.charSet,this.encodeType);
	}
	
	public int getUpdateCount() throws SQLException {
		return ps.getUpdateCount();
	}
	
	public boolean getMoreResults(int p0) throws SQLException {
		return ps.getMoreResults(p0);
	}
	
	public boolean getMoreResults() throws SQLException {
		return ps.getMoreResults();
	}
	
	public void setFetchDirection(int p0) throws SQLException {
		ps.setFetchDirection(p0);
	}
	
	public int getFetchDirection() throws SQLException {
		return ps.getFetchDirection();
	}
	
	public void setFetchSize(int p0) throws SQLException {
		ps.setFetchSize(p0);
	}
	
	public int getFetchSize() throws SQLException {
		return ps.getFetchSize();
	}
	
	public int getResultSetConcurrency() throws SQLException {
		return ps.getResultSetConcurrency();
	}
	
	public int getResultSetType() throws SQLException {
		return ps.getResultSetType();
	}
	
	public void clearBatch() throws SQLException {
		ps.clearBatch();
	}
	
	public int[] executeBatch() throws SQLException {
		int[] ii_return = null;
		try{
			ii_return = ps.executeBatch();
		}catch(SQLException ex){
			ex.getNextException().printStackTrace();
			throw new SQLException("" + ex.getNextException());
		}
		
		return ii_return;
	}
	
	public Connection getConnection() throws SQLException {
		return ps.getConnection();
	}
	
	public ResultSet getGeneratedKeys() throws SQLException {
		return ps.getGeneratedKeys();
	}
	
	public int getResultSetHoldability() throws SQLException {
		return ps.getResultSetHoldability();
	}

	/** 原来的PreparedStatement */
	protected PreparedStatement getBasePreparedStatement() {
		return ps;
	}

	/** 原来的PreparedStatement */
	protected void setBasePreparedStatement(PreparedStatement ps) {
		this.ps = ps;
	}

	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public void setPoolable(boolean poolable) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public boolean isPoolable() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public Object unwrap(Class iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isWrapperFor(Class iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public void setRowId(int parameterIndex, RowId x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNString(int parameterIndex, String value)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNCharacterStream(int parameterIndex, Reader value,
			long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNClob(int parameterIndex, NClob value) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setClob(int parameterIndex, Reader reader, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setBlob(int parameterIndex, InputStream inputStream, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNClob(int parameterIndex, Reader reader, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setSQLXML(int parameterIndex, SQLXML xmlObject)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setAsciiStream(int parameterIndex, InputStream x, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setBinaryStream(int parameterIndex, InputStream x, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setCharacterStream(int parameterIndex, Reader reader,
			long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setAsciiStream(int parameterIndex, InputStream x)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setBinaryStream(int parameterIndex, InputStream x)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setCharacterStream(int parameterIndex, Reader reader)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNCharacterStream(int parameterIndex, Reader value)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setClob(int parameterIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setBlob(int parameterIndex, InputStream inputStream)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNClob(int parameterIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
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
