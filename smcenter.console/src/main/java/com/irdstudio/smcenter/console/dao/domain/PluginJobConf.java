package com.irdstudio.smcenter.console.dao.domain;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * Description: 数据作业配置			
 * @author ligm
 * @date 2018-06-13
 */
public class PluginJobConf extends BaseInfo{

	private static final long serialVersionUID = 1L;	
	
	/** 配置标识符 */
	private String pluginConfId;
	/** 配置顺序 */
	private int confSort;
	/** 作业名称 */
	private String jobName;
	/** SQL脚本用途 */
	private String sqlPurpose;
	/** SQL脚本 */
	private String sqlContent;
	/** 参数组标识 */
	private String paramGroupId;
	/** 作业失败处理方式 */
	private String jobFaildDeal;
	/** 最后更新日期 */
	private String lastModifyDate;
	/** 作业实现 */
	private String jobImplement;
	

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
	public void setJobName(String jobName){
		this.jobName = jobName;
	}
	public String getJobName(){
		return this.jobName;
	}		
	public void setSqlPurpose(String sqlPurpose){
		this.sqlPurpose = sqlPurpose;
	}
	public String getSqlPurpose(){
		return this.sqlPurpose;
	}		
	public void setSqlContent(String sqlContent){
		this.sqlContent = sqlContent;
	}
	public String getSqlContent(){
		return this.sqlContent;
	}		
	public void setParamGroupId(String paramGroupId){
		this.paramGroupId = paramGroupId;
	}
	public String getParamGroupId(){
		return this.paramGroupId;
	}		
	public void setJobFaildDeal(String jobFaildDeal){
		this.jobFaildDeal = jobFaildDeal;
	}
	public String getJobFaildDeal(){
		return this.jobFaildDeal;
	}		
	public void setLastModifyDate(String lastModifyDate){
		this.lastModifyDate = lastModifyDate;
	}
	public String getLastModifyDate(){
		return this.lastModifyDate;
	}		
	public void setJobImplement(String jobImplement){
		this.jobImplement = jobImplement;
	}
	public String getJobImplement(){
		return this.jobImplement;
	}		

}
