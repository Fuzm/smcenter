package com.irdstudio.smcenter.agent.restful.vo;

public class ResponseVO {
	
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";

	private String flag = "fail";
	
	private String msg;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
