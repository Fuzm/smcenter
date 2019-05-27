package com.irdstudio.ssm.framework.vo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 所有domain和vo对象需要继承的父类</p>
 * 该类主要存放了数据分页信息和用户相关信息，用户相关信息是{@link UserInfo}类成员变量的简化版
 * @author Cytus_
 * @since 2018-04-30 17:50:23
 * @version 1.0
 */
public class BaseInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int DEFAULT_MAX_PAGE_SIZE = 1000;
	
	private static final int DEFAULT_MIN_PAGE_SIZE = 10;
	
	private static final int DEFAULT_MIN_PAGE_NO = 1;
	
	private int page;
	
	private int size;
	
	private int total;
	
	/* 支持easyui */
	private int rows;
	
	private String loginUserId;
	
	private String loginUserOrgCode;
	
	private String loginUserLeageOrgCode;
	
	private String loginUserOrgLocation;

	public String getLoginUserOrgLocation() {
		return loginUserOrgLocation;
	}

	public void setLoginUserOrgLocation(String loginUserOrgLocation) {
		this.loginUserOrgLocation = loginUserOrgLocation;
	}


	public void setSize(int pageSize) {
		this.size = pageSize > DEFAULT_MAX_PAGE_SIZE ? DEFAULT_MAX_PAGE_SIZE : pageSize;
		this.rows = this.size;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}

	public String getLoginUserOrgCode() {
		return loginUserOrgCode;
	}

	public void setLoginUserOrgCode(String loginUserOrgCode) {
		this.loginUserOrgCode = loginUserOrgCode;
	}

	public String getLoginUserLeageOrgCode() {
		return loginUserLeageOrgCode;
	}

	public void setLoginUserLeageOrgCode(String loginUserLeageOrgCode) {
		this.loginUserLeageOrgCode = loginUserLeageOrgCode;
	}
	
	public void checkAndSetPageInfo() {
		this.size = this.size <= 0 ? DEFAULT_MIN_PAGE_SIZE : this.size;
		this.page = this.page < 1 ? DEFAULT_MIN_PAGE_NO : this.page;
		this.rows = this.size;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows > DEFAULT_MAX_PAGE_SIZE ? DEFAULT_MAX_PAGE_SIZE : rows;
		this.size = this.rows;
	}
	
}
