package com.irdstudio.smcenter.core.assembly.plugin.check;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.irdstudio.smcenter.core.assembly.plugin.AbstractPlugin;
import com.irdstudio.smcenter.core.assembly.plugin.PluginConst;

/**
 * 数据检查应用插件
 * @author guangming.li
 * @version 1.0
 * @date 2013-10-25
 */
public class DataCheckPlugin extends AbstractPlugin{

	/* 存放检查配置数据 */
	private List<PluginCheckConf> checkList;		

	@Override
	protected boolean doReadConfigureFromDB(Connection conn,
			String szConfIdentify) throws SQLException {
		PluginCheckConfDao checkDao = new PluginCheckConfDao(conn);
		checkList = checkDao.queryPluginCheckConfWithCond(
				" where plugin_conf_id='" + szConfIdentify + "'",
				"order by conf_sort");
		return true;
	}	
	
	/**
	 * 根据检查配置清单执行检查
	 */
	public boolean execute() {
		boolean flag = true;
		Connection conn = null;
		ResultSet rs = null;
		Statement st = null;
		try {
			conn = this.getPluginConnection();
			st = conn.createStatement();
			// 循环配置的检查语句列表，逐个执行
			for (int i = 0; i < checkList.size(); i++) {

				PluginCheckConf pcc = checkList.get(i);
				
				// 判断否在有效期内,不在有效期内的配置直接跳过
				if (isInValidConf(context.getCurrentDataDate(),
						pcc.getValidDate(), pcc.getInvalidDate())) {
					continue;
				}
				
				this.logger.info("........................................................................");
				this.logger.info("准备执行检查：" + pcc.getCheckItemName() + "...");

				// 解释检查语句中的参数
				String sql = context.toParseSysVariable(pcc.getCheckItemSql());
				this.logger.info(sql);

				// 执行SQL,获得返回记录集
				boolean bCheckFlag = false;
				rs = st.executeQuery(sql);
				if (rs.next()) {
					String[] exps = parseExpression(pcc.getCheckSucCondition());
					if (exps == null) {
						this.writeFaildLog("成功条件配置有误!");
						bCheckFlag = false;
					} else {
						bCheckFlag = isSuccess(rs.getString(exps[0]), exps);
						if (!bCheckFlag) {
							// 如果检查失败,解释失败描述信息中的字段变量
							this.context.szLastErrorMsg = context
									.toParseFieldVariable(
											pcc.getCheckErrDesc(), rs)
									+ this.context.szLastErrorMsg;
						}
					}
					rs.close();
					rs = null;
				} else {
					this.writeFaildLog("检查语句查出来的记录数为零，无法比较!", sql);
				}

				if (bCheckFlag) {
					this.writeSuccessLog("执行检查:" + pcc.getCheckItemName(),
							sql);
				} else {
					this.writeFaildLog("执行检查:" + pcc.getCheckItemName(), sql
							+ ";" + context.szLastErrorMsg);
					// 根据策略判断是继续(跳过)，还是中止检查
					if (PluginConst.FAILD_DEAL_SKIP.equals(pcc
							.getCheckFaildDeal())) {
						// 如果失败处理方式为跳过,则继续
						continue;
					} else {
						// 如果失败处理方式为中止,退出循环
						flag = false;
						break;
					}
				}
			}

		} catch (SQLException e) {
			flag = false;
			context.szLastErrorMsg = e.getMessage();
		} finally {
			this.close(rs, st, null);
			this.closePluginConnection(conn);
		}
		return flag;
	}
}
