package com.irdstudio.smcenter.core.assembly.plugin.dataexport;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import com.irdstudio.smcenter.core.assembly.plugin.PluginConst;
import com.irdstudio.smcenter.core.tinycore.jdbc.executor.SafeReleaseUtil;
import com.irdstudio.smcenter.core.tinycore.log.ILogger;
/**
 * 导出数据到文本文件
 * @author guangming.li
 * @version 1.0
 * @date 2014-09-16
 */
public class DataExportExecutor {
	
	/* 装载程序使用的连接 */
	private Connection exportConn = null;
	/* 数据装载配置信息 */
	private PluginExportConf exportConf = null;
	/* 文件日志记录者 */
	private ILogger logger = null;

	/**
	 * 构造函数(传入装载配置,连接,及文本日志记录对象)
	 * @param loadConf
	 * @param conn
	 * @param logger
	 */
	public DataExportExecutor(PluginExportConf exportConf, Connection conn,
			ILogger logger) {
		this.exportConn = conn;
		this.exportConf = exportConf;
		this.logger = logger;
	}

	/**
	 * 执行导出
	 */
	public boolean run() {
		boolean flag = true;
		logger.info("begin export........................");
		Statement st = null;
		ResultSet rs = null;
		FileWriter fos = null;
		try {
			st = exportConn.createStatement();
			st.setMaxRows(0);
			// 创建一个ResultSet对象，提交SQL语句
			if (PluginConst.EXPORT_MODE_BY_TABLE.equals(exportConf
					.getExportMode())) {
				// 按表名导出数据
				rs = st.executeQuery("SELECT * FROM "
						+ exportConf.getExportTarget());
			} else {
				// 其余情况按自定义SQL方式查询导出数据
				rs = st.executeQuery(exportConf.getExportTarget());
			}
			// 输出字段
			ResultSetMetaData rsmd = rs.getMetaData();
			fos = new FileWriter(exportConf.getExportToFile());
			int numCols = rsmd.getColumnCount();
			// 输出列头
			for (int i = 1; i <= numCols; i++) {
				logger.info("字段名:" + rsmd.getColumnLabel(i));
			}
			// 按行将数据写入到文件中
			int iCount = 0;
			while (rs.next()) {
				for (int i = 1; i <= numCols; i++) {
					fos.write(formatString(rs.getString(i))
							+ exportConf.getFieldSeparator());
				}
				fos.write("\r\n");
				iCount++;
			}
			logger.info("导出记录总数:" + iCount);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			SafeReleaseUtil.close(rs);
			SafeReleaseUtil.close(st);
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return flag;
	}

	/**
	 * 格式化要导出的字段(替换回车与换行符)
	 * 如果为空,则返回空字符串
	 * @param string
	 * @return
	 */
	private String formatString(String string) {
		if(string == null) return "";
		return string.replaceAll("[\\n\\r]", "");
	}
}
