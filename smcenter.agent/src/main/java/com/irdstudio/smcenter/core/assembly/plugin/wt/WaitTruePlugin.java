package com.irdstudio.smcenter.core.assembly.plugin.wt;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.irdstudio.smcenter.core.assembly.plugin.AbstractPlugin;
/**
 * 等待条件为真应用插件
 * @author guangming.li
 * @version 1.0
 * @since 1.0 2013-10-30
 * @modify 
 * 		2014-05-06 轮询的动作由任务线程根据任务类型去做 
 */
public class WaitTruePlugin extends AbstractPlugin{
	
	/* 存放等待条件配置数据 */
	private List<PluginWtCondition> wtcList;
	@Override
	protected boolean doReadConfigureFromDB(Connection conn,
			String szConfIdentify) throws SQLException {
		boolean flag = true;
		PluginWtConditionDao condDao = new PluginWtConditionDao(conn);
		wtcList = condDao.queryWithCond(" where plugin_conf_id='"
				+ szConfIdentify + "'", "order by conf_sort");
		if (wtcList.size() < 1) {
			context.szLastErrorMsg = "未读取到配置标识为：" + szConfIdentify
					+ "的等待指定条件配置!";
			flag = false;
		} else {
			flag = true;
		}
		return flag;
	}

	/**
	 * 执行阶段
	 * 等待指令条件
	 */
	public boolean execute() {
		boolean bFlag = false;
		Connection conn = null;
		ResultSet rs = null;
		Statement st = null;
		boolean isHaveException = false;
		try {
			conn = this.getPluginConnection();
			st = conn.createStatement();
			// 循环配置的等待语句列表，逐个执行
			for (int i = 0; i < wtcList.size(); i++) {

				PluginWtCondition wtc = wtcList.get(i);

				this.logger.info("准备检查等待条件：" + wtc.getCheckItemName() + "...");

				// 解释检查语句中的参数
				String sql = context.toParseSysVariable(wtc.getCheckItemSql());
				this.logger.info(sql);

				// 执行SQL,获得返回记录集
				boolean bCheckFlag = false;
				rs = st.executeQuery(sql);
				if (rs.next()) {
					String[] exps = parseExpression(wtc.getCheckSucCondition());
					if (exps == null) {
						this.writeFaildLog("成功条件配置有误!");
						bCheckFlag = false;
					} else {
						bCheckFlag = isSuccess(rs.getString(exps[0]), exps);
					}
					rs.close();
					rs = null;
					// 如果检查失败,则跳过循环
					if (!bCheckFlag) {
						this.logger.info("\"" + wtc.getCheckItemName()
								+ "\"条件未满足!");
						bFlag = false;
						break;
					} else {
						this.logger.info("\"" + wtc.getCheckItemName()
								+ "\"条件已满足!");
						bFlag = true;
					}
				} else {
					bFlag = false;
					this.writeFaildLog("检查语句查出来的记录数为零，无法比较!", sql);
				}
			}
		} catch (SQLException e) {
			isHaveException = true;
			context.szLastErrorMsg = e.getMessage();
		} finally {
			this.close(rs, st, null);
			this.closePluginConnection(conn);
		}

		// 如果存在异常,判定为失败并退出
		if (isHaveException) {
			bFlag = false;
		}

		return bFlag;
	}
}
