package com.irdstudio.smcenter.core.assembly.jxp.conf;

import java.util.ArrayList;
import java.util.List;

/**
 * @docRoot: 节点描述类，核心描述类
 * @author 李广明
 * @version 1.0
 * @date 2006-11-13
 */
public class NodeSection {

	/* 要生成的标签名字 */
	private String labelName = "";
	/* 节点类型 */
	private int type = 4;
	/* 绑定的字段 */
	private String field = "";
	/* 标签值的最大长度 */
	private int maxLen = 0;
	/* 使用的函数名称 */
	private String func = "";
	/* 函数参数 */
	private String param = "";
	/* 数据源(SQL语句，可使用变量) */
	private String dataSrc = "";
	/* 用于装载属性配置结点 */
	private List<NodeAttrSection> attrs = new ArrayList<NodeAttrSection>();
	/* 用于指向下一级的Node节点或绑定描述 */
	private List<Object> next = new ArrayList<Object>();

	public String getDataSrc() {
		return dataSrc;
	}

	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}

	public void addAttr(NodeAttrSection attr) {
		attrs.add(attr);
	}

	public List<NodeAttrSection> getAttrs() {
		return attrs;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getFunc() {
		return func;
	}

	public void setFunc(String func) {
		this.func = func;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public int getMaxLen() {
		return maxLen;
	}

	public void setMaxLen(int maxLen) {
		this.maxLen = maxLen;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<Object> getNext() {
		return next;
	}

	/**
	 * 增加一个下一步的操作对象
	 * 
	 * @param obj
	 */
	public void addNext(Object obj) {
		next.add(obj);
	}
}
