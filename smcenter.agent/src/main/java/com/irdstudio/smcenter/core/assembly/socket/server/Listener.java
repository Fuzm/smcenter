package com.irdstudio.smcenter.core.assembly.socket.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.irdstudio.smcenter.core.assembly.socket.core.BusiEntryBus;
import com.irdstudio.smcenter.core.assembly.socket.util.PackUtil;
import com.irdstudio.smcenter.core.util.vo.UniKeyValueObject;

/**
 * 用于处理交易的线程,请求到达后,由本线程进行全权处理
 * @author 李广明
 * @version 1.1
 * @date 2008-5-5
 *  定版日期:2008-11-27
 *  此类为整个交易服务的核心程序,到此版本时已经运行稳定
 *  请不要轻易修改此程序,切记
 */

public class Listener extends Thread{
	
   private Socket client;
   private String msg=null;
   private OutputStream socketOut = null;
   private InputStream socketIn = null;
   private BufferedReader br = null;
   private PrintWriter pw = null;
   
   private Logger logger  =  Logger.getLogger("[交易接口]");
   
   public Listener(Socket client){
      this.client = client;
   }
   
   public void run() {
		try {
			
			br = getReader(client);
			pw = getWriter(client);

			//读取前4位数据,即数据包长度
			char[] ch = new char[4];
			int len = 0;
			if ((len = br.read(ch, 0, 4)) < 0) {
				closeSocketStream();
				throw new Exception("接收4位报文体长度数据时出错");
			}
			len=Integer.parseInt(String.valueOf(ch).trim());
			
			//接收指定长度的数据
			char[] ch2=new char[len];
			if(br.read(ch2, 0, len)<0){
				closeSocketStream();
				throw new Exception("接收指定长度报文体数据时出错");
			}			
			msg = String.valueOf(ch2);
			logger.info("[交易管理]:接收到" + String.valueOf(ch) + msg);
			
			//拆包后交给相应的程序处理并组装进行应答的包
			UniKeyValueObject inUvo = PackUtil.getPack(msg);
			inUvo.addVariable("ClientIp", client.getInetAddress()
					.getHostAddress());
			UniKeyValueObject outUvo = BusiEntryBus.getInstance().doExecute(
					inUvo);
			String backStr = PackUtil.parsePack(outUvo);
			logger.info("[交易管理]:返回" + backStr);
			pw.println(backStr);
			pw.flush();
			closeSocketStream();
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e.getMessage());
		} finally{
			try {
				if (client != null){
					logger.info("[连接管理]:断开连接...");
					client.close(); // 断开连接
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
   }
   
   private PrintWriter getWriter(Socket socket) throws IOException {
	   socketOut = socket.getOutputStream();
	   return new PrintWriter(socketOut, true);
   }

   private BufferedReader getReader(Socket socket) throws IOException {
	   socketIn = socket.getInputStream();
	   return new BufferedReader(new InputStreamReader(socketIn));
   }
   
   /**
    * 关闭socket读取通道
    */
   private void closeSocketStream(){
	   try {
		   if (br != null)
			   br.close();
		   if (pw != null)
			   pw.close();
		   if (socketOut != null)
			   socketOut.close();
		   if (socketIn != null)
			   socketIn.close();
		} catch (IOException e) {			
			e.printStackTrace();
			logger.fatal(e.getMessage());
		}
	}
}
