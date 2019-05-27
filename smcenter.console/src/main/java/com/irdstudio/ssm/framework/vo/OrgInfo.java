package com.irdstudio.ssm.framework.vo;

import java.io.Serializable;

public class OrgInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String orgCode;
	
	private String orgName;
	
	private String orgLocation;
	
	private String orgAreaCode;

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgLocation() {
		return orgLocation;
	}

	public void setOrgLocation(String orgLocation) {
		this.orgLocation = orgLocation;
	}

	public String getOrgAreaCode() {
		return orgAreaCode;
	}

	public void setOrgAreaCode(String orgAreaCode) {
		this.orgAreaCode = orgAreaCode;
	}
	
	
}
