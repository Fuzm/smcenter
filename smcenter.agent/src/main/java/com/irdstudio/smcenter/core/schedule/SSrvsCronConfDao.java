package com.irdstudio.smcenter.core.schedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 定时调度配置实体类-[表名: 定时调度配置实体类(s_srvs_cron_conf)]
 * 通过JDBC实现基本的数据表操作(CRUD) 
 * @author 代码自动生成
 * @version 1.0
 * @date 2017-10-30
 */
public class SSrvsCronConfDao {

	/* 连接对象 */
	Connection conn = null;	

	public SSrvsCronConfDao(Connection conn){
		this.conn = conn;
	}

	/**
	 * 根据主键查询单条记录
	 * @param 
	 * @return
	 * @throws SQLException
	 */
	public List<SSrvsCronConf> querySSrvsCronConfWithAll()
			throws SQLException {
		List<SSrvsCronConf> dcList = new ArrayList<SSrvsCronConf>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn
					.prepareStatement("SELECT * FROM s_srvs_cron_conf");
			rs = ps.executeQuery();
			while (rs.next()) {
				SSrvsCronConf dc = new SSrvsCronConf();
				dc.setJobCode(rs.getString("job_code"));
				dc.setJobDesc(rs.getString("job_desc"));
				dc.setJobClassType(rs.getString("job_class_type"));
				dc.setJobClass(rs.getString("job_class"));
				dc.setJobMethod(rs.getString("job_method"));
				dc.setCronExpression(rs.getString("cron_expression"));
				dc.setAgainTime(rs.getInt("again_time"));
				dc.setRetrySecond(rs.getInt("retry_second"));
				dc.setRemark(rs.getString("remark"));
				dc.setAgentId(rs.getString("agent_id"));
				dc.setServiceVersion(rs.getString("service_version"));
				dc.setServiceGroup(rs.getString("service_group"));
				dc.setServiceTimeout(rs.getBigDecimal("service_timeout"));
				dcList.add(dc);
			}
		} catch (SQLException e) {
			throw new SQLException("querySSrvsCronConfWithAll is Wrong!"
					+ e.getMessage());
		} finally {
			close(rs, null, ps);
		}
		return dcList;
	}
	
	/**
	 * 根据主键查询单条记录
	 * @param 
	 * @return
	 * @throws SQLException
	 */
	public List<SSrvsCronConf> querySSrvsCronConfWithAgentId(String agentId)
			throws SQLException {
		List<SSrvsCronConf> dcList = new ArrayList<SSrvsCronConf>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM s_srvs_cron_conf where agent_id = ?");
			ps.setString(1, agentId);
			rs = ps.executeQuery();
			while (rs.next()) {
				SSrvsCronConf dc = new SSrvsCronConf();
				dc.setJobCode(rs.getString("job_code"));
				dc.setJobDesc(rs.getString("job_desc"));
				dc.setJobClassType(rs.getString("job_class_type"));
				dc.setJobClass(rs.getString("job_class"));
				dc.setJobMethod(rs.getString("job_method"));
				dc.setCronExpression(rs.getString("cron_expression"));
				dc.setAgainTime(rs.getInt("again_time"));
				dc.setRetrySecond(rs.getInt("retry_second"));
				dc.setRemark(rs.getString("remark"));
				dc.setAgentId(rs.getString("agent_id"));
				dc.setServiceVersion(rs.getString("service_version"));
				dc.setServiceGroup(rs.getString("service_group"));
				dc.setServiceTimeout(rs.getBigDecimal("service_timeout"));
				dcList.add(dc);
			}
		} catch (SQLException e) {
			throw new SQLException("querySSrvsCronConfWithAll is Wrong!"
					+ e.getMessage());
		} finally {
			close(rs, null, ps);
		}
		return dcList;
	}
	
	/**
	 * 根据Key查询配置
	 * @param szCondition 
	 * @return
	 * @throws SQLException
	 */
	public SSrvsCronConf querySSrvsCronConfWithKey(String jobCode)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		SSrvsCronConf dc = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM s_srvs_cron_conf where job_code=?");
			ps.setString(1, jobCode);
			rs = ps.executeQuery();
			if (rs.next()) {
				dc = new SSrvsCronConf();
				dc.setJobCode(rs.getString("job_code"));
				dc.setJobDesc(rs.getString("job_desc"));
				dc.setJobClassType(rs.getString("job_class_type"));
				dc.setJobClass(rs.getString("job_class"));
				dc.setJobMethod(rs.getString("job_method"));
				dc.setCronExpression(rs.getString("cron_expression"));
				dc.setAgainTime(rs.getInt("again_time"));
				dc.setRetrySecond(rs.getInt("retry_second"));
				dc.setRemark(rs.getString("remark"));
				dc.setAgentId(rs.getString("agent_id"));
				dc.setServiceVersion(rs.getString("service_version"));
				dc.setServiceGroup(rs.getString("service_group"));
				dc.setServiceTimeout(rs.getBigDecimal("service_timeout"));
			}
		} catch (SQLException e) {
			throw new SQLException("querySSrvsCronConfWithKey is Wrong!"
					+ e.getMessage());
		} finally {
			close(rs, null, ps);
		}
		return dc;
	}
	

	/**
	 * 根据查询条件查询多条或单条记录
	 * @param szCondition 
	 * @return
	 * @throws SQLException
	 */
	public List<SSrvsCronConf> querySSrvsCronConfWithCond(String szCondition,String szOrderBy)
			throws SQLException {
		List<SSrvsCronConf> dcList = new ArrayList<SSrvsCronConf>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM s_srvs_cron_conf "
					+ szCondition + " " + szOrderBy);
			rs = ps.executeQuery();
			while (rs.next()) {
				SSrvsCronConf dc = new SSrvsCronConf();
				dc.setJobCode(rs.getString("job_code"));
				dc.setJobDesc(rs.getString("job_desc"));
				dc.setJobClassType(rs.getString("job_class_type"));
				dc.setJobClass(rs.getString("job_class"));
				dc.setJobMethod(rs.getString("job_method"));
				dc.setCronExpression(rs.getString("cron_expression"));
				dc.setAgainTime(rs.getInt("again_time"));
				dc.setRetrySecond(rs.getInt("retry_second"));
				dc.setRemark(rs.getString("remark"));
				dc.setAgentId(rs.getString("agent_id"));
				dc.setServiceVersion(rs.getString("service_version"));
				dc.setServiceGroup(rs.getString("service_group"));
				dc.setServiceTimeout(rs.getBigDecimal("service_timeout"));
				dcList.add(dc);
			}
		} catch (SQLException e) {
			throw new SQLException("querySSrvsCronConfWithCond is Wrong!"
					+ e.getMessage());
		} finally {
			close(rs, null, ps);
		}
		return dcList;
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

}
