package com.irdstudio.smcenter.core.assembly.plugin.dataload;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.irdstudio.smcenter.core.assembly.plugin.AbstractPlugin;
import com.irdstudio.smcenter.core.assembly.plugin.PluginConst;
import com.irdstudio.smcenter.core.assembly.plugin.PluginLogService;
import com.irdstudio.smcenter.core.util.date.CurrentDateUtil;
import com.irdstudio.smcenter.core.util.date.DateCalculate;
/**
 * 文本文件数据装载插件
 * @author guangming.li
 * @version 1.0
 * @date 2014-04-22
 */
public class TxtFileLoadPlugin extends AbstractPlugin {
	
	/* 表数据装配配置列表 */
	private List<PluginLoadConf> loadList = null;
	
	/**
	 * 从数据库中读取装载配置列表后开始装载
	 */
	@Override
	protected boolean doReadConfigureFromDB(Connection conn,
			String szConfIdentify) throws SQLException {
		PluginLoadConfDao plcDao = new PluginLoadConfDao(conn);
		loadList = plcDao.queryWithCond(" where plugin_conf_id='"
				+ szConfIdentify + "'", "order by conf_sort");
		if (loadList.size() < 1) {
			context.szLastErrorMsg = "未读取到配置标识为：" + szConfIdentify + "的数据装载配置!";
			return false;
		} else {
			return true;
		}
	}	
	
	/**
	 * 执行装载文本文件
	 */
	public boolean execute() {
		
		boolean flag = true;
		
		// 循环配置装载配置列表，逐个执行装载
		for (int i = 0; i < loadList.size(); i++) {

			PluginLoadConf loadConf = loadList.get(i);
			
			// 解释数据装载配置中的变量(支持系统变量系统中的变量)
			loadConf.setTableName(context.toParseSysVariable(loadConf
					.getTableName()));
			loadConf.setLoadFromFile(context.toParseSysVariable(loadConf
					.getLoadFromFile()));
			loadConf.setBeforeLoadSql(context.toParseSysVariable(loadConf
					.getBeforeLoadSql()));
			loadConf.setAfterLoadSql(context.toParseSysVariable(loadConf
					.getAfterLoadSql()));
			
			this.logger
			.info("........................................................................");
			this.logger.info("准备执行装载：" + loadConf.getTableCnname() + "("
					+ loadConf.getTableName() + ")...");
			
			// 执行装载
			Connection conn = this.getPluginConnection();
			DataImportExecutor dataImport = new DataImportExecutor(loadConf,
					conn, logger);
			PluginLoadResult loadResult = dataImport.run();
			this.closePluginConnection(conn);
			
			// 补充需要记录的装载结果信息
			loadResult.setEndTime(CurrentDateUtil.getTodayDateEx2());
			loadResult.setCostTime(DateCalculate.computeIntervalMills(
					loadResult.getStartTime(), loadResult.getEndTime()));
			loadResult.setBatchDate(context.getCurrentDataDate());
			loadResult.setBatchSerialNo(context.szBatchSn);
			loadResult.setBatchId(context.szBatchId);
			
			// 记录数据装载结果到数据库中
			PluginLogService.getInstance().writeLoadResult(loadResult);
			
			// 判断是否调用成功
			if (!DataImportConst.LOAD_RESULT_FAILD.equals(loadResult
					.getLoadResult())) {
				this.writeSuccessLog("执行数据装载:" + loadConf.getTableCnname()
						+ "(" + loadConf.getTableName() + ")");
			} else {
				this.writeFaildLog("执行数据装载:" + loadConf.getTableCnname()
						+ "(" + loadConf.getTableName() + ")");
				// 根据装载失败处理方式判断是继续(跳过)，还是中止检查
				if (!PluginConst.FAILD_DEAL_SKIP.equals(loadConf
						.getLoadFaildDeal())) {
					// 如果失败处理方式为中止,退出循环
					flag = false;
					break;
				}
			}			
		}
		return flag;
	}
}
