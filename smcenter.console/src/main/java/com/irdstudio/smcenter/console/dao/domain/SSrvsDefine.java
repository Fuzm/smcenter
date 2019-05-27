package com.irdstudio.smcenter.console.dao.domain;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * Description: 01.服务组件信息			
 * @author ligm
 * @date 2018-06-09
 */
public class SSrvsDefine extends BaseInfo{

	private static final long serialVersionUID = 1L;	
	
	/** 服务组件代码 */
	private String srvsCode;
	/** 服务组件名称 */
	private String srvsName;
	/** 服务实现类 */
	private String srvsClass;
	/** 服务描述 */
	private String srvsDesc;
	/** 服务组件类型 */
	private String srvsType;
	/** 是否提供配置页面 */
	private String isPrvdConfPage;
	/** 是否提供管理页面 */
	private String isPrvdMgrPage;
	/** 是否提供服务日志 */
	private String isPrvdLog;
	/** 是否支持流量控制 */
	private String isFluxControl;
	/** 最大同时处理数 */
	private int maxFlux;
	

	public void setSrvsCode(String srvsCode){
		this.srvsCode = srvsCode;
	}
	public String getSrvsCode(){
		return this.srvsCode;
	}		
	public void setSrvsName(String srvsName){
		this.srvsName = srvsName;
	}
	public String getSrvsName(){
		return this.srvsName;
	}		
	public void setSrvsClass(String srvsClass){
		this.srvsClass = srvsClass;
	}
	public String getSrvsClass(){
		return this.srvsClass;
	}		
	public void setSrvsDesc(String srvsDesc){
		this.srvsDesc = srvsDesc;
	}
	public String getSrvsDesc(){
		return this.srvsDesc;
	}		
	public void setSrvsType(String srvsType){
		this.srvsType = srvsType;
	}
	public String getSrvsType(){
		return this.srvsType;
	}		
	public void setIsPrvdConfPage(String isPrvdConfPage){
		this.isPrvdConfPage = isPrvdConfPage;
	}
	public String getIsPrvdConfPage(){
		return this.isPrvdConfPage;
	}		
	public void setIsPrvdMgrPage(String isPrvdMgrPage){
		this.isPrvdMgrPage = isPrvdMgrPage;
	}
	public String getIsPrvdMgrPage(){
		return this.isPrvdMgrPage;
	}		
	public void setIsPrvdLog(String isPrvdLog){
		this.isPrvdLog = isPrvdLog;
	}
	public String getIsPrvdLog(){
		return this.isPrvdLog;
	}		
	public void setIsFluxControl(String isFluxControl){
		this.isFluxControl = isFluxControl;
	}
	public String getIsFluxControl(){
		return this.isFluxControl;
	}		
	public void setMaxFlux(int maxFlux){
		this.maxFlux = maxFlux;
	}
	public int getMaxFlux(){
		return this.maxFlux;
	}		

}
