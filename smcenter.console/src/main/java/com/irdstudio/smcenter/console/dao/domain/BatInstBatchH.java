package com.irdstudio.smcenter.console.dao.domain;

import java.math.BigDecimal;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * Description: 批次历史信息			
 * @author ligm
 * @date 2018-06-13
 */
public class BatInstBatchH extends BaseInfo{

	private static final long serialVersionUID = 1L;	
	
	/** 批次标识 */
	private String batchId;
	/** 批次序号 */
	private int batchOrder;
	/** 批次名称 */
	private String batchName;
	/** 子系统代码 */
	private String subsCode;
	/** 批次状态 */
	private String batchState;
	/** 干预状态 */
	private String batchInterveneState;
	/** 阶段编号 */
	private String stageId;
	/** 阶段名称 */
	private String stageName;
	/** 批次日期 */
	private String batchDate;
	/** 启动时间 */
	private String startTime;
	/** 结束时间 */
	private String endTime;
	/** 运行时长 */
	private BigDecimal costTime;
	/** 需调度任务数 */
	private int needRunCount;
	/** 运行中任务数 */
	private int existRunCount;
	/** 未运行任务数 */
	private int notRunCount;
	/** 运行成功任务数 */
	private int succeedRunCount;
	/** 运行失败任务数 */
	private int faildRunCount;
	/** 运行警告任务数 */
	private int warnRunCount;
	/** 运行置过任务数 */
	private int skipRunCount;
	/** 批次流水号 */
	private String batchSerialNo;
	/** 备注 */
	private String remark;
	

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
	public void setBatchState(String batchState){
		this.batchState = batchState;
	}
	public String getBatchState(){
		return this.batchState;
	}		
	public void setBatchInterveneState(String batchInterveneState){
		this.batchInterveneState = batchInterveneState;
	}
	public String getBatchInterveneState(){
		return this.batchInterveneState;
	}		
	public void setStageId(String stageId){
		this.stageId = stageId;
	}
	public String getStageId(){
		return this.stageId;
	}		
	public void setStageName(String stageName){
		this.stageName = stageName;
	}
	public String getStageName(){
		return this.stageName;
	}		
	public void setBatchDate(String batchDate){
		this.batchDate = batchDate;
	}
	public String getBatchDate(){
		return this.batchDate;
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
	public void setCostTime(BigDecimal costTime){
		this.costTime = costTime;
	}
	public BigDecimal getCostTime(){
		return this.costTime;
	}		
	public void setNeedRunCount(int needRunCount){
		this.needRunCount = needRunCount;
	}
	public int getNeedRunCount(){
		return this.needRunCount;
	}		
	public void setExistRunCount(int existRunCount){
		this.existRunCount = existRunCount;
	}
	public int getExistRunCount(){
		return this.existRunCount;
	}		
	public void setNotRunCount(int notRunCount){
		this.notRunCount = notRunCount;
	}
	public int getNotRunCount(){
		return this.notRunCount;
	}		
	public void setSucceedRunCount(int succeedRunCount){
		this.succeedRunCount = succeedRunCount;
	}
	public int getSucceedRunCount(){
		return this.succeedRunCount;
	}		
	public void setFaildRunCount(int faildRunCount){
		this.faildRunCount = faildRunCount;
	}
	public int getFaildRunCount(){
		return this.faildRunCount;
	}		
	public void setWarnRunCount(int warnRunCount){
		this.warnRunCount = warnRunCount;
	}
	public int getWarnRunCount(){
		return this.warnRunCount;
	}		
	public void setSkipRunCount(int skipRunCount){
		this.skipRunCount = skipRunCount;
	}
	public int getSkipRunCount(){
		return this.skipRunCount;
	}		
	public void setBatchSerialNo(String batchSerialNo){
		this.batchSerialNo = batchSerialNo;
	}
	public String getBatchSerialNo(){
		return this.batchSerialNo;
	}		
	public void setRemark(String remark){
		this.remark = remark;
	}
	public String getRemark(){
		return this.remark;
	}		

}
