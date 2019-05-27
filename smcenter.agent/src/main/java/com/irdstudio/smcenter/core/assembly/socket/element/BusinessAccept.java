package com.irdstudio.smcenter.core.assembly.socket.element;
/**
 * 用于描述需要接收的参数
 * @author 李广明
 * @version 1.0
 * @date 2005-05-10
 */
public class BusinessAccept {

	private String paramName = "";			//参数名称
	private String paramDesc = "";			//参数描述
	private String paramVerify = "";		//参数校验
	
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamDesc() {
		return paramDesc;
	}
	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}
	public String getParamVerify() {
		return paramVerify;
	}
	public void setParamVerify(String paramVerify) {
		this.paramVerify = paramVerify;
	}
}
