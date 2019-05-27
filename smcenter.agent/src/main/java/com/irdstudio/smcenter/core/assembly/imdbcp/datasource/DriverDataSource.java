/**
 * <p>Title: 驱动型数据源</p>
 * <p>Description: 不使用连接池的数据源，即直接建立JDBC连接</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 北京易初电子技术有限公司</p>
 * @author 黄瑞文
 * @version 1.0
 * 2006-11-22
 */

package com.irdstudio.smcenter.core.assembly.imdbcp.datasource;

import javax.sql.DataSource;

import com.irdstudio.smcenter.core.assembly.imdbcp.encode.CustomConnection;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * 不使用连接池的数据源，即直接建立JDBC连接
 * @author hrw
 * 2006-11-26
 */
public class DriverDataSource implements DataSource 
{
	/** 连接参数 */
	private DataSourceParm connParm;
	
	/**
	 * 构造函数
	 * @param parm
	 * @return javax.sql.DataSource
	 */
	public DriverDataSource(DataSourceParm parm){
		this.connParm = parm;
	}
	
	/**
	 * 获取连接
	 * @return Connection
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		return this.getConnection(this.connParm.getUser(), this.connParm.getPassword());
	}
	
	/**
	 * 获取连接
	 * @return Connection
	 * @throws SQLException
	 */
	public Connection getConnection(String username, String password) throws SQLException {
		try {
			Class.forName(this.connParm.getDriver());
		} catch (ClassNotFoundException e ) {
			throw new SQLException ("数据库驱动不存在！");
		}
		Properties extendParm = (Properties)this.connParm.getExtendProperties().clone();
		extendParm.setProperty("user", username);
		extendParm.setProperty("password", password);
		Connection _con = DriverManager.getConnection(this.connParm.getUrl(), extendParm);	
		CustomConnection _cscon = new CustomConnection(_con,this.connParm.getCharSet(),this.connParm.getEncodeType()); 

		return _cscon;
	}
	
	/** 关闭数据源 */
	public void close() throws SQLException{
	}
	
	public PrintWriter getLogWriter() throws SQLException {
		return DriverManager.getLogWriter();
	}

	public int getLoginTimeout() throws SQLException {
		return DriverManager.getLoginTimeout();
	}

	public void setLogWriter(PrintWriter arg0) throws SQLException {
		DriverManager.setLogWriter(arg0);
	}

	public void setLoginTimeout(int arg0) throws SQLException {
		DriverManager.setLoginTimeout(arg0);
	}

	/** 连接参数 */
	public DataSourceParm getConnectionParm() {
		return connParm;
	}

	/** 连接参数 */
	public void setConnectionParm(DataSourceParm connParm) {
		this.connParm = connParm;
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
