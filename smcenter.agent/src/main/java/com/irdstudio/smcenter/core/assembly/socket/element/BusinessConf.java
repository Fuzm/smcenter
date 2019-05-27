package com.irdstudio.smcenter.core.assembly.socket.element;

import java.util.Hashtable;
import java.util.Map;

/**
 * 交易配置信息类,所有的交易配置信息均存放在此
 * @author 李广明
 * @version 1.0
 * @date 2008-05-08
 *
 */
public class BusinessConf {
	
	public static boolean isHandLoad  = false;		//用于在不使用配置文件,自行装载配置的情况下使用
	
	public static String coreServerIp = "";			//用于保存核心系统交易接口服务程序的IP
	public static String coreServerPort = "";		//用于保存核心系统交易接口服务程序的端口

	public static String creditServerIp = "";		//用于保存信贷系统交易接口服务程序的IP
	public static String creditServerPort = "";		//用于保存信贷系统交易接口服务程序的端口
	
	public static String keyValueSplit = String.valueOf((char)32);	//键值分隔符,默认值为空格
	public static String memberSplit   = String.valueOf((char)10);	//成员分隔符,默认值回车符
	
	//存放所有交易的交易码及交易类型信息
	public static Map busiTypeMap = new Hashtable();
	
	//存放交易的执行配置信息
	public static Map busiMap = new Hashtable();
	
	//存放原子交易的配置信息
	public static Map atomMap = new Hashtable();	
	
}
