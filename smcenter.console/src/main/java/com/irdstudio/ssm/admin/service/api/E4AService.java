package com.irdstudio.ssm.admin.service.api;

import com.irdstudio.ssm.admin.service.vo.AuthInfoVO;

public interface E4AService {
	
	/**
	 * 登录
	 * @param authInfoVO
	 * @return
	 */
	public AuthInfoVO login(AuthInfoVO authInfoVO);
	
	/**
	 * 登录校验
	 * @param authInfoVO
	 * @return
	 */
	public AuthInfoVO valifyLogin(AuthInfoVO authInfoVO);
	
	/**
	 * 登出
	 * @param authInfoVO
	 * @return
	 */
	public boolean logout(AuthInfoVO authInfoVO);
	
	/**
	 * 用户密码修改
	 * @param authInfoVO
	 * @return
	 */
	public AuthInfoVO modifyUserPassword(AuthInfoVO authInfoVO);
	
}
