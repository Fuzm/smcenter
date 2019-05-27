/**
 * <p>Title: 转码工具类</p>
 * <p>Description: 转码层</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 北京易初电子技术有限公司</p>
 * @author 黄瑞文
 * @version 1.0
 * 2006-11-22
 */
package com.irdstudio.smcenter.core.assembly.imdbcp.encode;

import java.sql.SQLException;

/**
 * 转码工具类
 * 不能被实例化
 * @author hrw
 * 2006-11-26
 */
public class Util {
	
	/** 私有化构造函数，禁止实例化 */
	private Util() {
		
	}
	
	/**
	 * 转码转出(默认编码 => 目标编码)，主要用于写数据库时转码
	 * @param sourceStr 源字符串
	 * @param charSet 字符集
	 * @param type 转换类型 0 不转换，1 按字符集转换，2 分析转换
	 * @return 转换后的字符串
	 */
	public static String encodeString(String sourceStr,String charSet,int type) 
	throws SQLException {
		
		if (type == 0 || sourceStr == null) {
			return sourceStr;
		} else {
			byte[] bytes = sourceStr.getBytes();
			if (bytes.length <= 0) {
				return sourceStr;
			}
			
			try {
				if (type == 1) {
					
					return new String(bytes,charSet); 
				} else if (isNeedEncode(bytes)) {
					// 优化转码，需要转码
	
					return new String(bytes,charSet);
				} else {
					// 优化转码，不需要转码
	
					return sourceStr;
				}
			} catch (Exception e) {
				throw new SQLException(e.getMessage());
			}		
		}
	}
	
	/**
	 * 转码转入(目标编码 => 默认编码 )，主要用于从数据库中读取数据后转换
	 * @param sourceStr 源字符串
	 * @param charSet 字符集
	 * @param type 转换类型 0 不转换，1 按字符集转换，2 分析转换
	 * @return 转换后的字符串
	 */
	public static String decodeString(String sourceStr,String charSet,int type) 
	throws SQLException {
		if (type == 0 || sourceStr == null) {
			return sourceStr;
		} else {
			try {
				byte[] bytes = sourceStr.getBytes(charSet);
				if (bytes.length <= 0) {
					return sourceStr;
				}
				if (type == 1) {
					return new String(bytes); 
					
				} else if (isNeedEncode(bytes)) {
					// 优化转码，需要转码
					return new String(bytes);
					
				} else {
					// 优化转码，不需要转码
					return sourceStr;
				}
			} catch (Exception e) {
				throw new SQLException(e.getMessage());
			}
		}
	}
	
	/**
	 * 转码转出(默认编码 => 目标编码)，主要用于写数据库时转码
	 * @param sourceStr 源字符串
	 * @param charSet 字符集
	 * @param type 转换类型 0 不转换，1 按字符集转换，2 分析转换
	 * @return 转换后的字符串
	 */
	public static Object encodeObject(Object obj,String charSet,int type) 
	throws SQLException {
		if (obj instanceof String) {
			return Util.encodeString((String)obj, charSet, type);
		} else {
			return obj;
		}
	}
	
	/**
	 * 转码转入(目标编码 => 默认编码 )，主要用于从数据库中读取数据后转换
	 * @param sourceStr 源字符串
	 * @param charSet 字符集
	 * @param type 转换类型 0 不转换，1 按字符集转换，2 分析转换
	 * @return 转换后的字符串
	 */
	public static Object decodeObject(Object obj,String charSet,int type) 
	throws SQLException {
		if (obj instanceof String) {
			return Util.decodeString((String)obj, charSet, type);
		} else {
			return obj;
		}
	}
	
	/**
	 * 判断是否需要转码
	 * @param bytes 相应编码的字节数组
	 * @return 是否需要转码
	 */
	private static boolean isNeedEncode (byte[] bytes) {
		if (bytes == null || bytes.length <= 0) {
			return false;
		}
		
		for (int i = 0; i<bytes.length; i++ ) {
			  if (bytes[i] < 0) {
				  return true;
			  }
		}
		
		return false;
	}
}
