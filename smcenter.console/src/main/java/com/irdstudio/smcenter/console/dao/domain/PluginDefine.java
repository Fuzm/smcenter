package com.irdstudio.smcenter.console.dao.domain;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * Description: 应用插件信息			
 * @author ligm
 * @date 2018-06-13
 */
public class PluginDefine extends BaseInfo{

	private static final long serialVersionUID = 1L;	
	
	/** 应用插件ID */
	private int pluginId;
	/** 应用插件名称 */
	private String pluginName;
	/** 应用插件实现类 */
	private String pluginClass;
	/** 应用插件描述 */
	private String pluginDesc;
	/** 是否需要其它数据源变量 */
	private String needOtherDsVar;
	/** 应用插件配置地址 */
	private String pluginConfigUrl;
	/** 应用插件分类 */
	private String pluginCatalog;
	

	public void setPluginId(int pluginId){
		this.pluginId = pluginId;
	}
	public int getPluginId(){
		return this.pluginId;
	}		
	public void setPluginName(String pluginName){
		this.pluginName = pluginName;
	}
	public String getPluginName(){
		return this.pluginName;
	}		
	public void setPluginClass(String pluginClass){
		this.pluginClass = pluginClass;
	}
	public String getPluginClass(){
		return this.pluginClass;
	}		
	public void setPluginDesc(String pluginDesc){
		this.pluginDesc = pluginDesc;
	}
	public String getPluginDesc(){
		return this.pluginDesc;
	}		
	public void setNeedOtherDsVar(String needOtherDsVar){
		this.needOtherDsVar = needOtherDsVar;
	}
	public String getNeedOtherDsVar(){
		return this.needOtherDsVar;
	}		
	public void setPluginConfigUrl(String pluginConfigUrl){
		this.pluginConfigUrl = pluginConfigUrl;
	}
	public String getPluginConfigUrl(){
		return this.pluginConfigUrl;
	}		
	public void setPluginCatalog(String pluginCatalog){
		this.pluginCatalog = pluginCatalog;
	}
	public String getPluginCatalog(){
		return this.pluginCatalog;
	}		

}
