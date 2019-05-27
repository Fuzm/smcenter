package com.irdstudio.smcenter.core.assembly.plugin.reform;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.irdstudio.smcenter.core.assembly.plugin.AbstractPlugin;
import com.irdstudio.smcenter.core.util.date.DateCalculate;
import com.irdstudio.smcenter.core.util.date.DateConvert;

/**
 * 重整DB2数据库表应用插件
 * @author guangming.li
 * @version 1.0
 * @date 2013-11-07
 */
public class ReformTableForDB2Plugin extends AbstractPlugin {
	
	/* 存放数据表重整配置数据 */
	private List<PluginReformConf> rcList;

	@Override
	protected boolean doReadConfigureFromDB(Connection conn,
			String szConfIdentify) throws SQLException {
		PluginReformConfDao rcDao = new PluginReformConfDao(conn);
		rcList = rcDao.queryPluginReformConfWithCond(
				" where plugin_conf_id='" + szConfIdentify + "'",
				"order by conf_sort");
		return true;
	}	

	/**
	 * 执行DB2数据表重整
	 */
	public boolean execute() {

		boolean flag = true;
		// 循环配置的文件操作列表，逐个执行
		for (int i = 0; i < rcList.size(); i++) {

			PluginReformConf rc = rcList.get(i);
			// 判断否在有效期内,不在有效期内的配置直接跳过
			if (isInValidConf(context.getCurrentDataDate(), rc.getValidDate(),
					rc.getInvalidDate())) {
				continue;
			}

			// 根据操作周期配置判断是否要到达下次处理日期
			if (!isArrivedNextOpDate(rc)) {
				// 如果没有到达处理日期,直接跳过
				continue;
			} else {

				// 分析文件处理配置信息并获得需要执行的完整外部命令
				String[] cmdForReform = getReformCmd(rc);

				this.logger
						.info("........................................................................");
				this.logger.info("准备重整" + rc.getReformTableSchema() + "."
						+ rc.getReformTableName() + "...");

				// 调用外部命令去进行数据表重整操作
				boolean bResult = this.callExternCmd(cmdForReform);

				// 判断是否调用成功
				if (bResult) {
					this.writeSuccessLog("重整" + rc.getReformTableSchema() + "."
							+ rc.getReformTableName());
					// 更新上次重整日期为今天
					String sql = "update urps.plugin_reform_conf set last_reform_date='"
							+ context.getCurrentDataDate()
							+ "' where plugin_conf_id='"
							+ rc.getPluginConfId()
							+ "' and conf_sort=" + rc.getConfSort();
					if (!this.executeSql(sql)) {
						// 如果更新上次操作日期出错,记日志并标志为失败
						flag = false;
						this.writeFaildLog("更新上次重整日期");
					}
				} else {
					this.writeFaildLog("重整" + rc.getReformTableSchema() + "."
							+ rc.getReformTableName());
				}
			}
		}
		return flag;
	}

	/**
	 * 组合成执行DB2表重整的命令
	 * @param rc
	 * @return
	 */
	private String[] getReformCmd(PluginReformConf rc) {
		
		String[] cmdParams = new String[6];
		cmdParams[0] = this.binPath + "db2shell/reformTable.sh";
		cmdParams[1] = context.getDataSourceInfo().getDsDbName();
		cmdParams[2] = context.getDataSourceInfo().getDsUserId();
		cmdParams[3] = context.getDataSourceInfo().getDsUserPwd();
		cmdParams[4] = rc.getReformTableSchema();
		cmdParams[5] = rc.getReformTableName();
		
		return cmdParams;
	}

	/**
	 * 判断是否到达下一处理日期
	 * @param pfoc
	 * @return
	 */
	private boolean isArrivedNextOpDate(PluginReformConf rc) {

		boolean bFlag = false;
		if (rc.getLastReformDate() == null || "".equals(rc.getLastReformDate())) {
			// 如果没有设置上次重整日期,默认以当前日期来判断（配置表中要求必须设置该日期)
			rc.setLastReformDate(context.getCurrentDataDate());
		}

		// 取得下次重整日期(上次操作日期+周期)
		String nextOpDate = DateConvert.toString(DateCalculate.addDays(
				DateConvert.toDateWithSeparate1(rc.getLastReformDate()),
				rc.getReformCycleDay()));

		// 判断下次操作日期是否与今天的数据日期相等或大于
		if (context.getCurrentDataDate().compareTo(nextOpDate) >= 0) {
			bFlag = true;
		}

		return bFlag;
	}
}
