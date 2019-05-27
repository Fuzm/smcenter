package com.irdstudio.smcenter.core.batch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;

import com.irdstudio.smcenter.core.assembly.plugin.PluginConst;
import com.irdstudio.smcenter.core.assembly.plugin.common.SSubsInfo;
import com.irdstudio.smcenter.core.assembly.plugin.common.SSubsInfoDao;
import com.irdstudio.smcenter.core.batch.dao.BatBatchInfoConfig;
import com.irdstudio.smcenter.core.batch.dao.BatBatchInfoConfigDao;
import com.irdstudio.smcenter.core.batch.dao.BatInstBatch;
import com.irdstudio.smcenter.core.batch.dao.BatInstBatchDao;
import com.irdstudio.smcenter.core.batch.dao.BatTaskUnitConfig;
import com.irdstudio.smcenter.core.batch.dao.BatTaskUnitConfigDao;
import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.TConnPool;
import com.irdstudio.smcenter.core.tinycore.jdbc.executor.SafeReleaseUtil;

/**
 * 批次取数辅助类
 * 
 * @author guangming.li
 * @version 1.0
 * @date 2014-04-25
 */
public class BatchDataUtil {

	/**
	 * 根据任务编号查询批次任务配置信息
	 * 
	 * @param conn
	 * @param taskId
	 * @return
	 */
	public synchronized static BatTaskUnitConfig getBatTaskUnitConfig(Connection conn, String taskId) throws SQLException {
		if (StringUtils.isBlank(taskId)) {
			throw new SQLException("任务编号为空！");
		}
		BatTaskUnitConfigDao batTaskUnitConfigDao = new BatTaskUnitConfigDao(conn);
		BatTaskUnitConfig batTaskUnitConfig = null;
		batTaskUnitConfig = batTaskUnitConfigDao.queryWithKeys(taskId);
		return batTaskUnitConfig;
	}

	/**
	 * 根据批次标识查询批次配置表bat_batch_info_config，取得基本信息
	 * 
	 * @param conn
	 * @param batchId
	 * @return
	 * @throws SQLException
	 */
	public synchronized static BatBatchInfoConfig getBatBatchInfoConfig(Connection conn, String batchId) throws SQLException {
		BatBatchInfoConfigDao batBatchInfoConfigDao = new BatBatchInfoConfigDao(conn);
		return batBatchInfoConfigDao.queryWithKeys(batchId);
	}

	/**
	 * 根据批次标识查询批次实例表bat_inst_batch
	 * 
	 * @param conn
	 * @param batchId
	 * @return
	 * @throws SQLException
	 */
	public synchronized static BatInstBatch getBatInstBatch(Connection conn, String batchId) throws SQLException {
		BatInstBatchDao batInstBatchDao = new BatInstBatchDao(conn);
		return batInstBatchDao.queryWithKeys(batchId);
	}

	/**
	 * 根据子系统代码查询子系统信息
	 * 
	 * @param conn
	 * @param subsInfo
	 * @return
	 * @throws SQLException
	 */
	public synchronized static SSubsInfo getSSubsInfo(Connection conn, String subsCode) throws SQLException {
		SSubsInfoDao sSubsInfoDao = new SSubsInfoDao(conn);
		return sSubsInfoDao.querySSubsInfoWithKeys(subsCode);
	}

	/**
	 * 查询实例表中的干预状态是否为置过
	 * 
	 * @param taskId
	 * @return
	 * @throws SQLException
	 */
	public static boolean isTaskSkiped(String taskId) {
		boolean isSkiped = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String state = "";
		Connection conn = null;
		try {
			conn = TConnPool.getDefaultPool().getConnection();
			ps = conn.prepareStatement("SELECT task_intervene_state FROM bat_inst_task WHERE task_id=?");
			ps.setString(1, taskId);
			rs = ps.executeQuery();
			if (rs.next()) {
				state = (rs.getString("task_intervene_state"));
			}
			if (state != null && (state.equals(PluginConst.TASK_INT_STATE_SKIP) || state.equals(PluginConst.TASK_INT_STATE_EXIT))) {
				isSkiped = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			isSkiped = false;
		} finally {
			SafeReleaseUtil.close(rs, null, ps);
			TConnPool.getDefaultPool().releaseConnection(conn);
		}
		return isSkiped;
	}

	/**
	 * 查询实例表中的干预状态
	 * @author caijiufa
	 * @since 20181205
	 * 
	 * @param taskId
	 * @return
	 * @throws SQLException
	 */
	public static String getTaskState(String taskId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String state = "";
		Connection conn = null;
		try {
			conn = TConnPool.getDefaultPool().getConnection();
			ps = conn.prepareStatement("SELECT task_intervene_state FROM bat_inst_task WHERE task_id=?");
			ps.setString(1, taskId);
			rs = ps.executeQuery();
			if (rs.next()) {
				state = (rs.getString("task_intervene_state"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SafeReleaseUtil.close(rs, null, ps);
			TConnPool.getDefaultPool().releaseConnection(conn);
		}
		return state;
	}

}
