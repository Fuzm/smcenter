package com.irdstudio.smcenter.console.service.vo;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * <p>Description: 核算日終步驟				<p>
 * @author ligm
 * @date 2018-06-13
 */
public class BatInstLoansMonitorVO extends BaseInfo {

	private static final long serialVersionUID = 1L;	
	
	private String SEQ_NO;
	private String JOB_NAM;
	private String PRCS_DT;
	private String JOB_STS;
	private String ERR_MSG;
	private String START_RUN_TIME;
	private String MAC_TIME;
	public String getSEQ_NO() {
		return SEQ_NO;
	}
	public void setSEQ_NO(String sEQ_NO) {
		SEQ_NO = sEQ_NO;
	}
	public String getJOB_NAM() {
		return JOB_NAM;
	}
	public void setJOB_NAM(String jOB_NAM) {
		JOB_NAM = jOB_NAM;
	}
	public String getPRCS_DT() {
		return PRCS_DT;
	}
	public void setPRCS_DT(String pRCS_DT) {
		PRCS_DT = pRCS_DT;
	}
	public String getJOB_STS() {
		return JOB_STS;
	}
	public void setJOB_STS(String jOB_STS) {
		JOB_STS = jOB_STS;
	}
	public String getERR_MSG() {
		return ERR_MSG;
	}
	public void setERR_MSG(String eRR_MSG) {
		ERR_MSG = eRR_MSG;
	}
	public String getSTART_RUN_TIME() {
		return START_RUN_TIME;
	}
	public void setSTART_RUN_TIME(String sTART_RUN_TIME) {
		START_RUN_TIME = sTART_RUN_TIME;
	}
	public String getMAC_TIME() {
		return MAC_TIME;
	}
	public void setMAC_TIME(String mAC_TIME) {
		MAC_TIME = mAC_TIME;
	}
}
