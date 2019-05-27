package com.irdstudio.smcenter.core.assembly.plugin.common;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.irdstudio.smcenter.core.assembly.plugin.AbstractPlugin;
import com.irdstudio.smcenter.core.tinycore.jdbc.session.CustomTransaction;
import com.irdstudio.smcenter.core.tinycore.jdbc.session.TransactionUtil;
import com.irdstudio.smcenter.core.util.date.DateCalculate;
import com.irdstudio.smcenter.core.util.date.DateConvert;
/**
 * 更新子系统信息应用插件
 * @author guangming.li
 * @version 1.0
 * @date 2013-10-30
 */
public class SubsInfoUpdatePlugin extends AbstractPlugin {
	
	/* 存放系统信息更新动作列表信息 */
	private List<PluginSysupdConf> sysupdList;
	/* 数据库会话操作类(可开启事务) */
	private CustomTransaction tran;
	/* 更新数据日期(等于平台数据日期)动作 */
	private final static int URPS_SUBS_DATA_DATE_ACT = 1;
	/* 更新上一数据日期(等于平台上一数据日期)动作 */
	private final static int URPS_SUBS_LAST_DATA_DATE_ACT = 2;
	/* 更新装数完成日期(等于子系统数据日期)动作 */
	private final static int URPS_SUBS_LOAD_DATE_ACT = 3;
	/* 更新批次完成日期(等于子系统数据日期)动作 */
	private final static int URPS_SUBS_BAT_DATE_ACT = 4;
	/* 更新数据日期(+1)动作 */
	private final static int UPD_SUBS_DATA_DATE_ACT = 5;
	/* 更新上一数据日期(+1)动作 */
	private final static int UPD_SUBS_LAST_DATA_DATE_ACT = 6;
	/* 更新装数完成日期(+1)动作 */
	private final static int UPD_SUBS_LOAD_DATE_ACT = 7;
	/* 更新批次完成日期(+1)动作 */
	private final static int UPD_SUBS_BAT_DATE_ACT = 8;

	@Override
	protected boolean doReadConfigureFromDB(Connection conn,
			String szConfIdentify) throws SQLException {
		PluginSysupdConfDao sysupdDao = new PluginSysupdConfDao(conn);
		sysupdList = sysupdDao.queryWithCond(
				" where plugin_conf_id='" + szConfIdentify + "'",
				"order by conf_sort");
		if (sysupdList.size() < 1) {
			context.szLastErrorMsg = "未读取到配置标识为：" + szConfIdentify
					+ "的系统信息更新配置!";
			return false;
		} else {
			return true;
		}
	}

	public boolean execute() {
		
		Connection conn = null;
		conn = this.getPluginConnection();
		tran = TransactionUtil.createTransaction(conn);
		
		boolean bFlag = false;
		// 循环配置的更新动作列表，逐个执行
		for (int i = 0; i < sysupdList.size(); i++) {
			PluginSysupdConf psc = sysupdList.get(i);
			int iAction = Integer.valueOf(psc.getSysupdAction());
			String sql = "update s_subs_info set ";
			switch (iAction) {
			case URPS_SUBS_DATA_DATE_ACT:
				// 更新数据日期(等于平台数据日期)动作
				sql += "subs_data_date=(select subs_data_date from s_subs_info where subs_code='URPS')";
				break;
			case URPS_SUBS_LAST_DATA_DATE_ACT:
				// 更新上一数据日期(等于平台上一数据日期)动作
				sql += "subs_last_data_date=(select subs_last_data_date from s_subs_info where subs_code='URPS')";
				break;
			case URPS_SUBS_LOAD_DATE_ACT:
				// 更新装数完成日期(等于子系统数据日期)动作
				sql += "subs_load_date=subs_data_date";
				break;
			case URPS_SUBS_BAT_DATE_ACT:
				// 更新批次完成日期(等于子系统数据日期)动作
				sql += "subs_bat_date=subs_data_date";
				break;
			case UPD_SUBS_DATA_DATE_ACT:
				// 更新数据日期(+1)动作
				sql += "subs_data_date='"
						+ DateConvert.toString(DateCalculate.addDays(
								DateConvert.toDateWithSeparate1(context
										.getCurrentDataDate()), 1)) + "'";
				break;
			case UPD_SUBS_LAST_DATA_DATE_ACT:
				// 更新上一数据日期(+1)动作
				sql += "subs_last_data_date='"
						+ DateConvert.toString(DateCalculate.addDays(
								DateConvert.toDateWithSeparate1(context
										.getLastDataDate()), 1)) + "'";
				break;
			case UPD_SUBS_LOAD_DATE_ACT:
				// 更新装数完成日期(+1)动作
				sql += "subs_load_date='"
						+ DateConvert.toString(DateCalculate.addDays(
								DateConvert.toDateWithSeparate1(context
										.getLoadFinishedDate()), 1)) + "'";
				break;
			case UPD_SUBS_BAT_DATE_ACT:
				// 更新批次完成日期(+1)动作
				sql += "subs_bat_date='"
						+ DateConvert.toString(DateCalculate.addDays(
								DateConvert.toDateWithSeparate1(context
										.getBatFinishedDate()), 1)) + "'";
				break;
			}
			sql += " where subs_code='" + context.getSubsCode() + "'";
			logger.info(sql);
			try {
				tran.executeSql(sql);
				bFlag = true;
			} catch (SQLException e) {
				context.szLastErrorMsg = e.getMessage();
				e.printStackTrace();
				bFlag = false;
			}
		}
		
		// 如果执行成功则一起提交,否则回滚
		try {
			if (bFlag) {
				tran.commit();
			} else {
				tran.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			bFlag = false;
			context.szLastErrorMsg = "提交或回滚失败：" + e.getMessage();
		} finally {
			TransactionUtil.releaseTransaction(tran);
			if (conn != null) {				
				this.closePluginConnection(conn);
			}
		}

		return bFlag;
	}
}
