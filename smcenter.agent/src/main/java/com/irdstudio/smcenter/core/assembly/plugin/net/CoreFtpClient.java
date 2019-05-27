package com.irdstudio.smcenter.core.assembly.plugin.net;
/**
 * 用于与FTP交互(上传或下载)
 * @author guangming.li
 * @version 1.0
 * @date 2013-11-12
 */
public class CoreFtpClient {
	
	/**
	 * 构造函数(IP与端口)
	 * @param ip
	 * @param port
	 */
	public CoreFtpClient(String ip,int port){
		
	}
	
	/**
	 * 根据用户名及密码连接FTP服务器
	 * @param user
	 * @param pwd
	 * @return
	 */
	public boolean open(String user,String pwd){
		return false;		
	}
	
	/**
	 * 设置上传或下载时的模式
	 * 文本或二进制
	 * @param mode
	 */
	public void setMode(int mode){
		
	}
	
	/**
	 * 上传本地文件到FTP服务器上
	 * @param localFileName
	 * @param remoteFileName
	 * @return
	 */
	public boolean upload(String localFileName,String remoteFileName){
		return false;
		
	}
	
	/**
	 * 下载FTP上的文件到本地
	 * @param remoteFileName
	 * @param localFileName
	 * @return
	 */
	public boolean download(String remoteFileName,String localFileName){
		return false;
		
	}
	
	/**
	 * 关闭与FTP服务器的连接
	 */
	public void close(){
		
	}

}
