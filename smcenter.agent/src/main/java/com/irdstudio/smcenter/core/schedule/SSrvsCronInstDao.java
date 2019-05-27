package com.irdstudio.smcenter.core.schedule;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.irdstudio.smcenter.core.util.date.CurrentDateUtil;
/**
 * 定时调度实例实体类类-[表名: 定时调度实例实体类(s_srvs_cron_inst)]
 * 通过JDBC实现基本的数据表操作(CRUD) 
 * @author 代码自动生成
 * @version 1.0
 * @date 2017-10-30
 */
public class SSrvsCronInstDao {

	/* 连接对象 */
	Connection conn = null;	

	public SSrvsCronInstDao(Connection conn){
		this.conn = conn;
	}

	/**
	 * 根据主键查询单条记录
	 * @param 
	 * @return
	 * @throws SQLException
	 */
	public List<SSrvsCronInst> querySSrvsCronInstWithAll()
			throws SQLException {
		List<SSrvsCronInst> dcList = new ArrayList<SSrvsCronInst>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn
					.prepareStatement("SELECT * FROM s_srvs_cron_inst");
			rs = ps.executeQuery();
			while (rs.next()) {
				SSrvsCronInst dc = new SSrvsCronInst();
				dc.setJobCode(rs.getString("job_code"));
				dc.setJobDesc(rs.getString("job_desc"));
				dc.setState(rs.getInt("state"));
				dc.setStartTime(rs.getString("start_time"));
				dc.setEndTime(rs.getString("end_time"));
				dc.setCostTime(rs.getBigDecimal("cost_time"));
				dc.setAgentId(rs.getString("agent_id"));
				dc.setResultDesc(rs.getString("result_desc"));
				dcList.add(dc);
			}
		} catch (SQLException e) {
			throw new SQLException("querySSrvsCronInstWithAll is Wrong!"
					+ e.getMessage());
		} finally {
			close(rs, null, ps);
		}
		return dcList;
	}

	/**
	 * 根据Key查询运行实例
	 * @param szCondition 
	 * @return
	 * @throws SQLException
	 */
	public SSrvsCronInst querySSrvsCronInstWithKey(String jobCode)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		SSrvsCronInst dc = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM s_srvs_cron_inst where job_code=?");
			ps.setString(1, jobCode);
			rs = ps.executeQuery();
			if (rs.next()) {
				dc = new SSrvsCronInst();
				dc.setJobCode(rs.getString("job_code"));
				dc.setJobDesc(rs.getString("job_desc"));
				dc.setState(rs.getInt("state"));
				dc.setStartTime(rs.getString("start_time"));
				dc.setEndTime(rs.getString("end_time"));
				dc.setCostTime(rs.getBigDecimal("cost_time"));
				dc.setAgentId(rs.getString("agent_id"));
				dc.setResultDesc(rs.getString("result_desc"));
			}
		} catch (SQLException e) {
			throw new SQLException("querySSrvsCronInstWithKey is Wrong!"
					+ e.getMessage());
		} finally {
			close(rs, null, ps);
		}
		return dc;
	}

	/**
	 * 关闭资源
	 * @param theRs
	 * @param theStmt
	 * @param thePs
	 */
	protected void close(ResultSet theRs, Statement theStmt,
			PreparedStatement thePs) {
		try {
			if (theRs != null)
				theRs.close();
			if (theStmt != null)
				theStmt.close();
			if (thePs != null)
				thePs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化定时任务实例
	 * @param jobCode
	 */
	public void initializeInst(String jobCode) {
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		try {

			// 清除已有的实例数据
			ps1 = conn
					.prepareStatement("delete from s_srvs_cron_inst where job_code=?");
			ps1.setString(1, jobCode);
			ps1.execute();

			// 写入新的实例数据
			ps2 = conn
					.prepareStatement("insert into s_srvs_cron_inst(job_code,job_desc,state,start_time,end_time,cost_time,agent_id,result_desc) select job_code,job_desc,?,'','',0,agent_id,'已初始化,等待触发...' from s_srvs_cron_conf where job_code = ?");
			ps2.setInt(1, ScheduleJobUtil.STATE_INITIALIZE);
			ps2.setString(2, jobCode);
			ps2.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(null, null, ps1);
			close(null, null, ps2);
		}
	}

	/**
	 * 更新状态为运行中
	 * @param jobCode
	 * @throws SQLException 
	 */
	public void updateStateToRunning(String jobCode) throws SQLException {
		PreparedStatement ps = null;
		try {
			// 更新状态为运行中
			ps = conn
					.prepareStatement("update s_srvs_cron_inst set state=?,start_time=?,result_desc='正在运行...' where job_code = ?");
			String startTime = CurrentDateUtil.getTodayDateEx2();
			ps.setInt(1, ScheduleJobUtil.STATE_RUNNING);
			ps.setString(2, startTime);
			ps.setString(3, jobCode);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			close(null, null, ps);
		}
	}

	/**
	 * 将状态更新为运行结束
	 * @param jobCode
	 * @param startMills
	 * @param isHaveError
	 * @param result
	 */
	public void updateStateToEnd(String jobCode, long startMills, boolean isHaveError, String result) {
		
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		int state = isHaveError ? ScheduleJobUtil.STATE_ERROR
				: ScheduleJobUtil.STATE_END;
		long endMills = System.currentTimeMillis();

		// 计算作业耗时
		BigDecimal costTime = new BigDecimal(endMills - startMills)
				.divide(new BigDecimal(1000.00));
		try {

			// 更新作业状态
			ps1 = conn
					.prepareStatement("update s_srvs_cron_inst set state=?,end_time=?,cost_time=?,result_desc=? where job_code = ?");
			String endTime = CurrentDateUtil.getTodayDateEx2();
			ps1.setInt(1, state);
			ps1.setString(2, endTime);
			ps1.setBigDecimal(3, costTime);
			ps1.setString(4, result);
			ps1.setString(5, jobCode);
			ps1.execute();

			// 复制记录到历史记录表
			ps2 = conn
					.prepareStatement("insert into s_srvs_cron_his(record_id,job_code,job_desc,state,start_time,end_time,cost_time,agent_id,result_desc)"
							+ " select ?,job_code,job_desc,state,start_time,end_time,cost_time,agent_id,result_desc from s_srvs_cron_inst where job_code=?");
			ps2.setString(1, createUUIDKey());
			ps2.setString(2, jobCode);
			ps2.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(null, null, ps1);
			close(null, null, ps2);
		}
	}
	
	/**
	 * 产生32位的UUID(JDK自带)
	 * @return
	 */
	private String createUUIDKey() {
		return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
	}
	

}
