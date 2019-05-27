package com.irdstudio.smcenter.console.dao.domain;

import java.math.BigDecimal;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * Description: 服务调用插件			
 * @author fuzm
 * @date 2018-06-16
 */
public class PluginServiceConf extends BaseInfo{

	private static final long serialVersionUID = 1L;	
	
	/** 配置标识符 */
	private String pluginConfId;
	/** 配置顺序 */
	private BigDecimal confSort;
	/** 服务ID */
	private String serviceId;
	/** 服务名称 */
	private String serviceName;
	/** 服务接口类 */
	private String serviceInterface;
	/** 服务实现方法 */
	private String serviceMethod;
	/** 方法返回类型 */
	private String returnType;
	/** 参数组标识 */
	private String paramGroupId;
	/** 服务版本 */
	private String version;
	/** 服务组别 */
	private String group;
	/** 调用超时时间(毫秒) */
	private BigDecimal timeout;
	

	public void setPluginConfId(String pluginConfId){
		this.pluginConfId = pluginConfId;
	}
	public String getPluginConfId(){
		return this.pluginConfId;
	}		
	public void setConfSort(BigDecimal confSort){
		this.confSort = confSort;
	}
	public BigDecimal getConfSort(){
		return this.confSort;
	}		
	public void setServiceId(String serviceId){
		this.serviceId = serviceId;
	}
	public String getServiceId(){
		return this.serviceId;
	}		
	public void setServiceName(String serviceName){
		this.serviceName = serviceName;
	}
	public String getServiceName(){
		return this.serviceName;
	}		
	public void setServiceInterface(String serviceInterface){
		this.serviceInterface = serviceInterface;
	}
	public String getServiceInterface(){
		return this.serviceInterface;
	}		
	public void setServiceMethod(String serviceMethod){
		this.serviceMethod = serviceMethod;
	}
	public String getServiceMethod(){
		return this.serviceMethod;
	}		
	public void setReturnType(String returnType){
		this.returnType = returnType;
	}
	public String getReturnType(){
		return this.returnType;
	}		
	public void setParamGroupId(String paramGroupId){
		this.paramGroupId = paramGroupId;
	}
	public String getParamGroupId(){
		return this.paramGroupId;
	}		
	public void setVersion(String version){
		this.version = version;
	}
	public String getVersion(){
		return this.version;
	}		
	public void setGroup(String group){
		this.group = group;
	}
	public String getGroup(){
		return this.group;
	}		
	public void setTimeout(BigDecimal timeout){
		this.timeout = timeout;
	}
	public BigDecimal getTimeout(){
		return this.timeout;
	}		

}
