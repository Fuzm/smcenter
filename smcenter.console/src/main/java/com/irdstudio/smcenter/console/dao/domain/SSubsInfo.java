package com.irdstudio.smcenter.console.dao.domain;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * Description: 子系统基础信息表			
 * @author ligm
 * @date 2018-06-13
 */
public class SSubsInfo extends BaseInfo{

	private static final long serialVersionUID = 1L;	
	
	/** 子系统代码 */
	private String subsCode;
	/** 子系统名称 */
	private String subsName;
	/** 子系统数据日期 */
	private String subsDataDate;
	/** 子系统上一数据日期 */
	private String subsLastDataDate;
	/** 子系统装数完成日期 */
	private String subsLoadDate;
	/** 子系统批次完成日期 */
	private String subsBatDate;
	/** 批次编号前缀 */
	private String batchIdPrefix;
	/** 备用日期 */
	private String rsvDate;
	/** 备用状态 */
	private String rsvState;
	

	public void setSubsCode(String subsCode){
		this.subsCode = subsCode;
	}
	public String getSubsCode(){
		return this.subsCode;
	}		
	public void setSubsName(String subsName){
		this.subsName = subsName;
	}
	public String getSubsName(){
		return this.subsName;
	}		
	public void setSubsDataDate(String subsDataDate){
		this.subsDataDate = subsDataDate;
	}
	public String getSubsDataDate(){
		return this.subsDataDate;
	}		
	public void setSubsLastDataDate(String subsLastDataDate){
		this.subsLastDataDate = subsLastDataDate;
	}
	public String getSubsLastDataDate(){
		return this.subsLastDataDate;
	}		
	public void setSubsLoadDate(String subsLoadDate){
		this.subsLoadDate = subsLoadDate;
	}
	public String getSubsLoadDate(){
		return this.subsLoadDate;
	}		
	public void setSubsBatDate(String subsBatDate){
		this.subsBatDate = subsBatDate;
	}
	public String getSubsBatDate(){
		return this.subsBatDate;
	}		
	public void setBatchIdPrefix(String batchIdPrefix){
		this.batchIdPrefix = batchIdPrefix;
	}
	public String getBatchIdPrefix(){
		return this.batchIdPrefix;
	}		
	public void setRsvDate(String rsvDate){
		this.rsvDate = rsvDate;
	}
	public String getRsvDate(){
		return this.rsvDate;
	}		
	public void setRsvState(String rsvState){
		this.rsvState = rsvState;
	}
	public String getRsvState(){
		return this.rsvState;
	}		

}
