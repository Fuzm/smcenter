package com.irdstudio.smcenter.console.dao.domain;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * Description: 批次任务阶段			
 * @author ligm
 * @date 2018-06-13
 */
public class BatBatchStageConfig extends BaseInfo{

	private static final long serialVersionUID = 1L;	
	
	/** 阶段编号 */
	private String stageId;
	/** 阶段名称 */
	private String stageName;
	/** 批次标识 */
	private String batchId;
	/** 备注 */
	private String remark;
	

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
	public void setBatchId(String batchId){
		this.batchId = batchId;
	}
	public String getBatchId(){
		return this.batchId;
	}		
	public void setRemark(String remark){
		this.remark = remark;
	}
	public String getRemark(){
		return this.remark;
	}		

}
