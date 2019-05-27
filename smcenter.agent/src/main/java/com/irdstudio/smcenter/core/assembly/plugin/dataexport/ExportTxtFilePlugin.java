package com.irdstudio.smcenter.core.assembly.plugin.dataexport;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.irdstudio.smcenter.core.assembly.plugin.AbstractPlugin;

/**
 * 导出数据到文本文件中
 * @author guangming.li
 * @version 1.0
 * @date 2014-09-16
 */
public class ExportTxtFilePlugin extends AbstractPlugin{
	
	/* 表数据装配配置列表 */
	private List<PluginExportConf> exportList = null;

	/**
	 * 从数据库中读取导出配置列表
	 */
	@Override
	protected boolean doReadConfigureFromDB(Connection conn,
			String szConfIdentify) throws SQLException {
		PluginExportConfDao pecDao = new PluginExportConfDao(conn);
		exportList = pecDao.queryWithCond(" where plugin_conf_id='"
				+ szConfIdentify + "'", "order by conf_sort");
		if (exportList.size() < 1) {
			context.szLastErrorMsg = "未读取到配置标识为：" + szConfIdentify + "的数据导出配置!";
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 导出数据到文本文件
	 */
	public boolean execute() {

		boolean flag = true;

		// 循环配置装载配置列表，逐个执行装载
		for (int i = 0; i < exportList.size(); i++) {

			PluginExportConf exportConf = exportList.get(i);

			// 解释数据导出配置中的变量(支持系统变量系统中的变量)
			exportConf.setExportTarget(context.toParseSysVariable(exportConf
					.getExportTarget()));
			exportConf.setExportToFile(context.toParseSysVariable(exportConf
					.getExportToFile()));

			this.logger
					.info("........................................................................");
			this.logger.info("准备导出数据：从\"" + exportConf.getExportTarget()
					+ "\"到\"" + exportConf.getExportToFile() + "\"...");

			// 执行装载
			Connection conn = this.getPluginConnection();
			DataExportExecutor dataExport = new DataExportExecutor(exportConf,
					conn, logger);
			flag = dataExport.run();
			this.closePluginConnection(conn);
		}
		return flag;
	}
}
