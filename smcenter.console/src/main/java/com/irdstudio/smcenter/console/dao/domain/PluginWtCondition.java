package com.irdstudio.smcenter.console.dao.domain;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * Description: 等待条件表			
 * @author ligm
 * @date 2018-06-13
 */
public class PluginWtCondition extends BaseInfo{

	private static final long serialVersionUID = 1L;	
	
	/** 配置标识符 */
	private String pluginConfId;
	/** 配置顺序 */
	private int confSort;
	/** 检查项名称 */
	private String checkItemName;
	/** 检查项SQL */
	private String checkItemSql;
	/** 检查成功条件 */
	private String checkSucCondition;
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
	public void setCheckItemName(String checkItemName){
		this.checkItemName = checkItemName;
	}
	public String getCheckItemName(){
		return this.checkItemName;
	}		
	public void setCheckItemSql(String checkItemSql){
		this.checkItemSql = checkItemSql;
	}
	public String getCheckItemSql(){
		return this.checkItemSql;
	}		
	public void setCheckSucCondition(String checkSucCondition){
		this.checkSucCondition = checkSucCondition;
	}
	public String getCheckSucCondition(){
		return this.checkSucCondition;
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
