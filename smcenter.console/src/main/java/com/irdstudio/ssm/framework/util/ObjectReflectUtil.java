package com.irdstudio.ssm.framework.util;

/**
 * javabean反射工具类
 * @author luobo
 */
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ObjectReflectUtil {

	/**
	 * 获对对象类所有的属性及属性值，以map方式进行返回
	 * 
	 * @param obj
	 *            对象类
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Map getKeyAndValue(Object obj)
			throws   InvocationTargetException {
		Map map = new HashMap();
		Class userCla = (Class) obj.getClass();
		/* 得到类中的所有属性集合 */
		Field[] fs = userCla.getDeclaredFields();
		for (Field field : fs) {

			map.put(field.getName(), getPropertyValue(obj, field.getName()));

		}
		return map;

	}

	/**
	 * 根据对象类的属性名称获取属性值
	 * 
	 * @param obj
	 *            对象
	 * @param fieldName
	 *            字段名称
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Object getPropertyValue(Object obj, String fieldName)   {

		String firstLetter = fieldName.substring(0, 1).toUpperCase();
		String getter = "get" + firstLetter + fieldName.substring(1);
		Method method;
		try {
			method = obj.getClass().getMethod(getter);
			Object value = method.invoke(obj, new Object[] {});
			return value;
		} catch (Exception e) {
			// TODO Auto-generated catch block
		 
		}  
		
		return null;

	}

	/**
	 * 
	 * @param obj
	 *            对象类
	 * @param fieldName
	 *            属性字段名称
	 * @param value
	 *            需要设置的值
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static void setPropertyValue(Object obj, String fieldName, Object value) throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		String firstLetter = fieldName.substring(0, 1).toUpperCase();
		String setter = "set" + firstLetter + fieldName.substring(1);
		Method method = obj.getClass().getMethod(setter, value.getClass());
		method.invoke(obj, new Object[] { value });

	}

	public static Object invokeMethodValueByMethodName(Object obj, String methodName, Object value1)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		Method method = obj.getClass().getMethod(methodName, value1.getClass());
		return method.invoke(obj, new Object[] { value1 });

	}

	public static Object invokeMethodValueByMethodName(Object obj, String methodName, Object value1, Object value2)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		Method method = obj.getClass().getMethod(methodName, value1.getClass(), value2.getClass());
		return method.invoke(obj, new Object[] { value1 });

	}
	/**
	 * 根据方法名获取有三个参数的方法反回值
	 * @param obj
	 * @param methodName
	 * @param value1
	 * @param value2
	 * @param value3
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */

	public static Object invokeMethodValueByMethodName(Object obj, String methodName, Object value1, Object value2,
			Object value3) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		Method method = obj.getClass().getMethod(methodName, value1.getClass(), value2.getClass(), value3.getClass());
		return method.invoke(obj, new Object[] { value1 });

	}

	/**
	 * 根据方法名获取无参数的的值
	 * @param obj
	 * @param methodName
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Object invokeMethodValueByMethodName(Object obj, String methodName) throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Method method = obj.getClass().getMethod(methodName);
		return method.invoke(obj, new Object[] {});

	}

}
