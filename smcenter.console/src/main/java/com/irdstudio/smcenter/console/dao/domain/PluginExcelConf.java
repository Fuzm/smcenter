package com.irdstudio.smcenter.console.dao.domain;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * Description: Excel操作配置表			
 * @author ligm
 * @date 2018-06-13
 */
public class PluginExcelConf extends BaseInfo{

	private static final long serialVersionUID = 1L;	
	
	/** 配置标识符 */
	private String pluginConfId;
	/** 配置顺序 */
	private int confSort;
	/** Excel操作类型 */
	private String excelOperType;
	/** 参数键值对 */
	private String paramKeyValue;
	/** Excel操作配置文件 */
	private String configureFile;
	/** 失败处理方式 */
	private String faildDeal;
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
	public void setExcelOperType(String excelOperType){
		this.excelOperType = excelOperType;
	}
	public String getExcelOperType(){
		return this.excelOperType;
	}		
	public void setParamKeyValue(String paramKeyValue){
		this.paramKeyValue = paramKeyValue;
	}
	public String getParamKeyValue(){
		return this.paramKeyValue;
	}		
	public void setConfigureFile(String configureFile){
		this.configureFile = configureFile;
	}
	public String getConfigureFile(){
		return this.configureFile;
	}		
	public void setFaildDeal(String faildDeal){
		this.faildDeal = faildDeal;
	}
	public String getFaildDeal(){
		return this.faildDeal;
	}		
	public void setLastModifyDate(String lastModifyDate){
		this.lastModifyDate = lastModifyDate;
	}
	public String getLastModifyDate(){
		return this.lastModifyDate;
	}		

}
