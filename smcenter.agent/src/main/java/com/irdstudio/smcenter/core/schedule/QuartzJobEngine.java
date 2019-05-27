package com.irdstudio.smcenter.core.schedule;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.irdstudio.smcenter.agent.job.ScheduleJob;
import com.irdstudio.smcenter.core.init.AgentInstInfo;
import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.TConnPool;

/**
 * Quartz作业执行引擎(所有作业均由此类启动)
 * @author ligm
 * @date 2018-06-16
 */
public class QuartzJobEngine implements Job{
	
	private static Logger logger = Logger.getLogger(QuartzJobEngine.class);

	@Override
	public void execute(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		Connection conn = null;
		String result = "";
		boolean isHaveError = false;
		SSrvsCronInstDao instDao = null;
		String jobCode = jobExecutionContext.getJobDetail().getKey().getName();

		// 作业执行开始计时
		long startMills = System.currentTimeMillis();
		try {

			// 初始化并获取定时调度任务实例信息
			conn = TConnPool.getDefaultPool().getConnection();
			instDao = new SSrvsCronInstDao(conn);
			SSrvsCronInst cronInst = instDao.querySSrvsCronInstWithKey(jobCode);

			// 如果没有实例,说明尚未初始化,不可以运行，跳出
			if (cronInst == null) {
				logger.info(jobCode + "尚未初始化!");
				return;
			}

			// 如果实例正在运行,则跳过本次定时调度
			if (ScheduleJobUtil.STATE_RUNNING == cronInst.getState()) {
				StringBuffer sb = new StringBuffer(cronInst.getJobDesc());
				sb.append("(").append(jobCode).append(")正在由")
						.append(cronInst.getAgentId()).append("运行!");
				logger.info(sb);
				return;
			}

			// 如果当前Agent与要求的Agent不匹配,也不运行该定时调度任务,跳出
			if(!AgentInstInfo.AGENT_ID.equals(cronInst.getAgentId())){
				StringBuffer sb = new StringBuffer(cronInst.getJobDesc());
				sb.append("(").append(jobCode).append(")只能由")
						.append(cronInst.getAgentId()).append("运行!!!");
				logger.error(sb);
				return;
			}
			
			// 说明符合条件，从配置中读出作业执行类进行执行
			SSrvsCronConfDao confDao = new SSrvsCronConfDao(conn);
			SSrvsCronConf cronConf = confDao.querySSrvsCronConfWithKey(jobCode);
			if (cronConf == null) {
				logger.info(jobCode + "配置异常!");
				return;
			}
			
			// 将作业状态更新为正在执行中
			logger.info(new StringBuffer("开始执行作业[").append(jobCode).append(
					"]..."));
			instDao.updateStateToRunning(jobCode);
			logger.info("执行作业类:" + cronConf.getJobClass() + ",方法:" + cronConf.getJobMethod());
			if (ScheduleConstant.JOB_CLASS_HSF.equals(cronConf
					.getJobClassType())) {
				
				logger.info("HSF服务方法调用开始......");
				// 按HSF方式调用作业类
				/*HSFSpringConsumerBean consumerBean = new HSFSpringConsumerBean();
				consumerBean.setInterfaceName(cronConf.getJobClass());
				consumerBean.setVersion(cronConf.getServiceVersion());
				consumerBean.setGeneric("true"); // 设置 generic 为 true
				// 设置组别
				if (StringUtils.isNotEmpty(cronConf.getServiceGroup())) {
					consumerBean.setGroup(cronConf.getServiceGroup());
				} else {
					consumerBean.setGroup("HSF");
				}
				// 设置超时
				if (cronConf.getServiceTimeout() != null) {
					consumerBean.setClientTimeout(cronConf.getServiceTimeout().intValue());
				}

				//初始化
				consumerBean.syncInit();
				GenericService svc = (GenericService) consumerBean.getObject(); // 强转接口接口为 GenericService
				
				// 泛化接口调用
				Object ivkResult = svc.$invoke(cronConf.getJobMethod(), null, null);
				logger.info("service interface: " + cronConf.getJobClass() + ", service version: " + cronConf.getServiceVersion() + 
						", service group: " + cronConf.getServiceGroup() + ", service timeout: " + cronConf.getServiceTimeout() +
						", invoke result :" + ivkResult);
				logger.info("HSF服务方法调用结束！");*/
				
			} else if (ScheduleConstant.JOB_CLASS_DUBBO.equals(cronConf
					.getJobClassType())) {
				// 按DUBBO方式调用作业类
				
			} else {
				// 按本地类调用,可以是任意类(需要指定方法)
				logger.info("本地方法" + cronConf.getJobClass() + "调用开始......");
				Object job = Class.forName(cronConf.getJobClass()).newInstance();
				if(job != null && ScheduleJob.class.isInstance(job)) {
					((ScheduleJob) job).doExcetue(conn, cronConf);
					logger.info("ScheduleJob -- 本地方法调用结束！");
				} else {
					Method method = job.getClass().getMethod(cronConf.getJobMethod());
					method.invoke(job, null);
					logger.info("本地方法调用结束！");
				}
			}

			// 将作业状态更改为执行成功,并计算耗时,并将记录复制到历史表
			if (instDao != null) {
				instDao.updateStateToEnd(jobCode, startMills, isHaveError,
						result);
			}
			logger.info(new StringBuffer("执行作业[").append(jobCode).append(
					"]完成..."));

		} catch (SQLException e) {
			isHaveError = true;
			result = e.getMessage();
			e.printStackTrace();
			// 将作业状态更改为执行有错误,并计算耗时,并将记录复制到历史表
			if (instDao != null) {
				instDao.updateStateToEnd(jobCode, startMills, isHaveError,
						result);
			}
			logger.info(new StringBuffer("执行作业[").append(jobCode).append(
					"]发生错误..."));
		} catch (Exception e) {
			isHaveError = true;
			result = e.getMessage();
			e.printStackTrace();
			// 将作业状态更改为执行有错误,并计算耗时,并将记录复制到历史表
			if (instDao != null) {
				instDao.updateStateToEnd(jobCode, startMills, isHaveError,
						result);
			}
			logger.info(new StringBuffer("执行作业[").append(jobCode).append(
					"]发生错误..."));
		} finally {
			// 释放连接资源
			TConnPool.getDefaultPool().releaseConnection(conn);
		}
		
	}

}
