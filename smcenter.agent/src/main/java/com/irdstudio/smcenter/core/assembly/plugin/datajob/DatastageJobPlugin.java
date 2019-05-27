package com.irdstudio.smcenter.core.assembly.plugin.datajob;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * DataStage作业插件
 * @author guangming.li
 * @version 1.0
 * @date 2013-10-21
 * @modify 2014-04-22 公共逻辑部分移到父类
 */
public class DatastageJobPlugin extends AbstractDataJobPlugin{
	

	public static String SEHELLNAME = "execDataStageJob.sh";

	/**
	 * 调用并执行DataStage作业
	 */
	public boolean doExecuteJob(PluginJobConf jobConf) {
		
		this.logger.info("执行Datastage作业：" + jobConf.getJobName() + "...");
		
		// 解释出作业所需要的参数
		String[] jobParams = parseDsParams(jobConf);

		// 调用SHELL命令去调用DS作业
		boolean bResult = this.callExternCmd(binPath + "datastage/"
				+ SEHELLNAME, jobParams);
		
		return bResult;
	}
    
	/**
	 * 解释数据作业的参数
	 * @param pds
	 * @return
	 */
	private String[] parseDsParams(PluginJobConf pds) {

		String DSJOB = pds.getJobName();
		StringBuffer jobParamsBuf = new StringBuffer();
		String HostIP = context.vv.getValue("HostIP");
		String HostUser = context.vv.getValue("HostUser");
		String HostPwd = context.vv.getValue("HostPwd");
		String DSPRJ = context.vv.getValue("DSPRJ");
		String DSHOME = context.vv.getValue("DSHOME");

		// 根据参数组标识去作业参数配置表读取参数
		if (null != pds.getParamGroupId() && !"".equals(pds.getParamGroupId())) {
			Connection conn = null;
			try {
				conn = this.getPluginConnection();
				PluginJobParamDao jobParamDao = new PluginJobParamDao(conn);
				List<PluginJobParam> jobParamList = jobParamDao.queryWithCond(
						"where ds_params='" + pds.getParamGroupId() + "'", "");
				for (int i = 0; i < jobParamList.size(); i++) {
					jobParamsBuf.append(" -param $").append(
							jobParamList.get(i).getJobParamName().trim())
							.append("=").append(
									context.toParseSysVariable(this
											.toNotNullAndTrim(jobParamList.get(
													i).getJobParamValue())));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				this.writeFaildLog("解释" + pds.getJobName() + "作业参数", e
						.getMessage());
			} finally {
				this.closePluginConnection(conn);
			}
		}

		logger.info("Data Job Params:" + jobParamsBuf);

		String shellParams[] = new String[7];
		shellParams[0] = this.toNotNullAndTrim(DSJOB);
		shellParams[1] = this.toNotNullAndTrim(jobParamsBuf.toString());
		shellParams[2] = this.toNotNullAndTrim(HostIP);
		shellParams[3] = this.toNotNullAndTrim(HostUser);
		shellParams[4] = this.toNotNullAndTrim(HostPwd);
		shellParams[5] = this.toNotNullAndTrim(DSPRJ);
		shellParams[6] = this.toNotNullAndTrim(DSHOME);

		return shellParams;
	}
 

}
