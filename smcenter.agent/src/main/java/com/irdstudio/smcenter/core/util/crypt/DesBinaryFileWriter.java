package com.irdstudio.smcenter.core.util.crypt;

import java.io.DataOutputStream;
import java.io.IOException;

import com.irdstudio.smcenter.core.util.crypt.Des;

/**
 * 用DES加密写二进制文件
 * @author guangming.li
 * @version 1.0
 * @date 2014-07-13
 */
public class DesBinaryFileWriter {
	
	/* 二进制流对象 */
	private DataOutputStream out = null;
	/* DES加密用的密钥 */
	private String key = "pwd1234";
	/* 加密前使用的字符串编码格式 */
	private String encoding = "utf-8";
	/* DES加密对象 */
	private Des des = null;
	
	/**
	 * 构造函数
	 * 以二进制流对象,DES加密key及字符加密前的编码格式来构造
	 * @param out 二进制流对象
	 * @param key DES加密用到的key
	 * @param encoding 加密前使用的字符串编码格式
	 */
	public DesBinaryFileWriter(DataOutputStream out, String key, String encoding) {
		this.out = out;
		this.key = key;
		this.encoding = encoding;
		this.des= new Des();
	}
	
	/**
	 * 构造函数
	 * 以二进制流对象,其余使用默认值
	 * @param out
	 */
	public DesBinaryFileWriter(DataOutputStream out) {
		this.out = out;
		this.des= new Des();
	}
	
	/**
	 * 将传入的字符串加密后写入文件中
	 * 先写入字符串转换后的字节长度(编码后)
	 * 最后写入字符串转后的字节(编码后)
	 * @param words
	 * @throws IOException
	 */
	public void writeEncryptString(String words) throws IOException {
		byte[] encryptBuf = this.des.encrypt(this.key, words
				.getBytes(this.encoding));
		out.writeInt(encryptBuf.length);
		out.write(encryptBuf);
	}
	
	/**
	 * 关闭二进制文件流
	 * @throws IOException
	 */
	public void close() throws IOException {
		if (out != null) {
			out.flush();
			out.close();
		}
	}
}
