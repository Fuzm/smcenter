package com.irdstudio.smcenter.core.assembly.socket.core.util;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.irdstudio.smcenter.core.util.pub.Convert;
/**
 * 内部函数解释器
 * @author 李广明
 * @version 1.0
 * @date 2008-08-16
 *
 */
public class FuncParser {

	private Logger logger = Logger.getLogger("[交易接口]:[函数解释]");

	/**
	 * 入口方法
	 * @param funcName
	 * @param param
	 * @return
	 */
	public String callFunc(String funcName, String param) {
		//使用反射机制将需要映射到需要调用的内置函数
		String result = "";
		Method doMethod = null;
		Class thisClass = this.getClass();
		Class[] parameterTypes = new Class[] { String.class };
		Object[] arguments = new Object[] { param };
		try {
			doMethod = thisClass.getMethod(funcName.toLowerCase(),
					parameterTypes);
			result = (String) doMethod.invoke(this, arguments);
		} catch (NoSuchMethodException e) {
			logger.debug("不支持[" + funcName + "]函数!");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 分解函数,用于分解参数
	 * @param param
	 * @return
	 */
	private String[] splitParam(String param) {
		if (param.charAt(param.length() - 1) == ',')
			param = param.concat(" ");
		return param.split(",");
	}

	/**
	 * 用于连接字符串
	 * @param param
	 * @return
	 */
	public String concat(String param) {
		String[] params = splitParam(param);
		String result = "";
		for (int i = 0; i < params.length; i++) {
			result.concat(params[i]);
		}
		return result;
	}

	/**
	 * 用于求和
	 * @param param
	 * @return
	 */
	public String sum(String param) {
		long result = 0;
		String[] params = splitParam(param);
		for (int i = 0; i < params.length; i++) {
			result += Convert.StrToInt(params[i], 0);
		}
		return new Long(result).toString();
	}

	/**
	 * 主要用于将字段变量
	 * 标准变量的值直接取出来后返回
	 * @param param
	 * @return
	 */
	public String getvalue(String param) {
		return param;
	}
}
