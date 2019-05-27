package com.irdstudio.smcenter.core.assembly.plugin.datajob;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.irdstudio.smcenter.core.assembly.plugin.AbstractPlugin;
import com.irdstudio.smcenter.core.util.pub.PathUtil;

/**
 * 执行SQL作业(配置直接从XML中读取)
 * @author guangming.li
 * @version 1.2
 * @date 2014-10-08
 */
public class XMLSqlJobPlugin extends AbstractPlugin{
	
	/* 用于存放从XML文件中读取的SQL*/
	String[] sqls = null;

	@Override
	protected boolean doReadConfigureFromDB(Connection conn,
			String szConfIdentify) throws SQLException {
		return false;
	}
	
	/**
	 * 只支持从XML中读取配置
	 */
	public boolean readPluginConfigureFromFile(String szConfIdentify) {
		return readXMLConfigureSQLs(szConfIdentify);
	}

	@Override
	public boolean execute() {
		this.logger.info("执行作业配置(XML)的SQL脚本...");
		boolean flag = true;

		for (int i = 0; i < sqls.length; i++) {
			if ("".equals(sqls[i].trim())) {
				// 如果是空行直接跳过
				continue;
			}
			String sql = context.toParseSysVariable(sqls[i]);
			boolean subFlag = this.executeSql(sql);
			if (subFlag) {
				this.writeSuccessLog("执行作业配置的SQL脚本", sql);
			} else {
				this.writeFaildLog("执行作业配置的SQL脚本", sql + ","
						+ context.szLastErrorMsg);
				flag = subFlag;
				// 如果执行其中的一条出错，则直接跳出,后续的不再执行
				break;
			}
		}
		return flag;
	}
	
	/**
	 * 从XML中读取配置的SQL数据
	 * @param szConfIdentify
	 */
	private boolean readXMLConfigureSQLs(String xmlConfigFile) {
		xmlConfigFile = PathUtil.getClassRootPath() + xmlConfigFile;
		this.logger.info(xmlConfigFile);
		boolean flag = true;
		// 检查文件是否存在
		if (xmlConfigFile != null && !"".equals(xmlConfigFile)) {

			SAXBuilder sb = new SAXBuilder();
			Document doc = null;

			// 打开文件,读出配置
			try {

				doc = sb.build(new FileInputStream(xmlConfigFile));

				// 取得根元素
				Element root = doc.getRootElement();
				String allSQL = root.getText();
				if (allSQL == null || "".equals(allSQL)) {
					flag = false;
				} else {
					this.sqls = allSQL.split(";");
				}

			} catch (FileNotFoundException e) {
				this.logger.error("文件" + xmlConfigFile + "没有找到!");
				e.printStackTrace();
				flag = false;
			} catch (JDOMException e) {
				this.logger.error("读XML文件异常,请检查XML文件!");
				e.printStackTrace();
				flag = false;
			} catch (IOException e) {
				this.logger.error("读XML文件异常,请检查XML文件!");
				e.printStackTrace();
			} finally {
				doc = null;
			}
		}
		return flag;
	}
}
