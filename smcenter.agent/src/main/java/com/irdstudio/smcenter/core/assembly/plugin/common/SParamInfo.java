package com.irdstudio.smcenter.core.assembly.plugin.common;/** * 01.平台参数信息表(URPS.s_param_info)  * 参数使用范围 1.全局参数 2.子系统参数 3.应用插件参数 * 为全局参数时，所有情况下可以引用到 为子系统参数时，子系统代码匹配才可引用到 为应用插件参数时，应用插件代码匹配才可以引用到  * @author 代码自动生成 * @version 1.0 * @date 2013-11-06 */public class SParamInfo {	/* 参数代码 */	private String paramCode;	/* 参数名称 */	private String paramName;	/* 参数值 */	private String paramValue;	/* 参数使用范围 */	private String paramScope;	/* 子系统代码 */	private String subsCode;	/* 应用插件ID */	private int pluginId;	/* 备注 */	private String remark;	public SParamInfo(){	}	public void setParamCode(String paramCode) {		this.paramCode = paramCode;	}	public String getParamCode() {		return paramCode;	}	public void setParamName(String paramName) {		this.paramName = paramName;	}	public String getParamName() {		return paramName;	}	public void setParamValue(String paramValue) {		this.paramValue = paramValue;	}	public String getParamValue() {		return paramValue;	}	public void setParamScope(String paramScope) {		this.paramScope = paramScope;	}	public String getParamScope() {		return paramScope;	}	public void setSubsCode(String subsCode) {		this.subsCode = subsCode;	}	public String getSubsCode() {		return subsCode;	}	public void setPluginId(int pluginId) {		this.pluginId = pluginId;	}	public int getPluginId() {		return pluginId;	}	public void setRemark(String remark) {		this.remark = remark;	}	public String getRemark() {		return remark;	}}