package com.irdstudio.smcenter.core.assembly.license.dm;/** * 01.系统注册授权信息(s_inst_license) * @author 代码自动生成 * @version 1.0 * @date 2014-07-13 */public class SInstLicense {	/* 系统实例标识 */	private String sysInstId;	/* 主机名/IP地址 */	private String hostName;	/* Web端口 */	private String webPort;	/* 授权系统代码 */	private String sysCode;	/* 授权系统名称 */	private String sysName;	/* 授权对象 */	private String authTarget;	/* 授权起始日期 */	private String authBeginDate;	/* 授权截止日期 */	private String authEndDate;	/* 备注 */	private String remark;	public SInstLicense(){	}	public void setSysInstId(String sysInstId) {		this.sysInstId = sysInstId;	}	public String getSysInstId() {		return sysInstId;	}	public void setHostName(String hostName) {		this.hostName = hostName;	}	public String getHostName() {		return hostName;	}	public void setWebPort(String webPort) {		this.webPort = webPort;	}	public String getWebPort() {		return webPort;	}	public void setSysCode(String sysCode) {		this.sysCode = sysCode;	}	public String getSysCode() {		return sysCode;	}	public void setSysName(String sysName) {		this.sysName = sysName;	}	public String getSysName() {		return sysName;	}	public void setAuthTarget(String authTarget) {		this.authTarget = authTarget;	}	public String getAuthTarget() {		return authTarget;	}	public void setAuthBeginDate(String authBeginDate) {		this.authBeginDate = authBeginDate;	}	public String getAuthBeginDate() {		return authBeginDate;	}	public void setAuthEndDate(String authEndDate) {		this.authEndDate = authEndDate;	}	public String getAuthEndDate() {		return authEndDate;	}	public void setRemark(String remark) {		this.remark = remark;	}	public String getRemark() {		return remark;	}}