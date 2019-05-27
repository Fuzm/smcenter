package com.irdstudio.smcenter.core.assembly.plugin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.irdstudio.smcenter.core.assembly.plugin.common.PluginDefine;
import com.irdstudio.smcenter.core.assembly.plugin.common.PluginDefineDao;
import com.irdstudio.smcenter.core.assembly.plugin.common.SParamInfo;
import com.irdstudio.smcenter.core.assembly.plugin.common.SParamInfoDao;
import com.irdstudio.smcenter.core.assembly.plugin.common.SSubsDatasource;
import com.irdstudio.smcenter.core.assembly.plugin.common.SSubsDatasourceDao;
import com.irdstudio.smcenter.core.assembly.plugin.common.SSubsInfo;
import com.irdstudio.smcenter.core.assembly.plugin.common.SSubsInfoDao;
import com.irdstudio.smcenter.core.assembly.plugin.util.URPSCryptUtil;
import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.TConnPool;
import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.base.IConnPool;
import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.impl.ConnPoolForC3P0;
import com.irdstudio.smcenter.core.util.date.DateCalculate;
import com.irdstudio.smcenter.core.util.date.DateConvert;
import com.irdstudio.smcenter.core.util.pub.JvmUtil;
import com.irdstudio.smcenter.core.util.pub.PathUtil;
import com.irdstudio.smcenter.core.util.vo.VariableValue;
/**
 * 应用插件执行者
 * @author guangming.li
 * @version 1.2
 * @since 1.0
 * @date 2014-05-15 
 */
public class PluginExecutor {
	
	/* 应用 插件上下文 */
	private PluginContext ctx = null;
	
	/* 插件抽象类 (用于实例化相应的应用插件)*/
	private AbstractPlugin plugin = null;
	
	/* 应用插件定义信息 */
	private PluginDefine pluginDefine = null;
	
	/**
	 * 私有的构造函数(防止许外部类执行)
	 * @param ctx
	 */
	private PluginExecutor(PluginContext ctx){
		this.ctx = ctx;
	}

	/**
	 * 应用插件主调程序(调起JCI应用插件)
	 * @param ctx
	 * @return
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public static boolean callPlugin(PluginContext ctx) throws SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		
		// 构造应用插件执行者对象
		PluginExecutor executor = new PluginExecutor(ctx);

		// 如果初始化成功则开始执行 
		if(executor.doInitialize()){
			return executor.doExecute();
		} else {
			return false;
		}
	}
	
	/**
	 * 初始化应用插件执行者
	 * 检查传入的参数及初始化相关参数
	 * @return
	 */
	private boolean doInitialize() {
		
		boolean flag = false;

		// 从数据库中读取插件信息表并实例化插件
		Connection conn = null;
		try {
			
			conn = TConnPool.getDefaultPool().getConnection();
			PluginDefineDao pdDao = new PluginDefineDao(conn);
			this.pluginDefine = pdDao.queryWithKeys(ctx.nPluginId);
			this.plugin = (AbstractPlugin) Class.forName(
					pluginDefine.getPluginDealClass()).newInstance();
			
			// 初始应用插件日志服务给应用插件
			plugin.logService = PluginLogService.getInstance();				
			
			// 初始化插件可使用的变量到上下文对象中
			ctx.szPluginName = pluginDefine.getPluginName();
			ctx.vv = this.getSubsVariable(conn,ctx.szSubsCode);
			if (ctx.vv == null) {
				plugin.writeFaildLog("从数据库读取系统变量配置");
				return false;
			} else {
				// 将程序的全局变量写一份到变量区
				ctx.vv.addVariable("PLUGIN_BIN_PATH", PathUtil.getClassRootPath());
			}
			
			// 初始化应用插件数据源到上下文中
			bindDataSourceToPluginContext();
			
			// 给应用插件挂载上下文
			plugin.setPluginContext(ctx);			
			
			flag = true;
		} catch (SQLException e) {
			ctx.szLastErrorMsg = e.getMessage();
			e.printStackTrace();
		} catch (InstantiationException e) {
			ctx.szLastErrorMsg = e.getMessage();
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			ctx.szLastErrorMsg = e.getMessage();
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			ctx.szLastErrorMsg = e.getMessage();
			e.printStackTrace();
		} finally {
			TConnPool.getDefaultPool().releaseConnection(conn);
		}

		return flag;
	}

	/**
	 * 初始化应用插件使用的数据源到上下文中
	 * @throws SQLException 
	 */
	private void bindDataSourceToPluginContext()
			throws SQLException {
		
		// 如果数据源为空则取默认连接池
		if (ctx.szSubsDsCode == null || "".equals(ctx.szSubsDsCode)) {
			ctx.setConnPool(TConnPool.getDefaultPool());
			return;
		}

		// 先从已有数据源池中取连接池(没有,则初始化)
		IConnPool connPool = TConnPool.getPoolInst(ctx.szSubsDsCode);
		if (connPool == null) {
			// 初始化数据源并放到数据源池中
			if (ctx.getDataSourceInfo() == null) {
				throw new SQLException("没有找到执行应用插件所需要的数据源!");
			} else {
				if (PluginConst.S_SUBS_DS_TYPE_ORACLE.equals(ctx
						.getDataSourceInfo().getSubsDsType())) {
					connPool = new ConnPoolForC3P0(ctx.getDataSourceInfo()
							.getDsConnStr(), ctx.getDataSourceInfo()
							.getDsUserId(), ctx.getDataSourceInfo()
							.getDsUserPwd(),
							"oracle.jdbc.driver.OracleDriver", 10, 1, 1, 20, 2);
					TConnPool.addConnInst(ctx.szSubsDsCode, connPool);
				} else if (PluginConst.S_SUBS_DS_TYPE_ACCESS.equals(ctx
						.getDataSourceInfo().getSubsDsType())) {

					connPool = new ConnPoolForC3P0(ctx.getDataSourceInfo()
							.getDsConnStr(), ctx.getDataSourceInfo()
							.getDsUserId(), URPSCryptUtil
							.toDecryptWithNoException(ctx.getDataSourceInfo()
									.getDsUserPwd()),
							"sun.jdbc.odbc.JdbcOdbcDriver", 10, 2, 2, 20, 2);
					TConnPool.addConnInst(ctx.szSubsDsCode, connPool);
				}
			}
		}
		ctx.setConnPool(connPool);
	}

	/**
	 * 应用插件执行者
	 * 实现应用插件的执行逻辑
	 */
	private boolean doExecute() {
		
		plugin.testVariable();

		// 初始化应用插件(读取配置)
		plugin.logger.info("开始初始化...");
		plugin.logger.info("配置来源方式:" + ctx.nPluginConfType + ",配置标识:"
				+ ctx.szPluginConfId + ",任务编号:" + ctx.szTaskId + "，任务名称:"
				+ ctx.szTaskName);
		if (ctx.nPluginConfType == PluginConst.CONFIG_DATA_FROM_DB) {
			if (plugin.readPluginConfigureFromDB(ctx.szPluginConfId)) {
				plugin.writeSuccessLog("从数据库读取配置", "配置标识符:"
						+ ctx.szPluginConfId);
			} else {
				plugin.writeFaildLog("从数据库读取配置", ctx.szLastErrorMsg);
				plugin.logger.info("应用插件初始化失败!");
				return false;
			}
		} else {
			if (plugin.readPluginConfigureFromFile(ctx.szPluginConfId)) {
				plugin.writeSuccessLog("从配置文件中读取配置", "配置文件:"
						+ ctx.szPluginConfId);
			} else {
				plugin.writeFaildLog("从配置文件中读取配置", ctx.szLastErrorMsg);
				plugin.logger.info("应用插件初始化失败!");
				return false;
			}
		}

		// 执行应用插件逻辑
		if (plugin.execute()) {
			
			plugin.writeSuccessLog("应用插件执行", "");
			
			StringBuffer info = new StringBuffer();
			info.append("最大连接数：").append(TConnPool.getDefaultPool().getConnectionCount()).append(",");
			info.append(JvmUtil.getJvmMemoryWithM());
			
			plugin.logger.info(info);

			return true;			
		} else {
			
			plugin.writeFaildLog("应用 插件执行", ctx.szLastErrorMsg);
			
			StringBuffer info = new StringBuffer();
			info.append("最大连接数：").append(TConnPool.getDefaultPool().getConnectionCount()).append(",");
			info.append(JvmUtil.getJvmMemoryWithM());
			plugin.logger.info(info);
			
			return false;			
		}		
	}

	/**
	 * 读取所有子系统可以用的变量
	 * @param conn
	 * @param subsCode
	 * @return
	 * @throws SQLException 
	 */
	private VariableValue getSubsVariable(Connection conn, String subsCode)
			throws SQLException {
		
		// 1.从子系统基础信息表中读出所有日期作为变量
		VariableValue vv = null;
		try {

			vv = new VariableValue();
			SSubsInfoDao subsDao = new SSubsInfoDao(conn);
			SSubsInfo subsInfo = subsDao.querySSubsInfoWithKeys(subsCode);
			// 设置子系统数据日期
			vv.addVariable(PluginConst.SUBS_DATA_DATE,
					subsInfo.getSubsDataDate());
			vv.addVariable(PluginConst.SUBS_DATA_DATE_8, subsInfo
					.getSubsDataDate().replaceAll("-", ""));
			// 设置子系统上一数据日期
			vv.addVariable(PluginConst.SUBS_LAST_DATA_DATE,
					subsInfo.getSubsLastDataDate());
			// 设置下一数据日期(根据上一数据日期加1得来)
			vv.addVariable(
					PluginConst.SUBS_NEXT_DATA_DATE,
					DateConvert.toString(DateCalculate.addDays(DateConvert
							.toDateWithSeparate1(subsInfo.getSubsDataDate()), 1)));
			// 设置子系统数据装完日期
			vv.addVariable(PluginConst.SUBS_LOAD_DATE,
					subsInfo.getSubsLoadDate());
			// 设置子系统批次完成日期
			vv.addVariable(PluginConst.SUBS_BAT_DATE, subsInfo.getSubsBatDate());

			// 2.从系统参数表中读出设置的参数作为变量
			SParamInfoDao paramDao = new SParamInfoDao(conn);
			List<SParamInfo> paramList = paramDao.querySParamInfoWithCond("",
					"");
			for (int i = 0; i < paramList.size(); i++) {
				SParamInfo param = paramList.get(i);
				// 2.1全局参数，放入变量区
				if (PluginConst.PARAM_SCOPE_ALL.equals(param.getParamScope())) {
					vv.addVariable(param.getParamCode().trim(), paramList
							.get(i).getParamValue().trim());
				} else if (PluginConst.PARAM_SCOPE_SUBS.equals(param
						.getParamScope())
						&& subsCode.equals(param.getSubsCode())) {
					// 2.2子系统参数,放入变量区
					vv.addVariable(param.getParamCode().trim(), paramList
							.get(i).getParamValue().trim());
				} else if (PluginConst.PARAM_SCOPE_PLUGIN.equals(param
						.getParamScope())
						&& this.pluginDefine.getPluginId() == param.getPluginId()) {
					// 2.3应用插件所需参数,放入变量区
					vv.addVariable(param.getParamCode().trim(), paramList
							.get(i).getParamValue().trim());
				}
			}

			// 3.将子系统代码放入到变量区
			vv.addVariable(PluginConst.SUBS_CODE, subsCode);
			
			// 4.如果有指定当前数据源,则初始化相关变量
			if (!(this.ctx.szSubsDsCode == null || ""
					.equals(this.ctx.szSubsDsCode))) {
				SSubsDatasourceDao sdsDao = new SSubsDatasourceDao(conn);
				SSubsDatasource sds = sdsDao.queryWithKeys(subsCode,
						this.ctx.szSubsDsCode);
				this.ctx.setDataSourceInfo(sds);
				// 将当前数据源信息写一份到变量区
				if (sds != null) {
					vv.addVariable(PluginConst.CUR_DB_NAME, sds.getDsDbName());
					vv.addVariable(PluginConst.CUR_DB_CONN_STR, sds
							.getDsConnStr());
					vv.addVariable(PluginConst.CUR_DB_USER_ID, sds
							.getDsUserId());
					vv.addVariable(PluginConst.CUR_DB_USER_PWD, sds
							.getDsUserPwd());
					vv.addVariable(PluginConst.CUR_DB_USER_PWD_D, URPSCryptUtil
							.toDecryptWithNoException(sds.getDsUserPwd()));
					vv.addVariable(PluginConst.CUR_DB_SCHEMA, sds
							.getDsSchemaName());
				}
			}

			// 5.判断是否需要将其它数据源作放入变量区
			if (PluginConst.STD_Y.equals(this.pluginDefine.getNeedOtherDsVar())) {
				SSubsDatasourceDao sdsDao = new SSubsDatasourceDao(conn);
				List<SSubsDatasource> sdsList = sdsDao.queryWithCond("", "");
				for (int i = 0; i < sdsList.size(); i++) {
					SSubsDatasource sds = sdsList.get(i);
					vv.addVariable(sds.getSubsDsCode() + "."
							+ PluginConst.DB_NAME, sds.getDsDbName());
					vv.addVariable(sds.getSubsDsCode() + "."
							+ PluginConst.DB_CONN_STR, sds.getDsConnStr());
					vv.addVariable(sds.getSubsDsCode() + "."
							+ PluginConst.DB_USER_ID, sds.getDsUserId());
					vv.addVariable(sds.getSubsDsCode() + "."
							+ PluginConst.DB_USER_PWD, sds.getDsUserPwd());
					vv.addVariable(sds.getSubsDsCode() + "."
							+ PluginConst.DB_USER_PWD_D, URPSCryptUtil
							.toDecryptWithNoException(sds.getDsUserPwd()));
					vv.addVariable(sds.getSubsDsCode() + "."
							+ PluginConst.DB_SCHEMA, sds.getDsSchemaName());
				}
			}
		} catch (SQLException e) {
			vv = null;
			throw e;
		}
		
		return vv;
	}
}
