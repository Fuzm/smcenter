package com.irdstudio.smcenter.core.assembly.plugin.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 * 数据表操作类-[表名:应用插件定义类(PluginDefine)] 
 * 实现基本的CRUD数据库操作(JDBC) 
 * @author 代码自动生成
 * @version 1.0
 * @date 2013-10-10
 */
public class PluginDefineDao {

	/* 连接对象 */
	Connection conn = null;	
	
	public PluginDefineDao(Connection conn) {
		this.conn = conn;
	}
	
	/**
	 * 根据主键查询指定的应用插件定义
	 * @param pluginId
	 * @return
	 * @throws SQLException
	 */
	public PluginDefine queryWithKeys(int pluginId)
			throws SQLException {
		PluginDefine dc = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn
					.prepareStatement("SELECT * FROM plugin_define WHERE plugin_id=?");
			ps.setInt(1, pluginId);
			rs = ps.executeQuery();
			if (rs.next()) {
				dc = new PluginDefine();
				dc.setPluginId(rs.getInt("plugin_id"));
				dc.setPluginName(rs.getString("plugin_name"));
				dc.setPluginDealClass(rs.getString("plugin_class")==null?rs.getString("plugin_class"):rs.getString("plugin_class").trim());
				dc.setPluginDesc(rs.getString("plugin_desc"));
				dc.setNeedOtherDsVar(rs.getString("need_other_ds_var"));
			}
		} catch (SQLException e) {
			throw new SQLException("queryPluginDefineWithKeys is Wrong!"
					+ e.getMessage());
		} finally {
			close(rs, null, ps);
		}
		return dc;
	}
	
	/**
	 * 根据指定条件进行查询(需要带WHERE)
	 * @param szCondition
	 * @return
	 * @throws SQLException
	 */
	public List<PluginDefine> queryWithCond(String szCondition)
			throws SQLException {
		List<PluginDefine> dcList = new ArrayList<PluginDefine>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM plugin_define "
					+ szCondition);
			rs = ps.executeQuery();
			while (rs.next()) {
				PluginDefine dc = new PluginDefine();
				dc.setPluginId(rs.getInt("plugin_id"));
				dc.setPluginName(rs.getString("plugin_name"));
				dc.setPluginDealClass(rs.getString("plugin_class")==null?rs.getString("plugin_class"):rs.getString("plugin_class").trim());
				dc.setPluginDesc(rs.getString("plugin_desc"));
				dcList.add(dc);
			}
		} catch (SQLException e) {
			throw new SQLException("queryPluginDefineWithCond is Wrong!"
					+ e.getMessage());
		} finally {
			close(rs, null, ps);
		}
		return dcList;
	}
	/**
	 * 创建应用插件定义
	 * @param pd
	 * @throws SQLException
	 */
	public void createPluginDefine(PluginDefine pd) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(
				"");
		ps.setString(1, "");
		ps.execute();
		close(null, null, ps);
	}

	/**
	 * 更新应用插件定义
	 * @param pd
	 * @throws SQLException
	 */
	public void updatePluginDefine(PluginDefine pd) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(
				"");
		ps.setString(1, "");
		ps.execute();
		close(null, null, ps);
	}

	/**
	 * 删除应用插件定义(根据主键)
	 * @param key1
	 * @param key2
	 * @throws SQLException
	 */
	public void deletePluginDefineWithKeys(String key1, String key2)
			throws SQLException {
		PreparedStatement ps = conn.prepareStatement(
				"");
		ps.setString(1, "");
		ps.execute();
		close(null, null, ps);
	}

	/**
	 * 删除应用插件定义(根据条件)
	 * @param key1
	 * @param key2
	 * @throws SQLException
	 */
	public void deletePluginDefineWithCond(String cond) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(
				"");
		ps.setString(1, "");
		ps.execute();
		close(null, null, ps);
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
