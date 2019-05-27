package com.irdstudio.ssm.framework.vo;

import java.io.Serializable;

public class DutyInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String dutyCode;
	
	private String dutyName;

	public String getDutyCode() {
		return dutyCode;
	}

	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}

	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}
	
	
}
