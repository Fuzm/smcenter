package com.irdstudio.smcenter.core.schedule;

/**
 * Description: 代理节点信息			
 * @author ligm
 * @date 2018-06-13
 */
public class SAgentInfo {
	
	/** 代理节点标识 */
	private String agentId;
	/** 代理节点名称 */
	private String agentName;
	/** 代理节点状态 */
	private String agentState;
	/** 代理节点地址 */
	private String agentUrl;
	/** 启动时间 */
	private String startTime;
	/** 停止时间 */
	private String stopTime;
	

	public void setAgentId(String agentId){
		this.agentId = agentId;
	}
	public String getAgentId(){
		return this.agentId;
	}		
	public void setAgentName(String agentName){
		this.agentName = agentName;
	}
	public String getAgentName(){
		return this.agentName;
	}		
	public void setAgentState(String agentState){
		this.agentState = agentState;
	}
	public String getAgentState(){
		return this.agentState;
	}		
	public void setAgentUrl(String agentUrl){
		this.agentUrl = agentUrl;
	}
	public String getAgentUrl(){
		return this.agentUrl;
	}		
	public void setStartTime(String startTime){
		this.startTime = startTime;
	}
	public String getStartTime(){
		return this.startTime;
	}		
	public void setStopTime(String stopTime){
		this.stopTime = stopTime;
	}
	public String getStopTime(){
		return this.stopTime;
	}		

}
