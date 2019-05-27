package com.irdstudio.smcenter.core.assembly.plugin.hsf;

public class PluginServiceParam {

	/* 作业参数组标识 */
	private String paramGroupId;
	/* 作业参数名称 */
	private String serviceParamName;
	/* 作业参类型 */
	private String serviceParamType;
	/* 作业参数值 */
	private String serviceParamValue;
	
	public String getParamGroupId() {
		return paramGroupId;
	}
	public void setParamGroupId(String paramGroupId) {
		this.paramGroupId = paramGroupId;
	}
	public String getServiceParamName() {
		return serviceParamName;
	}
	public void setServiceParamName(String serviceParamName) {
		this.serviceParamName = serviceParamName;
	}
	public String getServiceParamType() {
		return serviceParamType;
	}
	public void setServiceParamType(String serviceParamType) {
		this.serviceParamType = serviceParamType;
	}
	public String getServiceParamValue() {
		return serviceParamValue;
	}
	public void setServiceParamValue(String serviceParamValue) {
		this.serviceParamValue = serviceParamValue;
	}

}
