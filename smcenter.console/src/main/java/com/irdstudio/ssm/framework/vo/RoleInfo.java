package com.irdstudio.ssm.framework.vo;

import java.io.Serializable;

public class RoleInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String roleCode;
	
	private String roleName;

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}
