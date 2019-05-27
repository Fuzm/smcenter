package com.irdstudio.smcenter.core.util.url;
/**
 * URL辅助类
 * @author 李广民
 * @version 1.0
 * @date 2008-09-24
 */
public class UrlUtil {
	
	/**
	 * 根据URL分析得出其所在的IP地址
	 * @param url
	 * @return
	 */
	public static String getIp(String url){
		String tmp = url.substring(url.indexOf("//") + 2);
		int end = tmp.indexOf(":");
		if(end == -1){
			end = tmp.indexOf("/");
			if(end != -1){
				tmp = tmp.substring(0,end); 
			}
		} else {
			tmp = tmp.substring(0,end);
		}
		return tmp;
	}
	
	public static void main(String[] args){
		System.out.println(UrlUtil
				.getIp("http://168.168.241.150:8080/plats/login.jsp"));
	}

}
