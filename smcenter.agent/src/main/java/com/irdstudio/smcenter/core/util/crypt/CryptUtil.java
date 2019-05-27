/******加密解密类******/
package com.irdstudio.smcenter.core.util.crypt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 加密解密类
 * @author Crystal
 * 2009-04-08
 */
public class CryptUtil {
	
	/*
	 * DES加密
	 * plainText: 明文
	 * keycode: 密钥，长度为8Byte(64bit)
	 * return: 密文
	 */
	public static byte[] encryptDES(byte[] plainText, byte[] keycode)
		throws InvalidKeyException{
		try {
			Cipher cipher = Cipher.getInstance("DES");
			DESKeySpec keySpec = new DESKeySpec(keycode);
			SecretKeyFactory keyFac = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFac.generateSecret(keySpec);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return cipher.doFinal(plainText);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();			
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * DES解密
	 * cipherText: 密文
	 * keycode: 密钥
	 * return: 明文
	 */	
	public static byte[] decryptDES(byte[] cipherText, byte[] keycode){
		try {
			Cipher cipher = Cipher.getInstance("DES");
			DESKeySpec keySpec = new DESKeySpec(keycode);
			SecretKeyFactory keyFac = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFac.generateSecret(keySpec);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return cipher.doFinal(cipherText);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 将byte数组以HEX格式字符串返回
	 * src: 源byte数组
	 */
	public static String bytes2Hexstr(byte[] src){
		StringBuffer result = new StringBuffer();
		for(int i=0; i<src.length; i++){
			result.append(String.format("%02X", src[i]));
		}
		return result.toString();
	}
	
	/*
	 * 将HEX字符串转换成byte数组
	 * src: 源字符串
	 */
	public static byte[] hexstr2Bytes(String src){
		byte[] res = new byte[src.length()/2];
		for(int i=0; i<res.length; i++){
			res[i] = Integer.valueOf(src.substring(i*2, i*2+2), 16).byteValue();
		}
		return res;
	}
	
	public static void main(String[] argv) throws Exception{
		byte[] b = {0x00, (byte)0xff, 0x10, 127};
		System.out.println(bytes2Hexstr(b));
		byte[] res = hexstr2Bytes("B3F81D6499E0C0580AB160AB5106E6CF");
		byte[] str = decryptDES(res, "sdbcrypt".getBytes());
		System.out.println(new String(str));
		
		res = encryptDES("A5N2xeNG".getBytes(), "sdbcrypt".getBytes());
		String s = bytes2Hexstr(res);
		System.out.println(s);
		
		// Des演示
		Des  des = new Des();
		String key="testdes";
		String src = "Java中文测试a";
		System.out.println("Now encrypt the src:\n" + src );
		byte[] encryptBuf = des.encrypt(key, src.getBytes());
		System.out.println(encryptBuf);
		byte[] decryptBuf = des.decrypt(key, encryptBuf);
		System.out.println("Decrypt String:\n" + new String( decryptBuf ));		
	}
	
}
