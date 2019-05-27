package com.irdstudio.smcenter.core.util.pub;

import java.lang.reflect.Array;
import java.util.Map;

/**
 * 字符串辅助类
 * @author 李广明
 * @version 1.0
 * @date 2008-05-06
 *
 */
public class StringUtil {

	/**
	 * 
	 * @param str
	 * @param length
	 * @return
	 */
	public static String leftFull(String str,int length){
		return "";
	}
	
	/**
	 * 给整数按指定位数左侧补零
	 * @param src 原始数值
	 * @param formatLength 总位数（不足位补零）
	 * @return 补零后的数值
	 */
	public static String frontCompWithZore(int src, int formatLength) {
		String addedStr = String .format("%0" + formatLength + "d" , src);
		return addedStr;
	}
	
    /**
     * 判断对象、字符、集合、数组是否为空
     * @param obj
     * @return boolean
     * @decription check is null or "" or set，size=0 or list.size=0 or Array.length=0 or Map.isEmpty;
     */
    @SuppressWarnings("rawtypes")
	public static boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof java.lang.String) {
            return "".equals(obj);
        } else if (obj instanceof java.util.Set) {
            return ((java.util.Set) obj).size() == 0;
        } else if (obj instanceof java.util.List) {
            return ((java.util.List) obj).size() == 0;
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        } else if (obj instanceof java.util.Map) {
            return ((Map) obj).isEmpty();
        }
        return false;
    }
}
