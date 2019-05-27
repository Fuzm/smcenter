package com.irdstudio.smcenter.core.schedule;

import java.math.BigDecimal;

/**
 * <p>Description: 定时调度配置实体类<p>
 * @author ligm
 * @date 2018-06-16 增加对dubbo,hsf调用的支持
 */
public class SSrvsCronConf {
	
	/** 定时作业编号 */
	private String jobCode;
	/** 定时作业描述 */
	private String jobDesc;
	/** 作业类类型,dubbo或hsf或local */
	private String jobClassType;
	/** 作业实现类 */
	private String jobClass;
	/** 作业类方法 */
	private String jobMethod;
	/** Cron表达式 */
	private String cronExpression;
	/** 失败重试次数 */
	private int againTime;
	/** 重试间隔秒数 */
	private int retrySecond;
	/** 备注 */
	private String remark;
	/** Agent实例标识 */
	private String agentId;
	/** 服务组别 */
	private String serviceGroup;
	/** 服务版本 */
	private String serviceVersion;
	/** 服务调用超时时间 */
	private BigDecimal serviceTimeout;
	
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
	public void setJobClass(String jobClass){
		this.jobClass = jobClass;
	}
	public String getJobClass(){
		return this.jobClass;
	}		
	public void setCronExpression(String cronExpression){
		this.cronExpression = cronExpression;
	}
	public String getCronExpression(){
		return this.cronExpression;
	}		
	public void setAgainTime(int againTime){
		this.againTime = againTime;
	}
	public int getAgainTime(){
		return this.againTime;
	}		
	public void setRetrySecond(int retrySecond){
		this.retrySecond = retrySecond;
	}
	public int getRetrySecond(){
		return this.retrySecond;
	}		
	public void setRemark(String remark){
		this.remark = remark;
	}
	public String getRemark(){
		return this.remark;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getJobClassType() {
		return jobClassType;
	}
	public void setJobClassType(String jobClassType) {
		this.jobClassType = jobClassType;
	}
	public String getJobMethod() {
		return jobMethod;
	}
	public void setJobMethod(String jobMethod) {
		this.jobMethod = jobMethod;
	}
	public String getServiceGroup() {
		return serviceGroup;
	}
	public void setServiceGroup(String serviceGroup) {
		this.serviceGroup = serviceGroup;
	}
	public String getServiceVersion() {
		return serviceVersion;
	}
	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}
	public BigDecimal getServiceTimeout() {
		return serviceTimeout;
	}
	public void setServiceTimeout(BigDecimal serviceTimeout) {
		this.serviceTimeout = serviceTimeout;
	}	
	
}
