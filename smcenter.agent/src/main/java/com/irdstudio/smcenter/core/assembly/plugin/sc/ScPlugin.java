package com.irdstudio.smcenter.core.assembly.plugin.sc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.irdstudio.smcenter.core.assembly.plugin.AbstractPlugin;
import com.irdstudio.smcenter.core.assembly.plugin.PluginConst;
/**
 * 转存清数应用插件
 * @author fangmei.chen
 * @version 1.0
 * @date 2013-11-01
 */
public class ScPlugin  extends AbstractPlugin{

	/* 存放转存清数配置数据 */
	private List<PluginScData> dsList;
	/* 转存清数据SHELL脚本所在的路径 */
	private String shellDir;

	@Override
	protected boolean doReadConfigureFromDB(Connection conn,
			String szConfIdentify) throws SQLException {
		PluginScDataDao dsDao = new PluginScDataDao(conn);
		dsList = dsDao.queryPluginScDataWithCond(" where plugin_conf_id='"
				+ szConfIdentify + "'", "order by conf_sort");
		shellDir = this.binPath + "db2shell/";
		if (dsList.size() < 1) {
			context.szLastErrorMsg = "未读取到配置标识为：" + szConfIdentify + "的转存清数配置!";
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 转存清数插件执行逻辑
	 */
	public boolean execute() {
		
		boolean flag = true;

		// 循环配置的转存清数作业列表，逐个执行
		for (int i = 0; i < dsList.size(); i++) {

			PluginScData pds = dsList.get(i);
			// 判断否在有效期内,不在有效期内的配置直接跳过
			if (isInValidConf(context.getCurrentDataDate(), pds.getValidDate(),
					pds.getInvalidDate())) {
				continue;
			}
			
			this.logger.info("........................................................................");
			this.logger.info("准备调用转存清数处理：" + pds.getScDesc() + "...");

			// 获得需要执行的SHELL命令
			String[] cmdForSc = getFullScCmd(pds);

			// 调用SHELL命令去执行转存清数
			boolean bResult = this.callExternCmd(cmdForSc);

			// 判断是否调用成功
			if (bResult) {
				this.writeSuccessLog("执行转存清数:" + pds.getScDesc());
			} else {
				this.writeFaildLog("执行转存清数:" + pds.getScDesc());
				if (PluginConst.FAILD_DEAL_SKIP.equals(pds.getScFaildDeal())) {
					// 如果失败处理方式为跳过,则继续
					continue;
				} else {
					// 如果失败处理方式为中止,退出循环
					flag = false;
					break;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 获得最终执行转存清数的命令
	 * @param pds
	 * @return
	 */
	private String[] getFullScCmd(PluginScData pds) {

		String dbName = context.getDataSourceInfo().getDsDbName();
		String dbUser = context.getDataSourceInfo().getDsUserId();
		String dbPwd = context.getDataSourceInfo().getDsUserPwd();
		String dbSchema = context.getDataSourceInfo().getDsSchemaName();
		String pathTmpData = context.vv.getValue("path_tmpdata");
		String dataDate = context.getCurrentDataDate();

		// 转存清数配置信息
		String opType = pds.getOpType().toUpperCase();
		String opSql = pds.getOpSql();
		String writeTable = pds.getWirteTable();
		String day = "1 ";// 系统的日期几号
		day = String.valueOf(this.getDay(dataDate));

		// 根据OP_TYPE判断执行何种转存清数命令
		String[] cmdParams = null;
		if ("LDCRIST".equals(opType)) {
			// 通用load cursor 移动数据
			cmdParams = new String[8];
			cmdParams[0] = shellDir + "loadCursorForTask.sh";
			cmdParams[1] = dbName;
			cmdParams[2] = dbUser;
			cmdParams[3] = dbPwd;
			cmdParams[4] = dbSchema;
			cmdParams[5] = opSql;
			cmdParams[6] = writeTable;
			cmdParams[7] = "insert";
		} else if ("LDCRRPL".equals(opType)) {
			// 通用load cursor 移动数据
			cmdParams = new String[8];
			cmdParams[0] = shellDir + "loadCursorForTask.sh";
			cmdParams[1] = dbName;
			cmdParams[2] = dbUser;
			cmdParams[3] = dbPwd;
			cmdParams[4] = dbSchema;
			cmdParams[5] = opSql;
			cmdParams[6] = writeTable;
			cmdParams[7] = "replace";
		} else if ("LDCRMTAB".equals(opType)) {
			// 月表数据处理：如果是1号，用1号的数据替换该表的上月数据，否则直接插入数据
			cmdParams = new String[8];
			cmdParams[0] = shellDir + "loadCursorForMonthTab.sh";
			cmdParams[1] = dbName;
			cmdParams[2] = dbUser;
			cmdParams[3] = dbPwd;
			cmdParams[4] = dbSchema;
			cmdParams[5] = opSql;
			cmdParams[6] = writeTable;
			cmdParams[7] = day;
		} else if ("LDFLIST".equals(opType)) {
			// 通用装入数据(INSERT方式)
			cmdParams = new String[9];
			cmdParams[0] = shellDir + "loadFileForTask.sh";
			cmdParams[1] = dbName;
			cmdParams[2] = dbUser;
			cmdParams[3] = dbPwd;
			cmdParams[4] = dbSchema;
			cmdParams[5] = opSql;
			cmdParams[6] = writeTable;
			cmdParams[7] = "insert";
			cmdParams[8] = pathTmpData;
		} else if ("LDFLRPL".equals(opType)) {
			// 通用装入数据(REPLACE方式)
			cmdParams = new String[9];
			cmdParams[0] = shellDir + "loadFileForTask.sh";
			cmdParams[1] = dbName;
			cmdParams[2] = dbUser;
			cmdParams[3] = dbPwd;
			cmdParams[4] = dbSchema;
			cmdParams[5] = opSql;
			cmdParams[6] = writeTable;
			cmdParams[7] = "replace";
			cmdParams[8] = pathTmpData;
		} else if ("CLRTABDAT".equals(opType)) {
			// 清数处理通用程序
			cmdParams = new String[6];
			cmdParams[0] = shellDir + "clearTableData.sh";
			cmdParams[1] = dbName;
			cmdParams[2] = dbUser;
			cmdParams[3] = dbPwd;
			cmdParams[4] = dbSchema;
			cmdParams[5] = opSql;
		} else if ("DELPATM".equals(opType)) {
			// 删除分区表最小分区，并增加一个分区
			cmdParams = new String[7];
			cmdParams[0] = shellDir + "detachMonthPartion.sh";
			cmdParams[1] = dbName;
			cmdParams[2] = dbUser;
			cmdParams[3] = dbPwd;
			cmdParams[4] = dbSchema;
			cmdParams[5] = writeTable;
			cmdParams[6] = this.getMindate(pds, dataDate) + " ";
		}
		return cmdParams;
	}

	public String rpl(String str) {
		String tmp_str = "null";
		if (str != null) {
			tmp_str = tmp_str.replaceAll(" ", "");
		}
		return tmp_str;
	}

	public String getMindate(PluginScData pds, String sjrq) {
		Connection conn = null;
		String mindate = "";
		try {
			conn = this.getPluginConnection();
			PluginScDataDao dsDao = new PluginScDataDao(conn);
			mindate = dsDao.getMindate(sjrq, pds.getSaveM());

		} catch (SQLException e) {
			this.context.szLastErrorMsg = e.getMessage();

			e.printStackTrace();
		} finally {
			this.closePluginConnection(conn);
		}
		return mindate;

	}

	public int getDay(String sjrq) {

		return Integer.parseInt((sjrq.substring(9, 10)));
	}
}
