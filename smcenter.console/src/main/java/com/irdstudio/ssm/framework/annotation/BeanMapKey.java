package com.irdstudio.ssm.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 * java bean 和 map 互转</p>
 * 主要用于java bean 中成员变量转成map 时 map中key值的定义
 * 
 * @author Cytus_
 * @since 2018年5月13日 上午9:15:19
 * @version 1.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface BeanMapKey {

	String value();
	
}
