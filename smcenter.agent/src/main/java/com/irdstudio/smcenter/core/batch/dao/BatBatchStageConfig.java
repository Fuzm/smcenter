package com.irdstudio.smcenter.core.batch.dao;/** * 01.批次任务阶段(BAT_BATCH_STAGE_CONFIG) * @author 代码自动生成 * @version 1.0 * @date 2014-04-21 */public class BatBatchStageConfig {	/* 阶段编号 */	private String stageId;	/* 阶段名称 */	private String stageName;	/* 所属批次标识 */	private String batchId;	/* 备注 */	private String remark;	public BatBatchStageConfig(){	}	public void setStageId(String stageId) {		this.stageId = stageId;	}	public String getStageId() {		return stageId;	}	public void setStageName(String stageName) {		this.stageName = stageName;	}	public String getStageName() {		return stageName;	}	public void setBatchId(String batchId) {		this.batchId = batchId;	}	public String getBatchId() {		return batchId;	}	public void setRemark(String remark) {		this.remark = remark;	}	public String getRemark() {		return remark;	}}