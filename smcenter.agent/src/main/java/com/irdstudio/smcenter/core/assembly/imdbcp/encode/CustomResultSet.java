/**
 * <p>Title: ResultSet的转码类</p>
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
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

/**
 * 转码层，ResultSet的转码类
 * 
 * @author hrw 2006-11-26
 */
public class CustomResultSet implements ResultSet {

	/** 原来的ResultSet */
	protected ResultSet rs;
	/** 编码 */
	protected String charSet;
	/** 编码方式, 0 不转码，1 转码，2 优化转码 */
	protected int encodeType;

	/**
	 * 构造函数
	 * 
	 * @param _rs
	 *            原来的CallableStatement
	 * @param charSet
	 *            编码
	 * @param encodeType
	 *            编码方式, 0 不转码，1 转码，2 优化转码
	 */
	public CustomResultSet(ResultSet _rs, String charSet, int encodeType) {
		this.rs = _rs;
		this.charSet = charSet;
		this.encodeType = encodeType;
	}

	public Object getObject(String p0, Map p1) throws SQLException {
		Object o = rs.getObject(p0, p1);

		return Util.decodeObject(o, this.charSet, this.encodeType);
	}

	public Object getObject(int p0, Map p1) throws SQLException {
		Object o = rs.getObject(p0, p1);

		return Util.decodeObject(o, this.charSet, this.encodeType);
	}

	/**
	 * 重载过的方法
	 * 
	 * @param p0
	 * @return
	 * @throws SQLException
	 */
	public Object getObject(String p0) throws SQLException {
		Object o = rs.getObject(p0);

		return Util.decodeObject(o, this.charSet, this.encodeType);
	}

	/**
	 * 重载过的方法
	 * 
	 * @param p0
	 * @return
	 * @throws SQLException
	 */
	public Object getObject(int p0) throws SQLException {
		Object o = rs.getObject(p0);

		return Util.decodeObject(o, this.charSet, this.encodeType);
	}

	public boolean getBoolean(String p0) throws SQLException {
		return rs.getBoolean(p0);
	}

	public boolean getBoolean(int p0) throws SQLException {
		return rs.getBoolean(p0);
	}

	public byte getByte(String p0) throws SQLException {
		return rs.getByte(p0);
	}

	public byte getByte(int p0) throws SQLException {
		return rs.getByte(p0);
	}

	public short getShort(int p0) throws SQLException {
		return rs.getShort(p0);
	}

	public short getShort(String p0) throws SQLException {
		return rs.getShort(p0);
	}

	public int getInt(int p0) throws SQLException {
		return rs.getInt(p0);
	}

	public int getInt(String p0) throws SQLException {
		return rs.getInt(p0);
	}

	public long getLong(String p0) throws SQLException {
		return rs.getLong(p0);
	}

	public long getLong(int p0) throws SQLException {
		return rs.getLong(p0);
	}

	public float getFloat(String p0) throws SQLException {
		return rs.getFloat(p0);
	}

	public float getFloat(int p0) throws SQLException {
		return rs.getFloat(p0);
	}

	public double getDouble(int p0) throws SQLException {
		return rs.getDouble(p0);
	}

	public double getDouble(String p0) throws SQLException {
		return rs.getDouble(p0);
	}

	public byte[] getBytes(int p0) throws SQLException {
		return rs.getBytes(p0);
	}

	public byte[] getBytes(String p0) throws SQLException {
		return rs.getBytes(p0);
	}

	public boolean next() throws SQLException {
		return rs.next();
	}

	public URL getURL(String p0) throws SQLException {
		return rs.getURL(p0);
	}

	public URL getURL(int p0) throws SQLException {
		return rs.getURL(p0);
	}

	public int getType() throws SQLException {
		return rs.getType();
	}

	public boolean previous() throws SQLException {
		return rs.previous();
	}

	public void close() throws SQLException {
		rs.close();
	}

	/**
	 * 重载过的方法
	 * 
	 * @param p0
	 * @return
	 * @throws SQLException
	 */
	public String getString(String p0) throws SQLException {
		String s = rs.getString(p0);

		return Util.decodeString(s, this.charSet, this.encodeType);
	}

	/**
	 * 重载过的方法
	 * 
	 * @param p0
	 * @return
	 * @throws SQLException
	 */
	public String getString(int p0) throws SQLException {
		String s = rs.getString(p0);

		return Util.decodeString(s, this.charSet, this.encodeType);
	}

	public Ref getRef(int p0) throws SQLException {
		return rs.getRef(p0);
	}

	public Ref getRef(String p0) throws SQLException {
		return rs.getRef(p0);
	}

	public Time getTime(String p0, java.util.Calendar p1) throws SQLException {
		return rs.getTime(p0, p1);
	}

	public Time getTime(int p0) throws SQLException {
		return rs.getTime(p0);
	}

	public Time getTime(String p0) throws SQLException {
		return rs.getTime(p0);
	}

	public Time getTime(int p0, java.util.Calendar p1) throws SQLException {
		return rs.getTime(p0, p1);
	}

	public Date getDate(String p0, java.util.Calendar p1) throws SQLException {
		return rs.getDate(p0, p1);
	}

	public Date getDate(String p0) throws SQLException {
		return rs.getDate(p0);
	}

	public Date getDate(int p0) throws SQLException {
		return rs.getDate(p0);
	}

	public Date getDate(int p0, java.util.Calendar p1) throws SQLException {
		return rs.getDate(p0, p1);
	}

	public boolean wasNull() throws SQLException {
		return rs.wasNull();
	}

	public java.math.BigDecimal getBigDecimal(String p0) throws SQLException {
		return rs.getBigDecimal(p0);
	}

	/**
	 * @deprecated
	 */
	public java.math.BigDecimal getBigDecimal(String p0, int p1)
			throws SQLException {
		return rs.getBigDecimal(p0, p1);
	}

	/**
	 * @deprecated
	 */
	public java.math.BigDecimal getBigDecimal(int p0, int p1)
			throws SQLException {
		return rs.getBigDecimal(p0, p1);
	}

	public java.math.BigDecimal getBigDecimal(int p0) throws SQLException {
		return rs.getBigDecimal(p0);
	}

	public Timestamp getTimestamp(int p0) throws SQLException {
		return rs.getTimestamp(p0);
	}

	public Timestamp getTimestamp(int p0, Calendar p1) throws SQLException {
		return rs.getTimestamp(p0, p1);
	}

	public Timestamp getTimestamp(String p0, Calendar p1) throws SQLException {
		return rs.getTimestamp(p0, p1);
	}

	public Timestamp getTimestamp(String p0) throws SQLException {
		return rs.getTimestamp(p0);
	}

	public java.io.InputStream getAsciiStream(String p0) throws SQLException {
		return rs.getAsciiStream(p0);
	}

	public java.io.InputStream getAsciiStream(int p0) throws SQLException {
		return rs.getAsciiStream(p0);
	}

	/**
	 * @deprecated
	 */
	public java.io.InputStream getUnicodeStream(String p0) throws SQLException {
		return rs.getUnicodeStream(p0);
	}

	/**
	 * @deprecated
	 */
	public java.io.InputStream getUnicodeStream(int p0) throws SQLException {
		return rs.getUnicodeStream(p0);
	}

	public java.io.InputStream getBinaryStream(String p0) throws SQLException {
		return rs.getBinaryStream(p0);
	}

	public java.io.InputStream getBinaryStream(int p0) throws SQLException {
		return rs.getBinaryStream(p0);
	}

	public SQLWarning getWarnings() throws SQLException {
		return rs.getWarnings();
	}

	public void clearWarnings() throws SQLException {
		rs.clearWarnings();
	}

	public String getCursorName() throws SQLException {
		return rs.getCursorName();
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		return rs.getMetaData();
	}

	public int findColumn(String p0) throws SQLException {
		return rs.findColumn(p0);
	}

	public java.io.Reader getCharacterStream(int p0) throws SQLException {
		return rs.getCharacterStream(p0);
	}

	public java.io.Reader getCharacterStream(String p0) throws SQLException {
		return rs.getCharacterStream(p0);
	}

	public boolean isBeforeFirst() throws SQLException {
		return rs.isBeforeFirst();
	}

	public boolean isAfterLast() throws SQLException {
		return rs.isAfterLast();
	}

	public boolean isFirst() throws SQLException {
		return rs.isFirst();
	}

	public boolean isLast() throws SQLException {
		return rs.isLast();
	}

	public void beforeFirst() throws SQLException {
		rs.beforeFirst();
	}

	public void afterLast() throws SQLException {
		rs.afterLast();
	}

	public boolean first() throws SQLException {
		return rs.first();
	}

	public boolean last() throws SQLException {
		return rs.last();
	}

	public int getRow() throws SQLException {
		return rs.getRow();
	}

	public boolean absolute(int p0) throws SQLException {
		return rs.absolute(p0);
	}

	public boolean relative(int p0) throws SQLException {
		return rs.relative(p0);
	}

	public void setFetchDirection(int p0) throws SQLException {
		rs.setFetchDirection(p0);
	}

	public int getFetchDirection() throws SQLException {
		return rs.getFetchDirection();
	}

	public void setFetchSize(int p0) throws SQLException {
		rs.setFetchSize(p0);
	}

	public int getFetchSize() throws SQLException {
		return rs.getFetchSize();
	}

	public int getConcurrency() throws SQLException {
		return rs.getConcurrency();
	}

	public boolean rowUpdated() throws SQLException {
		return rs.rowUpdated();
	}

	public boolean rowInserted() throws SQLException {
		return rs.rowInserted();
	}

	public boolean rowDeleted() throws SQLException {
		return rs.rowDeleted();
	}

	public void updateNull(String p0) throws SQLException {
		rs.updateNull(p0);
	}

	public void updateNull(int p0) throws SQLException {
		rs.updateNull(p0);
	}

	public void updateBoolean(String p0, boolean p1) throws SQLException {
		rs.updateBoolean(p0, p1);
	}

	public void updateBoolean(int p0, boolean p1) throws SQLException {
		rs.updateBoolean(p0, p1);
	}

	public void updateByte(int p0, byte p1) throws SQLException {
		rs.updateByte(p0, p1);
	}

	public void updateByte(String p0, byte p1) throws SQLException {
		rs.updateByte(p0, p1);
	}

	public void updateShort(String p0, short p1) throws SQLException {
		rs.updateShort(p0, p1);
	}

	public void updateShort(int p0, short p1) throws SQLException {
		rs.updateShort(p0, p1);
	}

	public void updateInt(int p0, int p1) throws SQLException {
		rs.updateInt(p0, p1);
	}

	public void updateInt(String p0, int p1) throws SQLException {
		rs.updateInt(p0, p1);
	}

	public void updateLong(int p0, long p1) throws SQLException {
		rs.updateLong(p0, p1);
	}

	public void updateLong(String p0, long p1) throws SQLException {
		rs.updateLong(p0, p1);
	}

	public void updateFloat(String p0, float p1) throws SQLException {
		rs.updateFloat(p0, p1);
	}

	public void updateFloat(int p0, float p1) throws SQLException {
		rs.updateFloat(p0, p1);
	}

	public void updateDouble(int p0, double p1) throws SQLException {
		rs.updateDouble(p0, p1);
	}

	public void updateDouble(String p0, double p1) throws SQLException {
		rs.updateDouble(p0, p1);
	}

	public void updateBigDecimal(String p0, java.math.BigDecimal p1)
			throws SQLException {
		rs.updateBigDecimal(p0, p1);
	}

	public void updateBigDecimal(int p0, java.math.BigDecimal p1)
			throws SQLException {
		rs.updateBigDecimal(p0, p1);
	}

	public void updateString(String p0, String p1) throws SQLException {
		String enCodeX = Util.encodeString(p1, this.charSet, this.encodeType);

		rs.updateString(p0, enCodeX);
	}

	public void updateString(int p0, String p1) throws SQLException {
		String enCodeX = Util.encodeString(p1, this.charSet, this.encodeType);

		rs.updateString(p0, enCodeX);

	}

	public void updateBytes(String p0, byte[] p1) throws SQLException {
		rs.updateBytes(p0, p1);
	}

	public void updateBytes(int p0, byte[] p1) throws SQLException {
		rs.updateBytes(p0, p1);
	}

	public void updateDate(String p0, Date p1) throws SQLException {
		rs.updateDate(p0, p1);
	}

	public void updateDate(int p0, Date p1) throws SQLException {
		rs.updateDate(p0, p1);
	}

	public void updateTime(String p0, Time p1) throws SQLException {
		rs.updateTime(p0, p1);
	}

	public void updateTime(int p0, Time p1) throws SQLException {
		rs.updateTime(p0, p1);
	}

	public void updateTimestamp(int p0, Timestamp p1) throws SQLException {
		rs.updateTimestamp(p0, p1);
	}

	public void updateTimestamp(String p0, Timestamp p1) throws SQLException {
		rs.updateTimestamp(p0, p1);
	}

	public void updateAsciiStream(String p0, java.io.InputStream p1, int p2)
			throws SQLException {
		rs.updateAsciiStream(p0, p1, p2);
	}

	public void updateAsciiStream(int p0, java.io.InputStream p1, int p2)
			throws SQLException {
		rs.updateAsciiStream(p0, p1, p2);
	}

	public void updateBinaryStream(String p0, java.io.InputStream p1, int p2)
			throws SQLException {
		rs.updateBinaryStream(p0, p1, p2);
	}

	public void updateBinaryStream(int p0, java.io.InputStream p1, int p2)
			throws SQLException {
		rs.updateBinaryStream(p0, p1, p2);
	}

	public void updateCharacterStream(String p0, java.io.Reader p1, int p2)
			throws SQLException {
		rs.updateCharacterStream(p0, p1, p2);
	}

	public void updateCharacterStream(int p0, java.io.Reader p1, int p2)
			throws SQLException {
		rs.updateCharacterStream(p0, p1, p2);
	}

	public void updateObject(int p0, Object p1, int p2) throws SQLException {
		Object enCodeX = Util.encodeObject(p1, this.charSet, this.encodeType);

		rs.updateObject(p0, enCodeX, p2);
	}

	public void updateObject(int p0, Object p1) throws SQLException {
		Object enCodeX = Util.encodeObject(p1, this.charSet, this.encodeType);

		rs.updateObject(p0, enCodeX);
	}

	public void updateObject(String p0, Object p1) throws SQLException {
		Object enCodeX = Util.encodeObject(p1, this.charSet, this.encodeType);

		rs.updateObject(p0, enCodeX);
	}

	public void updateObject(String p0, Object p1, int p2) throws SQLException {
		Object enCodeX = Util.encodeObject(p1, this.charSet, this.encodeType);

		rs.updateObject(p0, enCodeX, p2);
	}

	public void insertRow() throws SQLException {
		rs.insertRow();
	}

	public void updateRow() throws SQLException {
		rs.updateRow();
	}

	public void deleteRow() throws SQLException {
		rs.deleteRow();
	}

	public void refreshRow() throws SQLException {
		rs.refreshRow();
	}

	public void cancelRowUpdates() throws SQLException {
		rs.cancelRowUpdates();
	}

	public void moveToInsertRow() throws SQLException {
		rs.moveToInsertRow();
	}

	public void moveToCurrentRow() throws SQLException {
		rs.moveToCurrentRow();
	}

	public Statement getStatement() throws SQLException {
		return rs.getStatement();
	}

	public Blob getBlob(String p0) throws SQLException {
		return rs.getBlob(p0);
	}

	public Blob getBlob(int p0) throws SQLException {
		return rs.getBlob(p0);
	}

	public Clob getClob(int p0) throws SQLException {
		return rs.getClob(p0);
	}

	public Clob getClob(String p0) throws SQLException {
		return rs.getClob(p0);
	}

	public Array getArray(int p0) throws SQLException {
		return rs.getArray(p0);
	}

	public Array getArray(String p0) throws SQLException {
		return rs.getArray(p0);
	}

	public void updateRef(int p0, Ref p1) throws SQLException {
		rs.updateRef(p0, p1);
	}

	public void updateRef(String p0, Ref p1) throws SQLException {
		rs.updateRef(p0, p1);
	}

	public void updateBlob(String p0, Blob p1) throws SQLException {
		rs.updateBlob(p0, p1);
	}

	public void updateBlob(int p0, Blob p1) throws SQLException {
		rs.updateBlob(p0, p1);
	}

	public void updateClob(String p0, Clob p1) throws SQLException {
		rs.updateClob(p0, p1);
	}

	public void updateClob(int p0, Clob p1) throws SQLException {
		rs.updateClob(p0, p1);
	}

	public void updateArray(String p0, Array p1) throws SQLException {
		rs.updateArray(p0, p1);
	}

	public void updateArray(int p0, Array p1) throws SQLException {
		rs.updateArray(p0, p1);
	}

	/** 原来的ResultSet */
	protected ResultSet getBaseResultSet() {
		return this.rs;
	}

	/** 设置原来的ResultSet */
	protected void setBaseResultSet(ResultSet rs) {
		this.rs = rs;
	}

	public Object unwrap(Class iface) throws SQLException {
		return this.rs.unwrap(iface);
	}

	public boolean isWrapperFor(Class iface) throws SQLException {
		return this.rs.isWrapperFor(iface);
	}

	public RowId getRowId(int columnIndex) throws SQLException {
		return this.rs.getRowId(columnIndex);
	}

	public RowId getRowId(String columnLabel) throws SQLException {
		return this.rs.getRowId(columnLabel);
	}

	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		this.rs.updateRowId(columnIndex, x);
	}

	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		this.rs.updateRowId(columnLabel, x);
	}

	public int getHoldability() throws SQLException {
		return this.rs.getHoldability();
	}

	public boolean isClosed() throws SQLException {
		return this.rs.isClosed();
	}

	public void updateNString(int columnIndex, String nString)
			throws SQLException {
		this.rs.updateNString(columnIndex, nString);
	}

	public void updateNString(String columnLabel, String nString)
			throws SQLException {
		this.rs.updateNString(columnLabel, nString);
	}

	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		this.rs.updateNClob(columnIndex, nClob);
	}

	public void updateNClob(String columnLabel, NClob nClob)
			throws SQLException {
		this.rs.updateNClob(columnLabel, nClob);
	}

	public NClob getNClob(int columnIndex) throws SQLException {
		return this.rs.getNClob(columnIndex);
	}

	public NClob getNClob(String columnLabel) throws SQLException {
		return this.rs.getNClob(columnLabel);
	}

	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		return this.rs.getSQLXML(columnIndex);
	}

	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		return this.rs.getSQLXML(columnLabel);
	}

	public void updateSQLXML(int columnIndex, SQLXML xmlObject)
			throws SQLException {
		this.rs.updateSQLXML(columnIndex, xmlObject);
	}

	public void updateSQLXML(String columnLabel, SQLXML xmlObject)
			throws SQLException {
		this.rs.updateSQLXML(columnLabel, xmlObject);
	}

	public String getNString(int columnIndex) throws SQLException {
		return this.rs.getNString(columnIndex);
	}

	public String getNString(String columnLabel) throws SQLException {
		return this.rs.getNString(columnLabel);
	}

	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		return this.rs.getNCharacterStream(columnIndex);
	}

	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		return rs.getNCharacterStream(columnLabel);
	}

	public void updateNCharacterStream(int columnIndex, Reader x, long length)
			throws SQLException {
		this.rs.updateNCharacterStream(columnIndex, x, length);
	}

	public void updateNCharacterStream(String columnLabel, Reader reader,
			long length) throws SQLException {
		this.rs.updateNCharacterStream(columnLabel, reader);

	}

	public void updateAsciiStream(int columnIndex, InputStream x, long length)
			throws SQLException {
		this.rs.updateAsciiStream(columnIndex, x, length);

	}

	public void updateBinaryStream(int columnIndex, InputStream x, long length)
			throws SQLException {
		this.rs.updateBinaryStream(columnIndex, x, length);

	}

	public void updateCharacterStream(int columnIndex, Reader x, long length)
			throws SQLException {
		this.rs.updateCharacterStream(columnIndex, x, length);

	}

	public void updateAsciiStream(String columnLabel, InputStream x, long length)
			throws SQLException {
		this.rs.updateAsciiStream(columnLabel, x, length);

	}

	public void updateBinaryStream(String columnLabel, InputStream x,
			long length) throws SQLException {
		this.rs.updateBinaryStream(columnLabel, x, length);

	}

	public void updateCharacterStream(String columnLabel, Reader reader,
			long length) throws SQLException {
		this.rs.updateCharacterStream(columnLabel, reader, length);

	}

	public void updateBlob(int columnIndex, InputStream inputStream, long length)
			throws SQLException {
		this.rs.updateBlob(columnIndex, inputStream, length);

	}

	public void updateBlob(String columnLabel, InputStream inputStream,
			long length) throws SQLException {
		this.rs.updateBlob(columnLabel, inputStream, length);

	}

	public void updateClob(int columnIndex, Reader reader, long length)
			throws SQLException {
		this.rs.updateClob(columnIndex, reader, length);

	}

	public void updateClob(String columnLabel, Reader reader, long length)
			throws SQLException {
		this.rs.updateClob(columnLabel, reader, length);

	}

	public void updateNClob(int columnIndex, Reader reader, long length)
			throws SQLException {
		this.rs.updateNClob(columnIndex, reader, length);

	}

	public void updateNClob(String columnLabel, Reader reader, long length)
			throws SQLException {
		this.rs.updateNClob(columnLabel, reader, length);

	}

	public void updateNCharacterStream(int columnIndex, Reader x)
			throws SQLException {
		this.rs.updateNCharacterStream(columnIndex, x);

	}

	public void updateNCharacterStream(String columnLabel, Reader reader)
			throws SQLException {
		this.rs.updateNCharacterStream(columnLabel, reader);
	}

	public void updateAsciiStream(int columnIndex, InputStream x)
			throws SQLException {
		this.rs.updateAsciiStream(columnIndex, x);

	}

	public void updateBinaryStream(int columnIndex, InputStream x)
			throws SQLException {
		this.rs.updateBinaryStream(columnIndex, x);
	}

	public void updateCharacterStream(int columnIndex, Reader x)
			throws SQLException {
		this.rs.updateCharacterStream(columnIndex, x);

	}

	public void updateAsciiStream(String columnLabel, InputStream x)
			throws SQLException {
		this.rs.updateAsciiStream(columnLabel, x);
	}

	public void updateBinaryStream(String columnLabel, InputStream x)
			throws SQLException {
		this.rs.updateBinaryStream(columnLabel, x);

	}

	public void updateCharacterStream(String columnLabel, Reader reader)
			throws SQLException {
		this.rs.updateCharacterStream(columnLabel, reader);

	}

	public void updateBlob(int columnIndex, InputStream inputStream)
			throws SQLException {
		this.rs.updateBlob(columnIndex, inputStream);

	}

	public void updateBlob(String columnLabel, InputStream inputStream)
			throws SQLException {
		this.rs.updateBlob(columnLabel, inputStream);

	}

	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		this.rs.updateClob(columnIndex, reader);

	}

	public void updateClob(String columnLabel, Reader reader)
			throws SQLException {
		this.rs.updateClob(columnLabel, reader);

	}

	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		this.rs.updateNClob(columnIndex, reader);
	}

	public void updateNClob(String columnLabel, Reader reader)
			throws SQLException {
		this.rs.updateNClob(columnLabel, reader);

	}

	@Override
	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}