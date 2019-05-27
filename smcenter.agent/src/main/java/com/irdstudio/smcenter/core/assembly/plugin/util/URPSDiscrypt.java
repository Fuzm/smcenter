package com.irdstudio.smcenter.core.assembly.plugin.util;
/**
 * 供Shell调用的解密类
 * @author guangming.li
 * @version 1.0
 * @date 2013-11-04
 */
public class URPSDiscrypt {
	
	/**
	 * 传入要解密的字符串
	 * @param args
	 */
	public static void main(String[] args) {
		//testCrypt();
		if ((args != null) && (args.length == 1)) {
			String password = URPSCryptUtil.toDecryptWithNoException(args[0]);
			System.out.println(password);
		}
		System.exit(0);
	}
	
	/**
	 * 测试加密与解密
	 */
	public static void testCrypt() {
		System.out.println(URPSCryptUtil.toEncrypt("A5N2xeNG"));
		System.out.println(URPSCryptUtil
				.toDecryptWithNoException("B3F81D6499E0C0580AB160AB5106E6CF"));
		System.out.println(URPSCryptUtil.toEncrypt("abcdefg123"));
		System.out.println(URPSCryptUtil.toDecryptWithNoException("ss"));
	}
}
