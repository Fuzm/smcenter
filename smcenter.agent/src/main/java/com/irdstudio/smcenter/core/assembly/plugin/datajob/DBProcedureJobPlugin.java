package com.irdstudio.smcenter.core.assembly.plugin.datajob;
/**
 * 数据库存储过程作业插件
 * @author cwa
 * @version 0.1
 * @date 2014-04-24
 */
public class DBProcedureJobPlugin extends AbstractDataJobPlugin {

	@Override
	public boolean doExecuteJob(PluginJobConf jobConf) {
		this.logger.info("执行数据库存储过程作业：" + jobConf.getJobName() + "...");
		this.context.szLastErrorMsg = "尚未实现!";
		return false;
	}

}
