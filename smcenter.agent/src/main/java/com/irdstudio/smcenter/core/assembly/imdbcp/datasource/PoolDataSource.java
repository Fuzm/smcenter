/**
 * <p>Title: 本地连接池</p>
 * <p>Description: 本地连接池，代码本身实现的连接池</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 北京易初电子技术有限公司</p>
 * @author 黄瑞文
 * @version 1.0
 * 2006-11-22
 */

package com.irdstudio.smcenter.core.assembly.imdbcp.datasource;

import javax.sql.DataSource;

import com.irdstudio.smcenter.core.assembly.imdbcp.resource.RsConnection;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * 本地连接池，代码本身实现的连接池
 * @author hrw
 */
public class PoolDataSource implements DataSource {
	
	/** 版本号 */
	public static final String VERSION = "PoolDataSource 1.0.0.1";
	
	/** 连接池状态 -- 初始化中 */
	public static int DSSTATE_INITING = 1;
	/** 连接池状态 -- 连接中，正常，可以申请连接 */
	public static int DSSTATE_CONNECTING = 2;
	/** 连接池状态 -- 断开中 */
	public static int DSSTATE_CLOSEING = 4;
	
	/** 未被使用的连接集合 */
	private ArrayList freeConnections = new ArrayList();
	
	/** 使用中的连接集合 */
	private ArrayList useConnections = new ArrayList();
	
	/** 连接参数 */
	private DataSourceParm connParm;
	
	/** 连接池状态 */
	private int state = DSSTATE_INITING;
	
	/** 监控线程 */
	private PoolMonitorThread monitor = null; 
	
	/**
	 * 构造函数
	 */
	public PoolDataSource(){
		
	}
	
	/**
	 * 构造函数
	 * @param parm
	 * @return javax.sql.DataSource
	 */
	public PoolDataSource(DataSourceParm parm){
		this.connParm = parm;
	}
	
	/**
	 * 获取连接
	 * 连接池没有初始化完成需要等待
	 * @return Connection
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		return this.getConnection(this.connParm.getUser(), this.connParm.getPassword());
	}
	
	/** 等待连接池初始化 */
	private synchronized void waitForInit() throws SQLException {
		int state = this.getState();
		if (state == PoolDataSource.DSSTATE_INITING) {
			this.init();
		}
	}
	
	/** 初始化 */
	private synchronized void init() throws SQLException {
		System.err.println(VERSION);
		System.err.println(this.connParm.toString());
		
		for (int i = 0; i< this.connParm.getMinConnection(); i++) {
			this.freeConnections.add(this.newConnection(this.connParm.getUser(), this.connParm.getPassword()));
		}
		
		monitor = new PoolMonitorThread(this,this.connParm.getCheckInterval());
		monitor.start();
		
		this.setState(PoolDataSource.DSSTATE_CONNECTING);
		
	}
	
	
	/** 获取自由连接数 */
	public int getFreeCount() {
		return this.freeConnections.size();
	}

	public int getConnectionCount(){
		return this.freeConnections.size() + this.useConnections.size();
	}
   
	/**
	 * 新建连接，当已申请连接没有达到最大值时使用
	 * @return Connection
	 */
	public synchronized RsConnection newConnection(String username, String password) throws SQLException {
		try {
			Class.forName(this.connParm.getDriver());
		} catch (ClassNotFoundException e ) {
			throw new SQLException ("数据库驱动不存在！");
		}
		Properties extendParm = (Properties)this.connParm.getExtendProperties().clone();
		extendParm.setProperty("user", username);
		extendParm.setProperty("password", password);
		Connection _con = DriverManager.getConnection(this.connParm.getUrl(), extendParm);	
		_con.setTransactionIsolation(this.connParm.getTransation());
		RsConnection _rscon = new RsConnection(_con,this.connParm); 
		_rscon.setDataSource(this);
		
		return 	_rscon;
	}
   
	/**
	 * 释放连接
	 * 连接被使用完毕后使用，通常由Connection.close()方法中调用，使用者无需关心
	 * @param con 被释放的连接
	 */
	public void freeConnection(RsConnection con){
		synchronized (freeConnections) {
			synchronized (useConnections) {
				if (this.getConnectionCount() < this.connParm.getLimitConnection()){
					useConnections.remove(con);
			
					freeConnections.add(con);
					// 唤醒
					freeConnections.notify();
				} else {
					con.realClose();
				}
			}
		}
	}
	
	/**
	 * 清除连接
	 * 通常当连接不健康由连接反调
	 * @param con 清除的连接
	 */
	public void clearConnection(RsConnection con){
		synchronized (freeConnections) {
			freeConnections.remove(con);
			synchronized (useConnections) {
				useConnections.remove(con);
				// 唤醒
				if (this.getFreeCount() < this.connParm.getLimitConnection()){
					freeConnections.notify();
				}
			}
		}
	}
	
	/**
	 * 检查连接池，包括
	 *    1.检查空闲连接是否还是连通的，非连通则自动关闭
	 *    2.检查所有使用中的连接是否健康，不健康则断开，并且释放，真正断开。
	 *    3.补全最小连接
	 * @return int
	 */
	public void doCheck() {
		//System.out.println("****************************docheck");
		// 1.检查空闲连接
		synchronized (this.freeConnections) {
			ArrayList list = (ArrayList)this.freeConnections.clone();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				RsConnection tscon = (RsConnection)iterator.next();
				
				if (!tscon.checkConnected()) {
					System.err.println("[DS]空闲连接已经不可用，强行关闭！ ");
					try {
						tscon.close();
					} catch (SQLException e) {
						System.err.println("[DS]强行关闭连接错误！");
						e.printStackTrace(System.err);
					}
				}
			}
			list.clear();
		
		
			// 2.检查所有使用中的连接
			synchronized (this.useConnections) {
				ArrayList list2 = (ArrayList)this.useConnections.clone();
				for (Iterator iterator = list2.iterator(); iterator.hasNext();) {
					RsConnection tscon = (RsConnection)iterator.next();
					if (tscon.isOverTime()) {
						System.err.println("[DS]连接超时没有关闭，强行关闭！ sql=" + tscon.getLastSql());
						try {
							tscon.close();
						} catch (SQLException e) {
							System.err.println("[DS]强行关闭连接错误！");
							e.printStackTrace(System.err);
						}
					}
				}
				list2.clear();
			}
		}
		
		// 3.补全最小连接数
		while (this.getConnectionCount() < this.connParm.getMinConnection()) {
			synchronized (this.freeConnections) {
				try {
					this.freeConnections.add(this.newConnection(this.connParm.getUser(), this.connParm.getPassword()));
				} catch (Exception e) {
					break;
				}
				
			}
		}
		
		// 清除多余的连接，超过最大连接数的连接
		while ((this.getConnectionCount() > this.connParm.getMaxConnection()
				&& this.getFreeCount() > 10) 
				|| (this.getConnectionCount() > this.connParm.getLimitConnection()
					&& this.getFreeCount() > 0)){
			RsConnection conn = null;
			try {
				conn = (RsConnection)this.getConnection();
				conn.setHealth(false);
				conn.close();
				conn = null;
			} catch (Exception e) {
			} finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (Exception e) {
						
					}
				}
			}
		}

	}
	
	/**
	 * 关闭数据源
	 */
	public void close() throws SQLException{
		this.setState(PoolDataSource.DSSTATE_CLOSEING);
		monitor.setStop(true);
		try {
			Thread.sleep(200);
		} catch (Exception e) {}
		
		monitor = null;
		
		Collection list = (Collection)this.useConnections.clone();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			RsConnection rsconn = (RsConnection)iterator.next();
			rsconn.setHealth(false);
			try {
				rsconn.close();
			} catch (Exception e) {}
		}
		
		list = (Collection)this.freeConnections.clone();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			RsConnection rsconn = (RsConnection)iterator.next();
			rsconn.setHealth(false);
			try {
				rsconn.close();
			} catch (Exception e) {}
		}
		
		this.useConnections.clear();
		this.freeConnections.clear();
		
	}
	
	/**
	 * 改变状态
	 * @param state
	 */
	public void setState(int state){
		this.state = state;
	}
	
	/** 连接池状态 */
	public int getState(){
		return this.state;
	}
	
	public Connection getConnection(String username, String password) throws SQLException {
		// 等待连接初始化
		waitForInit();
		
		int state = this.getState();
		if (state == PoolDataSource.DSSTATE_CONNECTING) {
			long maxTime = System.currentTimeMillis() + this.connParm.getMaxWaitTime() * 1000;
			synchronized (this.freeConnections) {
				boolean isFirst = true;
				while (true) {
					if (this.freeConnections.size() > 0) {
						synchronized (this.useConnections) {
							RsConnection con = (RsConnection)this.freeConnections.remove(0);
							con.setLastSql(null);
							this.useConnections.add(con);
							return con;
						}
					} else if (!isFirst || this.freeConnections.size() + this.useConnections.size() 
							< this.connParm.getLimitConnection()) {
						synchronized (this.useConnections) {
							RsConnection con = this.newConnection(username,password);
							this.useConnections.add(con);
							return con;
						}
						
					} else {
						isFirst = false;
						// 等待 
						if (this.getState() != PoolDataSource.DSSTATE_CONNECTING) {
							// 状态改变，跳出循环
							break;
						} else {
							// 等待
							try {
								this.freeConnections.wait(this.connParm.getMaxWaitTime());
							} catch (Exception e) {}
							if (maxTime < System.currentTimeMillis()) {
								// 超时
								throw new SQLException("[DS]获取连接超时！");
							}
						}
					} 
				}
			}
		} 
		
		if (state == PoolDataSource.DSSTATE_INITING){
			// 等待初始化失败
			throw new SQLException("[DS]等待连接池初始化失败！");
		} else {
			// 连接池关闭中
			throw new SQLException("[DS]连接池关闭中，不能申请连接！");
		}
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
