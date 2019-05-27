package com.irdstudio.smcenter.console.dao.domain;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * Description: 数据报文文件生成配置表			
 * @author ligm
 * @date 2018-06-13
 */
public class PluginDatapackConf extends BaseInfo{

	private static final long serialVersionUID = 1L;	
	
	/** 配置标识符 */
	private String pluginConfId;
	/** 配置顺序 */
	private int confSort;
	/** 数据组包描述 */
	private String packDesc;
	/** 组包配置文件 */
	private String packConfigFile;
	/** 生成报文文件编码格式 */
	private String packFileEncoding;
	/** 生成报文文件类型 */
	private String packFileType;
	/** 生成报文文件 */
	private String packGenerateFile;
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
	public void setPackDesc(String packDesc){
		this.packDesc = packDesc;
	}
	public String getPackDesc(){
		return this.packDesc;
	}		
	public void setPackConfigFile(String packConfigFile){
		this.packConfigFile = packConfigFile;
	}
	public String getPackConfigFile(){
		return this.packConfigFile;
	}		
	public void setPackFileEncoding(String packFileEncoding){
		this.packFileEncoding = packFileEncoding;
	}
	public String getPackFileEncoding(){
		return this.packFileEncoding;
	}		
	public void setPackFileType(String packFileType){
		this.packFileType = packFileType;
	}
	public String getPackFileType(){
		return this.packFileType;
	}		
	public void setPackGenerateFile(String packGenerateFile){
		this.packGenerateFile = packGenerateFile;
	}
	public String getPackGenerateFile(){
		return this.packGenerateFile;
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
