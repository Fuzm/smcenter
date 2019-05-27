package com.irdstudio.ssm.framework.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookiesUtil {
	
	public static void addCookie(HttpServletResponse response, String key, String value, int expire) {
		Cookie cookie = new Cookie(key, value);
		cookie.setPath("/");
		cookie.setMaxAge(expire);
		response.addCookie(cookie);
	}

	/**
	 * 获取cookies中key的value
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request, String key) {
		String cookieValue = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null && key != null) {
			for (Cookie cookie : cookies) {
				if (key.equals(cookie.getName())) {
					cookieValue = cookie.getValue();
				}
			}
		}
		return cookieValue;
	}
}
