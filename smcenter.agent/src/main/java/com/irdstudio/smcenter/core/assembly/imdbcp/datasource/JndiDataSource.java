/**
 * <p>Title: JNDI数据源</p>
 * <p>Description: JNDI数据源，WebSphere连接池也适用</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 北京易初电子技术有限公司</p>
 * @author 黄瑞文
 * @version 1.0
 * 2006-11-22
 */

package com.irdstudio.smcenter.core.assembly.imdbcp.datasource;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.irdstudio.smcenter.core.assembly.imdbcp.encode.CustomConnection;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * JNDI数据源，WebSphere连接池也适用
 * @author hrw
 * 2006-11-26
 */
public class JndiDataSource implements DataSource {
	/** 数据源 */
	private DataSource baseDs = null;
	/** 连接参数 */
	private DataSourceParm connParm;
	
	/** 
	 * 构造函数
	 * @param parm 连接参数
	 * @exception 找不到命名服务
	 */
	public JndiDataSource(DataSourceParm parm) throws NamingException {
		this.connParm = parm;
		baseDs = (DataSource)(new InitialContext()).lookup(parm.getDataSourceName());
	}
	
	/**
	 * 获取连接，需要包装转码层
	 * @return Connection
	 */
	public Connection getConnection() throws SQLException {
		Connection _con = baseDs.getConnection();
		
		return new CustomConnection(_con,connParm.getCharSet(),connParm.getEncodeType());
	}
	
	/**
	 * 获取连接，需要包装转码层
	 * @param username
	 * @param password
	 * @return Connection
	 */
	public Connection getConnection(String username, String password) throws SQLException {
		Connection _con = baseDs.getConnection(username,password);
		
		return new CustomConnection(_con,connParm.getCharSet(),connParm.getEncodeType());
	}
	
	/** 关闭数据源 */
	public void close() throws SQLException {
		
	}
	
	public PrintWriter getLogWriter() throws SQLException {
		return baseDs.getLogWriter();
	}

	public int getLoginTimeout() throws SQLException {
		return baseDs.getLoginTimeout();
	}

	public void setLogWriter(PrintWriter arg0) throws SQLException {
		baseDs.setLogWriter(arg0);
	}

	public void setLoginTimeout(int arg0) throws SQLException {
		baseDs.setLoginTimeout(arg0);
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
