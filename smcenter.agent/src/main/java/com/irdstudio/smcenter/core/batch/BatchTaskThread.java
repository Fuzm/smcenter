package com.irdstudio.smcenter.core.batch;

import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;

import com.irdstudio.smcenter.core.assembly.plugin.PluginConst;
import com.irdstudio.smcenter.core.assembly.plugin.PluginContext;
import com.irdstudio.smcenter.core.assembly.plugin.PluginExecutor;
import com.irdstudio.smcenter.core.assembly.plugin.PluginLogService;
import com.irdstudio.smcenter.core.batch.dao.BatInstBatch;
import com.irdstudio.smcenter.core.batch.dao.BatInstTask;
import com.irdstudio.smcenter.core.batch.dao.BatTaskUnitConfig;
import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.TConnPool;
import com.irdstudio.smcenter.core.tinycore.log.ILogger;
import com.irdstudio.smcenter.core.tinycore.log.TLogger;
import com.irdstudio.smcenter.core.util.date.DateConst;
import com.irdstudio.smcenter.core.util.date.DateVerify;
import com.irdstudio.smcenter.core.util.pub.Convert;

/**
 * 批次任务运行线程
 * 
 * @author guangming.li
 * @version 1.0
 * @date 2014-04-26
 */
public class BatchTaskThread extends Thread {

	/* 批次运行实例信息 */
	private BatInstBatch batchInst = null;
	/* 任务实例 */
	private BatInstTask taskInst = null;
	/* 任务配置 */
	private BatTaskUnitConfig taskConf = null;
	/* 文件日志记录者 */
	private ILogger logger = null;
	/* 批次任务日志服务对象(数据库) */
	private PluginLogService taskLogService = null;
	/* 任务执行类型-普通任务 */
	public final static int TASK_RUN_TYPE_NORMAL = 1;
	/* 任务执行类型-轮询任务 */
	public final static int TASK_RUN_TYPE_CYCLE = 2;
	/* 任务执行类型-定时任务 */
	public final static int TASK_RUN_TYPE_CRON = 3;

	/**
	 * 构造构造函数
	 * 
	 * @param batchInst
	 * @param taskInst
	 * @param taskConf
	 * @param subsCode
	 */
	public BatchTaskThread(BatInstBatch batchInst, BatInstTask taskInst, BatTaskUnitConfig taskConf) {
		this.batchInst = batchInst;
		this.taskInst = taskInst;
		this.taskConf = taskConf;
		this.taskLogService = PluginLogService.getInstance();
	}

	/**
	 * 线程执行函数
	 */
	public void run() {

		// 使用批次的日志记录者
		logger = TLogger.getLogger(batchInst.getBatchSerialNo());

		// 根据不同的任务执行类型进行不同的执行方式
		int taskRunType = Convert.StrToInt(taskConf.getTaskRunType());
		switch (taskRunType) {
		case BatchTaskThread.TASK_RUN_TYPE_NORMAL:
			// 执行普通任务
			doNomalTask();
			break;
		case BatchTaskThread.TASK_RUN_TYPE_CYCLE:
			// 执行轮询任务
			doCycleTask();
			break;
		case BatchTaskThread.TASK_RUN_TYPE_CRON:
			// 执行定时任务
			doCronTask();
			break;
		}

		// 通知批次线程(主线程)任务线程-1
		BatchRunProcess.doTaskTheadCalc(-1);

	}

	/**
	 * 执行普通任务
	 */
	private void doNomalTask() {

		logger.debug("批次普通任务:" + taskInst.getTaskName() + "开始运行...");

		PluginContext ctx = newPluginContext();
		if (callPlugin(ctx)) {
			// 执行成功，修改任务状态为"成功"
			taskLogService.updateBatchTaskToEnd(taskConf, ctx.getStartMills(), PluginConst.TASK_STATE_SUCCESS);
		} else {
			// 执行失败,修改任务状态为"失败"
			taskLogService.updateBatchTaskToEnd(taskConf, ctx.getStartMills(), PluginConst.TASK_STATE_FAILD);
		}

		logger.debug("批次普通任务:" + taskInst.getTaskName() + "运行完成");
	}

	/**
	 * 以定期的方式执行任务
	 */
	private void doCronTask() {

		logger.debug("批次定时任务:" + taskInst.getTaskName() + "开始运行...");

		// 如果没有定期类型值,则不执行并直接置为失败
		if (null == taskConf.getTaskCycleType() || "".equals(taskConf.getTaskCycleType())) {
			// 执行失败,修改任务状态并退出
			taskLogService.updateBatchTaskToEnd(taskConf, System.currentTimeMillis(), PluginConst.TASK_STATE_FAILD);
		} else {

			boolean isCanExecute = false;
			// 判断是否月末，如果是，则执行，否则直接置过
			if (BatchConstant.BAT_CYCLE_TYPE_M.equals(taskConf.getTaskCycleType())) {
				isCanExecute = DateVerify.isDateTail(taskInst.getBatchDate(), DateConst.MONTH);
			} else if (BatchConstant.BAT_CYCLE_TYPE_S.equals(taskConf.getTaskCycleType())) {
				// 判断是否为季末，如果是，则执行，否则直接置过
				isCanExecute = DateVerify.isDateTail(taskInst.getBatchDate(), DateConst.SEASON);
			} else if (BatchConstant.BAT_CYCLE_TYPE_Y.equals(taskConf.getTaskCycleType())) {
				// 判断是否为年末，如果是，则执行，否则直接置过
				isCanExecute = DateVerify.isDateTail(taskInst.getBatchDate(), DateConst.YEAR);
			} else if (BatchConstant.BAT_CYCLE_TYPE_W.equals(taskConf.getTaskCycleType())) {
				// 判断是否为周末，如果是，则执行，否则直接置过
				isCanExecute = DateVerify.isDateTail(taskInst.getBatchDate(), DateConst.MONTH);
			}

			// 如果可以执行,则以执行结果为准,否则直接置过
			if (isCanExecute) {
				PluginContext ctx = newPluginContext();
				if (callPlugin(ctx)) {
					// 执行成功，修改任务状态为"成功"
					taskLogService.updateBatchTaskToEnd(taskConf, ctx.getStartMills(), PluginConst.TASK_STATE_SUCCESS);
				} else {
					// 执行失败,修改任务状态为"失败"
					taskLogService.updateBatchTaskToEnd(taskConf, ctx.getStartMills(), PluginConst.TASK_STATE_FAILD);
				}
			} else {
				// 说明未到执行时候,修改任务状态为"失败"，但干预状态为置过
				taskLogService.updateBatchTaskToEndEx(taskConf, System.currentTimeMillis(), PluginConst.TASK_STATE_FAILD);
			}
		}

		logger.debug("批次定时任务:" + taskInst.getTaskName() + "运行完成");
	}

	/**
	 * 以轮询方式执行任务
	 */
	private void doCycleTask() {

		logger.debug("批次轮询任务:" + taskInst.getTaskName() + "开始运行...");

		/* 累计轮询时长 */
		int accTime = 0;
		/* 轮询间隔 */
		int cycleInteval = taskConf.getCycleInteval();
		/* 最长轮询时长 */
		int maxWaitTime = taskConf.getMaxWaitTime();

		// 开始轮询式执行任务,直到满足退出条件
		PluginContext ctx = newPluginContext();
		boolean bResult = callPlugin(ctx);
		while (!bResult && !(maxWaitTime > 0 && accTime >= maxWaitTime)) {
			// 根据轮询间隔进行休眠
			try {
				accTime += cycleInteval;
				Thread.sleep(cycleInteval * 1000);

				/** modify by caijiufa 20181205，解决轮询任务无法退出的问题 **/
				String state = BatchDataUtil.getTaskState(taskInst.getTaskId());
				if (PluginConst.TASK_INT_STATE_SKIP.equals(state)) {// 如果已经置过则执行结果为成功,否则继续轮询
					bResult = true;
					break;
				}
				if (PluginConst.TASK_INT_STATE_EXIT.equals(state)) {// 如果已经强退则执行结果为失败
					bResult = false;
					break;
				}
				bResult = callPlugin(ctx);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (bResult) {
			// 执行成功，修改任务状态为"成功"
			taskLogService.updateBatchTaskToEnd(taskConf, ctx.getStartMills(), PluginConst.TASK_STATE_SUCCESS);
		} else {
			// 执行失败,修改任务状态为"失败"
			taskLogService.updateBatchTaskToEnd(taskConf, ctx.getStartMills(), PluginConst.TASK_STATE_FAILD);
		}

		logger.debug("批次轮询任务:" + taskInst.getTaskName() + "运行完成");
	}

	/**
	 * 设置应用插件上下文
	 * 
	 * @return
	 */
	private PluginContext newPluginContext() {

		PluginContext ctx = new PluginContext();

		/* 应用插件ID 用于唯一标识一个插件，调用程序根据应用插件ID调用相应的插件实现类 */
		ctx.nPluginId = taskConf.getPluginId();
		/* 插件配置数据来源方式 1,从数据库中读取;2,从配置文件中读取 */
		ctx.nPluginConfType = Convert.StrToInt(taskConf.getPluginSourceType(), PluginConst.CONFIG_DATA_FROM_DB);
		/* 插件配置数据来源标识 用于唯一标识一组配置数据 */
		ctx.szPluginConfId = taskConf.getPluginParaFlag();
		/* 批次流水号,表明是由哪个批次调起(用于记日志) */
		ctx.szBatchSn = batchInst.getBatchSerialNo();
		/* 批次标识,表明是由哪个批次调起(用于记日志) */
		ctx.szBatchId = batchInst.getBatchId();
		/* 任务编号,表明是批次的那个任务调起(用于记日志) */
		ctx.szTaskId = taskInst.getTaskId();
		/* 任务名称,表明是批次的那个任务调起(用于记日志) */
		ctx.szTaskName = taskInst.getTaskName();
		/* 所属子系统代码 */
		ctx.szSubsCode = batchInst.getSubsCode();
		/* 所属子系统的数据源代码-用于标识使用哪一个数据源 */
		ctx.szSubsDsCode = taskConf.getSubsDsCode();

		return ctx;
	}

	/**
	 * 调用应用插件 返回成功或失败
	 * 
	 * @return
	 */
	private boolean callPlugin(PluginContext ctx) {

		// 获得参数并调用应用参数,中间发生异常则失败
		boolean bIsCallSuccess = false;

		try {
			// 将接收到的参数初始到应用插件上下文中
			// ctx = new PluginContext();

			// 启动连接池
			// PoolService.startWithIntervene(ctx.szConnStr, ctx.szDBUserId,
			// URPSCryptUtil.toDecryptWithNoException(ctx.szDBUserPwd),
			// ctx.szDBSchema);

			// 将任务状态修改为“执行中”
			taskLogService.updateBatchTaskToRunning(ctx.szTaskId);

			// 调用应用插件
			bIsCallSuccess = PluginExecutor.callPlugin(ctx);

		} catch (SQLException e) {
			bIsCallSuccess = false;
			e.printStackTrace();
		} catch (InstantiationException e) {
			bIsCallSuccess = false;
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			bIsCallSuccess = false;
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			bIsCallSuccess = false;
			e.printStackTrace();
		} catch (Exception e) {
			bIsCallSuccess = false;
			e.printStackTrace();
		} finally {
			if (StringUtils.isNotBlank(ctx.szSubsDsCode) && TConnPool.getPoolInst(ctx.szSubsDsCode) != null) {// 如果使用了子系统数据源，执行完后关闭
				TConnPool.getPoolInst(ctx.szSubsDsCode).clearAndFree();// 释放连接池
				TConnPool.removeConnInst(ctx.szSubsDsCode);
			}
		}

		return bIsCallSuccess;
	}
}
