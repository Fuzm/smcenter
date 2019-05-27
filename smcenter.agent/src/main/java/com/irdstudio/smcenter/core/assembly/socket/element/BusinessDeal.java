package com.irdstudio.smcenter.core.assembly.socket.element;

import java.util.ArrayList;
import java.util.List;

/**
 * 交易处理段描述
 * @author 李广明
 * @version 1.0
 * @date 2008-05-12
 */
public class BusinessDeal {

	private int nodeType  = 0;			//节点类型,定义参见NodeType
	private String sql;					//需要执行的SQL语句
	private List next = new ArrayList();//用于指向下一级的Node节点或绑定描述
	
	public int getNodeType() {
		return nodeType;
	}
	public void setNodeType(int nodeType) {
		this.nodeType = nodeType;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public List getNext() {
		return next;
	}
	
	/**
	 * 增加一个下一步的操作对象
	 * @param obj
	 */
	public void addNext(Object obj){
		next.add(obj);
	}
}
