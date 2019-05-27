package com.irdstudio.smcenter.core.assembly.socket.core.file;

import com.irdstudio.smcenter.core.util.vo.UniKeyValueObject;

/**
 * 文件传送接口类
 * @author 李广明
 * @version 1.0
 * @date 2008-06-21
 */
public interface IFileSend {
	
	//文件传送错误信息定义
	public final static String CONNECT_FAILD = "发送文件时,连接接收者失败";
	public final static String ACCEPT_RETURN_FAILD = "接收返回信息失败";
	public final static String SUCCESSFAL	= "发送成功";
	public final static String SEND_FAILD	= "发送失败";

	/**
	 * 文件发送主方法(接口)
	 * @param outUvo
	 * @param fileFullName
	 * @return
	 */
	public String toSend(UniKeyValueObject outUvo,String fileFullName);
}
