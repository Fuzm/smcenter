package com.irdstudio.smcenter.core.assembly.plugin.hsf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PluginServiceConfDao {

	/* 连接对象 */
	Connection conn = null;

	public PluginServiceConfDao(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 根据主键查询单条记录
	 * 
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public PluginServiceConf queryWithKeys(String pluginConfId) throws SQLException {
		PluginServiceConf sc = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM plugin_service_conf WHERE plugin_conf_id=?");
			ps.setString(1, pluginConfId);
			rs = ps.executeQuery();
			if (rs.next()) {
				sc = new PluginServiceConf();
				sc.setPluginConfId(rs.getString("plugin_conf_id"));
				sc.setConfSort(rs.getInt("conf_sort"));
				sc.setServiceId(rs.getString("service_id"));
				sc.setServiceName(rs.getString("service_name"));
				sc.setServiceInterface(rs.getString("service_interface"));
				sc.setServiceMethod(rs.getString("service_method"));
				sc.setVersion(rs.getString("version"));
				sc.setGroup(rs.getString("service_group"));
				sc.setTimeout(rs.getBigDecimal("timeout"));
			}
		} catch (SQLException e) {
			throw new SQLException("queryPluginServiceConfWithKeys is Wrong!" + e.getMessage());
		} finally {
			close(rs, null, ps);
		}
		return sc;
	}

	/**
	 * 根据查询条件查询多条或单条记录
	 * 
	 * @param szCondition
	 * @return
	 * @throws SQLException
	 */
	public List<PluginServiceConf> queryWithCond(String szCondition, String szOrderBy) throws SQLException {
		List<PluginServiceConf> scList = new ArrayList<PluginServiceConf>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM plugin_service_conf " + szCondition + " " + szOrderBy);
			rs = ps.executeQuery();
			while (rs.next()) {
				PluginServiceConf sc = new PluginServiceConf();
				sc.setPluginConfId(rs.getString("plugin_conf_id"));
				sc.setConfSort(rs.getInt("conf_sort"));
				sc.setServiceId(rs.getString("service_id"));
				sc.setServiceName(rs.getString("service_name"));
				sc.setServiceInterface(rs.getString("service_interface"));
				sc.setServiceMethod(rs.getString("service_method"));
				sc.setReturnType(rs.getString("return_type"));
				sc.setParamGroupId(rs.getString("param_group_id"));
				sc.setVersion(rs.getString("version"));
				sc.setGroup(rs.getString("service_group"));
				sc.setTimeout(rs.getBigDecimal("timeout"));
				scList.add(sc);
			}
		} catch (SQLException e) {
			throw new SQLException("queryPluginServiceConfWithCond is Wrong!" + e.getMessage());
		} finally {
			close(rs, null, ps);
		}
		return scList;
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
