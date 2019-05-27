package com.irdstudio.smcenter.console.service.vo;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * <p>Description: 应用插件执行日志				<p>
 * @author ligm
 * @date 2018-06-13
 */
public class PluginExecLogVO extends BaseInfo {

	private static final long serialVersionUID = 1L;	
	
	/** 批次流水号 */
	private String batchSn;
	/** 应用插件ID */
	private int pluginId;
	/** 应用插件名称 */
	private String pluginName;
	/** 操作名称 */
	private String actionName;
	/** 操作结果 */
	private String actionResult;
	/** 操作详情 */
	private String actionDetailInfo;
	/** 任务编号 */
	private String taskId;
	/** 任务名称 */
	private String taskName;
	/** 记录时间 */
	private String recordTime;
	

	public void setBatchSn(String batchSn){
		this.batchSn = batchSn;
	}
	public String getBatchSn(){
		return this.batchSn;
	}		
	public void setPluginId(int pluginId){
		this.pluginId = pluginId;
	}
	public int getPluginId(){
		return this.pluginId;
	}		
	public void setPluginName(String pluginName){
		this.pluginName = pluginName;
	}
	public String getPluginName(){
		return this.pluginName;
	}		
	public void setActionName(String actionName){
		this.actionName = actionName;
	}
	public String getActionName(){
		return this.actionName;
	}		
	public void setActionResult(String actionResult){
		this.actionResult = actionResult;
	}
	public String getActionResult(){
		return this.actionResult;
	}		
	public void setActionDetailInfo(String actionDetailInfo){
		this.actionDetailInfo = actionDetailInfo;
	}
	public String getActionDetailInfo(){
		return this.actionDetailInfo;
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
	public void setRecordTime(String recordTime){
		this.recordTime = recordTime;
	}
	public String getRecordTime(){
		return this.recordTime;
	}		

}
