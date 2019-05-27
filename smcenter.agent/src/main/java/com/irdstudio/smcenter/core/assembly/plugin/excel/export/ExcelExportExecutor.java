package com.irdstudio.smcenter.core.assembly.plugin.excel.export;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.irdstudio.smcenter.core.tinycore.jdbc.executor.SafeReleaseUtil;
import com.irdstudio.smcenter.core.tinycore.log.ILogger;
import com.irdstudio.smcenter.core.tinycore.log.TLogger;
import com.irdstudio.smcenter.core.util.parse.VariableParserUtil;
import com.irdstudio.smcenter.core.util.vo.VariableValue;

/**
 * Excel导出执行者
 * @author guangming.li
 * @version 1.0
 * @date 2014-09-24
 */
public class ExcelExportExecutor {
	
	/* 导出配置文件地址 */
	private String xmlConfigureFile = "";
	/* 变量容器(用于解释变量) */
	private VariableValue vv = null;	
	/* 模板文件地址 */
	private String templateURL = "";
	/* 目标文件地址 */
	private String toFileURL = "";
	/* Export导出配置清单 */
	private List<ExportSectionXmlConf> esxfList = null;
	/* 日志输出对象 */
	private ILogger logger = null;
	/* 数据库连接对象 */
	private Connection conn = null;
	
	/**
	 * 构造函数
	 * @param xmlConfigFile 导出配置文件(XML)
	 * @param vv 变量容器(用于解释变量)
	 * @param conn 数据库连接
	 */
	public ExcelExportExecutor(String xmlConfigureFile, VariableValue vv,Connection conn) {
		this.xmlConfigureFile = xmlConfigureFile;
		this.vv = vv;
		// 初始化本类使用的日志输出对象
		this.logger = TLogger.getLogger("ExcelExport");
		this.conn = conn;
	}
	
	/**
	 * 构造函数
	 * @param xmlConfigFile 导出配置文件(XML)
	 * @param vv 变量容器(用于解释变量)
	 * @param conn 数据库连接
	 * @param logger 日志输出对象
	 */
	public ExcelExportExecutor(String xmlConfigureFile, VariableValue vv,
			Connection conn, ILogger logger) {
		this.xmlConfigureFile = xmlConfigureFile;
		this.vv = vv;
		this.logger = logger;
		this.conn = conn;
	}	
	
	/**
	 * 执行导出(调用入口)
	 * @return
	 */
	public boolean run(){
		if(doInitialize()){
			return doExport();
		}
		return false;
	}

	/**
	 * 按模板导出数据到目标Excel中
	 * @return
	 */
	private boolean doExport() {
		boolean flag = true;
		ExcelTemplateBuilder excel = new ExcelTemplateBuilder(this.templateURL);
		for (int i = 0; i < esxfList.size(); i++) {
			ExportSectionXmlConf esxf = esxfList.get(i);
			this.logger.info(esxf.getType() + ":" + esxf.getDataSrc());
			Statement st = null;
			ResultSet rs = null;
			try {
				st = conn.createStatement();
				rs = st.executeQuery(toParseSysVariable(esxf.getDataSrc()));
				int rowIndex = 0;
				while (rs.next()) {
					for (int j = 0; j < esxf.getWriterList().size(); j++) {
						ExportWriterXmlConf ewxf = esxf.getWriterList().get(j);
						excel.writeIn(rs.getString(ewxf.getValueField()), ewxf
								.getSheetNo(), ewxf.getRowNo() + rowIndex, ewxf
								.getCellNo());
					}
					rowIndex++;
					if (!"repeat".equalsIgnoreCase(esxf.getType())) {
						// 如果不是循环类型,则跳出记录循环
						break;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				flag = false;
			} finally {
				SafeReleaseUtil.close(rs, st, null);
			}
		}
		excel.saveAsTargetExcel(this.toFileURL);
		return flag;
	}

	private boolean doInitialize() {
		String xmlConfigFile = this.xmlConfigureFile;
		this.logger.info(xmlConfigFile);
		boolean flag = true;
		// 检查文件是否存在
		if (xmlConfigFile != null && !"".equals(xmlConfigFile)) {

			SAXBuilder sb = new SAXBuilder();
			Document doc = null;

			// 打开文件,读出配置
			try {

				esxfList = new ArrayList<ExportSectionXmlConf>();
				doc = sb.build(new FileInputStream(xmlConfigFile));

				// 取得根元素
				Element root = doc.getRootElement();
				
				// 获取Excel模板地址及输出文件地址
				this.templateURL = root.getAttributeValue("templateURL");
				this.toFileURL = root.getAttributeValue("toFile");
				this.templateURL = toParseSysVariable(this.templateURL);
				this.toFileURL = toParseSysVariable(this.toFileURL);

				// 取得所有第一级元素
				List<?> childs = root.getChildren();

				// 循环取得第一级元素的内容
				for (Iterator<?> it = childs.iterator(); it.hasNext();) {
					Element element = (Element) it.next();

					// 装载SQL标签中的描述到SqlDesc中
					if ("export".equalsIgnoreCase(element.getName())) {
						this.logger.info("加载Export配置...");
						this.esxfList
								.add(this.loadExportSection(element));
					}

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

	/**
	 * 加载导出配置
	 * @param element
	 * @return
	 */
	private ExportSectionXmlConf loadExportSection(Element element) {
		ExportSectionXmlConf export = new ExportSectionXmlConf();
		export.setDataSrc(element.getAttributeValue("dataSrc"));
		export.setType(element.getAttributeValue("type"));
		export.setDataSrc(element.getAttributeValue("dataSrc"));
		// 获取详细的导出配置
		if (element.getChildren().size() > 0) {
			List<?> childs = element.getChildren();
			for (Iterator<?> it = childs.iterator(); it.hasNext();) {
				Element tmpElement = (Element) it.next();
				if ("write".equalsIgnoreCase(tmpElement.getName())) {
					ExportWriterXmlConf writer = new ExportWriterXmlConf();
					writer.setSheetNo(NumberUtils.toInt(tmpElement
							.getAttributeValue("sheet")));
					writer.setRowNo(NumberUtils.toInt(tmpElement
							.getAttributeValue("row")));
					writer.setCellNo(NumberUtils.toInt(tmpElement
							.getAttributeValue("cell")));
					writer.setValueField(tmpElement.getAttributeValue("value"));
					writer.setDesc(tmpElement.getAttributeValue("desc"));
					export.addWriter(writer);
				}
			}
		}
		return export;
	}	

	/**
	 * 解释字符串,将其中的变量解释为值
	 * @param str
	 * @return
	 */
	public String toParseSysVariable(String str) {
		if (str == null || "".equals(str))
			return "";
		return VariableParserUtil.parseString(str, this.vv);
	}
}
