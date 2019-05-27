package com.irdstudio.smcenter.console.dao.domain;

import java.math.BigDecimal;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * Description: 定时调度配置			
 * @author ligm
 * @date 2018-06-13
 */
public class SSrvsCronConf extends BaseInfo{

	private static final long serialVersionUID = 1L;	
	
	/** 定时作业编号 */
	private String jobCode;
	/** 定时作业描述 */
	private String jobDesc;
	/** 作业类类型 */
	private String jobClassType;
	/** 作业实现类 */
	private String jobClass;
	/** 作业类方法 */
	private String jobMethod;
	/** 服务版本 */
	private String serviceVersion;
	/** 服务组别 */
	private String serviceGroup;
	/** 服务超时时间（毫秒） */
	private BigDecimal serviceTimeout;
	/** Cron表达式 */
	private String cronExpression;
	/** 失败重试次数 */
	private int againTime;
	/** 重试间隔秒数 */
	private int retrySecond;
	/** 代理节点标识 */
	private String agentId;
	/** 备注 */
	private String remark;
	

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
	public void setJobClassType(String jobClassType){
		this.jobClassType = jobClassType;
	}
	public String getJobClassType(){
		return this.jobClassType;
	}		
	public void setJobClass(String jobClass){
		this.jobClass = jobClass;
	}
	public String getJobClass(){
		return this.jobClass;
	}		
	public void setJobMethod(String jobMethod){
		this.jobMethod = jobMethod;
	}
	public String getJobMethod(){
		return this.jobMethod;
	}	
	public String getServiceVersion() {
		return serviceVersion;
	}
	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}
	public String getServiceGroup() {
		return serviceGroup;
	}
	public void setServiceGroup(String serviceGroup) {
		this.serviceGroup = serviceGroup;
	}
	public BigDecimal getServiceTimeout() {
		return serviceTimeout;
	}
	public void setServiceTimeout(BigDecimal serviceTimeout) {
		this.serviceTimeout = serviceTimeout;
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
