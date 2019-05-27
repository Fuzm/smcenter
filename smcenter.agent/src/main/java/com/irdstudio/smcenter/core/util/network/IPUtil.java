package com.irdstudio.smcenter.core.util.network;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.irdstudio.smcenter.core.tinycore.log.TLogger;

/**
 * IP地址辅助类
 * @author guangming.li
 * @version 1.0
 * @date 2014-07-24
 */
public class IPUtil {
	
	/* 日志分类  */
	private static String logCataName = "IPUtil";
	
	/**
	 * 获取所有的本机IP地址
	 * @return
	 * @throws Exception
	 */
	public static List<String> getAllLocalIP() {
		List<String> ipList = new ArrayList<String>();
		Enumeration<?> netInterfaces = null;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			e.printStackTrace();
			return ipList;
		}
		while (netInterfaces.hasMoreElements()) {

			NetworkInterface ni = (NetworkInterface) netInterfaces
					.nextElement();
			TLogger.getLogger(logCataName).debug(
					"NetworkInterface:" + ni.getName());

			// 取网络接口下(所有IP地址)
			Enumeration<?> addresses = ni.getInetAddresses();
			while (addresses.hasMoreElements()) {
				InetAddress ip = (InetAddress) addresses.nextElement();
				if (ip != null && ip.isSiteLocalAddress()
						&& !ip.isLoopbackAddress()
						&& ip instanceof Inet4Address) {
					TLogger.getLogger(logCataName).info(
							"Local IP Address:" + ip.getHostAddress());
					ipList.add(ip.getHostAddress());
				}
			} 
		}
		return ipList;
	}
	
	/**
	 * 判断是否本机IP
	 * @param ip
	 * @return
	 */
	public static boolean isLocalHostIP(String ip) {
		if (ip == null || "".equals(ip))
			return false;
		List<String> ipList = IPUtil.getAllLocalIP();
		for (int i = 0; i < ipList.size(); i++) {
			if (ipList.get(i) != null && ipList.get(i).equals(ip.trim())) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args){
		try {
			IPUtil.getAllLocalIP();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}