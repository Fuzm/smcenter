package com.irdstudio.smcenter.core.assembly.plugin.datajob;
/**
 * Kettle作业插件(待实现)
 * @author cwa
 * @version 0.1
 * @date 2014-04-24
 */
public class KettleJobPlugin extends AbstractDataJobPlugin {

	@Override
	public boolean doExecuteJob(PluginJobConf jobConf) {
		this.logger.info("执行Kettle作业：" + jobConf.getJobName() + "...");
		this.context.szLastErrorMsg = "尚未实现!";
		return false;
	}

}
