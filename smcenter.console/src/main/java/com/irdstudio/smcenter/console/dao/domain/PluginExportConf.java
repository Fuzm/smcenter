package com.irdstudio.smcenter.console.dao.domain;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * Description: 导出为文本文件插件			
 * @author ligm
 * @date 2018-06-13
 */
public class PluginExportConf extends BaseInfo{

	private static final long serialVersionUID = 1L;	
	
	/** 配置标识符 */
	private String pluginConfId;
	/** 配置顺序 */
	private int confSort;
	/** 数据导出方式 */
	private String exportMode;
	/** 数据表/SQL语句 */
	private String exportTarget;
	/** 文件存放路径 */
	private String exportToFile;
	/** 字段分隔符 */
	private String fieldSeparator;
	/** 文件编码格式 */
	private String fileCharset;
	

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
	public void setExportMode(String exportMode){
		this.exportMode = exportMode;
	}
	public String getExportMode(){
		return this.exportMode;
	}		
	public void setExportTarget(String exportTarget){
		this.exportTarget = exportTarget;
	}
	public String getExportTarget(){
		return this.exportTarget;
	}		
	public void setExportToFile(String exportToFile){
		this.exportToFile = exportToFile;
	}
	public String getExportToFile(){
		return this.exportToFile;
	}		
	public void setFieldSeparator(String fieldSeparator){
		this.fieldSeparator = fieldSeparator;
	}
	public String getFieldSeparator(){
		return this.fieldSeparator;
	}		
	public void setFileCharset(String fileCharset){
		this.fileCharset = fileCharset;
	}
	public String getFileCharset(){
		return this.fileCharset;
	}		

}
