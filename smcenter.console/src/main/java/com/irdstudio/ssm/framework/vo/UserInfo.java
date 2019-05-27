package com.irdstudio.ssm.framework.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 用户登录信息</p>
 * 该类主要存放用户登录后的相关信息，使用限制，仅在web controller层使用
 * @author Cytus_
 * @since 2018-05-01 13:34:32
 * @version 1.0
 */
public class UserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String SEESION_USER_KEY = "userInfo";

	private String userId;
	
	private String userName;
	
	private String openday;
	
	private OrgInfo legalOrg;
	
	private OrgInfo orgInfo;
	
	private List<RoleInfo> userRoles;
	
	private List<DutyInfo> userDutys;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOpenday() {
		return openday;
	}

	public void setOpenday(String openday) {
		this.openday = openday;
	}

	public OrgInfo getLegalOrg() {
		if (Objects.isNull(legalOrg)) {
			legalOrg = new OrgInfo();
		}
		return legalOrg;
	}

	public void setLegalOrg(OrgInfo legalOrg) {
		this.legalOrg = legalOrg;
	}

	public OrgInfo getOrgInfo() {
		if (Objects.isNull(orgInfo)) {
			orgInfo = new OrgInfo();
		}
		return orgInfo;
	}

	public void setOrgInfo(OrgInfo orgInfo) {
		this.orgInfo = orgInfo;
	}

	public List<RoleInfo> getUserRoles() {
		if (Objects.isNull(userRoles)) {
			userRoles = new ArrayList<RoleInfo>();
		}
		return userRoles;
	}

	public void setUserRoles(List<RoleInfo> userRoles) {
		this.userRoles = userRoles;
	}

	public List<DutyInfo> getUserDutys() {
		if (Objects.isNull(userDutys)) {
			userDutys = new ArrayList<DutyInfo>();
		}
		return userDutys;
	}

	public void setUserDutys(List<DutyInfo> userDutys) {
		this.userDutys = userDutys;
	}
	
}
