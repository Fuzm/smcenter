package com.irdstudio.smcenter.core.util.crypt;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * 用DES加密写二进制文件
 * @author guangming.li
 * @version 1.0
 * @date 2014-07-13
 */
public class DesBinaryFileReader {
	
	/* 二进制流对象 */
	private DataInputStream in = null;
	/* DES加密用的密钥 */
	private String key = "pwd1234";
	/* 加密前使用的字符串编码格式 */
	private String encoding = "utf-8";
	/* DES加密对象 */
	private Des des = null;
	
	/**
	 * 构造函数
	 * 以二进制流对象,DES加密key及字符加密前的编码格式来构造
	 * @param in 二进制流对象
	 * @param key DES加密用到的key
	 * @param encoding 加密前使用的字符串编码格式
	 */
	public DesBinaryFileReader(DataInputStream in, String key, String encoding) {
		this.in = in;
		this.key = key;
		this.encoding = encoding;
		this.des= new Des();
	}
	
	/**
	 * 构造函数
	 * 以二进制流对象,其余使用默认值
	 * @param in
	 */
	public DesBinaryFileReader(DataInputStream in) {
		this.in = in;
		this.des= new Des();
	}
	
	/**
	 * 获得解密后字符串
	 * 先读取字节数,后根据字节数读取相应的字节并解密
	 * 最后转换为指定字符编码后返回
	 * @throws IOException
	 */
	public String readDecryptString() throws IOException {
		int num = in.readInt();
		byte[] itemBuf = new byte[num];
		in.read(itemBuf, 0, num);
		byte[] decryptBuf = this.des.decrypt(this.key, itemBuf);
		return new String(decryptBuf, this.encoding);
	}
	
	/**
	 * 关闭二进制文件流
	 * @throws IOException
	 */
	public void close() throws IOException {
		if (in != null)
			in.close();
	}

}
