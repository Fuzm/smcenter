package com.irdstudio.smcenter.core.assembly.plugin.datajob;
/**
 * 数据库SQL语句作业
 * 直接通过执行SQL语句来实现业务逻辑
 * @author guangming.li
 * @version 1.0
 * @date 2014-04-25
 */
public class DBSqlJobPlugin extends AbstractDataJobPlugin {

	@Override
	public boolean doExecuteJob(PluginJobConf jobConf) {
		this.logger.info("执行作业配置(DB)的SQL脚本...");
		boolean flag = true;
		String[] conds = jobConf.getSqlContent().split(";");
		
		String sqlContent = jobConf.getSqlContent().toLowerCase().trim();
		if(sqlContent.startsWith("declare")||sqlContent.startsWith("begin")) {
			conds = new String[] {jobConf.getSqlContent()};
		}
		for (int i = 0; i < conds.length; i++) {
			if ("".equals(conds[i].trim())) {
				// 如果是空行直接跳过
				continue;
			}
			String sql = context.toParseSysVariable(conds[i]);
			boolean subFlag = this.executeSql(sql);
			if (subFlag) {
				this.writeSuccessLog("执行作业配置的SQL脚本", sql);
			} else {
				this.writeFaildLog("执行作业配置的SQL脚本", sql + ","
						+ context.szLastErrorMsg);
				flag = subFlag;
				// 如果执行其中的一条出错，则直接跳出,后续的不再执行
				break;
			}
		}
		return flag;
	}
}
