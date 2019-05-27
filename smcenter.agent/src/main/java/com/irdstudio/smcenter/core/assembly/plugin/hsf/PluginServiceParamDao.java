package com.irdstudio.smcenter.core.assembly.plugin.hsf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PluginServiceParamDao {

	/* 连接对象 */
	Connection conn = null;

	public PluginServiceParamDao(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 根据主键查询单条记录
	 * 
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public PluginServiceParam queryWithKeys(String paramGroupId,String serviceParamName) throws SQLException {
		PluginServiceParam param = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM plugin_service_param WHERE param_group_id=?,service_param_name=?");
			ps.setString(1, paramGroupId);
			ps.setString(2, serviceParamName);
			rs = ps.executeQuery();
			if (rs.next()) {
				param = new PluginServiceParam();
				param.setParamGroupId(rs.getString("param_group_id"));
				param.setServiceParamName(rs.getString("service_param_name"));
				param.setServiceParamType(rs.getString("service_param_type"));
				param.setServiceParamValue(rs.getString("service_param_value"));
			}
		} catch (SQLException e) {
			throw new SQLException("queryPluginServiceParamWithKeys is Wrong!" + e.getMessage());
		} finally {
			close(rs, null, ps);
		}
		return param;
	}

	/**
	 * 根据查询条件查询多条或单条记录
	 * @param szCondition 
	 * @return
	 * @throws SQLException
	 */
	public List<PluginServiceParam> queryWithCond(String szCondition,String szOrderBy)
			throws SQLException {
		List<PluginServiceParam> dcList = new ArrayList<PluginServiceParam>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM plugin_service_param " + szCondition + " " + szOrderBy);
			rs = ps.executeQuery();
			while (rs.next()) {
				PluginServiceParam param = new PluginServiceParam();
				param.setParamGroupId(rs.getString("param_group_id"));
				param.setServiceParamName(rs.getString("service_param_name"));
				param.setServiceParamType(rs.getString("service_param_type"));
				param.setServiceParamValue(rs.getString("service_param_value"));
				dcList.add(param);
			}
		} catch (SQLException e) {
			throw new SQLException("queryPluginServiceParamWithCond is Wrong!" + e.getMessage());
		} finally {
			close(rs, null, ps);
		}
		return dcList;
	}

	/**
	 * 关闭资源
	 * 
	 * @param theRs
	 * @param theStmt
	 * @param thePs
	 */
	protected void close(ResultSet theRs, Statement theStmt, PreparedStatement thePs) {
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
