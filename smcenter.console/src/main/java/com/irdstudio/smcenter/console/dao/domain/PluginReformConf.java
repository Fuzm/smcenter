package com.irdstudio.smcenter.console.dao.domain;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * Description: 数据表重整配置表			
 * @author ligm
 * @date 2018-06-13
 */
public class PluginReformConf extends BaseInfo{

	private static final long serialVersionUID = 1L;	
	
	/** 配置标识符 */
	private String pluginConfId;
	/** 配置顺序 */
	private int confSort;
	/** 表所属模式 */
	private String reformTableSchema;
	/** 重整表名称 */
	private String reformTableName;
	/** 重整组ID */
	private String reformGroupId;
	/** 重整周期 */
	private int reformCycleDay;
	/** 上次重整日期 */
	private String lastReformDate;
	/** 备用选项 */
	private String rsvOption;
	/** 生效日期 */
	private String validDate;
	/** 失效日期 */
	private String invalidDate;
	/** 最后更新日期 */
	private String lastModifyDate;
	

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
	public void setReformTableSchema(String reformTableSchema){
		this.reformTableSchema = reformTableSchema;
	}
	public String getReformTableSchema(){
		return this.reformTableSchema;
	}		
	public void setReformTableName(String reformTableName){
		this.reformTableName = reformTableName;
	}
	public String getReformTableName(){
		return this.reformTableName;
	}		
	public void setReformGroupId(String reformGroupId){
		this.reformGroupId = reformGroupId;
	}
	public String getReformGroupId(){
		return this.reformGroupId;
	}		
	public void setReformCycleDay(int reformCycleDay){
		this.reformCycleDay = reformCycleDay;
	}
	public int getReformCycleDay(){
		return this.reformCycleDay;
	}		
	public void setLastReformDate(String lastReformDate){
		this.lastReformDate = lastReformDate;
	}
	public String getLastReformDate(){
		return this.lastReformDate;
	}		
	public void setRsvOption(String rsvOption){
		this.rsvOption = rsvOption;
	}
	public String getRsvOption(){
		return this.rsvOption;
	}		
	public void setValidDate(String validDate){
		this.validDate = validDate;
	}
	public String getValidDate(){
		return this.validDate;
	}		
	public void setInvalidDate(String invalidDate){
		this.invalidDate = invalidDate;
	}
	public String getInvalidDate(){
		return this.invalidDate;
	}		
	public void setLastModifyDate(String lastModifyDate){
		this.lastModifyDate = lastModifyDate;
	}
	public String getLastModifyDate(){
		return this.lastModifyDate;
	}		

}
