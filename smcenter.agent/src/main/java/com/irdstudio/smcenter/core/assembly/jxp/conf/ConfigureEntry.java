package com.irdstudio.smcenter.core.assembly.jxp.conf;

import java.util.List;

/**
 * @docRoot
 * XML配置文件配置信息存放类(配置信息入口)
 * 目前由:
 * 		 1.SQL段(解释时最先执行)
 * 		 2.定义段(加载时放入内存)
 * 		 3.节点描述段组成(XML格式报文配置描述,递归组成)
 * 		 4.记录段(定长格式类报文配置描述,顺序组成)
 * @author 李广明
 * @version 1.0
 * @date 2006-11-13
 * 	@modify 2013-12-19
 * 		增加记录段、以支持对定长类报文的支持
 */
public class ConfigureEntry {

	/* SQL段描述 */
	private SqlSection sqlSection = null;
	/* 常量,参数定义段描述 */
	private DefineSection defineSection = null;
	/* 节点段,XML格式报文配置描述(递归组成) */
	private NodeSection nodeSection = null;
	/* 记录段,定长格式类报文配置描述(顺序组成) */
	private List<RecordSection> recordSection = null;

	public SqlSection getSqlSection() {
		return sqlSection;
	}

	public void setSqlSection(SqlSection sqlSection) {
		this.sqlSection = sqlSection;
	}

	public DefineSection getDefineSection() {
		return defineSection;
	}

	public void setDefineSection(DefineSection defineSection) {
		this.defineSection = defineSection;
	}

	public NodeSection getNodeSection() {
		return nodeSection;
	}

	public void setNodeSection(NodeSection nodeSection) {
		this.nodeSection = nodeSection;
	}
	
	public List<RecordSection> getRecordSection() {
		return recordSection;
	}
	
	public void setRecordSection(List<RecordSection> recordSection) {
		this.recordSection = recordSection;
	}
}
