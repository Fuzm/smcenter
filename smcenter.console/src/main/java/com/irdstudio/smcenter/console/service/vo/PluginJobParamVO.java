package com.irdstudio.smcenter.console.service.vo;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * <p>Description: 数据作业参数配置				<p>
 * @author ligm
 * @date 2018-06-13
 */
public class PluginJobParamVO extends BaseInfo {

	private static final long serialVersionUID = 1L;	
	
	/** 参数组标识 */
	private String paramGroupId;
	/** 作业参数名称 */
	private String jobParamName;
	/** 作业参数值 */
	private String jobParamValue;
	

	public void setParamGroupId(String paramGroupId){
		this.paramGroupId = paramGroupId;
	}
	public String getParamGroupId(){
		return this.paramGroupId;
	}		
	public void setJobParamName(String jobParamName){
		this.jobParamName = jobParamName;
	}
	public String getJobParamName(){
		return this.jobParamName;
	}		
	public void setJobParamValue(String jobParamValue){
		this.jobParamValue = jobParamValue;
	}
	public String getJobParamValue(){
		return this.jobParamValue;
	}		

}
