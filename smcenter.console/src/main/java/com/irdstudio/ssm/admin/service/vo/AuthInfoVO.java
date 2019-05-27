package com.irdstudio.ssm.admin.service.vo;

import java.io.Serializable;

import javax.servlet.http.HttpSession;

import com.irdstudio.ssm.framework.vo.UserInfo;

public class AuthInfoVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String usercode;
	
	private String password;
	
	private String newPassword;
	
	private String token;
	
	private String sessionId;
	
	private Long timestamp;
	
	private String md5;

	private boolean checkToken = false;
	
	private boolean changeSuccess = false;
	
	private String message;
	
	private UserInfo userInfo;

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public boolean isCheckToken() {
		return checkToken;
	}

	public void setCheckToken(boolean checkToken) {
		this.checkToken = checkToken;
	}

	public boolean isChangeSuccess() {
		return changeSuccess;
	}

	public void setChangeSuccess(boolean changeSuccess) {
		this.changeSuccess = changeSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
}
