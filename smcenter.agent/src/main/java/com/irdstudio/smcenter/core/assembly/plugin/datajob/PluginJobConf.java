package com.irdstudio.smcenter.core.assembly.plugin.datajob;/** * 01.数据作业配置(plugin_job_conf) * @author 代码自动生成 * @version 1.0 * @date 2014-04-24 */public class PluginJobConf {	/* 配置标识符 */	private String pluginConfId;	/* 配置顺序 */	private int confSort;	/* 作业名称 */	private String jobName;	/* 作业参数组标识 */	private String paramGroupId;	/* 作业实现类 */	private String jobImplement;	/* SQL脚本用途 */	private String sqlPurpose;	/* SQL脚本(可使用变量) */	private String sqlContent;	/* 作业失败处理方式 */	private String jobFaildDeal;	/* 最后更新日期 */	private String lastModifyDate;	public PluginJobConf(){	}	public void setPluginConfId(String pluginConfId) {		this.pluginConfId = pluginConfId;	}	public String getPluginConfId() {		return pluginConfId;	}	public void setConfSort(int confSort) {		this.confSort = confSort;	}	public int getConfSort() {		return confSort;	}	public void setJobName(String jobName) {		this.jobName = jobName;	}	public String getJobName() {		return jobName;	}	public void setParamGroupId(String paramGroupId) {		this.paramGroupId = paramGroupId;	}	public String getParamGroupId() {		return paramGroupId;	}	public void setJobImplement(String jobImplement) {		this.jobImplement = jobImplement;	}	public String getJobImplement() {		return jobImplement;	}	public void setSqlPurpose(String sqlPurpose) {		this.sqlPurpose = sqlPurpose;	}	public String getSqlPurpose() {		return sqlPurpose;	}	public void setSqlContent(String sqlContent) {		this.sqlContent = sqlContent;	}	public String getSqlContent() {		return sqlContent;	}	public void setJobFaildDeal(String jobFaildDeal) {		this.jobFaildDeal = jobFaildDeal;	}	public String getJobFaildDeal() {		return jobFaildDeal;	}	public void setLastModifyDate(String lastModifyDate) {		this.lastModifyDate = lastModifyDate;	}	public String getLastModifyDate() {		return lastModifyDate;	}}