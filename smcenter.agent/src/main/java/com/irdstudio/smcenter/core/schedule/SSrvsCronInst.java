package com.irdstudio.smcenter.core.schedule;

import java.math.BigDecimal;

/**
 * <p>Description: 定时调度实例实体类				<p>
 * @author admin
 * @date 2017-10-30
 */
public class SSrvsCronInst {

	/** 定时作业编号 */
	private String jobCode;
	/** 定时作业描述 */
	private String jobDesc;
	/** 状态 */
	private int state;
	/** 启动时间 */
	private String startTime;
	/** 结束时间 */
	private String endTime;
	/** 耗时(秒) */
	private BigDecimal costTime;
	/** 代理实例标识 */
	private String agentId;
	/** 结果描述 */
	private String resultDesc;
	
	public void setJobCode(String jobCode){
		this.jobCode = jobCode;
	}
	public String getJobCode(){
		return this.jobCode;
	}		
	public void setJobDesc(String jobDesc){
		this.jobDesc = jobDesc;
	}
	public String getJobDesc(){
		return this.jobDesc;
	}		
	public void setState(int state){
		this.state = state;
	}
	public int getState(){
		return this.state;
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
	public void setResultDesc(String resultDesc){
		this.resultDesc = resultDesc;
	}
	public String getResultDesc(){
		return this.resultDesc;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}		
}
