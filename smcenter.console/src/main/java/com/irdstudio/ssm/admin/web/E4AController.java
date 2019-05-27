package com.irdstudio.ssm.admin.web;

import java.util.Objects;

import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.irdstudio.ssm.admin.service.api.E4AService;
import com.irdstudio.ssm.admin.service.vo.AuthInfoVO;
import com.irdstudio.ssm.framework.constant.E4AConstant;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.session.SessionManager;
import com.irdstudio.ssm.framework.util.CookiesUtil;
import com.irdstudio.ssm.framework.util.StringUtil;
import com.irdstudio.ssm.framework.vo.UserInfo;
import com.irdstudio.ssm.framework.web.AbstractController;

/**
 * E4A控制器
 * @author fuzm
 *
 */
@RestController
@RequestMapping("/api")
public class E4AController extends AbstractController {
	
	private static Logger logger = LoggerFactory.getLogger(E4AController.class);
	@Autowired
	private E4AService e4aService;
	
	@Autowired
	private SessionManager sessionManager;
	
	@RequestMapping(value="/oauth/token", method=RequestMethod.POST)
	public  @ResponseBody ResponseData<Boolean> authToken(@RequestBody AuthInfoVO authInfoVO) {
		
		authInfoVO.setSessionId(this.httpRequest.getSession().getId());
		AuthInfoVO authInfo = e4aService.login(authInfoVO);
		//登录成功
		if (authInfo != null && Objects.nonNull(authInfo.getUserInfo())) {
			//将后续校验用到的信息存入cookies
			CookiesUtil.addCookie(this.httpResponse, E4AConstant.COOKIES_KEY_SSO_CLIENT, authInfo.getSessionId(), -1);
			CookiesUtil.addCookie(this.httpResponse, E4AConstant.COOKIES_KEY_SSO_TIMESTAMP, String.valueOf(authInfo.getTimestamp()), -1);
			CookiesUtil.addCookie(this.httpResponse, E4AConstant.COOKIES_KEY_SSO_MD5, authInfo.getMd5(), -1);
			
			//缓存会话
			sessionManager.setLoginInfo(authInfo.getUserInfo(), this.httpRequest);
			
			return this.getResponseData(true);
		} else { //登录失败
			ResponseData<Boolean> data = this.getResponseData(false);
			data.setMessage("登录失败，用户名或密码不正确或用户已被注销");
			return data;
		}
	}
	
	/**
	 * 获取session登录信息
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/session/info", method=RequestMethod.POST)
	public @ResponseBody ResponseData<UserInfo> getSessionInfo() {
		ResponseData<UserInfo> data = null;
		try {
			UserInfo userInfo = this.getUserInfo();
			data = this.getResponseData(userInfo);
		} catch(Exception e) {
			//e.printStackTrace();
			logger.error("获取session登录信息失败" + e.getMessage(), e);
			//将响应码改成验证失败
			this.httpResponse.setStatus(401);
		}
		return data;
	}
	
	/**
	 * 登出
	 * @param loginInfoVO
	 * @return
	 */
	@RequestMapping(value="/logout", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Boolean> logout() {
		ResponseData<Boolean> data = null;
		try {
			String sessionId = CookiesUtil.getCookieValue(this.httpRequest, E4AConstant.COOKIES_KEY_SSO_CLIENT);
			//清除Cookie 信息
			Cookie[] cookies = this.httpRequest.getCookies();
			for (Cookie c : cookies) {
				if (E4AConstant.COOKIES_KEY_SSO_CLIENT.equals(c.getName()) 
					|| E4AConstant.COOKIES_KEY_SSO_TIMESTAMP.equals(c.getName())
					|| E4AConstant.COOKIES_KEY_SSO_MD5.equals(c.getName()) ) {
					c.setMaxAge(0);
					this.httpResponse.addCookie(c);
				}
			}
			//清出session
			sessionManager.removeSession(sessionId);
			logger.info("清空session");
			data = this.getResponseData(true);
		} catch(Exception e) {
			//e.printStackTrace();
			//this.httpResponse.setStatus(401);
			logger.error("登出失败" + e.getMessage(), e);
			data = this.getResponseData(false);
			data.setMessage("登出失败!");
		}
		return data;
	}
	
	/**
	 * 密码修改
	 * @return
	 */
	@RequestMapping(value="/account/modifyUserPwd", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Boolean> changePasswrod(@RequestBody AuthInfoVO authInfoVO) {
		ResponseData<Boolean> data = null;
		
		//设置当前用户
		UserInfo userInfo = this.getUserInfo();
		authInfoVO.setUsercode(userInfo.getUserId());
		
		AuthInfoVO resultInfo = e4aService.modifyUserPassword(authInfoVO);
		if(resultInfo != null) {
			if(resultInfo.isChangeSuccess()) {
				data = this.getResponseData(true);
			} else {
				data = this.getResponseData(false);
				data.setMessage(resultInfo.getMessage());
			}
		} else {
			data = this.getResponseData(false);
		}
		
		return data;
	}
	
}
