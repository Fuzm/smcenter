package com.irdstudio.smcenter.core.assembly.socket.element;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * 交易信息段返回包描述段
 * @author 李广明
 * @version 1.0
 * @date 2008-05-12
 *
 */
public class BusinessReturn {

	private Map ds = new Hashtable();			//用于存储DS
	private List result = new ArrayList();		//用于返回信息描述
	
	public Map getDs() {
		return ds;
	}
	public void addDs(String ds,String sql) {
		this.ds.put(ds,sql);
	}
	public List getResult() {
		return result;
	}
	public void addResult(Object obj) {
		this.result.add(obj);
	}
}
