package com.irdstudio.smcenter.core.batch;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.irdstudio.smcenter.core.assembly.plugin.common.SSubsInfo;
import com.irdstudio.smcenter.core.batch.dao.BatBatchInfoConfig;
import com.irdstudio.smcenter.core.batch.dao.BatInstBatch;
import com.irdstudio.smcenter.core.batch.dao.BatInstTask;
import com.irdstudio.smcenter.core.batch.dao.BatInstTaskDao;
import com.irdstudio.smcenter.core.batch.dao.BatTaskUnitConfig;
import com.irdstudio.smcenter.core.batch.dao.BatTaskUnitConfigDao;
import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.TConnPool;
import com.irdstudio.smcenter.core.tinycore.jdbc.util.DataMoveUtil;
import com.irdstudio.smcenter.core.tinycore.jdbc.util.TableMove;
import com.irdstudio.smcenter.core.util.date.CurrentDateUtil;
import com.irdstudio.smcenter.core.util.date.DateCalculate;
import com.irdstudio.smcenter.core.util.date.DateConvert;
import com.irdstudio.smcenter.core.util.pub.ConnectionUtil;

/**
 * 批次运行引擎,用于按指定动作去运行指定批次
 * 
 * @author guangming.li & yuanping.zhang
 * @version 1.0
 * @date 2014-04-21
 */
public class BatchRunEngine {

	/* 批次运行(当天首次运行) */
	private final static String BATCH_RUN = "run";
	/* 批次停止(正在执行中的任务的后续任务置为挂起) */
	private final static String BATCH_STOP = "stop";
	/* 批次重跑(当天非首次运行) */
	private final static String BATCH_RERUN = "rerun";
	/* 批次续跑(从中断点开始跑) */
	private final static String BATCH_CONTINUE = "continue";

	/* 批次动作 */
	private String batchAction;
	/* 批次标识 */
	private String batchId;

	public BatchRunEngine(String batchAction, String batchId) {
		this.batchId = batchId;
		this.batchAction = batchAction.toLowerCase();
	}

	/**
	 * 开始运行
	 */
	public void start() throws Exception {

		// 根据动作进行判断后执行相应的批次运行方式
		if (BATCH_RUN.equals(batchAction)) {
			// 批次运行(当天首次运行)
			doRunBatch();
		} else if (BATCH_STOP.equals(batchAction)) {
			// 批次停止(正在执行中的任务的后续任务置为挂起)
			doStopBatch();
		} else if (BATCH_RERUN.equals(batchAction)) {
			// 批次重跑(当天非首次运行)
			doRerunBatch();
		} else if (BATCH_CONTINUE.equals(batchAction)) {
			// 批次续跑
			doContinueBatch();
		}
	}

	/**
	 * 批次运行(当天首次运行)
	 * 
	 * @throws Exception
	 */
	private void doRunBatch() throws Exception {
		Connection conn = null;
		try {

			// 1.取批次配置信息及子系统基本信息
			conn = TConnPool.getDefaultPool().getConnection();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
			BatBatchInfoConfig batchConfig = BatchDataUtil.getBatBatchInfoConfig(conn, batchId);
			// 1、批次基本信息与子系统基本信息数据完整性判断
			if (batchConfig == null) {
				throw new Exception("根据批次标识【" + batchId + "】查询批次配置表信息为空！");
			}
			String subsCode = batchConfig.getSubsCode();
			SSubsInfo sSubsInfo = BatchDataUtil.getSSubsInfo(conn, subsCode);
			if (StringUtils.isBlank(subsCode)) {
				throw new Exception("获取批次所属系统代码为空！");
			}
			if (sSubsInfo == null) {
				throw new Exception("根据子系统代码【" + subsCode + "】查询子系统基础信息为空！");
			}

			// 2、根据批次标识查询批次实例表bat_inst_batch
			BatInstBatch batchInst = BatchDataUtil.getBatInstBatch(conn, batchId);
			// 如果一个批次存在1个以上实例，说明异常。
			// 如果存在1个批次实例，则原本存在实例, 需要判断后再决定是否可以发起
			// 如果一个实例都没有，说明之前从未发起来，可直接运行该批次
			if (batchInst != null) {

				// 2.1如果存在批次实例且批次状态不为’9’，则抛出异常“批次尚未完成！”，批次一日多跑支持
				if (!"9".equals(batchInst.getBatchState()) && !"M".equals(batchConfig.getIsRunAgain())) {
					throw new Exception("批次尚未完成！");
				}
				// 2.2 子系统批次完成日期
				String subsBatDate = sSubsInfo.getSubsBatDate();
				if (StringUtils.isBlank(subsBatDate)) {
					throw new Exception("获取子系统批次完成日期为空！");
				}
				// 2.3 批次实例表中的批次日期
				String batchDate = batchInst.getBatchDate();
				if (StringUtils.isBlank(batchDate)) {
					throw new Exception("获取批次实例表中的批次日期为空！");
				}
				// 2.4比较:若子系统批次完成日期小于批次实例表中批次日期，则说明错误，抛出“上一日期的批次尚未运行成功！”
				if (format.parse(subsBatDate).before(format.parse(batchDate)) && !"M".equals(batchConfig.getIsRunAgain())) {
					throw new Exception("上一日期【" + subsBatDate + "】的批次尚未运行成功！");
				}

				// 2.5若子系统批次完成日期大于批次实例表中的批次日期，则错误，提示“上次批次完成日期异常，请检查！”
				if (format.parse(subsBatDate).after(format.parse(batchDate)) && !"M".equals(batchConfig.getIsRunAgain())) {
					throw new Exception("上次批次完成日期【" + subsBatDate + "】异常，请检查！");
				}
			}

			// 3、发起批次，移走上一批次实例及任务实例数据
			// 3.1根据[批次标识]将批次实例信息(bat_inst_batch)转移到实例历史表(bat_inst_batch_h)
			// 3.2根据[批次标识]将批次任务实例信息(bat_inst_task)转移到任务实例历史表(bat_inst_task_h)
			// 3.3根据[批次流水号]将批次任务的应用插件的执行日志(plugin_exec_log)转移到历史到(plugin_exec_log)
			// 设置数据转移条件
			String cond = " where batch_id = '" + batchId + "'";
			this.dataMove(conn, "bat_inst_batch", "bat_inst_batch_h", cond);
			this.dataMove(conn, "bat_inst_task", "bat_inst_task_h", cond);
			this.dataMove(conn, "plugin_exec_log", "plugin_exec_log", " where batch_sn like '" + batchId + "%'");

			// 4、实例化批次及实例化任务
			// 4.1从批次配置表中根据[批次标识]写入一条记录到批次实例表中。
			String condition1 = " where batch_id = '" + batchId + "'";
			TableMove batchDataMoveInst = DataMoveUtil.createTableMoveInst(conn, "bat_batch_info_config", "bat_inst_batch");
			// 批次实例的批次流水号batch_id + yyyyMMdd(上一批次完成日期+1) + 01
			String batchDate = DateConvert.toString(DateCalculate.addDays(DateConvert.toDateWithSeparate1(sSubsInfo.getSubsBatDate()), 1));
			// 首次跑设置批次流水号
			String preString = batchId + batchDate.replace("-", "");
			String no = "1";
			String batchSerialNo = preString + no;

			if ("M".equals(batchConfig.getIsRunAgain())) {// 定时任务批次的日期就按当前日期即可，批次一日多跑支持
				batchDate = DateConvert.toString(DateCalculate.addDays(DateConvert.toDateWithSeparate1(sSubsInfo.getSubsBatDate()), 0));
			}

			if (batchInst != null) {
				String bab = batchInst.getBatchSerialNo().substring(0, preString.length());
				no = batchInst.getBatchSerialNo().substring(preString.length(), batchInst.getBatchSerialNo().length());
				if ("M".equals(batchConfig.getIsRunAgain()) && bab.equals(preString)) {// 定时任务批次，批次一日多跑支持
					batchSerialNo = preString + (Integer.valueOf(no) + 1);
				}
			}

			batchDataMoveInst.addValueMapping("batch_serial_no", batchSerialNo);
			batchDataMoveInst.addValueMapping("batch_order", no);
			batchDataMoveInst.addValueMapping("batch_date", batchDate);
			batchDataMoveInst.addValueMapping("start_time", CurrentDateUtil.getTodayDateEx2());
			batchDataMoveInst.addValueMapping("batch_state", String.valueOf(BatchConstant.BATCH_STATE_INIT));
			batchDataMoveInst.addValueMapping("batch_intervene_state", String.valueOf(BatchConstant.TASK_INTERVENE_STATE_NO));
			batchDataMoveInst.setCondition(condition1);
			batchDataMoveInst.executeCopy();
			// 4.2、从批次任务表中根据[批次标识]及[执行场景标识]
			// [使用状态]写入批次标识下的任务到批次任务实例表中
			String sql1 = "insert into bat_inst_task(" + "batch_id,batch_order,batch_date,task_id,task_name,stage_id,stage_name," + "task_run_state,task_intervene_state,batch_serial_no)";
			String sql2 = "select a.batch_id,'" + no + "' as batch_order,'" + batchDate + "' as batch_date," + "a.task_id,a.task_name,a.stage_id,b.stage_name,	'" + BatchConstant.TASK_STATE_UNRUN + "' as task_run_state,'"
					+ BatchConstant.TASK_INTERVENE_STATE_NO + "' as task_intervene_state, '" + batchSerialNo + "' as batch_serial_no  from bat_task_unit_config a,bat_batch_stage_config b" + " where a.batch_id='" + batchId
					+ "' and a.task_use_state='0' and (a.task_use_area = '1' or a.task_use_area = '2')" + " and a.batch_id = b.batch_id  and a.stage_id = b.stage_id ";
			boolean moveFlag = DataMoveUtil.executeCustomCopy(conn, sql1, sql2);
			if (moveFlag) {
				// 5、执行批次调度
				BatchRunProcess batRunProcess = new BatchRunProcess(batchConfig);
				batRunProcess.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			TConnPool.getDefaultPool().releaseConnection(conn);
		}
	}

	/**
	 * 批次停止(正在执行中的任务的后续任务置为挂起)
	 */
	private void doStopBatch() throws Exception {
		Connection conn = null;
		try {
			conn = TConnPool.getDefaultPool().getConnection();
			// 1、根据[批次标识]查询批次配置表(bat_batch_info_config)，取得批次基本信息。
			BatBatchInfoConfig batBatchInfoConfig = BatchDataUtil.getBatBatchInfoConfig(conn, batchId);
			if (batBatchInfoConfig == null) {
				throw new Exception("根据批次标识查询批次配置表信息为空！");
			}
			// 获取当前正在执行的任务
			// 通过批次标识查询该批次下所有正在执行的任务
			BatInstTaskDao batInstTaskDao = new BatInstTaskDao(conn);
			// 设置查询条件
			String szCondition = "where batch_id = '" + batchId + "' and task_run_state = '2'";

			List<BatInstTask> batInstTaskList = batInstTaskDao.queryWithCond(szCondition, "");

			if (batInstTaskList.size() == 0) {
				throw new Exception("当前批次不存在正在执行的任务！");
			}

			Iterator<BatInstTask> it = batInstTaskList.iterator();
			while (it.hasNext()) {
				// 任务编号
				String taskId = it.next().getTaskId();
				// 查询以当前正在执行的任务作为前置任务的批次任务配置信息
				String condition = " where previous_task_id = '" + taskId + "'";
				BatTaskUnitConfigDao batTaskUnitConfigDao = new BatTaskUnitConfigDao(conn);
				List<BatTaskUnitConfig> batTaskUnitConfigList = batTaskUnitConfigDao.queryWithCond(condition, "");

				if (batTaskUnitConfigList.size() == 0) {
					continue;
				}

				// 通过批次标识、任务编号将后续任务的执行状态置为“执行失败”
				Iterator<BatTaskUnitConfig> batTaskUnitConfigIt = batTaskUnitConfigList.iterator();
				while (batTaskUnitConfigIt.hasNext()) {
					// 任务编号
					String nextTaskId = batTaskUnitConfigIt.next().getTaskId();
					// 设置修改条件
					String condi = " set task_run_state = '7' where batch_id = '" + batchId + "' and task_id = '" + nextTaskId + "'";

					batInstTaskDao.updateBatInstTask(condi);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			TConnPool.getDefaultPool().releaseConnection(conn);
		}
	}

	/**
	 * 批次重跑(当天非首次运行)
	 */
	private void doRerunBatch() throws Exception {
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			// 1、根据[批次标识]查询批次配置表(bat_batch_info_config)，取得批次基本信息。
			BatBatchInfoConfig batchConfig = BatchDataUtil.getBatBatchInfoConfig(conn, batchId);
			if (batchConfig == null) {
				throw new Exception("根据批次标识查询批次配置表信息为空！");
			}
			// 直接判断is_run_again字段,如果为’Y’则说明可以重跑，继续后续的步骤，
			// 如果为’N’，则说明该批次配置为不可以重跑，抛出异常“该批次不支持重
			// 跑！”
			String isRunAgain = batchConfig.getIsRunAgain();
			if (StringUtils.isBlank(isRunAgain)) {
				throw new Exception("查询是否允许重跑标识为空！");
			}
			if ("N".equals(isRunAgain)) {
				throw new Exception("该批次不支持重跑！");
			}

			// 2、根据[批次标识]查询批次实例表(bat_inst_batch)，是否存在正在运行的批次，
			// 如果不存在，则抛出异常“未找到重跑的批次！”，
			// 如果存在且批次状态为’3’（运行中）或’8’（初始），则抛出异常“批次正在运行中，不可以重跑!”
			BatInstBatch batchInst = BatchDataUtil.getBatInstBatch(conn, batchId);
			if (batchInst == null) {
				throw new Exception("未找到重跑的批次！");
			}
			// 批次状态
			String batchState = batchInst.getBatchState();
			if ("3".equals(batchState) || "8".equals(batchState)) {
				throw new Exception("批次正在运行中，不可以重跑！");
			}
			// 3、发起批次，移走上一批次实例及任务实例数据。
			// 根据[批次标识]将批次实例信息(bat_inst_batch)转移到实例历史表(bat_inst_batch_h)。
			// 根据[批次标识]将批次任务实例信息(bat_inst_task)转移到任务实例历史表(bat_inst_task_h)
			// 设置数据转移条件
			String cond = " where batch_id = '" + batchId + "'";
			this.dataMove(conn, "bat_inst_batch", "bat_inst_batch_h", cond);
			this.dataMove(conn, "bat_inst_task", "bat_inst_task_h", cond);

			// 4、实例化批次及实例化任务
			// 4.1根据原查询出来的批次实例信息，重新写一条记录到批次实例表中，基
			// 中批次日期一样，但批次流水号要重新生成，批次序号在原先的基础上加上。
			String condi1 = " where batch_id = '" + batchId + "'";
			TableMove batchDataMoveUtil = DataMoveUtil.createTableMoveInst(conn, "bat_inst_batch", "bat_inst_batch");
			batchDataMoveUtil.setCondition(condi1);
			// 设置数据映射
			// 1、批次流水号重新生成
			String batchSerialNo = batchInst.getBatchSerialNo();
			// 获取原批次流水号最后两位数
			String lastTwoSerial = batchSerialNo.substring(batchSerialNo.length() - 1, batchSerialNo.length());
			// 批次流水号在原批次流水号的最后两位序数加一
			if (Integer.parseInt(lastTwoSerial) < 10) {
				lastTwoSerial = "0" + (Integer.parseInt(lastTwoSerial) + 1);
			} else {
				lastTwoSerial = "" + (Integer.parseInt(lastTwoSerial) + 1);
			}
			batchSerialNo = batchSerialNo.substring(0, batchSerialNo.length() - 2) + lastTwoSerial;
			batchDataMoveUtil.addValueMapping("batch_serial_no", batchSerialNo);
			// 2、批次序号在原先基础上加上
			batchDataMoveUtil.addValueMapping("batch_order", batchInst.getBatchOrder() + 1);
			// 复制数据
			batchDataMoveUtil.executeCopy();

			// 4.2从批次任务表中根据[批次标识]及[]执行场景]查询出可重跑的任务写入到批次任务实例表中
			// 数据复制条件
			String condi = " where batch_id = '" + batchId + "' and task_use_state='0' and (task_use_area = '1' or task_use_area = '3') ";
			// 设置数据映射
			TableMove taskDataMoveUtil = DataMoveUtil.createTableMoveInst(conn, "bat_task_unit_config", "bat_inst_task");
			// 设置数据复制条件
			taskDataMoveUtil.setCondition(condi);
			// 复制数据
			taskDataMoveUtil.executeCopy();

			// 5、执行批次调度
			BatchRunProcess batRunProcess = new BatchRunProcess(batchConfig);
			batRunProcess.start();

		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.releaseConnection(conn);
		}
	}

	/**
	 * 批次续跑(从中断点继续跑)
	 */
	private void doContinueBatch() throws Exception {

		Connection conn = null;
		try {

			conn = TConnPool.getDefaultPool().getConnection();

			// 根据批次标识查询批次配置表(bat_batch_info_config),取得批次基本信息。
			BatBatchInfoConfig batchConfig = BatchDataUtil.getBatBatchInfoConfig(conn, batchId);
			if (batchConfig == null) {
				throw new Exception("根据批次标识查询批次配置表信息为空！");
			}

			// 重置失败的任务为待执行
			BatInstTaskDao taskInstDao = new BatInstTaskDao(conn);
			taskInstDao.resetFaildTask(batchId);

			// 执行批次调度
			BatchRunProcess batRunProcess = new BatchRunProcess(batchConfig);
			batRunProcess.start();

		} catch (Exception e) {
			throw e;
		} finally {
			TConnPool.getDefaultPool().releaseConnection(conn);
		}
	}

	/**
	 * 设置表数据迁移
	 * 
	 * @param conn
	 * @param srcTableName
	 * @param destTableName
	 */
	public void dataMove(Connection conn, String srcTableName, String destTableName, String cond) throws SQLException {
		// 实例化
		TableMove dataMoveInst = DataMoveUtil.createTableMoveInst(conn, srcTableName, destTableName);
		// 设置数据迁移条件
		dataMoveInst.setCondition(cond);
		// 将源表数据复制到目标表中，并将源表数据删除
		dataMoveInst.executeCut();
	}
}
