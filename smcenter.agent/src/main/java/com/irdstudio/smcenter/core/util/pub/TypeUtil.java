package com.irdstudio.smcenter.core.util.pub;
/**
 * 类型辅助类
 * @author 李广明
 * @version 1.0
 * @date 2008-12-31
 */
public class TypeUtil {
	
	public static boolean isInt(String str){
		try {
			(new Integer(str)).intValue();
		} catch (Exception e) {
			// 异常不作处理,将会返回默认值
			return false;
		}
		return true;
	}

}
