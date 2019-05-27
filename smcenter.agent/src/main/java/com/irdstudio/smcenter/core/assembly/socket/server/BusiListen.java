package com.irdstudio.smcenter.core.assembly.socket.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.apache.log4j.Logger;

import com.irdstudio.smcenter.core.assembly.socket.log.BusiLogService;
import com.irdstudio.smcenter.core.assembly.socket.util.XmlLoadUtil;

/**
 * 监听交易请求(主服务类)
 * @author 李广明
 * @version 1.0
 * @date 2008-05-05
 *
 */
public class BusiListen {
	
	//Socket连接信息
	private ServerSocket server = null ;
	String serviceName;
	Socket client = null;
	private int serverPort = 12007;				//默认端口
	private String xmlCfgFile = "busi.cfg.xml";	//默认配置文件
	
	//日志管理
	Logger logger  =  Logger.getLogger("[交易接口]");
	
	//单例模式
	private static BusiListen instance = null;
	
	private BusiListen(int port, String xmlCfgFile) {
		this.serverPort = port;
		this.xmlCfgFile = xmlCfgFile;
	}
	
	public static BusiListen getInstance(int port, String xmlCfgFile) {
		if (instance == null) {
			instance = new BusiListen(port, xmlCfgFile);
		}
		return instance;
	}
	
	public void start() {
		
		if(!XmlLoadUtil.loadFromXml(xmlCfgFile)){
			logger.debug("[启动管理]:装载配置失败");
			System.exit(0);
		}
		
		logger.info("[启动管理]:开始启动监听程序...");
		while (server == null) {
			try {
				server = new ServerSocket(serverPort);
				if (server != null)
					server.setSoTimeout(600000);
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("[启动管理]:在" + serverPort + "端口上启动监听失败");
				try {
					Thread.sleep(1000);
					System.exit(-1);
				} catch (Exception ex1) {
				}
			}
		}
		logger.info("[启动管理]:启动成功,端口:" + serverPort);
		
	    while (true) {
	    	logger.info("[连接管理]:等待客户端连接...");
	    	StringBuffer sb = new StringBuffer();
	    	sb.append("[连接管理]:");
			try {
				client = server.accept();
				sb.append("建立连接, 客户端:");
				sb.append(client.getInetAddress());
				sb.append(" 端口:").append(client.getPort());
				logger.info(sb);
				Listener listener = new Listener(client);
				listener.start();
			} catch (SocketTimeoutException e) {
				logger.debug("[连接管理]:等待连接超时,重新等待...");
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("[连接管理]:连接错误...");
				try {
					Thread.sleep(1000);
				} catch (Exception ex1) {
				}
			}
		}
	}
	
	/**
	 * 标准启动方式
	 * @param port
	 * @param xmlCfgFile
	 */
	public static void normalStart(int port,String xmlCfgFile){
		BusiListen server = new BusiListen(port,xmlCfgFile);
		server.start();
	}
	
	/**
	 * 启动函数,交易接口监听程序的总入口
	 * @param args
	 */
	public static void main(String[] args){
		
		//启动连接池
		try {
			//PoolService.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//启动交易日志服务
		BusiLogService.startService();
		
		//从命令行取得配置文件及监听端口
		int port = 12009;
		//String xmlCfgFile = "middlebusi.cfg.xml";
		String xmlCfgFile = "portal.cfg.xml";
		
		if(args.length == 2){
			port = Integer.valueOf(args[1]).intValue();
			xmlCfgFile = args[0];
		}

		//启动交易服务
		BusiListen server = new BusiListen(port,xmlCfgFile);
		server.start();
	}
}
