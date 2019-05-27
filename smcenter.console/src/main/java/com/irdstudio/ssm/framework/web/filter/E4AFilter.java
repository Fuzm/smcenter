package com.irdstudio.ssm.framework.web.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSONObject;
import com.irdstudio.ssm.admin.service.vo.AuthInfoVO;
import com.irdstudio.ssm.framework.constant.E4AConstant;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.session.SessionManager;
import com.irdstudio.ssm.framework.util.CookiesUtil;
import com.irdstudio.ssm.framework.util.GsonUtils;
import com.irdstudio.ssm.framework.util.MD5Util;
import com.irdstudio.ssm.framework.util.StringUtil;
import com.irdstudio.ssm.framework.util.URLUtil;
import com.irdstudio.ssm.framework.vo.UserInfo;

public class E4AFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(E4AFilter.class);

	private static String[] passedPaths = null;
	
	private SessionManager sessionManager;

	public void init(FilterConfig filterConfig) throws ServletException {
		WebApplicationContext applicationcontext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(filterConfig.getServletContext());
		
		//会话管理
		sessionManager = applicationcontext.getBean("sessionManager", SessionManager.class);
		
		String urls = filterConfig.getInitParameter("allowUrl");
		if (StringUtils.isNotBlank(urls)) {
			passedPaths = urls.split(";");
		}
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) rep;
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String requestPath = request.getServletPath();

		PathMatcher matcher = new AntPathMatcher();
		if (passedPaths != null) {
			for (String passedPath : passedPaths) {
				boolean flag = matcher.match(passedPath, requestPath);
				if (flag) {
					logger.info("sso client request path '" + requestPath + "'is matched,filter chain will be continued.");
					chain.doFilter(request, response);
					return;
				}
			}
		}
		
		//发送请求到E4A进行token校验
		AuthInfoVO authInfo = null;
		try {
			String sessionId = CookiesUtil.getCookieValue(request, E4AConstant.COOKIES_KEY_SSO_CLIENT);
			long timestamp = StringUtil.String2Long(CookiesUtil.getCookieValue(request, E4AConstant.COOKIES_KEY_SSO_TIMESTAMP));
			String md5 = CookiesUtil.getCookieValue(request, E4AConstant.COOKIES_KEY_SSO_MD5);
			
			authInfo = new AuthInfoVO();
			//校验session过期
			UserInfo userInfo = sessionManager.getLoginInfo(sessionId);
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
			
			//authInfo = e4aService.valifyLogin(vo);
			//logger.error("auth info: " + authInfo + ", authInfo check:" + authInfo.isCheckToken() + ", authInfo userInfo:" + authInfo.getUserInfo().getUserId());
				
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("sso client request token check fail: " + e.getMessage(), e);
			
			//验证失败
			response.setStatus(401);
			ResponseData<Boolean> data = new ResponseData<>();
			data.createData(false, "-1", "valify token 错误", false);
			
			String outputmessage = GsonUtils.toJson(data);
			PrintWriter rsp = response.getWriter();
			Throwable localThrowable6 = null;
			try {
				rsp.write(outputmessage);
			} catch (Throwable localThrowable1) {
				localThrowable6 = localThrowable1;
				throw localThrowable1;
			} finally {
				if (rsp != null) {
					if (localThrowable6 != null) {
						try {
							rsp.close();
						} catch (Throwable x2) {
							localThrowable6.addSuppressed(x2);
						}
					} else {
						rsp.close();
					}
				}
			}
			return;
		}
		
		//验证判断
		if(authInfo == null || !authInfo.isCheckToken() || authInfo.getUserInfo() == null
				|| authInfo.getUserInfo().getUserId() == null) {
			logger.info("登录校验失败：" + authInfo);
			//验证失败
			response.setStatus(401);
			ResponseData<Boolean> data = new ResponseData<>();
			data.createData(false, "-1", "登录验证失败", false);
			
			String outputmessage = GsonUtils.toJson(data);
			PrintWriter rsp = response.getWriter();
			Throwable localThrowable6 = null;
			try {
				rsp.write(outputmessage);
			} catch (Throwable localThrowable1) {
				localThrowable6 = localThrowable1;
				throw localThrowable1;
			} finally {
				if (rsp != null) {
					if (localThrowable6 != null) {
						try {
							rsp.close();
						} catch (Throwable x2) {
							localThrowable6.addSuppressed(x2);
						}
					} else {
						rsp.close();
					}
				}
			}
			return;
		}
		
		logger.info("校验通过");
		request.getSession().setAttribute(UserInfo.SEESION_USER_KEY, authInfo.getUserInfo());
		chain.doFilter(request,response);
		return;
	}
	
	@Override
	public void destroy() {
	}
	
	public static void main(String[] args) {
		String requestPath = "/static/css/te/test.jpg";
		String passedPath = "/static/**/*.jpg";
		PathMatcher matcher = new AntPathMatcher();
		System.out.println(matcher.match(passedPath, requestPath));
	}
}
