package com.irdstudio.smcenter.core.util.pub;
/**
 * 获得Jvm的一些相关信息
 * @author guangming.li
 * @version 1.0
 * @date 2013-11-11
 */
public class JvmUtil {

	/**
	 * 返回Jvm的内存信息,以M为单位 
	 * @return
	 */
	public static String getJvmMemoryWithM() {
		StringBuffer memInfo = new StringBuffer();
		memInfo.append("Jvm最大内存：")
				.append(Runtime.getRuntime().maxMemory() / 1024 / 1024)
				.append("M");
		memInfo.append(",Jvm已用内存：")
				.append(Runtime.getRuntime().totalMemory() / 1024 / 1024)
				.append("M");
		memInfo.append(",Jvm空闲内存：")
				.append(Runtime.getRuntime().freeMemory() / 1024 / 1024)
				.append("M");
		return memInfo.toString();
	}
}
