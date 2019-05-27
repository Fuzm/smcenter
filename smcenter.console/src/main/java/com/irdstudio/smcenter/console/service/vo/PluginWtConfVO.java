package com.irdstudio.smcenter.console.service.vo;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * <p>Description: 等待指定条件配置表				<p>
 * @author ligm
 * @date 2018-06-13
 */
public class PluginWtConfVO extends BaseInfo {

	private static final long serialVersionUID = 1L;	
	
	/** 配置标识符 */
	private String pluginConfId;
	/** 等待间隔 */
	private int checkInteval;
	/** 最长等待时间 */
	private int maxWaitTime;
	/** 等待描述 */
	private String waitDesc;
	

	public void setPluginConfId(String pluginConfId){
		this.pluginConfId = pluginConfId;
	}
	public String getPluginConfId(){
		return this.pluginConfId;
	}		
	public void setCheckInteval(int checkInteval){
		this.checkInteval = checkInteval;
	}
	public int getCheckInteval(){
		return this.checkInteval;
	}		
	public void setMaxWaitTime(int maxWaitTime){
		this.maxWaitTime = maxWaitTime;
	}
	public int getMaxWaitTime(){
		return this.maxWaitTime;
	}		
	public void setWaitDesc(String waitDesc){
		this.waitDesc = waitDesc;
	}
	public String getWaitDesc(){
		return this.waitDesc;
	}		

}
