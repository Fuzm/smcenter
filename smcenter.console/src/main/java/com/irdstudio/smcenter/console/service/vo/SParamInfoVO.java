package com.irdstudio.smcenter.console.service.vo;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * <p>Description: 参数信息表				<p>
 * @author ligm
 * @date 2018-06-13
 */
public class SParamInfoVO extends BaseInfo {

	private static final long serialVersionUID = 1L;	
	
	/** 参数代码 */
	private String paramCode;
	/** 参数名称 */
	private String paramName;
	/** 参数值 */
	private String paramValue;
	/** 参数使用范围 */
	private String paramScope;
	/** 子系统代码 */
	private String subsCode;
	/** 应用插件ID */
	private int pluginId;
	/** 备注 */
	private String remark;
	

	public void setParamCode(String paramCode){
		this.paramCode = paramCode;
	}
	public String getParamCode(){
		return this.paramCode;
	}		
	public void setParamName(String paramName){
		this.paramName = paramName;
	}
	public String getParamName(){
		return this.paramName;
	}		
	public void setParamValue(String paramValue){
		this.paramValue = paramValue;
	}
	public String getParamValue(){
		return this.paramValue;
	}		
	public void setParamScope(String paramScope){
		this.paramScope = paramScope;
	}
	public String getParamScope(){
		return this.paramScope;
	}		
	public void setSubsCode(String subsCode){
		this.subsCode = subsCode;
	}
	public String getSubsCode(){
		return this.subsCode;
	}		
	public void setPluginId(int pluginId){
		this.pluginId = pluginId;
	}
	public int getPluginId(){
		return this.pluginId;
	}		
	public void setRemark(String remark){
		this.remark = remark;
	}
	public String getRemark(){
		return this.remark;
	}		

}
