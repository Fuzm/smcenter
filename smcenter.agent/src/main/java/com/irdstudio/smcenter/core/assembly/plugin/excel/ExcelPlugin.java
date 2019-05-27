package com.irdstudio.smcenter.core.assembly.plugin.excel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.irdstudio.smcenter.core.assembly.plugin.AbstractPlugin;
import com.irdstudio.smcenter.core.assembly.plugin.PluginConst;
import com.irdstudio.smcenter.core.assembly.plugin.excel.export.ExcelExportExecutor;
import com.irdstudio.smcenter.core.util.pub.PathUtil;
/**
 * Excel导出插件(按Excel模板进行导出)
 * @author guangming.li
 * @version 1.0
 * @date 2014-09-23
 */
public class ExcelPlugin extends AbstractPlugin{
	
	/* Excel操作配置列表 */
	private List<PluginExcelConf> excelConfList = null;

	@Override
	protected boolean doReadConfigureFromDB(Connection conn,
			String szConfIdentify) throws SQLException {
		PluginExcelConfDao pecDao = new PluginExcelConfDao(conn);
		excelConfList = pecDao.queryWithCond(" where plugin_conf_id='"
				+ szConfIdentify + "'", "order by conf_sort");
		if (excelConfList.size() < 1) {
			context.szLastErrorMsg = "未读取到配置标识为：" + szConfIdentify
					+ "的Excel操作配置!";
			return false;
		} else {
			return true;
		}
	}
		

	@Override
	public boolean execute() {
		
		boolean result = true;
		Connection conn = this.getPluginConnection();
		for (int i = 0; i < excelConfList.size(); i++) {
			
			PluginExcelConf excelConf = excelConfList.get(i);
			excelConf.setConfigureFile(PathUtil.getClassRootPath()
					+ context.toParseSysVariable(excelConf.getConfigureFile()));
			this.logger.info("开始执行Excel操作[" + excelConf.getExcelOperType()
					+ ",配置文件:" + excelConf.getConfigureFile() + ",参数键值对："
					+ excelConf.getParamKeyValue() + "]...");
			
			// 设置变量解释器(根据参数键值对增加可以解释的变量)
			String[] params = excelConf.getParamKeyValue().split(",");
			for (int j = 0; j < params.length; j++) {
				String[] keyValues = params[j].split(":");
				context.vv.addVariable(keyValues[0], keyValues[1]);
			}
			
			// 根据不同的操作类型调用相应的执行者执行操作
			result = new ExcelExportExecutor(excelConf.getConfigureFile(),
					context.getVv(), conn, logger).run();
			
			if (!result
					&& PluginConst.FAILD_DEAL_OVER.equals(excelConf
							.getFaildDeal())) {
				this.writeFaildLog("执行Excel操作[" + excelConf.getExcelOperType()
						+ ",配置文件:" + excelConf.getConfigureFile() + ",参数键值对："
						+ excelConf.getParamKeyValue() + "]");
				break;
			} else {
				this.writeSuccessLog("执行Excel操作["
						+ excelConf.getExcelOperType() + ",配置文件:"
						+ excelConf.getConfigureFile() + ",参数键值对："
						+ excelConf.getParamKeyValue() + "]");
			}
		}
		
		this.closePluginConnection(conn);
		return result;
	}
}
