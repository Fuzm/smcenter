package com.irdstudio.smcenter.console.dao.domain;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * Description: 系统信息更新配置表			
 * @author ligm
 * @date 2018-06-13
 */
public class PluginSysupdConf extends BaseInfo{

	private static final long serialVersionUID = 1L;	
	
	/** 配置标识符 */
	private String pluginConfId;
	/** 配置顺序 */
	private int confSort;
	/** 系统信息更新动作 */
	private String sysupdAction;
	

	public void setPluginConfId(String pluginConfId){
		this.pluginConfId = pluginConfId;
	}
	public String getPluginConfId(){
		return this.pluginConfId;
	}		
	public void setConfSort(int confSort){
		this.confSort = confSort;
	}
	public int getConfSort(){
		return this.confSort;
	}		
	public void setSysupdAction(String sysupdAction){
		this.sysupdAction = sysupdAction;
	}
	public String getSysupdAction(){
		return this.sysupdAction;
	}		

}
