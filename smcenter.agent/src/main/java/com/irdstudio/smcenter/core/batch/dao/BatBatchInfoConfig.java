package com.irdstudio.smcenter.core.batch.dao;/** * 01.批次信息配置(BAT_BATCH_INFO_CONFIG) * @author 代码自动生成 * @version 1.0 * @date 2014-04-21 */public class BatBatchInfoConfig {	/* 批次标识 */	private String batchId;	/* 批次名称 */	private String batchName;	/* 所属子系统代码 */	private String subsCode;	/* 发起方式 */	private String launchType;	/* 定时周期 */	private String batchCronValue;	/* Agent实例标识 */	private String agentId;	/* 生效日期 */	private String validDate;	/* 任务并发数 */	private int equallyTaskAmount;	/* 是否允许重跑 */	private String isRunAgain;	/* 备注 */	private String remark;	public BatBatchInfoConfig(){	}	public void setBatchId(String batchId) {		this.batchId = batchId;	}	public String getBatchId() {		return batchId;	}	public void setBatchName(String batchName) {		this.batchName = batchName;	}	public String getBatchName() {		return batchName;	}	public void setSubsCode(String subsCode) {		this.subsCode = subsCode;	}	public String getSubsCode() {		return subsCode;	}	public void setLaunchType(String launchType) {		this.launchType = launchType;	}	public String getLaunchType() {		return launchType;	}	public void setBatchCronValue(String batchCronValue) {		this.batchCronValue = batchCronValue;	}	public String getBatchCronValue() {		return batchCronValue;	}	public void setValidDate(String validDate) {		this.validDate = validDate;	}	public String getValidDate() {		return validDate;	}	public void setEquallyTaskAmount(int equallyTaskAmount) {		this.equallyTaskAmount = equallyTaskAmount;	}	public int getEquallyTaskAmount() {		return equallyTaskAmount;	}	public void setIsRunAgain(String isRunAgain) {		this.isRunAgain = isRunAgain;	}	public String getIsRunAgain() {		return isRunAgain;	}	public void setRemark(String remark) {		this.remark = remark;	}	public String getRemark() {		return remark;	}	public String getAgentId() {		return agentId;	}	public void setAgentId(String agentId) {		this.agentId = agentId;	}}