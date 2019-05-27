package com.irdstudio.smcenter.core.assembly.plugin.dataload;
/**
 * @docRoot:
 * 实现EcImport类,主程序
 * 根据配置文件实现将一个文件导入到数据的过程
 * @author 李广明
 * @version 1.0
 *
 */
import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.irdstudio.smcenter.core.tinycore.jdbc.meta.MetaTable;
import com.irdstudio.smcenter.core.tinycore.jdbc.util.TableUtil;
import com.irdstudio.smcenter.core.tinycore.log.ILogger;
import com.irdstudio.smcenter.core.util.date.CurrentDateUtil;
public class DataImportExecutor{
	
	/* 装载程序使用的连接 */
	private Connection loadConn = null;
	/* 数据装载配置信息 */
	private PluginLoadConf loadConf = null;
	/* 文件日志记录者 */
	private ILogger logger = null;
	/* 导入结果信息(数据库日志) */
	private PluginLoadResult loadResult = null;

	/**
	 * 构造函数(传入装载配置,连接,及文本日志记录对象)
	 * @param loadConf
	 * @param conn
	 * @param logger
	 */
	public DataImportExecutor(PluginLoadConf loadConf, Connection conn,
			ILogger logger) {
		this.loadConf = loadConf;
		this.loadConn = conn;
		this.logger = logger;
	}

	/**
	 * 检查文件,确定导入模式
	 * 1.先清空后导入
	 * 2.直接追加
	 */
	public boolean init() {
		
		return true;
	}
	/**
	 * 在导入前需要执行的SQL语句
	 * 在该段执行
	 */
	public boolean beforeLoad() {
		
		// 先判断表是否存在,不存在则尝试创建表
		MetaTable table = MetaTable.getInstWithTable(loadConn, loadConf
				.getTableName());
		if (!table.isTableExist()) {
			// 表不存在,没有取到元数据,如果配置了建表语句则重新建表
			logger.info("表不存在," + loadConf.getTableName());
			String tableDDL = loadConf.getCreateTableDdl();
			if (!(tableDDL == null || "".equals(tableDDL))) {
				if (!TableUtil.createTable(loadConn, loadConf
						.getCreateTableDdl())) {
					logger.error("自动建表[" + loadConf.getTableName() + "]失败!");
					return false;
				} else {
					logger.info("自动建表[" + loadConf.getTableName() + "]成功!");
				}
			} else {
				logger.error("表不存在!" + loadConf.getTableName());
				return false;
			}
		}

		// 如果是先清空后导入模式，则先清除数据
		// 如果有装载前配置语句,则以装载前执行语句为准
		if (DataImportConst.LOAD_MODE_FULL.equals(loadConf.getTableLoadMode())) {
			if (!"".equals(loadConf.getBeforeLoadSql())) {
				String[] sqls = loadConf.getBeforeLoadSql().split(";");
				for (int i = 0; i < sqls.length; i++) {
					String sql = sqls[i];
					if (!this.executeSql(sql)) {
						return false;
					}
				}
			} else {
				if (!this.executeSql("truncate table "
						+ loadConf.getTableName())) {
					return false;
				}
			}
		} else {
			// 直接将数据追加到表中,先执行装载前SQL语句
			if (!"".equals(loadConf.getBeforeLoadSql())) {
				String[] sqls = loadConf.getBeforeLoadSql().split(";");
				for (int i = 0; i < sqls.length; i++) {
					String sql = sqls[i];
					if (!this.executeSql(sql)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * 在完成导入后需要执行的SQL语句
	 * 在该段执行
	 */
	public boolean afterLoad() {
		
		if (loadConf.getAfterLoadSql() == null || "".equals(loadConf.getAfterLoadSql())) {
			// 说明没有语句要执行,直接返回真
			return true;
		} else {
			String[] indexes = loadConf.getAfterLoadSql().split(";");
			for (int i = 0; i < indexes.length; i++) {
				logger.info("装载后执行语句:" + indexes[i]);
				if (this.executeSql(indexes[i])) {
					logger.error("装载后执行的语句失败:" + indexes[i]);
				}
			}
		}
		
		// 因为是最后一步,所以,在此关闭连接
//		TConnPool.getDefaultPool().releaseConnection(loadConn);
				
		return true;
	}

	/**
	 * 执行指定的SQL语句并返回成功或失败
	 * 异常信息自动放入到上下文中的szLastErrMsg
	 * @param sql
	 * @return
	 */
	private boolean executeSql(String sql) {
		boolean flag = true;	
		Statement st = null;
		try {
			st = loadConn.createStatement();
			st.execute(sql);
		} catch (SQLException e) {
			flag = false;
			e.printStackTrace();
			logger.error("执行SQL错误:" + sql , e);
			e.printStackTrace();			
		} finally {
			if (st != null)
				try {
					st.close();
					st = null;
				} catch (SQLException e) {
					logger.error("关闭st错误:" + sql , e);
					e.printStackTrace();
				}
		}
		return flag;
	}
	
	/**
	 * 将步聚组装成一个流程
	 * 必须在上一步骤成功的基础上才可以执行下一步
	 * @return
	 */
	public PluginLoadResult run() {
		
		// 初始化导入结果信息对象以供记录导入结果并用于返回
		loadResult = new PluginLoadResult();
		
		logger.info("表名:" + loadConf.getTableName());
		logger.info("文件:" + loadConf.getLoadFromFile());
		loadResult.setLoadFromFile(loadConf.getLoadFromFile());
		loadResult.setStartTime(CurrentDateUtil.getTodayDateEx2());
		loadResult.setTableName(loadConf.getTableName());
		loadResult.setTableCnname(loadConf.getTableCnname());
		loadResult.setTableType(loadConf.getTableType());
		loadResult.setUpSysname(loadConf.getUpSysname());
		loadResult.setTableLoadMode(loadConf.getTableLoadMode());

		// 判断文件是否都存在,不存在则直接置为失败
		File file = new File(loadConf.getLoadFromFile());
		if (file == null || !file.exists() || !file.isFile()) {
			logger.info("找不到要导入的文件:" + loadConf.getLoadFromFile());
			loadResult.setLoadResult(DataImportConst.LOAD_RESULT_FAILD);
			loadResult.setRemark("找不到要导入的文件:" + loadConf.getLoadFromFile());
			return loadResult;
		}
		
		// 检查文件大小,如果文件为零,则判断是否允许为空文件
		BigDecimal fileSize = new BigDecimal(file.length())
				.divide(new BigDecimal(1024.00 * 1024.00));
		logger.info("[文件大小(M)]:" + fileSize.toString());		
		loadResult.setFileSize(fileSize);
		if (file.length() < 1) {
			loadResult.setLoadRows(0);
			loadResult.setReadRows(0);
			loadResult.setRejectRows(0);
			if (DataImportConst.YES.equals(loadConf.getFileRowFlag())) {
				// 如果允许装载空文件(即允许记录数为0),则直接装载成功
				loadResult.setLoadResult(DataImportConst.LOAD_RESULT_SUCCESS);
			} else {
				loadResult.setLoadResult(DataImportConst.LOAD_RESULT_FAILD);
			}
			return loadResult;
		}


		// 执行导入前要执行的语句
		if (!beforeLoad()) {
			loadResult.setLoadResult(DataImportConst.LOAD_RESULT_FAILD);
			return loadResult;
		}
		
		// 导入数据
		logger.info("开始装载...");
		ImportFromTxt txtImport = new ImportFromTxt(loadConf, logger, loadConn);
		boolean impResult = txtImport.run();
		loadResult.setReadRows(txtImport.getFileRecordCount());
		loadResult.setRejectRows(txtImport.getFaildRecordCount());
		loadResult.setLoadRows(loadResult.getReadRows()
				- loadResult.getRejectRows());
		if (!impResult) {
			loadResult.setLoadResult(DataImportConst.LOAD_RESULT_FAILD);
			return loadResult;
		} else {
			// 如果全部导入失败则置为失败,有部分导入失败记录数,则置为警告
			if (loadResult.getRejectRows() > 0
					&& loadResult.getRejectRows() >= loadResult.getLoadRows()) {
				loadResult.setLoadResult(DataImportConst.LOAD_RESULT_FAILD);
			} else if (loadResult.getRejectRows() > 0
					&& loadResult.getRejectRows() < loadResult.getLoadRows()) {
				//loadResult.setLoadResult(DataImportConst.LOAD_RESULT_WARNING);
				loadResult.setLoadResult(DataImportConst.LOAD_RESULT_FAILD);
			} else {
				loadResult.setLoadResult(DataImportConst.LOAD_RESULT_SUCCESS);
			}
		}
		
		// 执行导入数据后需执行SQL
		if (!afterLoad()) {
			loadResult.setLoadResult(DataImportConst.LOAD_RESULT_FAILD);
			return loadResult;
		}
		
		return loadResult;
	}	
}
