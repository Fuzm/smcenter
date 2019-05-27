package com.irdstudio.smcenter.core.assembly.socket.element;

import java.util.ArrayList;

/**
 * 交易的描述信息类
 * @author 李广明
 * @version 1.0
 * @date 2008-05-10
 *
 */
public class BusinessDesc {
	
	String jym = "";							//交易码
	String desc = "";							//交易描述
	String dealClass = "";						//处理类
	ArrayList acceptParams = new ArrayList();	//接受的参数及参数校验信息
	BusinessDeal bd = null;						//交易处理信息段描述类
	BusinessReturn br = null;					//交易返回信息段描述类
	
	public String getJym() {
		return jym;
	}
	public void setJym(String jym) {
		this.jym = jym;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getDealClass() {
		return dealClass;
	}
	public void setDealClass(String dealClass) {
		this.dealClass = dealClass;
	}
	public ArrayList getAcceptParams() {
		return acceptParams;
	}
	public void addAcceptParam(Object obj) {
		this.acceptParams.add(obj);
	}
	public BusinessDeal getBd() {
		return bd;
	}
	public void setBd(BusinessDeal bd) {
		this.bd = bd;
	}
	public BusinessReturn getBr() {
		return br;
	}
	public void setBr(BusinessReturn br) {
		this.br = br;
	}
}
