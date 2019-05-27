package com.irdstudio.smcenter.core.assembly.plugin;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.irdstudio.smcenter.core.assembly.plugin.dataload.PluginLoadResult;
import com.irdstudio.smcenter.core.batch.BatchConstant;
import com.irdstudio.smcenter.core.batch.dao.BatTaskUnitConfig;
import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.TConnPool;
import com.irdstudio.smcenter.core.util.date.CurrentDateUtil;
import com.irdstudio.smcenter.core.util.pub.Convert;
import com.irdstudio.smcenter.core.util.pub.JvmUtil;

/**
 * 实现应用插件的数据库日志操作
 * 包括执行日志，进度日志
 * @author guangming.li
 * @version 1.1
 * @since 1.0 2013-10-11 
 * @modify 2014-07-09 增加并发支持
 */
public class PluginLogService {
	
	/*实例*/
	private static PluginLogService instance = null;	

	// 禁止多实例
	private PluginLogService() {

	}
	
	/**
	 *  获取实例 
	 * @throws SQLException 
	 */
	public synchronized static PluginLogService getInstance() {
		if (instance == null) {
			try {
				instance = new PluginLogService();
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}

		}
		return instance;
	}
	
	/**
	 * 记录应用插件执行日志
	 * @param szKeyDesc
	 * @param szExecResult
	 * @param szDtlDesc
	 * @param szExecBeginTime
	 */
	public void writePluginExecLog(String szBatchSn, int nPluginId,
			String szPluginName, String szActionName, String szActionResult,
			String szActionDetailDesc, String szTaskId, String szTaskName) {
		// 截取日志详细信息的描述
		if (szActionDetailDesc == null) {
			szActionDetailDesc = "";
		} else {
			if (szActionDetailDesc.length() > 4000) {
				szActionDetailDesc = szActionDetailDesc.substring(0, 2000);
			}
		}
		if (szActionName == null) {
			szActionName = "";
		} else {
			if (szActionName.length() > 254) {
				szActionName = szActionName.substring(0, 126);
			}
		}
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = TConnPool.getDefaultPool().getConnection();
			ps = conn
					.prepareStatement("INSERT INTO plugin_exec_log (BATCH_SN,PLUGIN_ID,PLUGIN_NAME,"
							+ "ACTION_NAME,ACTION_RESULT,ACTION_DETAIL_INFO,RECORD_TIME,"
							+ "TASK_ID,TASK_NAME) VALUES(?,?,?,?,?,?,?,?,?)");
			ps.setString(1, szBatchSn);
			ps.setInt(2, nPluginId);
			ps.setString(3, szPluginName);
			ps.setString(4, szActionName);
			ps.setString(5, szActionResult);
			ps.setString(6, szActionDetailDesc);
			ps.setString(7, CurrentDateUtil.getTodayDateEx4());
			ps.setString(8, szTaskId);
			ps.setString(9, szTaskName);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			TConnPool.getDefaultPool().releaseConnection(conn);
		}
	}
	
	/**
	 * 将批次任务实例表中对应的任务更新为执行中
	 * @param szTaskId
	 * @param state
	 * @throws SQLException 
	 */
	public boolean updateBatchTaskToRunning(String szTaskId)
			throws SQLException {
		boolean bFlag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = TConnPool.getDefaultPool().getConnection();
			ps = conn
					.prepareStatement("UPDATE bat_inst_task set task_run_state=?,start_time=? where task_id=?");
			ps.setString(1, PluginConst.TASK_STATE_RUNNING);
			ps.setString(2, CurrentDateUtil.getTodayDateEx2());
			ps.setString(3, szTaskId);
			ps.execute();
			bFlag = true;
		} catch (SQLException e) {
			bFlag = false;
			throw new SQLException(e.getMessage());
		} finally {
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			TConnPool.getDefaultPool().releaseConnection(conn);
		}
		return bFlag;
	}	

	/**
	 * 更改批次任务实例表中的状态更新为结束(成功或失败)
	 * @param szTaskId
	 * @param startMills
	 * @param state
	 */
	public boolean updateBatchTaskToEnd(BatTaskUnitConfig taskConf, long startMills,
			String state) {
		boolean bFlag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer info = new StringBuffer();
		info.append("最大连接数：")
				.append(TConnPool.getDefaultPool().getConnectionCount())
				.append(",");
		info.append(JvmUtil.getJvmMemoryWithM());
		long endMills = System.currentTimeMillis();
		int skipTactic = Convert.StrToInt(taskConf.getTaskSkipTactic(),
				BatchConstant.TASK_SKIP_TACTIC_FORBID);
		try {
			conn = TConnPool.getDefaultPool().getConnection();
			ps = conn
					.prepareStatement("UPDATE bat_inst_task set task_run_state=?,end_time=?,cost_time=?,task_intervene_state=?,state_desc=? where task_id=?");
			ps.setString(1, state);
			ps.setString(2, CurrentDateUtil.getTodayDateEx2());
			ps.setBigDecimal(3, new BigDecimal(endMills - startMills)
					.divide(new BigDecimal(1000.00)));
			ps.setString(4,
							skipTactic == BatchConstant.TASK_SKIP_TACTIC_AUTO ? BatchConstant.TASK_INTERVENE_STATE_PASS_S
									: BatchConstant.TASK_INTERVENE_STATE_NO_S);
			ps.setString(5, info.toString());
			ps.setString(6, taskConf.getTaskId());
			ps.execute();
			bFlag = true;
		} catch (SQLException e) {
			bFlag = false;
			e.printStackTrace();
		} finally {
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			TConnPool.getDefaultPool().releaseConnection(conn);
		}
		return bFlag;
	}

	/**
	 * 更改批次任务实例表中的状态更新为结束(成功或失败，定期任务专用)
	 * @param szTaskId
	 * @param startMills
	 * @param state
	 */
	public boolean updateBatchTaskToEndEx(BatTaskUnitConfig taskConf,
			long startMills, String state) {
		boolean bFlag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer info = new StringBuffer();
		info.append("最大连接数：").append(
				TConnPool.getDefaultPool().getConnectionCount()).append(",");
		info.append(JvmUtil.getJvmMemoryWithM());
		long endMills = System.currentTimeMillis();
		try {
			conn = TConnPool.getDefaultPool().getConnection();
			ps = conn
					.prepareStatement("UPDATE bat_inst_task set task_run_state=?,start_time=?,end_time=?,cost_time=?,task_intervene_state=?,state_desc=? where task_id=?");
			ps.setString(1, state);
			ps.setString(2, CurrentDateUtil.getTodayDateEx2());
			ps.setString(3, CurrentDateUtil.getTodayDateEx2());
			ps.setBigDecimal(4, new BigDecimal(endMills - startMills)
					.divide(new BigDecimal(1000.00)));
			ps.setString(5, BatchConstant.TASK_INTERVENE_STATE_PASS_S);
			ps.setString(6, info.toString());
			ps.setString(7, taskConf.getTaskId());
			ps.execute();
			bFlag = true;
		} catch (SQLException e) {
			bFlag = false;
			e.printStackTrace();
		} finally {
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			TConnPool.getDefaultPool().releaseConnection(conn);
		}
		return bFlag;
	}

	/**
	 * 记录装载结果信息
	 * 先删除原有结果,后写入
	 * @param loadResult
	 */
	public boolean writeLoadResult(PluginLoadResult loadResult){
		boolean bFlag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = TConnPool.getDefaultPool().getConnection();
			ps = conn.prepareStatement("DELETE FROM PLUGIN_LOAD_RESULT WHERE BATCH_SERIAL_NO=? AND TABLE_NAME=?");
			ps.setString(1, loadResult.getBatchSerialNo());
			ps.setString(2, loadResult.getTableName());			
			ps.execute();
			ps.close();
			ps = conn
					.prepareStatement("INSERT INTO PLUGIN_LOAD_RESULT(BATCH_SERIAL_NO,BATCH_DATE,BATCH_ID,UP_SYSNAME,TABLE_NAME,TABLE_CNNAME,TABLE_TYPE,TABLE_LOAD_MODE,LOAD_FROM_FILE,FILE_SIZE,START_TIME,END_TIME,COST_TIME,READ_ROWS,LOAD_ROWS,REJECT_ROWS,LOAD_RESULT,REMARK) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, loadResult.getBatchSerialNo());
			ps.setString(2, loadResult.getBatchDate());
			ps.setString(3, loadResult.getBatchId());
			ps.setString(4, loadResult.getUpSysname());
			ps.setString(5, loadResult.getTableName());
			ps.setString(6, loadResult.getTableCnname());
			ps.setString(7, loadResult.getTableType());
			ps.setString(8, loadResult.getTableLoadMode());
			ps.setString(9, loadResult.getLoadFromFile());
			ps.setBigDecimal(10, loadResult.getFileSize());
			ps.setString(11, loadResult.getStartTime());
			ps.setString(12, loadResult.getEndTime());
			ps.setBigDecimal(13, loadResult.getCostTime());
			ps.setInt(14, loadResult.getReadRows());
			ps.setInt(15, loadResult.getLoadRows());
			ps.setInt(16, loadResult.getRejectRows());
			ps.setString(17, loadResult.getLoadResult());
			ps.setString(18, loadResult.getRemark());
			ps.execute();
			bFlag = true;
		} catch (SQLException e) {
			bFlag = false;
			e.printStackTrace();
		} finally {
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			TConnPool.getDefaultPool().releaseConnection(conn);
		}
		return bFlag;
	}
}
