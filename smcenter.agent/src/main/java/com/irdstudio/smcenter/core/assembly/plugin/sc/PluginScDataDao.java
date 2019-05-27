package com.irdstudio.smcenter.core.assembly.plugin.sc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PluginScDataDao {/* 连接对象 */
Connection conn = null;	

public PluginScDataDao(Connection conn){
	this.conn = conn;
}

/**
 * 根据主键查询单条记录
 * @param 
 * @return
 * @throws SQLException
 */
public PluginScData queryPluginScDataWithKeys(String pluginConfId,int confSort)
		throws SQLException {
	PluginScData dc = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	try {
		ps = conn
				.prepareStatement("SELECT * FROM PLUGIN_SC_CONF WHERE URPS.plugin_conf_id=?,conf_sort=?");
		ps.setString(1,pluginConfId);
		ps.setInt(2,confSort);
		rs = ps.executeQuery();
		if (rs.next()) {
			dc = new PluginScData();
			dc.setPluginConfId(rs.getString("plugin_conf_id"));
			dc.setConfSort(rs.getInt("conf_sort")); 
			dc.setScDesc(rs.getString("SC_DESC"));
			dc.setOpType(rs.getString("OP_TYPE"));
			dc.setOpSql(rs.getString("OP_SQL"));
			dc.setWirteTable (rs.getString("WRITE_TABLE"));
			dc.setSaveM(rs.getInt("SAVE_M"));
			dc.setScFaildDeal(rs.getString("SC_FAILD_DEAL"));
			dc.setValidDate(rs.getString("valid_date"));
			dc.setInvalidDate(rs.getString("invalid_date"));
			dc.setLastModifyDate(rs.getString("last_modify_date"));
		}
	} catch (SQLException e) {
		throw new SQLException("queryPluginScDataWithKeys is Wrong!"
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
public List<PluginScData> queryPluginScDataWithCond(String szCondition,String szOrderBy)
		throws SQLException {
	List<PluginScData> dcList = new ArrayList<PluginScData>();
	PreparedStatement ps = null;
	ResultSet rs = null;
	try {
		ps = conn.prepareStatement("SELECT * FROM URPS.PLUGIN_SC_CONF "
				+ szCondition + " " + szOrderBy + " with ur");
		rs = ps.executeQuery();
		while (rs.next()) {
			PluginScData dc = new PluginScData();
			dc.setPluginConfId(rs.getString("plugin_conf_id"));
			dc.setConfSort(rs.getInt("conf_sort"));
			dc.setScDesc(rs.getString("SC_DESC"));
			dc.setOpType(rs.getString("OP_TYPE"));
			dc.setOpSql(rs.getString("OP_SQL"));
			dc.setWirteTable (rs.getString("WRITE_TABLE"));
			dc.setSaveM(rs.getInt("SAVE_M"));
			dc.setScFaildDeal(rs.getString("SC_FAILD_DEAL"));
			dc.setValidDate(rs.getString("valid_date"));
			dc.setInvalidDate(rs.getString("invalid_date"));
			dc.setLastModifyDate(rs.getString("last_modify_date"));
			dcList.add(dc);
		}
	} catch (SQLException e) {
		throw new SQLException("queryPluginScDataWithCond is Wrong!"
				+ e.getMessage());
	} finally {
		close(rs, null, ps);
	}
	return dcList;
}
public String getMindate(String sjrq,int save_m)throws SQLException{
	String mindate = "";
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql  = " select trim(char(int(year(xtrq))))||'-'|| " +
			" case when int(month(xtrq))<10 then '0'||trim(char(int(month(xtrq))))  " +
			" else trim(char(int(month(xtrq)))) end||'-'|| " +
			" case when int(day(xtrq))<10 then '0'||trim(char(int(day(xtrq)))) else trim(char(int(day(xtrq)))) end " +
			" from (select varchar(date('"+sjrq+"')-"+save_m+" month-(day(date('"+sjrq+"')-"+save_m+" month)-1)days+1 month-1 day) xtrq " +
			" from  sysibm.sysdummy1 ) tt with ur";
	System.err.println("getMindate sql="+sql);
	try {
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		if (rs.next()) {
			mindate = rs.getString(1);
		}
	} catch (SQLException e) {
		throw new SQLException("getMindate is Wrong!"
				+ e.getMessage());
	} finally {
		close(rs, null, ps);
	}
	return mindate;
	
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
