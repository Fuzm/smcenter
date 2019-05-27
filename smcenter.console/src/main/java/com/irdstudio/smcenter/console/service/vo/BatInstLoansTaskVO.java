package com.irdstudio.smcenter.console.service.vo;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * 核算任務
 * @author caijiufa
 *
 */
public class BatInstLoansTaskVO extends BaseInfo {

	private static final long serialVersionUID = 1L;	
	
	private String seqNo;
	private String execInd;
	private String prcsDt;
	private String jobSts;
	private String startDt;
	private String taskDesc;
	
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getExecInd() {
		return execInd;
	}
	public void setExecInd(String execInd) {
		this.execInd = execInd;
	}
	public String getPrcsDt() {
		return prcsDt;
	}
	public void setPrcsDt(String prcsDt) {
		this.prcsDt = prcsDt;
	}
	public String getJobSts() {
		return jobSts;
	}
	public void setJobSts(String jobSts) {
		this.jobSts = jobSts;
	}
	public String getStartDt() {
		return startDt;
	}
	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}
	public String getTaskDesc() {
		return taskDesc;
	}
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
	
}
