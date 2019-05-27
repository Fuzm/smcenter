package com.irdstudio.smcenter.core.assembly.socket.log;

import java.util.Iterator;
import java.util.Map;

public class BusiLogParser {
	
	private AbstractBusiLog busiLog = null;	//用于存放日志数据的对象
	
	public BusiLogParser(AbstractBusiLog busiLog){
		this.busiLog = busiLog;
	}
	/**
	 * 获得插入数据的SQL语句
	 * @return
	 */
	public String getInsertSql(){
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO ");
		sb.append(busiLog.getLogTableName());
		sb.append("(");
		StringBuffer next = new StringBuffer(") values(");
		Iterator it = busiLog.getLogData().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			sb.append(key);
			next.append(tranParamValue(value));
			sb.append(",");
			next.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		next.deleteCharAt(next.length()-1);
		sb.append(next).append(")");
		return sb.toString();
	}
	
	/**
	 * 根据类型进行判断是否需要加单引号
	 * @param value
	 * @return
	 */
	private String tranParamValue(Object value){
		if(value instanceof String){
			return "'" + value + "'";
		} else {
			return value.toString();
		}
	}
}
