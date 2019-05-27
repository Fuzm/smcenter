package com.irdstudio.ssm.admin.dao;

import com.irdstudio.ssm.admin.dao.domain.SUser;

public interface E4ADao {

	/**
	 * 用户信息查询
	 * @param sUser
	 * @return
	 */
	public SUser queryByCodeAndPw(SUser sUser);
	
	/**
	 * 更新用户密码
	 * @param sUser
	 * @return
	 */
	public int updateUserPassword(SUser sUser);
	
}
