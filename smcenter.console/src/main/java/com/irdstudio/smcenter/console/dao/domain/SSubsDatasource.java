package com.irdstudio.smcenter.console.dao.domain;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * Description: 子系统数据源信息表			
 * @author ligm
 * @date 2018-06-13
 */
public class SSubsDatasource extends BaseInfo{

	private static final long serialVersionUID = 1L;	
	
	/** 子系统代码 */
	private String subsCode;
	/** 子系统数据源代码 */
	private String subsDsCode;
	/** 子系统数据源名称 */
	private String subsDsName;
	/** 数据源-连接字符串 */
	private String dsConnStr;
	/** 数据源-用户名 */
	private String dsUserId;
	/** 数据源-用户密码 */
	private String dsUserPwd;
	/** 数据源-模式 */
	private String dsSchemaName;
	/** 数据源-数据库名 */
	private String dsDbName;
	/** 子系统数据源类型 */
	private String subsDsType;
	

	public void setSubsCode(String subsCode){
		this.subsCode = subsCode;
	}
	public String getSubsCode(){
		return this.subsCode;
	}		
	public void setSubsDsCode(String subsDsCode){
		this.subsDsCode = subsDsCode;
	}
	public String getSubsDsCode(){
		return this.subsDsCode;
	}		
	public void setSubsDsName(String subsDsName){
		this.subsDsName = subsDsName;
	}
	public String getSubsDsName(){
		return this.subsDsName;
	}		
	public void setDsConnStr(String dsConnStr){
		this.dsConnStr = dsConnStr;
	}
	public String getDsConnStr(){
		return this.dsConnStr;
	}		
	public void setDsUserId(String dsUserId){
		this.dsUserId = dsUserId;
	}
	public String getDsUserId(){
		return this.dsUserId;
	}		
	public void setDsUserPwd(String dsUserPwd){
		this.dsUserPwd = dsUserPwd;
	}
	public String getDsUserPwd(){
		return this.dsUserPwd;
	}		
	public void setDsSchemaName(String dsSchemaName){
		this.dsSchemaName = dsSchemaName;
	}
	public String getDsSchemaName(){
		return this.dsSchemaName;
	}		
	public void setDsDbName(String dsDbName){
		this.dsDbName = dsDbName;
	}
	public String getDsDbName(){
		return this.dsDbName;
	}		
	public void setSubsDsType(String subsDsType){
		this.subsDsType = subsDsType;
	}
	public String getSubsDsType(){
		return this.subsDsType;
	}		

}
