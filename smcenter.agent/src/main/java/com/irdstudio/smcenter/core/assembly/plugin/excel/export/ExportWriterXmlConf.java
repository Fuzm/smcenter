package com.irdstudio.smcenter.core.assembly.plugin.excel.export;
/**
 * Excel导出详细输出配置((读取XML)
 * 标明将那个字段输出到那个Sheet的那一行那一列
 * @author guangming.li
 * @version 1.0
 * @date 2014-09-23
 */
public class ExportWriterXmlConf {
	
	/* 指定sheet编号 */
	private int sheetNo;
	/* 指定行编号(如果是repeat模式则表明的是起始行) */
	private int rowNo;
	/* 指定列编号 */
	private int cellNo;
	/* 指定字段 */
	private String valueField;
	/* 描述信息 */
	private String desc;
	
	public int getSheetNo() {
		return sheetNo;
	}
	public void setSheetNo(int sheetNo) {
		this.sheetNo = sheetNo;
	}
	public int getRowNo() {
		return rowNo;
	}
	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}
	public int getCellNo() {
		return cellNo;
	}
	public void setCellNo(int cellNo) {
		this.cellNo = cellNo;
	}
	public String getValueField() {
		return valueField;
	}
	public void setValueField(String valueField) {
		this.valueField = valueField;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
