package com.irdstudio.smcenter.core.assembly.plugin.util;

import java.security.InvalidKeyException;

import com.irdstudio.smcenter.core.util.crypt.CryptUtil;

/**
 * URPS统一报送平台加密解密辅助类
 * @author guangming.li
 * @version 1.0
 *
 */
public class URPSCryptUtil {
	
	/* 加密解密所需要的密钥 */
	private static String key = "sdbcrypt"; 
	
	/**
	 * 加密函数 
	 * @param str
	 * @return 加密成功返回加密后的字符串,失败返回原字符串
	 */
	public static String toEncrypt(String str) {
		try {
			return CryptUtil.bytes2Hexstr(CryptUtil.encryptDES(str.getBytes(),
					key.getBytes()));
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			return str;
		}
	}

	/**
	 * 解密指定字符串
	 * 解密过程有异常则向上抛出异常
	 * @param str
	 * @return
	 * @throws Exception 
	 */
	public static String toDecrypt(String str) throws Exception {
		try {
			byte[] res = CryptUtil.hexstr2Bytes(str);
			return new String(CryptUtil.decryptDES(res, key.getBytes()));
		} catch (Exception e) {
			throw new Exception("解密字符串\"" + str + "\"失败,原因：" + e.getMessage());
		}
	}
	
	/**
	 * 解密指定字符串
	 * 解密失败则原样返回,不返回异常
	 * @param str
	 * @return
	 */
	public static String toDecryptWithNoException(String str) {
		try {
			byte[] res = CryptUtil.hexstr2Bytes(str);
			return new String(CryptUtil.decryptDES(res, key.getBytes()));
		} catch (Exception e) {
			return str + " can't decrypt!";
		}
	}
}
