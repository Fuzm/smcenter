package com.irdstudio.smcenter.core.assembly.plugin.excel.export;

import java.util.ArrayList;
import java.util.List;


/**
 * Excel导出段配置(读取XML)
 * @author guangming.li
 * @version 1.0
 * @date 2014-09-18
 */
public class ExportSectionXmlConf {
	
	/* 要导出的数据源(SQL) */
	private String dataSrc = "";
	/* 导出类型：单条记录/多条记录 */
	private String type = "";
	/* 写入配置列表 */
	private List<ExportWriterXmlConf> writerList = null;
	
	public ExportSectionXmlConf(){
		writerList = new ArrayList<ExportWriterXmlConf>();
	}
	
	public String getDataSrc() {
		return dataSrc;
	}
	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}	
	public List<ExportWriterXmlConf> getWriterList() {
		return writerList;
	}
	public void addWriter(ExportWriterXmlConf writer) {
		this.writerList.add(writer);
	} 
}
