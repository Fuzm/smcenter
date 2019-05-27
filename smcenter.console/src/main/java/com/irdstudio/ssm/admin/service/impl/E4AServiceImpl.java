package com.irdstudio.ssm.admin.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.irdstudio.ssm.admin.dao.E4ADao;
import com.irdstudio.ssm.admin.dao.domain.SUser;
import com.irdstudio.ssm.admin.service.api.E4AService;
import com.irdstudio.ssm.admin.service.vo.AuthInfoVO;
import com.irdstudio.ssm.framework.constant.E4AConstant;
import com.irdstudio.ssm.framework.session.SessionManager;
import com.irdstudio.ssm.framework.util.MD5Util;
import com.irdstudio.ssm.framework.util.StringUtil;
import com.irdstudio.ssm.framework.util.URLUtil;
import com.irdstudio.ssm.framework.vo.UserInfo;

/**
 * E4A认证服务
 * @author fuzm
 *
 */
@Service("e4aService")
public class E4AServiceImpl implements E4AService {
	
	private static final Logger logger = LoggerFactory.getLogger(E4AServiceImpl.class);
	
	@Autowired
	private E4ADao e4aDao;
	
	//@Autowired
	//private SessionManager sessionManager;
	
	@Override
	public AuthInfoVO login(AuthInfoVO authInfoVO) {
		AuthInfoVO authInfo = new AuthInfoVO();
		try {
			SUser param = new SUser();
			param.setActorno(authInfoVO.getUsercode());
			param.setPassword(MD5Util.getMD5ofStr(authInfoVO.getUsercode() + authInfoVO.getPassword()));
			
			SUser user = e4aDao.queryByCodeAndPw(param);
			//校验密码成功
			if (user != null && StringUtil.isNotEmpty(user.getActorno())) {
				logger.debug("登录成功！");
				long timestamp = System.currentTimeMillis();
				//session中的用户信息初始化
				UserInfo userInfo = new UserInfo();
				userInfo.setUserId(user.getActorno());
				userInfo.setUserName(user.getActorname());
				
				//生成token及验证信息
				String jsonStr = JSONObject.toJSONString(userInfo);
				String md5 = MD5Util.getMD5ofStr(URLUtil.md5(jsonStr, Long.valueOf(timestamp), E4AConstant.SECURITY_SALT));
				
				//会话管理器缓存
				//sessionManager.setLoginInfo(userInfo, );
				
				authInfo.setUsercode(authInfoVO.getUsercode());
				authInfo.setSessionId(authInfoVO.getSessionId());
				authInfo.setTimestamp(timestamp);
				authInfo.setMd5(md5);
				authInfo.setUserInfo(userInfo);
			}
			
		} catch(Exception e) {
			//e.printStackTrace();
			logger.error("登录失败，错误信息：" + e.getMessage(), e);
		}
		return authInfo;
	}

	@Override
	@Deprecated
	public AuthInfoVO valifyLogin(AuthInfoVO authInfoVO) {
		AuthInfoVO authInfo = null;
		try {
			String sessionId = authInfoVO.getSessionId();
			String md5 = authInfoVO.getMd5();
			String timestamp = authInfoVO.getMd5();
			
			authInfo = new AuthInfoVO();
			//校验session过期
			UserInfo userInfo = new UserInfo();
			
			if(userInfo != null && userInfo.getUserId() != null) {
				
				String jsonStr = JSONObject.toJSONString(userInfo);
				String checkMd5 = MD5Util.getMD5ofStr(URLUtil.md5(jsonStr, Long.valueOf(timestamp), E4AConstant.SECURITY_SALT));
				if(StringUtil.isNotEmpty(md5) && StringUtil.isNotEmpty(jsonStr) && checkMd5.equals(md5)) {
					authInfo.setCheckToken(true);
					authInfo.setUserInfo(userInfo);
				} else {
					throw new Exception("md5校验失败，登录用户信息校验出错:" + sessionId);
				}
				
			} else {
				//logger.error("session信息过期，无法获取登录用户信息校验出错");
				throw new Exception("session信息过期，无法获取登录用户信息校验出错:" + sessionId);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("验证过程出错，错误信息为：" + e.getMessage());
			authInfo = new AuthInfoVO();
			authInfo.setCheckToken(false);
		}
		
		return authInfo;
	}

	@Override
	@Deprecated
	public boolean logout(AuthInfoVO authInfoVO) {
		boolean success = false;
		try {
			String sessionId = authInfoVO.getSessionId();
			if(sessionId != null && StringUtils.isNotEmpty(sessionId)) {
				//sessionManager.removeSession(sessionId);
			}
			success = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return success;
	}
	
	@Override
	public AuthInfoVO modifyUserPassword(AuthInfoVO authInfoVO) {
		AuthInfoVO authInfo = new AuthInfoVO();
		try {
			SUser param = new SUser();
			param.setActorno(authInfoVO.getUsercode());
			param.setPassword(MD5Util.getMD5ofStr(authInfoVO.getUsercode() + authInfoVO.getPassword()));
			
			SUser user = e4aDao.queryByCodeAndPw(param);
			if(user != null) {
				SUser changeParam = new SUser();
				changeParam.setActorno(user.getActorno());
				changeParam.setPassword(MD5Util.getMD5ofStr(authInfoVO.getUsercode() + authInfoVO.getNewPassword()));
				e4aDao.updateUserPassword(changeParam);
				
				authInfo.setChangeSuccess(true);
				authInfo.setMessage("密码修改成功!");
			} else {
				logger.error("原密码错误！");
				authInfo.setChangeSuccess(false);
				authInfo.setMessage("原密码错误!");
			}
		} catch(Exception e) {
			logger.error("修改密码失败" + e.getMessage(), e);
			authInfo.setChangeSuccess(false);
			authInfo.setMessage("修改密码错误!");
		}
		
		return authInfo;
	}

}
