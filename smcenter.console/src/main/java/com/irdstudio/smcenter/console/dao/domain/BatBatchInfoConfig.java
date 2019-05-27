package com.irdstudio.smcenter.console.dao.domain;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * Description: 批次信息配置			
 * @author ligm
 * @date 2018-06-14
 */
public class BatBatchInfoConfig extends BaseInfo{

	private static final long serialVersionUID = 1L;	
	
	/** 批次标识 */
	private String batchId;
	/** 批次名称 */
	private String batchName;
	/** 子系统代码 */
	private String subsCode;
	/** 发起方式 */
	private String launchType;
	/** 定时周期 */
	private String batchCronValue;
	/** 生效日期 */
	private String validDate;
	/** 任务并发数 */
	private int equallyTaskAmount;
	/** 是否允许重跑 */
	private String isRunAgain;
	/** 代理节点标识 */
	private String agentId;
	/** 备注 */
	private String remark;
	

	public void setBatchId(String batchId){
		this.batchId = batchId;
	}
	public String getBatchId(){
		return this.batchId;
	}		
	public void setBatchName(String batchName){
		this.batchName = batchName;
	}
	public String getBatchName(){
		return this.batchName;
	}		
	public void setSubsCode(String subsCode){
		this.subsCode = subsCode;
	}
	public String getSubsCode(){
		return this.subsCode;
	}		
	public void setLaunchType(String launchType){
		this.launchType = launchType;
	}
	public String getLaunchType(){
		return this.launchType;
	}		
	public void setBatchCronValue(String batchCronValue){
		this.batchCronValue = batchCronValue;
	}
	public String getBatchCronValue(){
		return this.batchCronValue;
	}		
	public void setValidDate(String validDate){
		this.validDate = validDate;
	}
	public String getValidDate(){
		return this.validDate;
	}		
	public void setEquallyTaskAmount(int equallyTaskAmount){
		this.equallyTaskAmount = equallyTaskAmount;
	}
	public int getEquallyTaskAmount(){
		return this.equallyTaskAmount;
	}		
	public void setIsRunAgain(String isRunAgain){
		this.isRunAgain = isRunAgain;
	}
	public String getIsRunAgain(){
		return this.isRunAgain;
	}		
	public void setAgentId(String agentId){
		this.agentId = agentId;
	}
	public String getAgentId(){
		return this.agentId;
	}		
	public void setRemark(String remark){
		this.remark = remark;
	}
	public String getRemark(){
		return this.remark;
	}		

}
