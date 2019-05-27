/**
 * <p>Title: CallableStatement的转码类</p>
 * <p>Description: 转码层</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 北京易初电子技术有限公司</p>
 * @author 黄瑞文
 * @version 1.0
 * 2006-11-22
 */

package com.irdstudio.smcenter.core.assembly.imdbcp.encode;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Map;
import java.sql.Ref;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Array;
import java.util.Calendar;
import java.net.URL;
import java.io.InputStream;
import java.io.Reader;
import java.sql.NClob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.ParameterMetaData;
import java.sql.RowId;
import java.sql.SQLWarning;
import java.sql.Connection;
import java.sql.SQLXML;

/**
 * 转码层，CallableStatement的转码类
 * @author hrw
 * 2006-11-26
 */
public class CustomCallableStatement implements CallableStatement {

	/** 原来的CallableStatement */
	protected CallableStatement cs;
	/** 编码 */
	protected String charSet;
	/** 编码方式, 0 不转码，1 转码，2 优化转码 */
	protected int encodeType;
	
	/**
	 * 构造函数
	 * @param _cs 原来的CallableStatement
	 * @param charSet 编码
	 * @param encodeType 编码方式, 0 不转码，1 转码，2 优化转码
	 */
	public CustomCallableStatement(CallableStatement _cs, String charSet, int encodeType) {
		this.cs = _cs;
		this.charSet = charSet;
		this.encodeType = encodeType;
	}
	
	public void registerOutParameter(int parameterIndex, int sqlType) 
	throws SQLException {
		cs.registerOutParameter(parameterIndex,sqlType);
	}
	
	public void registerOutParameter(int parameterIndex, int sqlType, int scale) 
	throws SQLException {
		cs.registerOutParameter(parameterIndex,sqlType,scale);
	}
	
	public boolean wasNull() throws SQLException {
		return cs.wasNull();
	}
	
	public String getString(int parameterIndex) throws SQLException {
		
		String s = cs.getString(parameterIndex);
		
		return Util.decodeString(s, this.charSet, this.encodeType);
	}
	
	public boolean getBoolean(int parameterIndex) throws SQLException {
		return cs.getBoolean(parameterIndex);
	}
	
	public byte getByte(int parameterIndex) throws SQLException {
		return cs.getByte(parameterIndex);
	}
	
	public short getShort(int parameterIndex) throws SQLException {
		return cs.getShort(parameterIndex);
	}
	
	public int getInt(int parameterIndex) throws SQLException {
		return cs.getInt(parameterIndex);
	}
	
	public long getLong(int parameterIndex) throws SQLException {
		return cs.getLong(parameterIndex);
	}
	
	public float getFloat(int parameterIndex) throws SQLException {
		return cs.getFloat(parameterIndex);
	}
	
	public double getDouble(int parameterIndex) throws SQLException {
		return cs.getDouble(parameterIndex);
	}
	
	public BigDecimal getBigDecimal(int parameterIndex, int scale) 
	throws SQLException {
		return cs.getBigDecimal(parameterIndex);
	}
	
	public byte[] getBytes(int parameterIndex) throws SQLException {
		return cs.getBytes(parameterIndex);
	}
	
	public Date getDate(int parameterIndex) throws SQLException {
		return cs.getDate(parameterIndex);
	}
	
	public Time getTime(int parameterIndex) throws SQLException {
		return cs.getTime(parameterIndex);
	}
	
	public Timestamp getTimestamp(int parameterIndex) throws SQLException {
		return cs.getTimestamp(parameterIndex);
	}
	
	public Object getObject(int parameterIndex) throws SQLException {
		Object o = cs.getObject(parameterIndex);

		return Util.decodeObject(o, this.charSet, this.encodeType);
		
	}
	
	public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
		return cs.getBigDecimal(parameterIndex);
	}
	
	public Object getObject(int i, Map map) throws SQLException {
		Object o = cs.getObject(i,map);
		
		return Util.decodeObject(o, this.charSet, this.encodeType);
	}
	
	public Ref getRef(int i) throws SQLException {
		return cs.getRef(i);
	}
	
	public Blob getBlob(int i) throws SQLException {
		return cs.getBlob(i);
	}
	
	public Clob getClob(int i) throws SQLException {
		return cs.getClob(i);
	}
	
	public Array getArray(int i) throws SQLException {
		return cs.getArray(i);
	}
	
	public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
		return cs.getDate(parameterIndex,cal);
	}
	
	public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
		return cs.getTime(parameterIndex,cal);
	}
	
	public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SQLException {
		return cs.getTimestamp(parameterIndex,cal);
	}
	
	public void registerOutParameter(int paramIndex, int sqlType, String typeName) 
	throws SQLException {
		cs.registerOutParameter(paramIndex,sqlType,typeName);
	}
	
	public void registerOutParameter(String parameterName, int sqlType) 
	throws SQLException {
		cs.registerOutParameter(parameterName,sqlType);
	}
	
	public void registerOutParameter(String parameterName, int sqlType, int scale) 
	throws SQLException {
		cs.registerOutParameter(parameterName,sqlType,scale);
	}
	
	public void registerOutParameter(String parameterName, int sqlType, String typeName) 
	throws SQLException {
		cs.registerOutParameter(parameterName,sqlType,typeName);
	}
	
	public URL getURL(int parameterIndex) throws SQLException {
		return cs.getURL(parameterIndex);
	}
	
	public void setURL(String parameterName, URL val) throws SQLException {
		cs.setURL(parameterName,val);
	}
	
	public void setNull(String parameterName, int sqlType) throws SQLException {
		cs.setNull(parameterName,sqlType);
	}
	
	public void setBoolean(String parameterName, boolean x) throws SQLException {
		cs.setBoolean(parameterName,x);
	}
	
	public void setByte(String parameterName, byte x) throws SQLException {
		cs.setByte(parameterName,x);
	}
	
	public void setShort(String parameterName, short x) throws SQLException {
		cs.setShort(parameterName,x);
	}
	
	public void setInt(String parameterName, int x) throws SQLException {
		cs.setInt(parameterName,x);
	}
	
	public void setLong(String parameterName, long x) throws SQLException {
		cs.setLong(parameterName,x);
	}
	
	public void setFloat(String parameterName, float x) throws SQLException {
		cs.setFloat(parameterName,x);
	}
	
	public void setDouble(String parameterName, double x) throws SQLException {
		cs.setDouble(parameterName,x);
	}
	
	public void setBigDecimal(String parameterName, BigDecimal x) 
	throws SQLException {
		cs.setBigDecimal(parameterName,x);
	}
	
	public void setString(String parameterName, String x) throws SQLException {
		String enCodeX = Util.encodeString(x, this.charSet, this.encodeType);
		
		cs.setString(parameterName,enCodeX);
	}
	
	public void setBytes(String parameterName, byte[] x) throws SQLException {
		cs.setBytes(parameterName,x);
	}
	
	public void setDate(String parameterName, Date x) throws SQLException {
		cs.setDate(parameterName,x);
	}
	
	public void setTime(String parameterName, Time x) throws SQLException {
		cs.setTime(parameterName,x);
	}
	
	public void setTimestamp(String parameterName, Timestamp x) 
	throws SQLException {
		cs.setTimestamp(parameterName,x);
	}
	
	public void setAsciiStream(String parameterName, InputStream x, int length) 
	throws SQLException {
		cs.setAsciiStream(parameterName,x,length);
	}
	
	public void setBinaryStream(String parameterName, InputStream x, int length) 
	throws SQLException {
		cs.setBinaryStream(parameterName,x,length);
	}
	
	public void setObject(String parameterName, Object x, int targetSqlType, int scale) 
	throws SQLException {
		Object enCodeX = Util.encodeObject(x, this.charSet, this.encodeType);
		
		cs.setObject(parameterName,enCodeX,targetSqlType,scale);
	}
	
	public void setObject(String parameterName, Object x, int targetSqlType) 
	throws SQLException {
		Object enCodeX = Util.encodeObject(x, this.charSet, this.encodeType);
		
		cs.setObject(parameterName,enCodeX,targetSqlType);
	}
	
	
	public void setObject(String parameterName, Object x) throws SQLException {
		Object enCodeX = Util.encodeObject(x, this.charSet, this.encodeType);
					
		cs.setObject(parameterName,enCodeX);
	}
	
	public void setCharacterStream(String parameterName, Reader reader, int length) 
	throws SQLException {
		cs.setCharacterStream(parameterName,reader,length);
	}
	
	public void setDate(String parameterName, Date x, Calendar cal) 
	throws SQLException {
		cs.setDate(parameterName,x,cal);
	}
	
	public void setTime(String parameterName, Time x, Calendar cal) 
	throws SQLException {
		cs.setTime(parameterName,x,cal);
	}
	
	public void setTimestamp(String parameterName, Timestamp x, Calendar cal)
	throws SQLException {
		cs.setTimestamp(parameterName,x,cal);
	}
	
	public void setNull(String parameterName, int sqlType, String typeName) 
	throws SQLException {
		cs.setNull(parameterName,sqlType,typeName);
	}
	
	public String getString(String parameterName) throws SQLException {

		String s = cs.getString(parameterName);
		
		return Util.decodeString(s, this.charSet, this.encodeType);
	}
	
	public boolean getBoolean(String parameterName) throws SQLException {
		return cs.getBoolean(parameterName);
	}
	
	public byte getByte(String parameterName) throws SQLException {
		return cs.getByte(parameterName);
	}
	
	public short getShort(String parameterName) throws SQLException {
		return cs.getShort(parameterName);
	}
	
	public int getInt(String parameterName) throws SQLException {
		return cs.getInt(parameterName);
	}
	
	public long getLong(String parameterName) throws SQLException {
		return cs.getLong(parameterName);
	}
	
	public float getFloat(String parameterName) throws SQLException {
		return cs.getFloat(parameterName);
	}
	
	public double getDouble(String parameterName) throws SQLException {
		return cs.getDouble(parameterName);
	}
	
	public byte[] getBytes(String parameterName) throws SQLException {
		return cs.getBytes(parameterName);
	}
	
	public Date getDate(String parameterName) throws SQLException {
		return cs.getDate(parameterName);
	}
	
	public Time getTime(String parameterName) throws SQLException {
		return cs.getTime(parameterName);
	}
	
	public Timestamp getTimestamp(String parameterName) 
	throws SQLException {
		return cs.getTimestamp(parameterName);
	}
	
	public Object getObject(String parameterName) throws SQLException {
		Object o = cs.getObject(parameterName);
		
		return Util.decodeObject(o, this.charSet, this.encodeType);
	}
	
	public BigDecimal getBigDecimal(String parameterName) 
	throws SQLException {
		return cs.getBigDecimal(parameterName);
	}

	public Object getObject(String parameterName, Map map)
	throws SQLException {
		Object o = cs.getObject(parameterName,map);

		return Util.decodeObject(o, this.charSet, this.encodeType);
	}
	
	public Ref getRef(String parameterName) throws SQLException {
		return cs.getRef(parameterName);
	}
	
	public Blob getBlob(String parameterName) throws SQLException {
		return cs.getBlob(parameterName);
	}
	
	public Clob getClob(String parameterName) throws SQLException {
		return cs.getClob(parameterName);
	}
	
	public Array getArray(String parameterName) throws SQLException {
		return cs.getArray(parameterName);
	}
	
	public Date getDate(String parameterName, Calendar cal) 
	throws SQLException {
		return cs.getDate(parameterName,cal);
	}
	public Time getTime(String parameterName, Calendar cal) 
	throws SQLException {
		return cs.getTime(parameterName,cal);
	}
	
	public Timestamp getTimestamp(String parameterName, Calendar cal) 
	throws SQLException {
		return cs.getTimestamp(parameterName,cal);
	}
	
	public URL getURL(String parameterName) throws SQLException {
		return cs.getURL(parameterName);
	}
	
	public ResultSet executeQuery() throws SQLException {
		ResultSet _rs = cs.executeQuery();
		
		return new CustomResultSet(_rs,this.charSet,this.encodeType);
	}
	
	public int executeUpdate() throws SQLException {
		return cs.executeUpdate();
	}
	
	public void setNull(int parameterIndex, int sqlType) 
	throws SQLException {
		cs.setNull(parameterIndex,sqlType);
	}
	
	public void setBoolean(int parameterIndex, boolean x) 
	throws SQLException {
		cs.setBoolean(parameterIndex,x);
	}
	
	public void setByte(int parameterIndex, byte x) throws SQLException {
		cs.setByte(parameterIndex,x);
	}
	
	public void setShort(int parameterIndex, short x) throws SQLException {
		cs.setShort(parameterIndex,x);
	}
	
	public void setInt(int parameterIndex, int x) throws SQLException {
		cs.setInt(parameterIndex,x);
	}
	
	public void setLong(int parameterIndex, long x) throws SQLException {
		cs.setLong(parameterIndex,x);
	}
	
	public void setFloat(int parameterIndex, float x) throws SQLException {
		cs.setFloat(parameterIndex,x);
	}
	
	public void setDouble(int parameterIndex, double x) throws SQLException {
		cs.setDouble(parameterIndex,x);
	}
	
	public void setBigDecimal(int parameterIndex, BigDecimal x) 
	throws SQLException {
		cs.setBigDecimal(parameterIndex,x);
	}
	
	public void setString(int parameterIndex, String x) throws SQLException {
		String enCodeX = Util.encodeString(x, this.charSet, this.encodeType);
		
		cs.setString(parameterIndex,enCodeX);
	}
	
	public void setBytes(int parameterIndex, byte[] x) throws SQLException {
		cs.setBytes(parameterIndex,x);
	}
	
	public void setDate(int parameterIndex, Date x) throws SQLException {
		cs.setDate(parameterIndex,x);
	}
	
	public void setTime(int parameterIndex, Time x) throws SQLException {
		cs.setTime(parameterIndex,x);
	}
	
	public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
		cs.setTimestamp(parameterIndex,x);
	}
	
	public void setAsciiStream(int parameterIndex, InputStream x, int length) 
	throws SQLException {
		cs.setAsciiStream(parameterIndex,x,length);
	}
	
	public void setUnicodeStream(int parameterIndex, InputStream x, int length) 
	throws SQLException {
		cs.setUnicodeStream(parameterIndex,x,length);
	}
	
	public void setBinaryStream(int parameterIndex, InputStream x, int length) 
	throws SQLException {
		cs.setBinaryStream(parameterIndex,x,length);
	}
	
	public void clearParameters() throws SQLException {
		cs.clearParameters();
	}
	
	public void setObject(int parameterIndex, Object x, int targetSqlType, int scale) 
	throws SQLException {
		Object enCodeX = Util.encodeObject(x, this.charSet, this.encodeType);
			
		cs.setObject(parameterIndex,enCodeX,targetSqlType,scale);
	}
	
	public void setObject(int parameterIndex, Object x, int targetSqlType) 
	throws SQLException {
		Object enCodeX = Util.encodeObject(x, this.charSet, this.encodeType);
					
		cs.setObject(parameterIndex,enCodeX,targetSqlType);
	}
	
	public void setObject(int parameterIndex, Object x) throws SQLException {
		Object enCodeX = Util.encodeObject(x, this.charSet, this.encodeType);
			
		cs.setObject(parameterIndex,enCodeX);
	}
	
	public boolean execute() throws SQLException {
		return cs.execute();
	}
	
	public void addBatch() throws SQLException {
		cs.addBatch();
	}
	
	public void setCharacterStream(int parameterIndex, Reader reader, int length) 
	throws SQLException {
		cs.setCharacterStream(parameterIndex,reader,length);
	}
	
	public void setRef(int i, Ref x) throws SQLException {
		cs.setRef(i,x);
	}
	
	public void setBlob(int i, Blob x) throws SQLException {
		cs.setBlob(i,x);
	}
	
	public void setClob(int i, Clob x) throws SQLException {
		cs.setClob(i,x);
	}
	
	public void setArray(int i, Array x) throws SQLException {
		cs.setArray(i,x);
	}
	
	public ResultSetMetaData getMetaData() throws SQLException {
		return cs.getMetaData();
	}
	
	public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
		cs.setDate(parameterIndex,x,cal);
	}
	
	public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
		cs.setTime(parameterIndex,x,cal);
	}
	
	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) 
	throws SQLException {
		cs.setTimestamp(parameterIndex,x,cal);
	}
	
	public void setNull(int paramIndex, int sqlType, String typeName) 
	throws SQLException {
		cs.setNull(paramIndex,sqlType,typeName);
	}  
	
	public void setURL(int parameterIndex, URL x) throws SQLException {
		cs.setURL(parameterIndex,x);
	}
	
	public ParameterMetaData getParameterMetaData() throws SQLException {
		return cs.getParameterMetaData();
	}
	
	public ResultSet executeQuery(String sql) throws SQLException {
		String enCodeSql = Util.encodeString(sql, this.charSet, this.encodeType);
		ResultSet _rs = cs.executeQuery(enCodeSql);
		
		return new CustomResultSet(_rs,this.charSet,this.encodeType);
	}
	
	public int executeUpdate(String sql) throws SQLException {
		String enCodeSql = Util.encodeString(sql, this.charSet, this.encodeType);
		
		return cs.executeUpdate(enCodeSql);
	}
	
	public void close() throws SQLException {
		cs.close();
	}
	
	public int getMaxFieldSize() throws SQLException {
		return cs.getMaxFieldSize();
	}
	
	public void setMaxFieldSize(int max) throws SQLException {
		cs.setMaxFieldSize(max);
	}
	
	public int getMaxRows() throws SQLException {
		return cs.getMaxRows();
	}
	
	public void setMaxRows(int max) throws SQLException {
		cs.setMaxRows(max);
	}
	
	public void setEscapeProcessing(boolean enable) throws SQLException {
		cs.setEscapeProcessing(enable);
	}
	
	public int getQueryTimeout() throws SQLException {
		return cs.getQueryTimeout();
	}
	
	public void setQueryTimeout(int seconds) throws SQLException {
		cs.setQueryTimeout(seconds);
	}
	
	public void cancel() throws SQLException {
		cs.cancel();
	}
	
	public SQLWarning getWarnings() throws SQLException {
		return cs.getWarnings();
	}
	
	public void clearWarnings() throws SQLException {
		cs.clearWarnings();
	}
	
	public void setCursorName(String name) throws SQLException {
		cs.setCursorName(name);
	}
	
	public boolean execute(String sql) throws SQLException {
		String enCodeSql = Util.encodeString(sql, this.charSet, this.encodeType);
		
		return cs.execute(enCodeSql);
	}
	
	public ResultSet getResultSet() throws SQLException {
		ResultSet rs = cs.getResultSet();

		return new CustomResultSet(rs,this.charSet,this.encodeType);
	}
	
	public int getUpdateCount() throws SQLException {
		return cs.getUpdateCount();
	}
	
	public boolean getMoreResults() throws SQLException {
		return cs.getMoreResults();
	}
	
	public void setFetchDirection(int direction) throws SQLException {
		cs.setFetchDirection(direction);
	}
	
	public int getFetchDirection() throws SQLException {
		return cs.getFetchDirection();
	}
	
	public void setFetchSize(int rows) throws SQLException {
		cs.setFetchSize(rows);
	}
	
	public int getFetchSize() throws SQLException {
		return cs.getFetchSize();
	}
	
	public int getResultSetConcurrency() throws SQLException {
		return cs.getResultSetConcurrency();
	}
	
	public int getResultSetType() throws SQLException {
		return cs.getResultSetType();
	}
	
	public void addBatch(String sql) throws SQLException {
		String enCodeSql = Util.encodeString(sql, this.charSet, this.encodeType);
		
		cs.addBatch(enCodeSql);
	}
	
	public void clearBatch() throws SQLException {
		cs.clearBatch();
	}
	
	public int[] executeBatch() throws SQLException {
		return cs.executeBatch();
	}
	
	public Connection getConnection() throws SQLException {
		return cs.getConnection();
	}
	
	public boolean getMoreResults(int current) throws SQLException {
		return cs.getMoreResults(current);
	}
	
	public ResultSet getGeneratedKeys() throws SQLException {
		ResultSet rs = cs.getGeneratedKeys();
		
		return new CustomResultSet(rs,this.charSet,this.encodeType);
	}
	
	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		String enCodeSql = Util.encodeString(sql, this.charSet, this.encodeType);
		
		return cs.executeUpdate(enCodeSql,autoGeneratedKeys);
	}
	
	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		String enCodeSql = Util.encodeString(sql, this.charSet, this.encodeType);
				
		return cs.executeUpdate(enCodeSql,columnIndexes);
	}
	
	public int executeUpdate(String sql, String[] columnNames) throws SQLException {
		String enCodeSql = Util.encodeString(sql, this.charSet, this.encodeType);
		
		return cs.executeUpdate(enCodeSql,columnNames);
	}
	
	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
		String enCodeSql = Util.encodeString(sql, this.charSet, this.encodeType);
		
		return cs.execute(enCodeSql,autoGeneratedKeys);
	}
	
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		String enCodeSql = Util.encodeString(sql, this.charSet, this.encodeType);
		
		return cs.execute(enCodeSql,columnIndexes);
	}
	
	public boolean execute(String sql, String[] columnNames) throws SQLException {
		String enCodeSql = Util.encodeString(sql, this.charSet, this.encodeType);
		
		return cs.execute(enCodeSql,columnNames);
	}
	
	public int getResultSetHoldability() throws SQLException {
		return cs.getResultSetHoldability();
	}

	/** 原来的CallableStatement */
	protected CallableStatement getBaseCallableStatement() {
		return this.cs ;
	}
	/** 原来的CallableStatement */
	protected void setBaseCallableStatement(CallableStatement cs) {
		this.cs = cs;
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

	public RowId getRowId(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public RowId getRowId(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setRowId(String parameterName, RowId x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNString(String parameterName, String value)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNCharacterStream(String parameterName, Reader value,
			long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNClob(String parameterName, NClob value) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setClob(String parameterName, Reader reader, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setBlob(String parameterName, InputStream inputStream,
			long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNClob(String parameterName, Reader reader, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public NClob getNClob(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public NClob getNClob(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setSQLXML(String parameterName, SQLXML xmlObject)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public SQLXML getSQLXML(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public SQLXML getSQLXML(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNString(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNString(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Reader getNCharacterStream(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Reader getNCharacterStream(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Reader getCharacterStream(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Reader getCharacterStream(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setBlob(String parameterName, Blob x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setClob(String parameterName, Clob x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setAsciiStream(String parameterName, InputStream x, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setBinaryStream(String parameterName, InputStream x, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setCharacterStream(String parameterName, Reader reader,
			long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setAsciiStream(String parameterName, InputStream x)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setBinaryStream(String parameterName, InputStream x)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setCharacterStream(String parameterName, Reader reader)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNCharacterStream(String parameterName, Reader value)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setClob(String parameterName, Reader reader)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setBlob(String parameterName, InputStream inputStream)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNClob(String parameterName, Reader reader)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void closeOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public boolean isCloseOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T getObject(String parameterName, Class<T> type) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}