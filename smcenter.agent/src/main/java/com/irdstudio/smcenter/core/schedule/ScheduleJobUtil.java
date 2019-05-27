package com.irdstudio.smcenter.core.schedule;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.irdstudio.smcenter.core.init.AgentInstInfo;
import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.TConnPool;

/**
 * 定时任务作业辅助类
 * @author guangming.li
 * @date 2017-10-30
 */
public class ScheduleJobUtil {
	
	/** 作业状态：初始化 */
	public final static int STATE_INITIALIZE = 0;
	/** 作业状态：运行中 */
	public final static int STATE_RUNNING = 1;
	/** 作业状态：成功完成 */
	public final static int STATE_END = 2;
	/** 作业状态：失败 */
	public final static int STATE_ERROR = 9;	
	/** 日志输出对象 */
	private static Logger logger = Logger.getLogger(ScheduleJobUtil.class);	
	/* 默认作业组名称 */
	public static String JOB_GROUP_NAME = "AGENT_JOB_GROUP";
	/* 默认动态触发器名称 */
	public static String TRIGGER_NAME = "AGENT_TRIGGER";
	/* 默认触发器组名称 */
	public static String TRIGGER_GROUP_NAME = "AGENT_TRIGGER_GROUP";

	/**
	 * 装载并初始化当前应用服务所需要执行的定时任务
	 * 以IP地址为最基础的粒度来判断,对于同一台机器部署两个服务的情况暂不支持
	 */
	public static void loadJobsForAgent() {
		
		logger.info("开始加载Agent所需要执行的定时任务...");
//		boolean isHaveTask = false;
		Connection conn = null;
		try {
			conn = TConnPool.getDefaultPool().getConnection();
			// 从数据库中读取所有的定时任务配置信息
			SSrvsCronConfDao cronConfDao = new SSrvsCronConfDao(conn);
			List<SSrvsCronConf> cronConfList =  cronConfDao.querySSrvsCronConfWithAgentId(AgentInstInfo.AGENT_ID);
			for(SSrvsCronConf cronConf :cronConfList){
				// 与AgentId相匹配,说明需要执行
				if(AgentInstInfo.AGENT_ID.equals(cronConf.getAgentId())){
					// 如果有匹配到任务，标志为为有任务								
//					isHaveTask = true;
					logger.info("加入定时任务：" + cronConf.getJobDesc() + "...");
					//"0 */1 * * * ?"
					QuartzManager.addJob(cronConf.getJobCode(), JOB_GROUP_NAME,
							TRIGGER_NAME + "-" + cronConf.getJobCode(), TRIGGER_GROUP_NAME,
							QuartzJobEngine.class,
							cronConf.getCronExpression());				
					// 在实例表中写入实例数据
					SSrvsCronInstDao instDao = new SSrvsCronInstDao(conn);
					instDao.initializeInst(cronConf.getJobCode());
				}
			}
//			
//			// 启动任务管理器
//			if(isHaveTask){
//				logger.info("启动Quartz任务管理器...");
//				QuartzManager.startJobs();
//			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			TConnPool.getDefaultPool().releaseConnection(conn);
		}
		logger.info("加载Agent所需要执行的定时任务结束!");
	}
	
	/**
	 * 停止所有定时任务
	 */
	public static void stopJobsForAgent() {
		logger.info("开始停止Agent所执行的定时任务...");
		try {
			QuartzManager.shutdownJobs();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("停止Agent所执行的定时任务结束!");
	}
}
