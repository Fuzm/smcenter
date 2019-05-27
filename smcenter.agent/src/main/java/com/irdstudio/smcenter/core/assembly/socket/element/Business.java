package com.irdstudio.smcenter.core.assembly.socket.element;

import com.irdstudio.smcenter.core.util.vo.UniKeyValueObject;

/**
 * 交易配置基础信息类
 * @author 李广明
 * @version 1.0
 * @date 2008-07-07
 */
public class Business {
	
	private String id = "";					//用来唯一标识中间业务类
	private String jyms = "";				//那些交易的交易交由此中间业务类处理
	private String desc = "";				//中间业务类名称
	private String dealClass = "";			//中间业务处理类
	private UniKeyValueObject env = null;	//中间业务处理类环境变量设置
	
	public Business(){
		env = new UniKeyValueObject();
	}
	public String getDealClass() {
		return dealClass;
	}
	public void setDealClass(String dealClass) {
		this.dealClass = dealClass;
	}
	public UniKeyValueObject getEnv() {
		return env;
	}
	public void addEnv(String name,String value) {
		this.env.addVariable(name, value);
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getJyms() {
		return jyms;
	}
	public void setJyms(String jyms) {
		this.jyms = jyms;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
