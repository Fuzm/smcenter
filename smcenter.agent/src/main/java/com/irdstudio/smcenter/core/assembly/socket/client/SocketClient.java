package com.irdstudio.smcenter.core.assembly.socket.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import com.irdstudio.smcenter.core.assembly.socket.util.PackUtil;
import com.irdstudio.smcenter.core.util.vo.UniKeyValueObject;
/**
 * Socket客户端
 * @author 李广民
 * @version 1.0
 * @date 2008-08-04
 */
public class SocketClient implements IBusiClient{
	private String ip = "";
	private int port;
	
	public SocketClient(String ip,int port){
		this.ip = ip;
		this.port = port;
	}
	
	public UniKeyValueObject doExecuteBusi(UniKeyValueObject inUvo)
			throws Exception {
		
		//将UVO拆成字符串进行发送
		String backMsg = null;
		String msg = PackUtil.parsePack(inUvo);
		
		//连接服务端进行发送
		Socket socket = null;
		OutputStream socketOut = null;
		PrintWriter pw = null;
		InputStream socketIn = null;
		BufferedReader br = null;
		try {
			socket = new Socket(ip, port);
			socketOut = socket.getOutputStream();
			pw = new PrintWriter(socketOut, true);
			socketIn = socket.getInputStream();
			br = new BufferedReader(new InputStreamReader(socketIn));
			pw.println(msg);

			//读取前4位数据,即数据包长度
			char[] ch = new char[4];
			int len = 0;
			if ((len = br.read(ch, 0, 4)) < 0) {
				throw new Exception("接收4位报文体长度数据时出错");
			}
			len=Integer.parseInt(String.valueOf(ch).trim());
			
			//接收指定长度的数据
			char[] ch2=new char[len];
			if(br.read(ch2, 0, len)<0){
				throw new Exception("接收指定长度报文体数据时出错");
			}			
			backMsg = String.valueOf(ch2);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (br != null)
					br.close();
				if (pw != null)
					pw.close();
				if (socketOut != null)
					socketOut.close();
				if (socketIn != null)
					socketIn.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//将返回信息组成UVO包返回给调用程序
		UniKeyValueObject backUvo = PackUtil.getPack(backMsg);
		return backUvo;
	}
}
