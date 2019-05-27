package com.irdstudio.smcenter.core.schedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.irdstudio.smcenter.agent.boot.AgentConstant;
import com.irdstudio.smcenter.core.util.date.CurrentDateUtil;

/**
 * 通过JDBC实现基本的数据表操作(CRUD) 
 * @author 代码自动生成
 * @version 1.0
 * @date 2017-10-30
 */
public class SAgentInfoDao {

	/* 连接对象 */
	Connection conn = null;	

	public SAgentInfoDao(Connection conn){
		this.conn = conn;
	}
	
	/**
	 * 根据主键查询单条记录
	 * @param 
	 * @return
	 * @throws SQLException
	 */
	public SAgentInfo queryAgentInfoById(String agentId)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		SAgentInfo agentInfo = null;
		try {
			ps = conn.prepareStatement("select * from s_agent_info where agent_id = ?");
			ps.setString(1, agentId);
			rs = ps.executeQuery();
			if (rs.next()) {
				agentInfo = new SAgentInfo();
				agentInfo.setAgentId(rs.getString("agent_id"));
				agentInfo.setAgentName(rs.getString("agent_name"));
				agentInfo.setAgentState(rs.getString("agent_state"));
				agentInfo.setAgentUrl(rs.getString("agent_url"));
				agentInfo.setStartTime(rs.getString("start_time"));
				agentInfo.setStopTime(rs.getString("stop_time"));
			}
		} catch (SQLException e) {
			throw new SQLException("queryAgentInfoById is Wrong!"
					+ e.getMessage());
		} finally {
			close(rs, null, ps);
		}
		return agentInfo;
	}
	
	/**
	 * 更新状态为运行中
	 * @param agentId
	 * @throws SQLException 
	 */
	public void updateStateToRunning(String agentId) throws SQLException {
		PreparedStatement ps = null;
		try {
			// 更新状态为运行中
			ps = conn
					.prepareStatement("update s_agent_info set agent_state=?,start_time=? where agent_id = ?");
			String startTime = CurrentDateUtil.getTodayDateEx2();
			ps.setString(1, AgentConstant.AGENT_STATE_RUNING);
			ps.setString(2, startTime);
			ps.setString(3, agentId); 
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			close(null, null, ps);
		}
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
