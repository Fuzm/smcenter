package com.irdstudio.smcenter.core.util.pub;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * @docRoot:
 * 转换类,用于一些常用数据类型的转换
 * @author 李广明
 * @version 1.0
 *
 */
public class Convert {	
	/**
	 * 用于将字符串转换成整型
	 * @param str
	 * @return
	 */
	public static int StrToInt(String str,int defaultValue){
		int value = defaultValue;
		if (str == null || "".equals(str.trim()))
			return value;
		try {
			value = (new Integer(str)).intValue();
		} catch (Exception e) {
			// 异常不作处理,将会返回默认值
		}
		return value;
	}

	/**
	 * 用于将字符串转换成整型 默认即返回0
	 * @param str
	 * @return
	 */
	public static int StrToInt(String str) {
		return StrToInt(str, -1);
	}
	
	/**
	 * 将字符型转换成又精度型
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static double StrToDouble(String str,double defaultValue){
		double value = defaultValue;
		if (str == null || "".equals(str.trim()))
			return value;
		try {
			value = Double.parseDouble(str);
		} catch (Exception e) {
			// 异常不作处理,将会返回默认值
		}
		return value;
	}
	/**
	 * 将object转换成double
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static double ObjectToDouble(Object obj,double defaultValue){
		double value = defaultValue;
		if (obj == null)
			return value;
		try {
			value = ((Double)obj).doubleValue();
		} catch (Exception e) {
			// 异常不作处理,将会返回默认值
		}
		return value;
	}	
	
	/**
	 * 将字符串的值转换成布尔型
	 * @param str
	 * @return
	 */
	public static boolean StrToBoolean(String str){
		if("true".equals(str.toLowerCase())){
			return true;
		}
		else{
			return false;
		}
	}
	
	public static String toMoney(String num,String prefix,int digi,String defaultvalue){

		try {
			BigDecimal big = new BigDecimal(num);
			big = big.setScale(digi, BigDecimal.ROUND_HALF_UP);
			//处理负数
			if (big.compareTo(new BigDecimal(String.valueOf(0))) < 0) {
				big = big.abs();
				prefix = "-" + prefix;
			}
			
			String prototype = big.toString();
			String body = prototype.substring(0, prototype.lastIndexOf("."));//整数
			String postfix = prototype.substring(prototype.lastIndexOf("."));//小数
			int count = body.length() / 3;//有多少个三位
			int head = body.length() % 3;// 不足3位
			//System.out.println(count + "::::" + head);
			//不足3位或刚好3位
			if (count == 0 || (count == 1 && head == 0)) {
				return prefix + body + postfix;
			}
			//多于3位或刚好整除
			prefix += prototype.substring(0, head == 0 ? 3 : head);//刚好整除的把前面3位也放到前缀.
			body = body.substring(head == 0 ? 3 : head);
			//System.out.println(prototype + "===" + prefix + ":" + body + ":"+ postfix);
			//整除的前3个放到前缀了,count-1
			count = head == 0 ? count - 1 : count;
			String[] arr = new String[count];
			//分割字符串
			for (int t = 0; t < count; t++) {
				arr[t] = body.substring((t) * 3, (t + 1) * 3);
			}
			//重新组装字符串
			prototype = "";
			for (int i = 0; i < arr.length; i++) {
				//System.out.println(i + ">" + arr[i]);
				prototype += "," + arr[i];
			}
			prototype = prefix + prototype + postfix;
			return prototype;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return defaultvalue;
		}		
		
	}
	
	/**
	 * 将字符串类型转换成日期类型
	 * @param str
	 * @return
	 */
	public static Date strToDate(String str){
		return null;
	}
}
