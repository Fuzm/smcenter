package com.irdstudio.ssm.framework.util;

import java.util.Objects;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtils implements ApplicationContextAware {

	private static ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		context = arg0;
	}
	
	public static Object getBean(String beanId) {
		assertContext();
		return context.getBean(beanId);
	}
	
	public static <T>  T getBean(String beanId, Class<T> clazz) {
		return context.getBean(beanId, clazz);
	}
	
	public static void assertContext() {
		if (Objects.isNull(context)) {
			throw new NullPointerException();
		}
	}

}
