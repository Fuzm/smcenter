package com.irdstudio.smcenter.core.assembly.socket.log;

import java.util.Hashtable;
import java.util.Map;

/**
 * 交易服务日志的接口类
 * @author 李广民
 * @version 1.0
 * @date 2008-11-04
 */
public abstract class AbstractBusiLog {
	
	private Map logData = new Hashtable();
	
	/**
	 * 获取日志表的名称
	 * @return
	 */
	public abstract String getLogTableName();
	
	/**
	 * 通用的增加日志数据的方法
	 * @param name
	 * @param value
	 */
	public void addValue(Object name,Object value){
		logData.put(name, value);
	}
	
	/**
	 * 获取日志数据(Map)
	 * @return
	 */
	public Map getLogData(){
		return logData;
	}
}
