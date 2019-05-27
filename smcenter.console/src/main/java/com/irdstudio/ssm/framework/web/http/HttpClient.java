package com.irdstudio.ssm.framework.web.http;

import java.util.Collection;

import javax.servlet.http.Cookie;

import org.springframework.http.HttpMethod;

/**
 * http客户端调用
 * @param <I>
 * @param <O>
 * 
 * @author Cytus_
 * @since 2018年5月16日 下午4:51:18
 * @version 1.0
 *
 */
public interface HttpClient<I, O> {
	
	public void setHeader(String key, String value);
	
	public void setCookie(Collection<Cookie> cookies);
	
	public O httpCall(String url, HttpMethod method, I inArgs, Class<O> clazz, Object... params) throws Exception;

}
