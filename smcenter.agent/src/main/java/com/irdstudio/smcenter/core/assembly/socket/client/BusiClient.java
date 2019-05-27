package com.irdstudio.smcenter.core.assembly.socket.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.irdstudio.smcenter.core.assembly.socket.util.PackUtil;
import com.irdstudio.smcenter.core.util.vo.UniKeyValueObject;
/**
 * 发送信贷交易的处理客户端程序
 * @author 李广明
 * @version 1.0
 * @date 2008-05-15
 *
 */
public class BusiClient {

	private String ip = "";
	private int port;
	
	public BusiClient(String ip,int port){
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
	
	/**
	 * 客户端
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws Exception {
		//PoolService.start();
		//test710002();
		//test730002();
		//test710007();
		//test730013();
		//test730071();
		//test730103();
		//test730102();
		//test730073();
		//test730074();
		test710005();
	}
	
	private static void test710102() {
		System.out.println("开始测试交易[710002]贴现出帐授权查询");
		UniKeyValueObject uvo = new UniKeyValueObject();
		uvo.addVariable("JYM", "710102");
		uvo.addVariable("jyje", "1000000");
		uvo.addVariable("jyrq", "2008-09-10");
		uvo.addVariable("jjbh", "HT01001408000004001");
	
		//localhost
		BusiClient client = new BusiClient("localhost",12008);
		try {
			UniKeyValueObject backUvo = client.doExecuteBusi(uvo);
			if("00".equals(backUvo.getValue("ydm"))){
				System.err.println("outUVO====="+PackUtil.parsePack(backUvo));
				System.out.println("测试交易[710002]贴现出帐授权查询通过!");
			}else{
				System.err.println("测试交易[710002]贴现出帐授权查询不通过!");
				System.err.println("outUVO====="+PackUtil.parsePack(backUvo));
			}	
		} catch (Exception e) {
			System.err.println("测试交易[710002]贴现出帐授权查询不通过!");
			e.printStackTrace();
		}
		
	}
	
	private static void test730002() {
		System.out.println("开始测试交易[730002]核心系统贴现出帐通知");
		UniKeyValueObject uvo = new UniKeyValueObject();
		uvo.addVariable("JYM", "730002");
		uvo.addVariable("JGM", "9350501001");
		uvo.addVariable("JYRQ", "2008-09-10");
		uvo.addVariable("JJBH", "HT01001408000004001");
		uvo.addVariable("PJHM", "HT01001408000004001");
		//localhost
		BusiClient client = new BusiClient("localhost",12008);
		try {
			UniKeyValueObject backUvo = client.doExecuteBusi(uvo);
			if("00".equals(backUvo.getValue("ydm"))){
				System.err.println("outUVO====="+PackUtil.parsePack(backUvo));
				System.out.println("测试交易[730002]核心系统贴现出帐通知通过!");
			}else{
				System.err.println("测试交易[730002]核心系统贴现出帐通知不通过!");
				System.err.println("outUVO====="+PackUtil.parsePack(backUvo));
			}	
		} catch (Exception e) {
			System.err.println("测试交易[730002]核心系统贴现出帐通知不通过!");
			e.printStackTrace();
		}
		
	}
	
	private static void test730013() {
		System.out.println("开始测试交易[730013]核心系统保证金入帐通知");
		UniKeyValueObject uvo = new UniKeyValueObject();
		uvo.addVariable("JYM", "730013");
		uvo.addVariable("jyje", "100000");
		uvo.addVariable("jyrq", "2008-08-08");
		uvo.addVariable("jjbh", "HT01001308000001000");
	
		//localhost
		BusiClient client = new BusiClient("localhost",12008);
		try {
			UniKeyValueObject backUvo = client.doExecuteBusi(uvo);
			if("00".equals(backUvo.getValue("ydm"))){
				System.out.println("测试交易[730013]核心系统保证金入帐通知通过!");
			}else{
				System.err.println("测试交易[730013]核心系统保证金入帐通知不通过!");
				System.err.println("outUVO====="+PackUtil.parsePack(backUvo));
			}	
		} catch (Exception e) {
			System.err.println("测试交易[730013]核心系统保证金入帐通知不通过!");
			e.printStackTrace();
		}
		
	}

	/**银承出帐银票出票授权查询*/
	private static void test710007(){
		System.out.println("开始测试交易[710007]银承出帐银票出票授权查询");
		UniKeyValueObject uvo = new UniKeyValueObject();
		uvo.addVariable("JYM", "710007");
		uvo.addVariable("jgm", "9999990116");
		uvo.addVariable("jyrq", "2008-06-12");
		uvo.addVariable("jjbh", "HT7583108000099002");
		BusiClient client = new BusiClient("localhost",12008);
		try {
			UniKeyValueObject backUvo = client.doExecuteBusi(uvo);
			if("00".equals(backUvo.getValue("ydm"))){
				System.out.println("测试交易[710007]银承出帐银票出票授权查询通过!");
			}else{
				System.err.println("测试交易[710007]银承出帐银票出票授权查询不通过!");
				System.err.println("outUVO====="+PackUtil.parsePack(backUvo));
			}	
		} catch (Exception e) {
			System.err.println("测试交易[710007]银承出帐银票出票授权查询不通过!");
			e.printStackTrace();
		}
	}
	
	/**核心系统银票退票成功通知*/
	private static void test730071(){
		System.out.println("开始测试交易[730071]核心系统银票退票成功通知");
		UniKeyValueObject uvo = new UniKeyValueObject();
		uvo.addVariable("JYM", "730071");
		uvo.addVariable("jgm", "9999990116");
		uvo.addVariable("jyrq", "2008-08-21");
		uvo.addVariable("jjbh", "HT01001308000007001");
		BusiClient client = new BusiClient("localhost",12008);
		try {
			UniKeyValueObject backUvo = client.doExecuteBusi(uvo);
			if("00".equals(backUvo.getValue("ydm"))){
				System.out.println("测试交易[730071]核心系统银票退票成功通知通过!");
			}else{
				System.err.println("测试交易[730071]核心系统银票退票成功通知不通过!");
				System.err.println("outUVO====="+PackUtil.parsePack(backUvo));
			}	
		} catch (Exception e) {
			System.err.println("测试交易[730071]核心系统银票退票成功通知不通过!");
			e.printStackTrace();
		}
	}
	
	/**核心系统追加保证金通知*/
	private static void test730103(){
		System.out.println("开始测试交易[730103]核心系统追加保证金通知");
		UniKeyValueObject uvo = new UniKeyValueObject();
		uvo.addVariable("JYM", "730103");
		uvo.addVariable("jgm", "9999990116");
		uvo.addVariable("jyrq", "2008-08-21");
		uvo.addVariable("ZJJE", "1000");
		uvo.addVariable("jjbh", "HT01001308000007");
		BusiClient client = new BusiClient("localhost",12008);
		try {
			UniKeyValueObject backUvo = client.doExecuteBusi(uvo);
			if("00".equals(backUvo.getValue("ydm"))){
				System.out.println("测试交易[730103]核心系统追加保证金通知通过!");
			}else{
				System.err.println("测试交易[730103]核心系统追加保证金通知不通过!");
				System.err.println("outUVO====="+PackUtil.parsePack(backUvo));
			}	
		} catch (Exception e) {
			System.err.println("测试交易[730103]核心系统追加保证金通知不通过!");
			e.printStackTrace();
		}
	}
	
	/**核心系统部提保证金通知*/
	private static void test730102(){
		System.out.println("开始测试交易[730102]核心系统部提保证金通知");
		UniKeyValueObject uvo = new UniKeyValueObject();
		uvo.addVariable("JYM", "730102");
		uvo.addVariable("jgm", "9999990116");
		uvo.addVariable("jyrq", "2008-08-21");
		uvo.addVariable("jjbh", "HT0100130800007001");
		uvo.addVariable("bzjje", "10000");
		BusiClient client = new BusiClient("localhost",12008);
		try {
			UniKeyValueObject backUvo = client.doExecuteBusi(uvo);
			if("00".equals(backUvo.getValue("ydm"))){
				System.out.println("测试交易[730102]核心系统部提保证金通知通过!");
			}else{
				System.err.println("测试交易[730102]核心系统部提保证金通知不通过!");
				System.err.println("outUVO====="+PackUtil.parsePack(backUvo));
			}	
		} catch (Exception e) {
			System.err.println("测试交易[730102]核心系统部提保证金通知不通过!");
			e.printStackTrace();
		}
	}
	
	/**银承到期扣款通知*/
	private static void test730073(){
		System.out.println("开始测试交易[730073]银承到期扣款通知");
		UniKeyValueObject uvo = new UniKeyValueObject();
		uvo.addVariable("JYM", "730073");
		uvo.addVariable("jgm", "9999990116");
		uvo.addVariable("jyrq", "2008-08-21");
		uvo.addVariable("jjbh", "HT0100130800007001");
		uvo.addVariable("bzjje", "10000");
		uvo.addVariable("KKJE", "2000");
		uvo.addVariable("ZDKJE", "2000");
		BusiClient client = new BusiClient("localhost",12008);
		try {
			UniKeyValueObject backUvo = client.doExecuteBusi(uvo);
			if("00".equals(backUvo.getValue("ydm"))){
				System.out.println("测试交易[730073]银承到期扣款通知通过!");
			}else{
				System.err.println("测试交易[730073]银承到期扣款通知不通过!");
				System.err.println("outUVO====="+PackUtil.parsePack(backUvo));
			}	
		} catch (Exception e) {
			System.err.println("测试交易[730073]银承到期扣款通知不通过!");
			e.printStackTrace();
		}
	}
	
	/**内部转贴现授权查询
JGM 9999990116
JYRQ 20080920
JJBH HT0100170800004
	 * */
	private static void test710005(){
		System.out.println("开始测试交易[710005]内部转贴现授权查询");
		UniKeyValueObject uvo = new UniKeyValueObject();
		uvo.addVariable("JYM", "710005");
		uvo.addVariable("jgm", "9999990116");
		uvo.addVariable("jyrq", "2008-09-20");
		uvo.addVariable("jjbh", "HT0100170800004");
		BusiClient client = new BusiClient("localhost",12008);
		try {
			UniKeyValueObject backUvo = client.doExecuteBusi(uvo);
			if("00".equals(backUvo.getValue("ydm"))){
				System.out.println("测试交易[710005]内部转贴现授权查询通过!");
			}else{
				System.err.println("测试交易[710005]内部转贴现授权查询不通过!");
				System.err.println("outUVO====="+PackUtil.parsePack(backUvo));
			}	
		} catch (Exception e) {
			System.err.println("测试交易[710005]内部转贴现授权查询不通过!");
			e.printStackTrace();
		}
	}
	
	/** 核心系统银票到期解付核销通知*/
	private static void test730074(){
		System.out.println("开始测试交易[730074] 核心系统银票到期解付核销通知");
		UniKeyValueObject uvo = new UniKeyValueObject();
		uvo.addVariable("JYM", "730074");
		uvo.addVariable("jgm", "9999990116");
		uvo.addVariable("jyrq", "2008-08-21");
		uvo.addVariable("jjbh", "HT0100130800007001");//银票借据编码
		uvo.addVariable("hphm", "GA000000000");//汇票号码
		uvo.addVariable("hszt", "1");//回收状态
		uvo.addVariable("flag", "1");//处理状态

		BusiClient client = new BusiClient("localhost",12008);
		try {
			UniKeyValueObject backUvo = client.doExecuteBusi(uvo);
			if("00".equals(backUvo.getValue("ydm"))){
				System.out.println("测试交易[730074] 核心系统银票到期解付核销通知通过!");
			}else{
				System.err.println("测试交易[730074] 核心系统银票到期解付核销通知不通过!");
				System.err.println("outUVO====="+PackUtil.parsePack(backUvo));
			}
		} catch (Exception e) {
			System.err.println("测试交易[730074] 核心系统银票到期解付核销通知不通过!");
			e.printStackTrace();
		}
	}
}
