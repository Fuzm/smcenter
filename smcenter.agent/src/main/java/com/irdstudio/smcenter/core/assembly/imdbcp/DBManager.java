/**
 * <p>Title: 数据源管理器</p>
 * <p>Description: 数据源管理器</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 北京易初电子技术有限公司</p>
 * @author 黄瑞文
 * @version 1.0
 * 2006-11-22
 */

package com.irdstudio.smcenter.core.assembly.imdbcp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.irdstudio.smcenter.core.assembly.imdbcp.datasource.DataSourceParm;
import com.irdstudio.smcenter.core.assembly.imdbcp.datasource.DriverDataSource;
import com.irdstudio.smcenter.core.assembly.imdbcp.datasource.JndiDataSource;
import com.irdstudio.smcenter.core.assembly.imdbcp.datasource.PoolDataSource;
import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.base.IConnPool;

/**
 * 数据源管理器，职责：
 * 管理数据源，不关心是那种数据源
 * @author hrw
 * 2006-11-26
 */
public class DBManager implements IConnPool{
	/** 实例 */
	private static DBManager instance = null;
	/** 配置参数 */
	private static Properties configs = null;
	
	/** 数据库参数数组 */
	private DataSourceParm[] connParms = null;
	
	/** 数据源数组 */
	private DataSource[] dataSources = null;
	
	// 禁止多实例
	private DBManager(){
		
	}
	
	/** 获取实例 */
	public synchronized static DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
			try {
				instance.init();
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		
		return instance;
	}
   
	/** 初始化 */
	public void init() throws Exception {
		
		connParms = DataSourceParm.getDataSourceParm(configs);
		
		dataSources = new DataSource[connParms.length];
		
	}
	
	/**
	 * 获取连接，
	 * 该方法等同getConnection(1)
	 * 主要用于只有一个数据源的方式
	 * @return Connection
	 */
	public Connection getConnection() throws SQLException{
		return getConnection(1);
	}
	
	/**
	 * 获取连接
	 * @param dataSource - 数据源序号 1开始
	 * @return Connection
	 */
	public Connection getConnection(int dataSource) throws SQLException{
		return getDataSource(dataSource).getConnection();
	}
	
	/**
	 * 获取数据源
	 * @param dataSource - 数据源序号
	 * @return javax.sql.DataSource 数据源
	 */
	private synchronized DataSource getDataSource(int dataSource) throws SQLException {
		if (dataSources[dataSource -1] == null) {
			dataSources[dataSource -1] = initDataSource(connParms[dataSource -1]);
		}
		return dataSources[dataSource -1];
	}
	
	/**
	 * 获取连接池中的连接数
	 * 只支持本地连接池
	 * @param dataSource 数据源编号
	 * @return -1 连接池没有初始化，0 不是本地连接池，其它表示空闲的连接数
	 */
	public int getFreeConnectionCount(int dataSource) {
		if (dataSources[dataSource -1] == null) {
			return -1;
		}
		
		if (dataSources[dataSource -1] instanceof PoolDataSource) {
			PoolDataSource pds = (PoolDataSource)dataSources[dataSource -1];
			return pds.getFreeCount();
		}
		
		return 0;
	}
	
	/**
	 * 获取连接池中的连接总数
	 * 只支持本地连接池
	 * @param dataSource 数据源编号
	 * @return -1 连接池没有初始化，0 不是本地连接池，其它表示连接池中的连接总数
	 */
	public int getConnectionCount(int dataSource) {
		if (dataSources[dataSource -1] == null) {
			return -1;
		}
		
		if (dataSources[dataSource -1] instanceof PoolDataSource) {
			PoolDataSource pds = (PoolDataSource)dataSources[dataSource -1];
			return pds.getConnectionCount();
		}
		
		return 0;
	}
	
	/**
	 * 初始化数据源
	 * @param parm - 数据源参数
	 * @return javax.sql.DataSource
	 * @roseuid 4562B30401C5
	 */
	private DataSource initDataSource(DataSourceParm connParm) throws SQLException {
		
		if (connParm.getConnectionType() == 1) {
			// JIDI连接池，WebSphere连接池
			try {
				return new JndiDataSource(connParm);
			} catch (Exception e) {
				throw new SQLException("获取数据源失败！");
			}
		} else if (connParm.getConnectionType() == 2) {
			// 本地连接池
			return new PoolDataSource(connParm);
		} else {
			// 不使用数据源
			return new DriverDataSource(connParm);
		}
	}
	
	/** 重新建立连接 */
	public void reInit() throws SQLException {
		close();
		
		//init();
	}
	
	/** 关闭数据源管理器，通常用于断开或重新建立连接 */
	public void close() {
		for (int i = 0; i< dataSources.length; i++) {
			
			close(i+1);
			dataSources[i] = null;
		}
	}
	
	/** 关闭数据源管理器，通常用于断开或重新建立连接 */
	public void close(int dataSource) {
		if (dataSource > 0 
				&& dataSources.length > dataSource-1
				&& dataSources[dataSource-1] != null) {
			if (dataSources[dataSource-1] instanceof PoolDataSource) {
				try {
					((PoolDataSource)dataSources[dataSource-1]).close();
				} catch (Exception e) {
					System.err.print("本地连接池数据源关闭异常！");
					e.printStackTrace(System.err);
				}
			} else if (dataSources[dataSource-1] instanceof DriverDataSource) {
				try {
					((DriverDataSource)dataSources[dataSource-1]).close();
				} catch (Exception e) {
					System.err.print("直接连接数据源关闭异常！");
					e.printStackTrace(System.err);
				}
			} else if (dataSources[dataSource-1] instanceof JndiDataSource) {
				try {
					((JndiDataSource)dataSources[dataSource-1]).close();
				} catch (Exception e) {
					System.err.print("Jndi数据源关闭异常！");
					e.printStackTrace(System.err);
				}
			} 
			dataSources[dataSource-1] = null;
			System.out.println("本地数据源连接池成功关闭!");
		}
	}

	/** 配置参数 */
	public static Properties getConfigs() {
		return configs;
	}

	/** 配置参数 */
	public static void setConfigs(Properties configs) {
		DBManager.configs = configs;
	}

	public void releaseConnection(Connection conn) {
		if(conn != null){
			try {
				if(!conn.isClosed()){
					conn.clearWarnings();
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 返回第一个数据源的连接总数
	 * 新增加的额外的方法
	 */
	public int getConnectionCount() {
		return getConnectionCount(1);
	}

	/**
	 * 不实现直接关闭连接池
	 */
	public void clearAndFree() {

	}
}
