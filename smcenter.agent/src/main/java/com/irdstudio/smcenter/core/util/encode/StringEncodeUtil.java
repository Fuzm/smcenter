package com.irdstudio.smcenter.core.util.encode;

import java.io.UnsupportedEncodingException;

/**
 * 字符类数据编码转换辅助类
 * @author 李广明
 * @version 1.0
 * @date 2007-10-25
 *
 */
public class StringEncodeUtil {

	public static String toIso_8859_1(String str){
		String result = str;
		try {
			result =  new String(str.getBytes(), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			System.out.println("字符编码转换为ISO-8859-1失败");
			e.printStackTrace();
		}
		return result;
	}
}
