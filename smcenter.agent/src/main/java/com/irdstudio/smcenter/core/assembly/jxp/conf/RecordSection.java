package com.irdstudio.smcenter.core.assembly.jxp.conf;
/**
 * 记录段(用于配置定长报文类文件的生成)
 * @author guangming.li
 * @version 1.0
 * @date 2013-12-19
 */
public class RecordSection {
	
	/* 数据源(SQL语句，可使用变量) */
	private String dataSrc = "";
	/* 分隔符 */
	private String split = "";
	/* 是否有分隔符 */
	private boolean haveSplit;
	/* 执行动作(SQL),用于格式化一条记录后执行,可用于更新状态 */
	private String actSql = "";
	/*格式化段*/
//	private List<>
	
}
