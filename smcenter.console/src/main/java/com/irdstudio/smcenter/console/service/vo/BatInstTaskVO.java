package com.irdstudio.smcenter.console.service.vo;

import java.math.BigDecimal;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * <p>Description: 批次任务实例				<p>
 * @author ligm
 * @date 2018-06-13
 */
public class BatInstTaskVO extends BaseInfo {

	private static final long serialVersionUID = 1L;	
	
	/** 批次标识 */
	private String batchId;
	/** 批次序号 */
	private int batchOrder;
	/** 任务编号 */
	private String taskId;
	/** 任务名称 */
	private String taskName;
	/** 阶段编号 */
	private String stageId;
	/** 任务执行状态 */
	private String taskRunState;
	/** 任务干预状态 */
	private String taskInterveneState;
	/** 运行时长 */
	private BigDecimal costTime;
	/** 启动时间 */
	private String startTime;
	/** 结束时间 */
	private String endTime;
	/** 警告次数 */
	private int warnCount;
	/** 其它 */
	private String other;
	/** 批次流水号 */
	private String batchSerialNo;
	/** 状态描述 */
	private String stateDesc;
	/** 批次日期 */
	private String batchDate;
	/** 阶段名称 */
	private String stageName;
	
	/** 启动事件不为空  */
	private Boolean startTimeNotNull;

	public void setBatchId(String batchId){
		this.batchId = batchId;
	}
	public String getBatchId(){
		return this.batchId;
	}		
	public void setBatchOrder(int batchOrder){
		this.batchOrder = batchOrder;
	}
	public int getBatchOrder(){
		return this.batchOrder;
	}		
	public void setTaskId(String taskId){
		this.taskId = taskId;
	}
	public String getTaskId(){
		return this.taskId;
	}		
	public void setTaskName(String taskName){
		this.taskName = taskName;
	}
	public String getTaskName(){
		return this.taskName;
	}		
	public void setStageId(String stageId){
		this.stageId = stageId;
	}
	public String getStageId(){
		return this.stageId;
	}		
	public void setTaskRunState(String taskRunState){
		this.taskRunState = taskRunState;
	}
	public String getTaskRunState(){
		return this.taskRunState;
	}		
	public void setTaskInterveneState(String taskInterveneState){
		this.taskInterveneState = taskInterveneState;
	}
	public String getTaskInterveneState(){
		return this.taskInterveneState;
	}		
	public void setCostTime(BigDecimal costTime){
		this.costTime = costTime;
	}
	public BigDecimal getCostTime(){
		return this.costTime;
	}		
	public void setStartTime(String startTime){
		this.startTime = startTime;
	}
	public String getStartTime(){
		return this.startTime;
	}		
	public void setEndTime(String endTime){
		this.endTime = endTime;
	}
	public String getEndTime(){
		return this.endTime;
	}		
	public void setWarnCount(int warnCount){
		this.warnCount = warnCount;
	}
	public int getWarnCount(){
		return this.warnCount;
	}		
	public void setOther(String other){
		this.other = other;
	}
	public String getOther(){
		return this.other;
	}		
	public void setBatchSerialNo(String batchSerialNo){
		this.batchSerialNo = batchSerialNo;
	}
	public String getBatchSerialNo(){
		return this.batchSerialNo;
	}		
	public void setStateDesc(String stateDesc){
		this.stateDesc = stateDesc;
	}
	public String getStateDesc(){
		return this.stateDesc;
	}		
	public void setBatchDate(String batchDate){
		this.batchDate = batchDate;
	}
	public String getBatchDate(){
		return this.batchDate;
	}		
	public void setStageName(String stageName){
		this.stageName = stageName;
	}
	public String getStageName(){
		return this.stageName;
	}
	public Boolean getStartTimeNotNull() {
		return startTimeNotNull;
	}
	public void setStartTimeNotNull(Boolean startTimeNotNull) {
		this.startTimeNotNull = startTimeNotNull;
	}	
}
