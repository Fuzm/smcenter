/**
 * <p>Title: 连接</p>
 * <p>Description: 资源监控层</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 北京易初电子技术有限公司</p>
 * @author 黄瑞文
 * @version 1.0
 * 2006-11-22
 */

package com.irdstudio.smcenter.core.assembly.imdbcp.resource;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import com.irdstudio.smcenter.core.assembly.imdbcp.datasource.DataSourceParm;
import com.irdstudio.smcenter.core.assembly.imdbcp.datasource.PoolDataSource;
import com.irdstudio.smcenter.core.assembly.imdbcp.encode.CustomConnection;
import com.irdstudio.smcenter.core.assembly.imdbcp.encode.Util;

/**
 * 资源监控层，Connection的监控类
 * @author hrw
 *
 */
public class RsConnection extends CustomConnection 
{
	/** 日志输出名称 */
	public static String PN = "[Resuource]";
	
	/** 
	 * 使用中的Statement集合
	 * 包括Statement、PreparedStatement、CallableStatement、ResultSet
	 */
	private ArrayList useResources = new ArrayList();
	
	/** 最后执行的SQL */
	private String lastSql = null;
	
	/** 最后执行SQL的时间 */
	private long lastSqlTime = 0;  
	
	/** 连接参数 */
	private DataSourceParm parm = null;
	
	/** 是否是健康的，当有资源没有释放，或连接使用超时 */
	private boolean health = true;
	
	/** 数据源 */
	PoolDataSource ds = null;
	
	/** 最大使用时间，超过时间，关闭该连接 */
	private long maxUseTime = 0;
	
	
	/**
	 * 构造函数
	 * @param parm 连接参数
	 */
	public RsConnection(Connection con,DataSourceParm parm) {
		super(con,parm.getCharSet(),parm.getEncodeType());
		this.parm = parm;
		this.lastSqlTime = System.currentTimeMillis();
		// 每个连接最大20小时
		maxUseTime = this.lastSqlTime + 20 * 60 * 60 * 1000;
	}

	public Statement createStatement(int p0, int p1, int p2) 
	throws SQLException {
		if (this.con == null) {
			this.setHealth(false);
			throw new SQLException(PN + "连接池异常！连接已经为空！");
		}
		
		Statement _stmt = con.createStatement(p0, p1, p2);
		if (this.parm.getFetchSize() > 0) {
			_stmt.setFetchSize(this.parm.getFetchSize());
		}
		RsStatement _rsst = new RsStatement(_stmt,this.charSet,this.encodeType);
		this.addResource(_rsst);
		
		return _rsst;
	}
	
	public Statement createStatement() throws SQLException {
		if (this.con == null) {
			this.setHealth(false);
			throw new SQLException(PN + "连接池异常！连接已经为空！");
		}
		
		Statement _stmt = con.createStatement();
		if (this.parm.getFetchSize() > 0) {
			_stmt.setFetchSize(this.parm.getFetchSize());
		}
		RsStatement _rsst = new RsStatement(_stmt,this.charSet,this.encodeType);
		this.addResource(_rsst);
		
		return _rsst;
	}
	
	public Statement createStatement(int p0, int p1) 
	throws SQLException {
		if (this.con == null) {
			this.setHealth(false);
			throw new SQLException(PN + "连接池异常！连接已经为空！");
		}
		
		Statement _stmt = con.createStatement(p0, p1);
		if (this.parm.getFetchSize() > 0) {
			_stmt.setFetchSize(this.parm.getFetchSize());
		}
		RsStatement _rsst = new RsStatement(_stmt,this.charSet,this.encodeType);
		this.addResource(_rsst);
		
		return _rsst;
	}
	
	public PreparedStatement prepareStatement(String p0,int p1) 
	throws SQLException {
		if (this.con == null) {
			this.setHealth(false);
			throw new SQLException(PN + "连接池异常！连接已经为空！");
		}
		
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		PreparedStatement _stmt = con.prepareStatement(enCodeX, p1);
		if (this.parm.getFetchSize() > 0) {
			_stmt.setFetchSize(this.parm.getFetchSize());
		}
		RsPreparedStatement _rsps = new RsPreparedStatement(_stmt, this.charSet,this.encodeType);
		this.addResource(_rsps);
		_rsps.setLastSql(p0);
		
		return _rsps;
	}
	
	public PreparedStatement prepareStatement(String p0,String[] p1) 
	throws SQLException {
		if (this.con == null) {
			this.setHealth(false);
			throw new SQLException(PN + "连接池异常！连接已经为空！");
		}
		
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		PreparedStatement _stmt = con.prepareStatement(enCodeX, p1);
		if (this.parm.getFetchSize() > 0) {
			_stmt.setFetchSize(this.parm.getFetchSize());
		}
		RsPreparedStatement _rsps = new RsPreparedStatement(_stmt, this.charSet,this.encodeType);
		this.addResource(_rsps);
		_rsps.setLastSql(p0);
		
		return _rsps;
	}
	
	public PreparedStatement prepareStatement(String p0,int p1, int p2, int p3) 
	throws SQLException {
		if (this.con == null) {
			this.setHealth(false);
			throw new SQLException(PN + "连接池异常！连接已经为空！");
		}
		
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		PreparedStatement _stmt = con.prepareStatement(enCodeX, p1, p2, p3);
		if (this.parm.getFetchSize() > 0) {
			_stmt.setFetchSize(this.parm.getFetchSize());
		}
		RsPreparedStatement _rsps = new RsPreparedStatement(_stmt, this.charSet,this.encodeType);
		this.addResource(_rsps);
		_rsps.setLastSql(p0);
		
		return _rsps;
	}
	
	public PreparedStatement prepareStatement(String p0) 
	throws SQLException {
		if (this.con == null) {
			this.setHealth(false);
			throw new SQLException(PN + "连接池异常！连接已经为空！");
		}
		
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		PreparedStatement _stmt = con.prepareStatement(enCodeX);
		if (this.parm.getFetchSize() > 0) {
			_stmt.setFetchSize(this.parm.getFetchSize());
		}
		RsPreparedStatement _rsps = new RsPreparedStatement(_stmt, this.charSet,this.encodeType);
		this.addResource(_rsps);
		_rsps.setLastSql(p0);
		
		return _rsps;
	}
	
	public PreparedStatement prepareStatement(String p0,int[] p1) 
	throws SQLException {
		if (this.con == null) {
			this.setHealth(false);
			throw new SQLException(PN + "连接池异常！连接已经为空！");
		}
		
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		PreparedStatement _stmt = con.prepareStatement(enCodeX, p1);
		if (this.parm.getFetchSize() > 0) {
			_stmt.setFetchSize(this.parm.getFetchSize());
		}
		RsPreparedStatement _rsps = new RsPreparedStatement(_stmt, this.charSet,this.encodeType);
		this.addResource(_rsps);
		_rsps.setLastSql(p0);
		
		
		return _rsps;
	}
	
	public PreparedStatement prepareStatement(String p0,int p1, int p2) 
	throws SQLException {
		if (this.con == null) {
			this.setHealth(false);
			throw new SQLException(PN + "连接池异常！连接已经为空！");
		}
		
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		PreparedStatement _stmt = con.prepareStatement(enCodeX, p1, p2);
		if (this.parm.getFetchSize() > 0) {
			_stmt.setFetchSize(this.parm.getFetchSize());
		}
		RsPreparedStatement _rsps = new RsPreparedStatement(_stmt, this.charSet,this.encodeType);
		this.addResource(_rsps);
		_rsps.setLastSql(p0);
		
		return _rsps;
	}
	
	public CallableStatement prepareCall(String p0, int p1,int p2) 
	throws SQLException {
		if (this.con == null) {
			this.setHealth(false);
			throw new SQLException(PN + "连接池异常！连接已经为空！");
		}
		
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		CallableStatement _cs = con.prepareCall(enCodeX, p1, p2);
		if (this.parm.getFetchSize() > 0) {
			_cs.setFetchSize(this.parm.getFetchSize());
		}
		RsCallableStatement _rscs = new RsCallableStatement(_cs,this.charSet,this.encodeType);
		this.addResource(_rscs);
		_rscs.setLastSql(p0);
		
		return _rscs;
	}
	
	public CallableStatement prepareCall(String p0) 
	throws SQLException {
		if (this.con == null) {
			this.setHealth(false);
			throw new SQLException(PN + "连接池异常！连接已经为空！");
		}
		
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		CallableStatement _cs = con.prepareCall(enCodeX);
		if (this.parm.getFetchSize() > 0) {
			_cs.setFetchSize(this.parm.getFetchSize());
		}
		RsCallableStatement _rscs = new RsCallableStatement(_cs,this.charSet,this.encodeType);
		this.addResource(_rscs);
		_rscs.setLastSql(p0);
		
		return _rscs;
	}
	
	public CallableStatement prepareCall(String p0, int p1,int p2, int p3) 
	throws SQLException {
		if (this.con == null) {
			this.setHealth(false);
			throw new SQLException(PN + "连接池异常！连接已经为空！");
		}
		
		String enCodeX = Util.encodeString(p0, this.charSet, this.encodeType);
		CallableStatement _cs = con.prepareCall(enCodeX, p1, p2, p3);
		if (this.parm.getFetchSize() > 0) {
			_cs.setFetchSize(this.parm.getFetchSize());
		}
		RsCallableStatement _rscs = new RsCallableStatement(_cs,this.charSet,this.encodeType);
		this.addResource(_rscs);
		_rscs.setLastSql(p0);
		
		return _rscs;
	}
	
	/**
	 * 关闭连接，职责：
	 * 需要监控资源是否释放
	 * 需要向数据库连接池登记释放连接
	 * 若连接为非健康（有资源没有释放），则强行释放资源，并打印日志，并真正关闭连接池
	 */
	public void close() throws SQLException {
		if (this.con == null) {
			this.setHealth(false);
		}
		this.closeAllResource();
		this.checkCommit();
		if (this.isHealth() && this.getMaxUseTime() > System.currentTimeMillis()) {
			this.setLastSql(null);
			// 释放连接
			this.ds.freeConnection(this);
		} else {
			// 真正关闭连接
			this.realClose();
		}
	}
	
	/**
	 * 检查事务是否关闭
	 * 若未关闭，若已经有没有关闭的资源，回滚，否则提交
	 * @return
	 */
	public boolean checkCommit() {
		if (this.con == null) {
			this.setHealth(false);
			return false;
		}
		
		try {
			if (!this.getAutoCommit()) {
				System.err.println(PN + "连接没有关闭事务，sql=" + this.lastSql);
				if (this.isHealth()) {
					this.setAutoCommit(true);
				} else {
					try {
						this.rollback();
					} catch (Exception er) {}
					this.setAutoCommit(true);
				}
				this.setHealth(false);
				
				return false;
			}
			
			return true;
		} catch (Exception se) {
			this.setHealth(false);
			
			return false;
		}
	}
	
	/**
	 * 是否超时
	 * @return boolean
	 */
	public boolean isOverTime() {
		if (!this.isHealth()) {
			return true;
		}
		if (this.lastSqlTime + this.parm.getHealthTime() * 1000 < System.currentTimeMillis()) {
			this.setHealth(false);
			System.err.println(PN + "连接超时没有释放，sql=" + this.lastSql);
			return true;
		}
		
		return false;
	}
	
	/**
	 * 关闭所有Statement，只是调用各个Statement.close(),不清除集合中的信息
	 */
	public void closeAllResource(){
		if (this.useResources == null) {
			this.setHealth(false);
			return ;
		}
		if (this.useResources.size() > 0) {
			// 非健康的
			this.setHealth(false);
			
			ArrayList list = (ArrayList)this.useResources.clone();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				RsInterface rsi = (RsInterface)iterator.next();
				if (rsi != null) {
					System.err.println(PN + "没有释放数据库资源" + rsi.getClass().getName()+ ",sql="+ rsi.getLastSql());
					try {
						rsi.close();
					} catch (Exception e) {
						System.err.println(PN + "强制是否资源出错！sql=" + rsi.getLastSql() );
						e.printStackTrace(System.err);
					}
				}
			}
			
			list.clear();
		}
	}
	
	/**
	 * 增加资源
	 * @param rs 资源接口
	 */
	public void addResource(RsInterface rs) {
		rs.setBaseConnnection(this);
		this.useResources.add(rs);
	}
	
	/**
	 * 移除资源
	 * @param rs 资源接口
	 */
	public void removeResource(RsInterface rs) {
		this.useResources.remove(rs);
		rs.setBaseConnnection(null);
	}
	
	/**	
	 * 真正关闭连接
	 * 通常只会在关闭连接时发现连接不健康，或检查连接使用连接发现连接不健康 
	 */
	public void realClose() {
		// 清除连接
		if (this.con == null)
			return ;
		
		this.ds.clearConnection(this);
		
		try {
			if (this.con != null) this.con.close();
		} catch (Exception e) {}
		this.con = null;
		this.ds = null;
		this.parm = null;
		if (useResources != null && useResources.size() > 0) {
			System.err.println(PN + "强制关闭连接时，仍有未关闭资源！");
			this.useResources.clear();
		}
		this.useResources = null;
	}
	
	/**	
	 * 真正关闭连接
	 * 通常只会在关闭连接时发现连接不健康，或检查连接使用连接发现连接不健康 
	 */
	public boolean checkConnected() {
		if (this.con == null) {
			this.setHealth(false);
			return false;
		}
		Statement st = null;
		try {
			st = this.createStatement();
			st.execute(this.parm.getCheckSQL());
		} catch (Exception e) {
			this.setHealth(false);
			return false;
		} finally {
			if (st != null) {
				try { st.close();} catch (Exception es) {}
			}
		}
		
		return true;
	}
	
	/**
	 * 设置最后执行的SQL
	 * @param sql
	 */
	public void setLastSql(String sql) {
		this.lastSql = sql;
		this.lastSqlTime = System.currentTimeMillis();
	}

	/** 是否健康，当有资源没有释放，或连接使用超时 */
	public boolean isHealth() {
		return health;
	}

	/** 是否健康，当有资源没有释放，或连接使用超时 */
	public void setHealth(boolean health) {
		this.health = health;
	}

	/** 数据源 */
	public PoolDataSource getDataSource() {
		return ds;
	}

	/** 数据源 */
	public void setDataSource(PoolDataSource ds) {
		this.ds = ds;
	}

	/** 最后执行的SQL */
	public String getLastSql() {
		return lastSql;
	}

	/** 最大使用时间，超过时间，关闭该连接 */
	public long getMaxUseTime() {
		return maxUseTime;
	}
}
