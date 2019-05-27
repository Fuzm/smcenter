package com.irdstudio.smcenter.core.assembly.plugin.datajob;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.irdstudio.smcenter.core.assembly.plugin.AbstractPlugin;
import com.irdstudio.smcenter.core.assembly.plugin.PluginConst;
/**
 * 抽象的数据作业调用插件
 * 供存储过程作业、DataStage作业、Java数据作业、Kettle作业继续
 * @author guangming.li
 * @version 1.0
 * @date 2014-04-24
 */
public abstract class AbstractDataJobPlugin extends AbstractPlugin{
	
	/* 存放数据作业配置数据 */
	protected List<PluginJobConf> dsList;
	/* SQL用途：1-不使用 */
	protected static String SQL_PURPOSE_NO = "1";
	/* SQL用途：2-作业执行前SQL */
	protected static String SQL_PURPOSE_BEFORE = "2";
	/* SQL用途：3-作业执行前SQL */
	protected static String SQL_PURPOSE_JOB = "3";
	/* SQL用途：4-作业执行前SQL */
	protected static String SQL_PURPOSE_AFTER = "4";
	/* 执行DataStage作业的中间Shell程序名 */
	
	@Override
	protected boolean doReadConfigureFromDB(Connection conn,
			String szConfIdentify) throws SQLException {
		PluginJobConfDao jobDao = new PluginJobConfDao(conn);
		dsList = jobDao.queryWithCond(" where plugin_conf_id='"
				+ szConfIdentify + "'", "order by conf_sort");
		if (dsList.size() < 1) {
			context.szLastErrorMsg = "未读取到配置标识为：" + szConfIdentify + "的数据作业配置!";
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 插件执行逻辑
	 */
	public boolean execute() {
		
		boolean flag = true;
				
		// 循环配置的作业列表，逐个执行
		for (int i = 0; i < dsList.size(); i++) {

			PluginJobConf jobConf = dsList.get(i);

			this.logger
					.info("........................................................................");
			this.logger.info("准备调用作业：" + jobConf.getJobName() + "...");

			// 判断SQL脚本用途
			if (SQL_PURPOSE_NO.equals(jobConf.getSqlPurpose())) {
				// 说明不使用,直接跳过
			} else if (SQL_PURPOSE_BEFORE.equals(jobConf.getSqlPurpose())) {
				// 说明为作业调用前使用
				flag = customDeal(jobConf);
			}
			
			boolean bResult = doExecuteJob(jobConf);
			
			// 判断是否调用成功
			if (bResult) {
				this.writeSuccessLog("执行作业:" + jobConf.getJobName());
				if (SQL_PURPOSE_AFTER.equals(jobConf.getSqlPurpose())) {
					// 说明为作业调用后使用
					flag = customDeal(jobConf);
				}				
			} else {
				this.writeFaildLog("执行作业:" + jobConf.getJobName());
				if (PluginConst.FAILD_DEAL_SKIP.equals(jobConf
						.getJobFaildDeal())) {
					// 如果失败处理方式为跳过,则继续
					if (SQL_PURPOSE_AFTER.equals(jobConf.getSqlPurpose())) {
						// 说明为作业调用后使用
						flag = customDeal(jobConf);
					}
					continue;
				} else {
					// 如果失败处理方式为中止,退出循环
					flag = false;
					break;
				}
			}
		}
		return flag;
	}	

	/**
	 * 由继承类实现去执行作业
	 * @return
	 */
	public abstract boolean doExecuteJob(PluginJobConf jobConf);


	
	/**
	 * 自定义执行(复杂处理)
	 * @param jobConf
	 * @return
	 */
	private boolean customDeal(PluginJobConf jobConf) {
		this.logger.info("执行作业配置的SQL脚本...");
		boolean flag = true;
		String[] conds = jobConf.getSqlContent().split(";");
		for (int i = 0; i < conds.length; i++) {
			String sql = context.toParseSysVariable(conds[i]);
			boolean subFlag = this.executeSql(sql);
			if (subFlag) {
				this.writeSuccessLog("执行作业配置的SQL脚本", sql);
			} else {
				this.writeFaildLog("执行作业配置的SQL脚本", sql + ","
						+ context.szLastErrorMsg);
				flag = subFlag;
			}
		}
		return flag;
	}
}
