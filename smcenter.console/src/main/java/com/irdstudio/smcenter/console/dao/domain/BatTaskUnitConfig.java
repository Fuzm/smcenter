package com.irdstudio.smcenter.console.dao.domain;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * Description: 批次任务配置			
 * @author ligm
 * @date 2018-06-14
 */
public class BatTaskUnitConfig extends BaseInfo{

	private static final long serialVersionUID = 1L;	
	
	/** 任务名称 */
	private String taskName;
	/** 任务编号 */
	private String taskId;
	/** 前一任务编号 */
	private String previousTaskId;
	/** 批次标识 */
	private String batchId;
	/** 阶段编号 */
	private String stageId;
	/** 执行场所编号 */
	private String localeId;
	/** 应用插件ID */
	private int pluginId;
	/** 插件配置数据来源方式 */
	private String pluginSourceType;
	/** 插件配置数据标识 */
	private String pluginParaFlag;
	/** 任务优先级 */
	private String taskPri;
	/** 任务执行类型 */
	private String taskRunType;
	/** 任务周期类型 */
	private String taskCycleType;
	/** 任务定时周期 */
	private String taskCronValue;
	/** 任务延时执行时间(秒) */
	private int taskDelayTime;
	/** 任务失败跳过策略 */
	private String taskSkipTactic;
	/** 任务重复调起时间间隔(秒) */
	private int againRunSpace;
	/** 任务预计执行时间(秒) */
	private int taskEstimateTime;
	/** 任务使用状态 */
	private String taskUseState;
	/** 任务最大重复调起次数 */
	private int maxRunCount;
	/** 任务运行超时时间(秒) */
	private int taskTimeoutTime;
	/** 任务运行超时策略 */
	private String taskTimeoutTactic;
	/** 子系统数据源代码 */
	private String subsDsCode;
	/** 任务使用场景 */
	private String taskUseArea;
	/** 应用插件类型 */
	private String pluginType;
	/** 最长等待时间 */
	private int maxWaitTime;
	/** 轮询间隔(秒) */
	private int cycleInteval;
	/** 备注 */
	private String remark;
	

	public void setTaskName(String taskName){
		this.taskName = taskName;
	}
	public String getTaskName(){
		return this.taskName;
	}		
	public void setTaskId(String taskId){
		this.taskId = taskId;
	}
	public String getTaskId(){
		return this.taskId;
	}		
	public void setPreviousTaskId(String previousTaskId){
		this.previousTaskId = previousTaskId;
	}
	public String getPreviousTaskId(){
		return this.previousTaskId;
	}		
	public void setBatchId(String batchId){
		this.batchId = batchId;
	}
	public String getBatchId(){
		return this.batchId;
	}		
	public void setStageId(String stageId){
		this.stageId = stageId;
	}
	public String getStageId(){
		return this.stageId;
	}		
	public void setLocaleId(String localeId){
		this.localeId = localeId;
	}
	public String getLocaleId(){
		return this.localeId;
	}		
	public void setPluginId(int pluginId){
		this.pluginId = pluginId;
	}
	public int getPluginId(){
		return this.pluginId;
	}		
	public void setPluginSourceType(String pluginSourceType){
		this.pluginSourceType = pluginSourceType;
	}
	public String getPluginSourceType(){
		return this.pluginSourceType;
	}		
	public void setPluginParaFlag(String pluginParaFlag){
		this.pluginParaFlag = pluginParaFlag;
	}
	public String getPluginParaFlag(){
		return this.pluginParaFlag;
	}		
	public void setTaskPri(String taskPri){
		this.taskPri = taskPri;
	}
	public String getTaskPri(){
		return this.taskPri;
	}		
	public void setTaskRunType(String taskRunType){
		this.taskRunType = taskRunType;
	}
	public String getTaskRunType(){
		return this.taskRunType;
	}		
	public void setTaskCycleType(String taskCycleType){
		this.taskCycleType = taskCycleType;
	}
	public String getTaskCycleType(){
		return this.taskCycleType;
	}		
	public void setTaskCronValue(String taskCronValue){
		this.taskCronValue = taskCronValue;
	}
	public String getTaskCronValue(){
		return this.taskCronValue;
	}		
	public void setTaskDelayTime(int taskDelayTime){
		this.taskDelayTime = taskDelayTime;
	}
	public int getTaskDelayTime(){
		return this.taskDelayTime;
	}		
	public void setTaskSkipTactic(String taskSkipTactic){
		this.taskSkipTactic = taskSkipTactic;
	}
	public String getTaskSkipTactic(){
		return this.taskSkipTactic;
	}		
	public void setAgainRunSpace(int againRunSpace){
		this.againRunSpace = againRunSpace;
	}
	public int getAgainRunSpace(){
		return this.againRunSpace;
	}		
	public void setTaskEstimateTime(int taskEstimateTime){
		this.taskEstimateTime = taskEstimateTime;
	}
	public int getTaskEstimateTime(){
		return this.taskEstimateTime;
	}		
	public void setTaskUseState(String taskUseState){
		this.taskUseState = taskUseState;
	}
	public String getTaskUseState(){
		return this.taskUseState;
	}		
	public void setMaxRunCount(int maxRunCount){
		this.maxRunCount = maxRunCount;
	}
	public int getMaxRunCount(){
		return this.maxRunCount;
	}		
	public void setTaskTimeoutTime(int taskTimeoutTime){
		this.taskTimeoutTime = taskTimeoutTime;
	}
	public int getTaskTimeoutTime(){
		return this.taskTimeoutTime;
	}		
	public void setTaskTimeoutTactic(String taskTimeoutTactic){
		this.taskTimeoutTactic = taskTimeoutTactic;
	}
	public String getTaskTimeoutTactic(){
		return this.taskTimeoutTactic;
	}		
	public void setSubsDsCode(String subsDsCode){
		this.subsDsCode = subsDsCode;
	}
	public String getSubsDsCode(){
		return this.subsDsCode;
	}		
	public void setTaskUseArea(String taskUseArea){
		this.taskUseArea = taskUseArea;
	}
	public String getTaskUseArea(){
		return this.taskUseArea;
	}		
	public void setPluginType(String pluginType){
		this.pluginType = pluginType;
	}
	public String getPluginType(){
		return this.pluginType;
	}		
	public void setMaxWaitTime(int maxWaitTime){
		this.maxWaitTime = maxWaitTime;
	}
	public int getMaxWaitTime(){
		return this.maxWaitTime;
	}		
	public void setCycleInteval(int cycleInteval){
		this.cycleInteval = cycleInteval;
	}
	public int getCycleInteval(){
		return this.cycleInteval;
	}		
	public void setRemark(String remark){
		this.remark = remark;
	}
	public String getRemark(){
		return this.remark;
	}		

}
