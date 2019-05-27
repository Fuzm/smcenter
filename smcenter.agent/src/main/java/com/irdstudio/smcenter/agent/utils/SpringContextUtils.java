package com.irdstudio.smcenter.agent.utils;

import java.util.Objects;

import org.springframework.context.ApplicationContext;

public class SpringContextUtils {

	private static ApplicationContext context;
	
	public static void setApplicationContext(ApplicationContext context) {
		if(SpringContextUtils.context == null) {
			SpringContextUtils.context = context;
		}
	}
	
	public static Object getBean(String beanId) {
		assertContext();
		return context.getBean(beanId);
	}
	
	public static <T>  T getBean(String beanId, Class<T> clazz) {
		return (T) context.getBean(beanId, clazz);
	}
	
	public static void assertContext() {
		if (Objects.isNull(context)) {
			throw new NullPointerException();
		}
	}

}
