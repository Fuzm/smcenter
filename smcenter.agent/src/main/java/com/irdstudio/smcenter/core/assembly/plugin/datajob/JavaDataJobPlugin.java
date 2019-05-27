package com.irdstudio.smcenter.core.assembly.plugin.datajob;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * Java数据作业插件
 * 由具体的Java类来实现作业处理
 * @author guangming.li
 * @version 1.0
 * @date 2014-04-24
 */
public class JavaDataJobPlugin extends AbstractDataJobPlugin{

	@Override
	public boolean doExecuteJob(PluginJobConf jobConf) {
		boolean flag = false;
		this.logger.info("执行Java作业：" + jobConf.getJobName() + "...");
		try {
			IJavaJob job = (IJavaJob) Class.forName(jobConf.getJobImplement())
					.newInstance();
			//设置参数
			if(StringUtils.isNoneBlank(jobConf.getParamGroupId())) {
				paserJobParams(jobConf.getParamGroupId());
			}
			flag = job.execute(context);
		} catch (InstantiationException e) {
			flag = false;
			context.szLastErrorMsg = e.getMessage();
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			flag = false;
			context.szLastErrorMsg = e.getMessage();
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			flag = false;
			context.szLastErrorMsg = e.getMessage();
			e.printStackTrace();
		} catch (Exception e) {
			flag = false;
			context.szLastErrorMsg = e.getMessage();
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 设置参数
	 * @param paramGroupId
	 * @author fuzm
	 * @version 2019年1月16日 下午9:36:33
	 */
	private void paserJobParams(String paramGroupId) {
		Connection conn = null;
		try {
			conn = this.getPluginConnection();
			PluginJobParamDao jobParamDao = new PluginJobParamDao(conn);
			//获取插件参数
			List<PluginJobParam> paramList = jobParamDao.queryWithCond("where param_group_id = '" + paramGroupId + "'", "");
			if(null != paramList && paramList.size() != 0) {
				for(int i=0; i<paramList.size(); i++) {
					PluginJobParam param = paramList.get(i);
					context.getVv().addVariable(param.getJobParamName(), param.getJobParamValue());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeFaildLog("解释" + paramGroupId + "作业参数", e
					.getMessage());
		} finally {
			this.closePluginConnection(conn);
		}
	}
}
