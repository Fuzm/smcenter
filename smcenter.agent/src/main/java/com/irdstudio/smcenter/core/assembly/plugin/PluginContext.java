package com.irdstudio.smcenter.core.assembly.plugin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.irdstudio.smcenter.core.assembly.plugin.common.SSubsDatasource;
import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.base.IConnPool;
import com.irdstudio.smcenter.core.util.parse.VariableParserUtil;
import com.irdstudio.smcenter.core.util.vo.VariableValue;

/**
 * 调用插件时所需要的上下文
 * @author guangmin.li
 * @version 1.2
 * @since 1.0 2013-10-09
 * @date 2014-05-15
 */
public class PluginContext {
	
	/* 应用插件可使用的参数变量 */
	public VariableValue vv;
	/* 错误信息 */
	public String szLastErrorMsg = "";
	/* 失败跳过统计 */
	public int nFaildSkipCount = 0;
	/* 应用插件编号 */
	public int nPluginId;
	/* 应用插件名称 */
	public String szPluginName;
	/* 应用插件配置来源方式 */
	public int nPluginConfType;
	/* 应用插件配置标识 */
	public String szPluginConfId;
	/* 批次流水号 */
	public String szBatchSn;
	/* 批次ID(标识) */
	public String szBatchId;	
	/* 任务编号 */
	public String szTaskId;
	/* 任务名称 */
	public String szTaskName;
	/* 所属子系统代码 */
	public String szSubsCode;
	/* 使用的数据源名称 */
	public String szSubsDsCode;
	/*本应用插件使用的数据信息*/
	private SSubsDatasource dsInfo = null;
	/*应用插件使用的连接池*/
	private IConnPool connPool = null;
	/* 获取执行开始时间点 */
	private long startMills;
	 

	/**
	 * 构造函数,传入所需要的参数
	 * @param nPluginId
	 * @param szPluginName
	 * @param nPluginConfType
	 * @param szPluginConfIdentify
	 * @param szBatchSn
	 * @param szTaskId
	 * @param szTaskName
	 * @param szDBUserId
	 * @param szDBUserPwd
	 * @param szDBUserSchema
	 */
	public PluginContext(int nPluginId, String szPluginName,
			int nPluginConfType, String szPluginConfIdentify, String szBatchSn,
			String szTaskId, String szTaskName) {
		this.nPluginId = nPluginId;
		this.szPluginName = szPluginName;
		this.szBatchSn = szBatchSn;
		this.szTaskId = szTaskId;
		this.szTaskName = szTaskName;
		this.szLastErrorMsg = "";
		this.startMills = System.currentTimeMillis();
	}
	
	/**
	 * 上下文缺省构造函数
	 */
	public PluginContext() {
		this.startMills = System.currentTimeMillis();
	}


	/**
	 * 解释字符串,将其中的变量解释为值
	 * @param str
	 * @return
	 */
	public String toParseSysVariable(String str) {
		if (str == null || "".equals(str))
			return "";
		return VariableParserUtil.parseString(str, this.vv);
	}
	
	/**
	 * 解释字符串,将其中的字段变量解释为值
	 * @param str
	 * @return
	 */
	public String toParseFieldVariable(String str, ResultSet rs) {
		if (str == null || "".equals(str))
			return "";		
		return VariableParserUtil.parseString(str, rs);
	}

	/**
	 * 获取当前数据日期
	 * @return
	 */
	public String getCurrentDataDate() {
		return vv.getValue(PluginConst.SUBS_DATA_DATE);
	}
	
	/**
	 * 获取上一数据日期
	 * @return
	 */
	public String getLastDataDate() {
		return vv.getValue(PluginConst.SUBS_LAST_DATA_DATE);
	}
	/**
	 * 获取数据装载完成日期
	 * @return
	 */
	public String getLoadFinishedDate() {
		return vv.getValue(PluginConst.SUBS_LOAD_DATE);
	}

	/**
	 * 获取批次完成日期
	 * @return
	 */
	public String getBatFinishedDate() {
		return vv.getValue(PluginConst.SUBS_BAT_DATE);
	}

	/**
	 * 获取下一数据日期(数据日期加1)
	 * @return
	 */
	public String getNextDataDate() {
		return vv.getValue(PluginConst.SUBS_NEXT_DATA_DATE);
	}
	
	/**
	 * 获取子系统代码
	 * @return
	 */
	public String getSubsCode(){
		return  vv.getValue(PluginConst.SUBS_CODE);
	}
	
	/**
	 * 获取应用插件使用的数据源信息对象
	 * @return
	 */
	public SSubsDatasource getDataSourceInfo() {
		return dsInfo;
	}

	/**
	 * 设置应用插件使用的数据源信息对象
	 * @param dsInfo
	 */
	public void setDataSourceInfo(SSubsDatasource dsInfo) {
		this.dsInfo = dsInfo;
	}
	
	/**
	 * 获取变量值容器
	 * @return
	 */
	public VariableValue getVv() {
		return vv;
	}

	/**
	 * 设置变量值容器
	 * @param vv
	 */
	public void setVv(VariableValue vv) {
		this.vv = vv;
	}
	
	
	/**
	 * 返回错误信息,经截取后
	 * @return
	 */
	public String getLastErrorMsg() {
		if(szLastErrorMsg == null)  {
			return "";
		} else {
			return szLastErrorMsg;
		}
	}
	
	/**
	 * 创建时间
	 * @return
	 */
	public long getStartMills() {
		return startMills;
	}	
	
	/**
	 * 设置应用插件使用的连接池
	 * @param defaultPool
	 */
	public void setConnPool(IConnPool pluginConnPool) {
		this.connPool = pluginConnPool;
	}
	
	/**
	 * 设置应用插件使用的连接池
	 * @param defaultPool
	 */
	public IConnPool getConnPool() {
		return this.connPool;
	}
	
	/**
	 * 取得一个连接
	 * @return
	 * @throws SQLException 
	 */
	public Connection getPluginConnection() throws SQLException{
		return this.connPool.getConnection();
	}
	
	/**
	 * 回收一个连接
	 * @param conn
	 */
	public void releasePluginConnection(Connection conn){
		this.connPool.releaseConnection(conn);
	}	
}
