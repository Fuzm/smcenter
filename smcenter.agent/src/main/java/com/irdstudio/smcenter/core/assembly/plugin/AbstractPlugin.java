package com.irdstudio.smcenter.core.assembly.plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.TConnPool;
import com.irdstudio.smcenter.core.tinycore.log.ILogger;
import com.irdstudio.smcenter.core.tinycore.log.TLogger;
import com.irdstudio.smcenter.core.util.pub.PathUtil;
import com.irdstudio.smcenter.core.util.pub.StreamGobbler;

/**
 * 初步封装插件接口,供后续插件类继承
 * @author guanmgin.li
 * @version 1.0
 * @date 2013-10-10
 */

public abstract  class AbstractPlugin implements IJCIPlugin {

	/* 插件上下文环境对象 */
	protected PluginContext context;
	/* 日志文件对象 */
	protected ILogger logger;
	/* 应用插件数据库日志对象 */
	protected PluginLogService logService;
	/* 类所在根目录(即BIN目录位置) */
	protected String binPath;

	/**
	 * 设置插件的上下文
	 */
	public void setPluginContext(PluginContext context) {
		this.context = context;
		//logger = TLogger.getLogger("[应用插件]:[" + context.szPluginName + "]");
		logger = TLogger.getLogger(context.szBatchSn);
		binPath = PathUtil.getClassRootPath();
	}
	
	/**
	 * 设置插件的数据库日志记录对象
	 * @param logService
	 */
	public void setLogService(PluginLogService logService) {
		this.logService = logService;
	}
	
	/**
	 * 实现从数据库中读取插件所需要的配置数据
	 * 注意：读取配置必须使用默认数据源(即综合服务系统所使用数据源)
	 */
	public boolean readPluginConfigureFromDB(String szConfIdentify) {	
		boolean flag = true;
		Connection conn = null;
		try {
			conn = TConnPool.getDefaultPool().getConnection();
			flag = doReadConfigureFromDB(conn,szConfIdentify);
		} catch (SQLException e) {
			this.context.szLastErrorMsg = e.getMessage();
			flag = false;
			e.printStackTrace();
		} finally {
			TConnPool.getDefaultPool().releaseConnection(conn);
		}
		return flag;		
	}
	
	/**
	 * 默认为不从配置文件中读取配置数据 
	 * 如果继承的插件子类需要用到，则在子类覆盖该方法即可
	 */
	public boolean readPluginConfigureFromFile(String szConfIdentify) {
		return false;
	}

	
	/**
	 * 插件子类真在需要实现的方法(从数据库中读取配置文件)
	 * @param conn
	 * @param szConfIdentify
	 * @return
	 * @throws SQLException
	 */
	protected abstract boolean doReadConfigureFromDB(Connection conn,
			String szConfIdentify) throws SQLException;

	/**
	 * 测试常用变量
	 */
	public void testVariable() {
		logger.info("当前子系统代码:" + context.vv.getValue("subs_code"));
		logger.info("当前数据日期:" + context.getCurrentDataDate());
		logger.info("上一数据日期:" + context.getLastDataDate());
		logger.info("数据装载完成日期:" + context.getLoadFinishedDate());
		logger.info("批次完成日期:" + context.getBatFinishedDate());
		logger.info("下一日期(数据日期+1):" + context.getNextDataDate());
	}
	
	/**
	 * 写应用插件执行日志(成功)
	 * @param szActionName
	 * @param szActionDetailDesc
	 */
	public void writeSuccessLog(String szActionName, String szActionDetailDesc) {
		logger.info(szActionName + "成功! " + szActionDetailDesc);
		logService.writePluginExecLog(context.szBatchSn, context.nPluginId,
				context.szPluginName, szActionName,
				PluginConst.LOG_FLAG_SUCCESS, szActionDetailDesc,
				context.szTaskId, context.szTaskName);
	}
	
	/**
	 * 写应用插件执行日志(成功)
	 * @param szActionName
	 */
	public void writeSuccessLog(String szActionName) {
		logger.info(szActionName + "成功! ");
		logService.writePluginExecLog(context.szBatchSn, context.nPluginId,
				context.szPluginName, szActionName + "成功! ",
				PluginConst.LOG_FLAG_SUCCESS, "", context.szTaskId,
				context.szTaskName);
	}
	
	/**
	 * 写应用插件执行日志(警告)
	 * @param szActionName
	 * @param szActionDetailDesc
	 */
	public void writeWarningLog(String szActionName, String szActionDetailDesc) {
		logger.info(szActionName + "产生警告!  " + szActionDetailDesc);
		logService.writePluginExecLog(context.szBatchSn, context.nPluginId,
				context.szPluginName, szActionName,
				PluginConst.LOG_FLAG_WARNING, szActionDetailDesc,
				context.szTaskId, context.szTaskName);
	}
	
	/**
	 * 写应用插件执行日志(失败)
	 * @param szActionName
	 * @param szActionDetailDesc
	 */
	public void writeFaildLog(String szActionName, String szActionDetailDesc) {
		context.szLastErrorMsg = szActionDetailDesc;
		writeFaildLog(szActionName);
	}
	
	/**
	 * 写应用插件执行日志(失败)
	 * @param szActionName
	 */
	public void writeFaildLog(String szActionName) {
		logger.info(szActionName + "失败!  " + context.szLastErrorMsg);
		logService.writePluginExecLog(context.szBatchSn, context.nPluginId,
				context.szPluginName, szActionName + "失败!",
				PluginConst.LOG_FLAG_FAILD, context.getLastErrorMsg(),
				context.szTaskId, context.szTaskName);
	}
	
	/**
	 * 获取连接(从连接池内)
	 * @return
	 * @throws SQLException
	 */
	public Connection getPluginConnection(){
		Connection conn = null;
		try {
			conn = context.getPluginConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			context.szLastErrorMsg = "获取连接失败!" + e.getMessage();
		}
		return conn;
	}
	
	/**
	 * 关闭指定连接(放入连接池内)
	 * @param conn
	 */
	public void closePluginConnection(Connection conn) {
		context.releasePluginConnection(conn);
	}	

	/**
	 * 执行指定的SQL语句并返回成功或失败
	 * 异常信息自动放入到上下文中的szLastErrMsg
	 * @param sql
	 * @return
	 */
	protected boolean executeSql(String sql) {
		boolean flag = true;
		Connection conn = null;
		Statement st = null;
		try {
			conn = this.getPluginConnection();
			st = conn.createStatement();
			st.execute(sql);
		} catch (SQLException e) {
			this.context.szLastErrorMsg = e.getMessage();
			flag = false;
			context.szLastErrorMsg = e.getMessage();
			e.printStackTrace();
		} finally {
			if (st != null)
				try {
					st.close();
					st = null;
				} catch (SQLException e) {
					context.szLastErrorMsg = e.getMessage();
					e.printStackTrace();
				}
			this.closePluginConnection(conn);
		}
		return flag;
	}
	
	/**
	 * 关闭资源
	 * @param theRs
	 * @param theStmt
	 * @param thePs
	 */
	public void close(ResultSet theRs, Statement theStmt,
			PreparedStatement thePs) {
		try {
			if (theRs != null)
				theRs.close();
			if (theStmt != null)
				theStmt.close();
			if (thePs != null)
				thePs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}		
	
	/**
	 * 判断是否为无效的配置
	 * @param curDate 当前日期
	 * @param validDate 生效日期
	 * @param inValidDate 失效日期
	 * @return
	 */
	protected boolean isInValidConf(String curDate, String validDate,
			String inValidDate) {
		
		return false;
		
		/*
		// 如果没有日期,则不进行比较,判断为有效
		if ("".equals(validDate) || "".equals(inValidDate)
				|| "".equals(curDate)) {
			return false;
		}
		if (DateVerify.isDate2(validDate) && DateVerify.isDate2(inValidDate)
				&& DateVerify.isDate2(curDate)) {
			if (curDate.compareTo(validDate) >= 0
					&& curDate.compareTo(inValidDate) < 0) {
				return false;
			} else {
				return true;
			}
		} else {
			// 如果日期格式无效,则直接返回配置无效
			return true;
		}
		*/
	}
	
	/**
	 * 调用外部命令(不论成功失败均记入日志)
	 * @param params
	 * @return
	 */
	protected boolean callExternCmd(String[] params){
		
		// 判断调用方式是否符合要求,不符合则退出
		if (params == null || params.length < 1) {
			context.szLastErrorMsg = "无效的外部命令调用方式!";
			return false;
		}

		boolean bFlag = true;
		int exitVal = -1;
		Process p = null;
		String szInfo = "";

		// 取得命令的所有参数,获得完整的命令
		StringBuffer szFullCmd = new StringBuffer(params[0]);
		for (int i = 1; i < params.length; i++) {
			szFullCmd.append(" ").append(params[i]);
		}

		// 外部命令运行过程
		Runtime rt = Runtime.getRuntime();
		try {
			if (params.length == 1) {
				// 如果数据只有一个参数,则当命令直接执行
				p = rt.exec(params[0]);
			} else {
				// 否则按数组方式执行
				p = rt.exec(params);
			}
			// 启用线程输出外部命令中的错误信息
			StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(),
					"ERROR");
			errorGobbler.start();
			// 启用线程输出外部命令中的普通信息
			StreamGobbler outGobbler = new StreamGobbler(p.getInputStream(),
					"STDOUT");
			outGobbler.start();
			exitVal = p.waitFor();
			// 将信息截获后放入到上下文中
			szInfo = ("返回值：" + exitVal + "，执行信息:" + errorGobbler.getInfo() + outGobbler
					.getInfo());
			context.szLastErrorMsg = szInfo;
		} catch (Exception ex) {
			// 调用时出现异常,直接记执行失败的日志并返回false
			bFlag = false;
			this.writeFaildLog("执行外部命令：" + szFullCmd, szInfo);
			ex.printStackTrace();
		} finally {
			if (p != null) {
				if (exitVal == 0) {
					// 记成功日志
					bFlag = true;
					this.writeSuccessLog("执行外部命令：" + szFullCmd, szInfo);
				} else {
					// 记失败日志
					bFlag = false;
					this.writeFaildLog("执行外部命令：" + szFullCmd, szInfo);
				}
			}
		}
		return bFlag;		
	}
	/**
	 * 应用插件调用外部命令
	 * @param cmd
	 * @param params
	 * @return
	 */
	protected boolean callExternCmd(String cmd, String[] params) {
		String[] cmdParams = new String[params == null ? 1 : params.length + 1];
		cmdParams[0] = cmd;
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				cmdParams[i + 1] = params[i];
			}
		}
		return callExternCmd(cmdParams);
	}
	
	/**
	 * 应用插件调用外部命令(无参数的情况下) 
	 * @param cmd
	 * @return
	 */
	protected boolean callExternCmd(String cmd) {
		String[] cmdParams = new String[1];
		cmdParams[0] = cmd;
		return callExternCmd(cmdParams);
	}

	/**
	 * 表达式是否为真
	 * @param result
	 * @param opt
	 * @return
	 */
	protected boolean isSuccess(String result, String[] opt) {

		int r = toNotNullAndTrim(result).compareTo(opt[2]);
		// int r = trim("b  ").compareTo(opt[2]);

		if (opt[1].equals(">")) {
			if (r <= 0)
				return false;
		} else if (opt[1].equals(">=")) {
			if (r < 0)
				return false;
		} else if (opt[1].equals("=")) {
			if (r < 0 || r > 0)
				return false;
		} else if (opt[1].equals("<")) {
			if (r >= 0)
				return false;
		} else if (opt[1].equals("<=")) {
			if (r > 0)
				return false;
		} else if (opt[1].equals("<>") || (opt[1].equals("!="))) {
			if (r == 0)
				return false;
		} else {
			context.szLastErrorMsg = "Operator [" + opt[1] + "] is illegal.";
			return false;
		}
		return true;
	}
	
	/**
	 * 解释表达式，得到三要素
	 * 比较值，比较符号，被比较值
	 * @param expression
	 * @return
	 */
	protected String[] parseExpression(String expression) {

		int i, j, k;

		String col = null;
		String symbol = null;
		String clause = null;

		expression = expression.replaceAll(" ", "");
		i = expression.indexOf("{");
		if (i == -1) {
			this.context.szLastErrorMsg = "illegal expression,lack of leads{";
			return null;
		}

		j = expression.indexOf("}", i);
		if (j == -1) {
			this.context.szLastErrorMsg = "illegal expression,lack of encloseure }";
			logger.info(this.context.szLastErrorMsg);
			return null;
		}

		switch (expression.charAt(j + 2)) {
		case '=':
		case '>':
			k = 2;
			break;
		default:
			k = 1;
		}

		col = expression.substring(i + 1, j);
		symbol = expression.substring(j + 1, j + 1 + k);
		clause = expression.substring(j + 1 + k);

		return new String[] { col, symbol, clause };

	}
	
	/**
	 * 去空格前判断是否为空指针
	 * @param str
	 * @return
	 */
	protected String toNotNullAndTrim(String str) {
		return str == null ? "" : str.trim();
	}	
}
