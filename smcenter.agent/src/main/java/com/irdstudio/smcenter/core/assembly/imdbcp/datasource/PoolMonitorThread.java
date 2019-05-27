/**
 * <p>Title: 本地连接池监控线程</p>
 * <p>Description: 本地连接池监控线程，只用于PoolDataSource</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 北京易初电子技术有限公司</p>
 * @author 黄瑞文
 * @version 1.0
 * 2006-11-22
 */

package com.irdstudio.smcenter.core.assembly.imdbcp.datasource;

/**
 * 数据库连接参数
 * 连接池监控线程，职责：占用时间过长，并且没有被释放的连接，
 * @author hrw
 * 2006-11-26
 */

public class PoolMonitorThread extends Thread {
	/** 需要监控的数据源 */
	private PoolDataSource ds = null;
	/** 检查间隔数，每次100ms */
	private int interval = 5 * 60 * 1000/10;
	/** 是否停止 */
	private boolean stop = false;
	
	/** 
	 * 构造函数   
	 * @param ds 需要监控的数据源
	 * @param interval 连接池检查时间间隔，单位s
	 */
	public PoolMonitorThread(PoolDataSource ds, int interval){
		this.ds = ds;
		this.interval = interval * 1000/100;
	}
	
	/** 线程运行 */
	public void run() {
		while (!stop) {
			this.ds.doCheck();
			try {
				for (int i = 0; i < interval; i++) {
					if (stop) break;
					sleep(100);
				}
			} catch (Exception e) {}
		}
		
		ds = null;
	}

	/** 是否停止 */
	public boolean isStop() {
		return stop;
	}
	
	/** 是否停止 */
	public void setStop(boolean stop) {
		this.stop = stop;
	}
	
	
}
