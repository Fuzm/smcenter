package com.irdstudio.smcenter.console.dao.domain;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * Description: 执行场所配置			
 * @author ligm
 * @date 2018-06-13
 */
public class BatTaskLocaleConfig extends BaseInfo{

	private static final long serialVersionUID = 1L;	
	
	/** 执行场所编号 */
	private String localeId;
	/** 执行场所名称 */
	private String localeName;
	/** 场所类型 */
	private String localeType;
	/** 场所IP */
	private String localeIp;
	/** 场所端口 */
	private String localePort;
	/** 登录方式 */
	private String loginType;
	/** 登录用户名 */
	private String loginUser;
	/** 登录密码 */
	private String loginPwd;
	/** 备注 */
	private String remark;
	

	public void setLocaleId(String localeId){
		this.localeId = localeId;
	}
	public String getLocaleId(){
		return this.localeId;
	}		
	public void setLocaleName(String localeName){
		this.localeName = localeName;
	}
	public String getLocaleName(){
		return this.localeName;
	}		
	public void setLocaleType(String localeType){
		this.localeType = localeType;
	}
	public String getLocaleType(){
		return this.localeType;
	}		
	public void setLocaleIp(String localeIp){
		this.localeIp = localeIp;
	}
	public String getLocaleIp(){
		return this.localeIp;
	}		
	public void setLocalePort(String localePort){
		this.localePort = localePort;
	}
	public String getLocalePort(){
		return this.localePort;
	}		
	public void setLoginType(String loginType){
		this.loginType = loginType;
	}
	public String getLoginType(){
		return this.loginType;
	}		
	public void setLoginUser(String loginUser){
		this.loginUser = loginUser;
	}
	public String getLoginUser(){
		return this.loginUser;
	}		
	public void setLoginPwd(String loginPwd){
		this.loginPwd = loginPwd;
	}
	public String getLoginPwd(){
		return this.loginPwd;
	}		
	public void setRemark(String remark){
		this.remark = remark;
	}
	public String getRemark(){
		return this.remark;
	}		

}
