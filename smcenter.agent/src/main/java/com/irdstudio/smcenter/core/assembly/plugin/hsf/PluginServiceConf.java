package com.irdstudio.smcenter.core.assembly.plugin.hsf;

import java.math.BigDecimal;

public class PluginServiceConf {
	
	/* 配置标识符 */
	private String pluginConfId;
	/* 配置顺序 */
	private int confSort;
	/* 服务ID */
	private String serviceId;
	/* 服务名称 */
	private String serviceName;
	/* 服务接口类 */
	private String serviceInterface;
	/* 服务方法 */
	private String serviceMethod;
	/* 参数返回类型 */
	private String returnType;
	/* 参数组ID */
	private String paramGroupId;
	/* 服务版本 */
	private String version;
	/* 服务组别 */
	private String group;
	/* 服务超时时间 */
	private BigDecimal timeout;
	
	public String getPluginConfId() {
		return pluginConfId;
	}
	public void setPluginConfId(String pluginConfId) {
		this.pluginConfId = pluginConfId;
	}
	public int getConfSort() {
		return confSort;
	}
	public void setConfSort(int confSort) {
		this.confSort = confSort;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceInterface() {
		return serviceInterface;
	}
	public void setServiceInterface(String serviceInterface) {
		this.serviceInterface = serviceInterface;
	}
	public String getServiceMethod() {
		return serviceMethod;
	}
	public void setServiceMethod(String serviceMethod) {
		this.serviceMethod = serviceMethod;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public String getParamGroupId() {
		return paramGroupId;
	}
	public void setParamGroupId(String paramGroupId) {
		this.paramGroupId = paramGroupId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public BigDecimal getTimeout() {
		return timeout;
	}
	public void setTimeout(BigDecimal timeout) {
		this.timeout = timeout;
	}
}
