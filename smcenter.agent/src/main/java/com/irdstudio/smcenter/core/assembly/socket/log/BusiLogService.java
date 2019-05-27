package com.irdstudio.smcenter.core.assembly.socket.log;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.TConnPool;

/**
 * 日志缓冲区,所有日志先写入此缓冲区
 * 由后台线程决定何时写入到日志表
 * @author 李广民
 * @version 1.0
 * @date 2008-11-04
 */
public class BusiLogService {
	
	private static List logBuffer1 = new ArrayList();	//日志缓冲区1
	private static List logBuffer2 = new ArrayList();	//日志缓冲区2,轮流接收日志信息
	private static boolean isSwitch = false;			//用于表明是否已经切换日志缓冲区
	private static Logger logger = Logger.getLogger("[交易接口]:[交易日志]");
	private static BusiLogWriterThread logWriter = null;		//用于写日志的线程
	private Connection conn = null;						//专用于写日志的线程
	private Statement stmt = null;						//用于批量写日志
	private int interval = 60;							//用于控制日志线程写日志的间隔日志,单位:秒,该默认值为1分钟

	/**
	 * 日志服务对外服务的主方法
	 * @param obj
	 */
	public static void addLog(AbstractBusiLog log){
		if (isSwitch) {
			logBuffer2.add(log);
		} else {
			logBuffer1.add(log);
		}
	}
	
	/**
	 * 将缓冲区的日志写入到日志表中
	 * 并根据缓冲区日志的多少情况重新分配日志线程写日志的间隔
	 */
	public int doWrite() {
		
		//logger.debug("开始读缓冲区日志...");
		List logBuffer = isSwitch ? logBuffer2 : logBuffer1;
		isSwitch = !isSwitch;
		
		if (logBuffer.size() > 0) {
			if (!initDatabaseObject()) {
				logger.error("交易日志服务写日志线程获取不到连接");
				return interval;
			}
		} else {
			return interval;
		}
		
		for (int i = 0; i < logBuffer.size(); i++) {
			BusiLogParser logParser = new BusiLogParser(
					(AbstractBusiLog) logBuffer.get(i));
			//logger.debug("开始写交易日志...");
			//logger.debug(logParser.getInsertSql());
			writeToLogTable(logParser.getInsertSql());
		}
		
		commit();
		logger.info("写入[" + logBuffer.size() + "]条交易记录");		
		logBuffer.clear();
		
		return interval;
	}
	
	/**
	 * 通知日志服务立即将日志写入数据库
	 * @return
	 */
	public static int flush(){
		//return doWrite();
		return 0;
	}
	
	/**
	 * 初始化数据库连接对象
	 * @return
	 */
	private boolean initDatabaseObject(){
		try {
			conn = TConnPool.getDefaultPool().getConnection();
			conn.setAutoCommit(false);
			if(stmt == null){
				stmt = conn.createStatement();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 执行SQL,将数据写到数据库
	 * @param sql
	 */
	private void writeToLogTable(String sql){
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 执行Commit
	 */
	private void commit(){
		try {
			if (stmt != null) {
				conn.commit();
				stmt.close();
				stmt = null;
			}
			conn.setAutoCommit(true);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 禁止外部实例化交易服务总线
	 */
	private BusiLogService(){

	}
	
	/**
	 * 启动交易服务的方法
	 */
	public static void startService(){
		logger.info("交易日志服务启动...");
		logWriter = new BusiLogWriterThread(new BusiLogService(), 60);
		logWriter.start();
	}
	
}
