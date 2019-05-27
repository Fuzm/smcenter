package com.irdstudio.smcenter.core.assembly.socket.element;
/**
 * 用于描述返回结果的类
 * @author 李广明
 * @version 1.0
 * @date 2008-05-12
 *
 */
public 	class ResultDesc{
	
	private String ds = "";				//指定从那个数据源里取
	private String key = "";			//指定键值对应中的key
	private String value = "";			//指定键值对应中的value
	private String func = "";			//指定处理函数
	private String param = "";			//指定处理函数的参数
	
	public String getDs() {
		return ds;
	}
	public void setDs(String ds) {
		this.ds = ds;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getFunc() {
		return func;
	}
	public void setFunc(String func) {
		this.func = func;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
}
