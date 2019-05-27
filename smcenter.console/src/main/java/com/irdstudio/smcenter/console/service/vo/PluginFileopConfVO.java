package com.irdstudio.smcenter.console.service.vo;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * <p>Description: 文件操作配置				<p>
 * @author ligm
 * @date 2018-06-13
 */
public class PluginFileopConfVO extends BaseInfo {

	private static final long serialVersionUID = 1L;	
	
	/** 配置标识符 */
	private String pluginConfId;
	/** 配置顺序 */
	private int confSort;
	/** 文件操作 */
	private String fileOp;
	/** 操作周期 */
	private int opCycleDay;
	/** 上次操作日期 */
	private String lastOpDate;
	/** 最后更新日期 */
	private String lastModifyDate;
	/** 目标距离(天) */
	private int distanceDay;
	/** 目标文件 */
	private String fileTarget;
	

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
	public void setFileOp(String fileOp){
		this.fileOp = fileOp;
	}
	public String getFileOp(){
		return this.fileOp;
	}		
	public void setOpCycleDay(int opCycleDay){
		this.opCycleDay = opCycleDay;
	}
	public int getOpCycleDay(){
		return this.opCycleDay;
	}		
	public void setLastOpDate(String lastOpDate){
		this.lastOpDate = lastOpDate;
	}
	public String getLastOpDate(){
		return this.lastOpDate;
	}		
	public void setLastModifyDate(String lastModifyDate){
		this.lastModifyDate = lastModifyDate;
	}
	public String getLastModifyDate(){
		return this.lastModifyDate;
	}		
	public void setDistanceDay(int distanceDay){
		this.distanceDay = distanceDay;
	}
	public int getDistanceDay(){
		return this.distanceDay;
	}		
	public void setFileTarget(String fileTarget){
		this.fileTarget = fileTarget;
	}
	public String getFileTarget(){
		return this.fileTarget;
	}		

}
