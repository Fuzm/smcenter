package com.irdstudio.smcenter.core.assembly.plugin.excel.util;
/**
 * Excel辅助类
 * @author guangming.li
 * @version 1.0
 * @date 2014-09-17
 */
public class ExcelUtil {
	
	/* Excel文件版本-excel 2003 */
	public final static int EXCEL_VERSION_2003 = 0x01;
	/* Excel文件版本-excel 2007及以上 */
	public final static int EXCEL_VERSION_2007 = 0x02;
	/* Excel文件版本-未知版本 */
	public final static int EXCEL_VERSION_UNKNOW = 0x00;
	
	/**
	 * 根据Excel文件后缀判断Excel文件版本
	 * @return
	 */
	public static int getExcelFileVersion(String excelFileURL) {

		if (excelFileURL == null || excelFileURL.equals("")) {
			return EXCEL_VERSION_UNKNOW;
		}

		if (excelFileURL.matches("^.+\\.(?i)(xls)$")) {
			return EXCEL_VERSION_2003;
		} else if (excelFileURL.matches("^.+\\.(?i)(xlsx)$")) {
			return EXCEL_VERSION_2007;
		} else {
			return EXCEL_VERSION_UNKNOW;
		}
	}

}
