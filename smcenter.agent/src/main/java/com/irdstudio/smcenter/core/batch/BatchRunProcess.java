package com.irdstudio.smcenter.core.batch;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import com.irdstudio.smcenter.core.batch.dao.BatBatchInfoConfig;
import com.irdstudio.smcenter.core.batch.dao.BatBatchStageConfig;
import com.irdstudio.smcenter.core.batch.dao.BatBatchStageConfigDao;
import com.irdstudio.smcenter.core.batch.dao.BatInstBatch;
import com.irdstudio.smcenter.core.batch.dao.BatInstBatchDao;
import com.irdstudio.smcenter.core.batch.dao.BatInstTask;
import com.irdstudio.smcenter.core.batch.dao.BatInstTaskDao;
import com.irdstudio.smcenter.core.batch.dao.BatTaskUnitConfig;
import com.irdstudio.smcenter.core.batch.dao.BatTaskUnitConfigDao;
import com.irdstudio.smcenter.core.init.AgentInstInfo;
import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.TConnPool;
import com.irdstudio.smcenter.core.tinycore.log.ILogger;
import com.irdstudio.smcenter.core.tinycore.log.LoggerFactory;
import com.irdstudio.smcenter.core.tinycore.log.TLogger;
import com.irdstudio.smcenter.core.tinycore.thread.TThreadPools;
import com.irdstudio.smcenter.core.util.date.CurrentDateUtil;
import com.irdstudio.smcenter.core.util.pub.Convert;

/**
 * 批次运行过程(主线程)
 * 
 * @author guangming.li
 * @version 1.0
 * @date 2014-04-25
 */
public class BatchRunProcess extends Thread {

	/* 批次标识 */
	private String batchId = "";
	/* 批次配置信息 */
	private BatBatchInfoConfig batchConfig = null;
	/* 批次运行实例信息 */
	private BatInstBatch batchInst = null;
	/* 批次任务配置信息 */
	private Map<String, BatTaskUnitConfig> taskConfigMap = null;

	/* 批次运行状态 ,用于控制运行过程 */
	private int runState = BatchConstant.BATCH_STATE_INIT;
	/* 批次最大并发任务数 */
	private int equallyTaskAmount = 0;
	/* 批次已启动任务数（线程数） */
	private static Integer taskThreadAmount = 0;

	/* 本线程中使用的连接对象 */
	private Connection conn = null;

	/* 本批次的日志记录者(文件文件) */
	private ILogger logger = null;

	/* 本批次的任务运行线程池 */
	private ExecutorService threadPool = null;

	/**
	 * 批次运行过程构造函数
	 * 
	 * @param batchConfig
	 */
	public BatchRunProcess(BatBatchInfoConfig batchConfig) {
		this.batchConfig = batchConfig;
	}

	/**
	 * 批次运行过程构造函数(根据批次标识来构造)
	 * 
	 * @param batchId
	 */
	public BatchRunProcess(String batchId) {
		this.batchId = batchId;
	}

	/**
	 * 开始运行批次
	 */
	public void run() {

		// 初始化用于更新批次实例状态的DAO变量
		BatInstBatchDao batchInstDao = null;

		try {

			conn = TConnPool.getDefaultPool().getConnection();

			// 初始化批配置信息及批次实例信息
			if (batchConfig == null) {
				BatchDataUtil.getBatBatchInfoConfig(conn, batchId);
			} else {
				batchId = batchConfig.getBatchId();
			}
			batchInst = BatchDataUtil.getBatInstBatch(conn, batchId);
			if (batchInst == null) {
				throw new SQLException("未读取到批次实例:" + batchId);
			}

			// 初始化批次任务运行线程池
			threadPool = TThreadPools.getThreadPool(batchInst.getBatchSerialNo());

			// 初始化批次日志记录者对象(以批次流水号作为文件名)
			String fullLogFileName = AgentInstInfo.BATCH_LOG_PATH + File.separator + batchInst.getBatchSerialNo() + ".log";
			System.err.println(fullLogFileName);
			ILogger fileLogger = LoggerFactory.makeTxtFileLogger(fullLogFileName, true);
			fileLogger.setName(batchInst.getBatchSerialNo());
			TLogger.registerCategoryLogger(batchInst.getBatchSerialNo(), fileLogger);
			logger = TLogger.getLogger(batchInst.getBatchSerialNo());
			logger.info("批次开始...");

			/* 设置批次最大可并发任务数 */
			equallyTaskAmount = batchConfig.getEquallyTaskAmount();
			/* 设置批次开始时间 */
			batchInst.setStartTime(CurrentDateUtil.getTodayDateEx2());

			// 设置查询条件

			String szCondition = "where batch_id = '" + batchId + "' ";
			// 查询批次下所有任务配置信息放入内存中
			taskConfigMap = new Hashtable<String, BatTaskUnitConfig>();
			BatTaskUnitConfigDao taskConfigDao = new BatTaskUnitConfigDao(conn);
			// 2017-9-12 针对跑批任务设置任务使用状态为正常才可以执行
			List<BatTaskUnitConfig> btucList = taskConfigDao.queryWithCond(szCondition, " and task_use_state='0'");
			for (int k = 0; k < btucList.size(); k++) {
				taskConfigMap.put(btucList.get(k).getTaskId(), btucList.get(k));
			}

			// 1.查询出批出拥有的阶段，阶段编号从小到大排序。
			BatBatchStageConfigDao stageDao = new BatBatchStageConfigDao(conn);
			String szOrderBy = "order by stage_id asc";
			List<BatBatchStageConfig> stageList = stageDao.queryWithCond(szCondition, szOrderBy);

			// 2.按小到大循环所有阶段，执行任务
			batchInstDao = new BatInstBatchDao(conn);
			for (int i = 0; i < stageList.size(); i++) {
				String stageId = stageList.get(i).getStageId();
				logger.info("Stage:" + stageId);
				// 执行该阶段下所有任务(直至完成或批次失败)
				batchInst.setStageId(stageId);
				batchInstDao.updateStage(batchInst);
				while (!doFinishStageAllTask(conn, stageId) && runState != BatchConstant.BATCH_STATE_ERROR) {
					// 如果还没有完成(睡眠10秒后继续)
					logger.info(Thread.currentThread().getName() + " Sleep 10 second!!!");
					Thread.sleep(10 * 1000);
				}
				// 判断批次状态是否为“出错”
				if (runState == BatchConstant.BATCH_STATE_ERROR) {
					/* 设置批次状态为出错 */
					batchInst.setBatchState(String.valueOf(runState));
					batchInstDao.updateBatchToEnd(batchInst);
					break;
				}
			}
			// 3.如果如果批次状态为无错误执行到此，说明执行完成
			if (runState != BatchConstant.BATCH_STATE_ERROR) {
				batchInst.setBatchState(String.valueOf(BatchConstant.BATCH_STATE_FINISHED));
				batchInstDao.updateBatchToEnd(batchInst);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			runState = BatchConstant.BATCH_STATE_ERROR;
			/* 设置批次状态为出错 */
			batchInst.setBatchState(String.valueOf(runState));
			batchInst.setRemark(e.getMessage());
			try {
				if (batchInstDao != null)
					batchInstDao.updateBatchToEnd(batchInst);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			TConnPool.getDefaultPool().releaseConnection(conn);
			TThreadPools.showdownThreadPool(batchInst.getBatchSerialNo());
			logger.info("批次结束..." + Thread.currentThread().getName());

			// 不成功，则发送消息通知管理员
			if (BatchConstant.BATCH_STATE_FINISHED != runState) {
				String content = "[名称：" + batchInst.getBatchName() + "，编号：" + batchId + "，实例：" + batchInst.getBatchSerialNo() + "，日期：" + batchInst.getBatchDate() + "]";
//				MsgService msgService = (MsgService) SpringContextUtils.getBean("msgService");
//				if (!msgService.sendSystemSms(content)) {
//					logger.info("批次失败预警信息发送失败!");
//				}
			}
		}
	}

	/**
	 * 执行阶段下的所有任务(可执行的)
	 * 
	 * @param conn
	 * @param stageId
	 * @return
	 * @throws SQLException
	 */
	private boolean doFinishStageAllTask(Connection conn, String stageId) throws SQLException {

		boolean bFinished = true;

		// 是否已经达到最大并发数,如果已经达到,直接退出
		synchronized (taskThreadAmount) {
			if (taskThreadAmount >= equallyTaskAmount) {
				return false;
			}
		}

		// 查询当前阶段所有的任务
		String allTaskCond = "where batch_id = '" + batchId + "' and stage_id = '" + stageId + "' order by task_run_state desc,task_id asc";
		BatInstTaskDao instTaskDao = new BatInstTaskDao(conn);
		List<BatInstTask> allTaskList = instTaskDao.queryWithCond(allTaskCond, "");

		// 判断有无可执行的任务,如有则执行
		for (int i = 0; i < allTaskList.size(); i++) {
			// 判断当前任务是否可开始
			BatInstTask taskInst = allTaskList.get(i);
			BatTaskUnitConfig taskConf = taskConfigMap.get(taskInst.getTaskId());
			// 通过该任务状态判断并获得最新批次运行状态
			this.runState = getBatchRunStateWithTask(taskInst, taskConf);
			if (runState == BatchConstant.BATCH_STATE_ERROR) {
				// 批次运行失败,直接退出并由上层处理
				return false;
			} else {
				// 判断任务是否可运行,如果可以，启动线程动作任务
				if (isTaskCanRun(taskInst, taskConf)) {
					// 启动一个线程运行任务(判断是否达到最大并发,如达到则直接退出函数)
					synchronized (taskThreadAmount) {
						if (taskThreadAmount >= equallyTaskAmount) {
							return false;
						}
						// 任务线程数加1
						taskThreadAmount++;
						logger.info("任务线程数：" + taskThreadAmount);
						BatchTaskThread taskThread = new BatchTaskThread(batchInst, taskInst, taskConf);
						if (threadPool == null || threadPool.isTerminated())
							threadPool = TThreadPools.getThreadPool(batchInst.getBatchSerialNo());
						threadPool.execute(taskThread);
					}
					bFinished = false;
				} else {
					// 不可以运行,判断是否已经运行成功后继续判断下一个
					bFinished = bFinished && isTaskRunSuccess(taskInst);
					continue;
				}
			}
		}
		return bFinished;
	}

	/**
	 * 判断任务是否可以运行
	 * 
	 * @param taskInst
	 * @param taskConf
	 * @return
	 * @throws SQLException
	 */
	private boolean isTaskCanRun(BatInstTask taskInst, BatTaskUnitConfig taskConf) throws SQLException {
		boolean isCanRun = false;
		int state = Convert.StrToInt(taskInst.getTaskRunState(), BatchConstant.TASK_STATE_FAILD);
		if (state == BatchConstant.TASK_STATE_UNRUN || state == BatchConstant.TASK_STATE_WTRUN) {
			// 未执行及待执行的,判断如果无前置任务则直接执行
			if (taskConf != null) {
				if (taskConf.getPreviousTaskId() == null || "".equals(taskConf.getPreviousTaskId())) {
					// 说明无前置任务，可以启动任务
					isCanRun = true;
				} else {
					// 判断前置任务是否完成
					if (isPreviousTaskOk(taskConf.getPreviousTaskId())) {
						// 说明前置任务完成，可以启动任务
						isCanRun = true;
					}
				}
			}
		} else if (BatchConstant.TASK_STATE_SUCCESS == state || BatchConstant.TASK_STATE_RUNNING == state || BatchConstant.TASK_STATE_OTRUNING == state || BatchConstant.TASK_STATE_WRNRUNING == state) {
			// 执行成功、执行中、警告执行中、超时执行等说明不可运行
			isCanRun = false;
		}
		return isCanRun;
	}

	/**
	 * 根据任务状态判断批次的运行状态
	 * 
	 * @param taskInst
	 * @param taskConf
	 * @return
	 */
	private int getBatchRunStateWithTask(BatInstTask taskInst, BatTaskUnitConfig taskConf) {
		int batchState = runState;
		int state = Convert.StrToInt(taskInst.getTaskRunState(), BatchConstant.TASK_STATE_FAILD);
		if (BatchConstant.TASK_STATE_FAILD == state) {
			// 执行失败的，根据策略判断是否可以跳过,如果不可以跳过，则批次失败
			int skipTactic = Convert.StrToInt(taskConf.getTaskSkipTactic(), BatchConstant.TASK_SKIP_TACTIC_FORBID);
			if (skipTactic != BatchConstant.TASK_SKIP_TACTIC_AUTO) {
				// 如果为手动，看干预状态是否为“置过”
				if (skipTactic == BatchConstant.TASK_SKIP_TACTIC_MANUAL) {
					int interveneState = Convert.StrToInt(taskInst.getTaskInterveneState());
					// 不为置过，置批次状态为失败
					if (interveneState != BatchConstant.TASK_INTERVENE_STATE_PASS) {
						batchState = BatchConstant.BATCH_STATE_ERROR;
					}
				} else {
					batchState = BatchConstant.BATCH_STATE_ERROR;
				}
			}
		} else {
			batchState = BatchConstant.BATCH_STATE_RUNNING;
		}
		return batchState;
	}

	/**
	 * 用于判断前置任务是否执行成功
	 * 
	 * @param previousTaskId
	 * @return
	 * @throws SQLException
	 */
	private boolean isPreviousTaskOk(String previousTaskId) throws SQLException {
		BatInstTaskDao instTaskDao = new BatInstTaskDao(this.conn);
		BatInstTask taskInst = instTaskDao.queryWithKeys(batchInst.getBatchId(), batchInst.getBatchOrder(), previousTaskId);
		return isTaskRunSuccess(taskInst);
	}

	/**
	 * 判断任务是否运行成功
	 * 
	 * @param taskInst
	 * @return
	 */
	private boolean isTaskRunSuccess(BatInstTask taskInst) {
		int state = Convert.StrToInt(taskInst.getTaskRunState(), BatchConstant.TASK_STATE_FAILD);
		int interveneState = Convert.StrToInt(taskInst.getTaskInterveneState(), BatchConstant.TASK_INTERVENE_STATE_NO);
		return state == BatchConstant.TASK_STATE_SUCCESS || interveneState == BatchConstant.TASK_INTERVENE_STATE_PASS;
	}

	/**
	 * 任务线程数增加或减少 供任务线程类进行线程的增加或减少
	 * 
	 * @param i
	 */
	public static void doTaskTheadCalc(int i) {
		synchronized (taskThreadAmount) {
			taskThreadAmount = taskThreadAmount + i;
		}
	}
}
