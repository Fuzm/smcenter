package com.irdstudio.ssm.framework.util;

import java.io.File;
import java.net.URL;

/**
 * 路径辅助类
 * 获取各种形式的路径
 * @author guangming.li
 * @version 1.0
 * @date 2013-11-04
 */
public final class PathUtil {

	/**
	 * 获取当前的工作目录(不一定是当前类所在的目录)
	 * @return
	 */
	public final static String getWorkDir(){
		return System.getProperty("user.dir");
	}
	
	/**
	 * 获取当前Java程序所在的目录的绝对路径
	 * 即类所在根目录
	 * 如果是在WEB工程,则通常是classes目录
	 * 如果是应用工程,则通常是BIN目录
	 * @return
	 */
	public final static String getClassRootPath() {
		String path = PathUtil.class.getResource("/").getPath();
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.lastIndexOf("windows") != -1) {
			// windows操作系统如果路径第一个字符为/,则去掉
			if (path.startsWith("/")) {
				return path.substring(1);
			}
		}
		return path;
	}
	
	/**
	 * 获取指定类所在的绝对路径
	 * @param srcClass
	 * @return
	 */
	public final static String getClassCurrentPath(Class<?> srcClass){
		return srcClass.getResource("").getPath();
	}

	/**
	 * 获取当前Java程序所在的目录的绝对路径
	 * 同上,但表示形式是URL形式,即以file:开头
	 * 通过URL.getPath()获得的目录与getClassRootPath一样
	 * @return
	 */
	public final static URL getClassRootURL() {
		return Thread.currentThread().getContextClassLoader().getResource("");
	}
	
	/**
	 * 获取工程根目录的绝对路径
	 * @return
	 */
	public final static String getProjectRootPath() {
		String path = PathUtil.class.getProtectionDomain().getCodeSource()
				.toString();
		if (path.indexOf("WEB-INF") >= 0) {
			path = path.substring(0, path.lastIndexOf("WEB-INF"));

		} else if (path.indexOf("bin") >= 0) {
			path = path.substring(0, path.lastIndexOf("bin"));
		}
		// LINUX/AIX获取路径特殊，需要“/”开头
		if (path.indexOf("file:/") >= 0) {
			path = path.substring(path.indexOf("file:/") + 5);
		}
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.lastIndexOf("windows") != -1) {
			// windows操作系统如果路径第一个字符为/,则去掉
			if (path.startsWith("/")) {
				return path.substring(1);
			}
		}
		return path;
	}
	
	/**
	 * 判断是否为绝对路径
	 * @param path
	 * @return
	 */
	public final static boolean isAbsolutePath(String path) {
		if (path == null || "".equals(path))
			return false;
		if (path.startsWith("/") || path.startsWith(":", 1)
				|| path.startsWith("file:/")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 格式化路径(如果是相对路径，则转换为绝对路径
	 * 如果是绝对路径,则直接返回)
	 * @param string
	 * @return
	 */	
	public final static String getFormatPath(String path) {
		if (PathUtil.isAbsolutePath(path)) {
			return path;
		} else {
			return PathUtil.getProjectRootPath() + path;
		}
	}

	/**
	 * 检查并创建目录(如果目录不存在的话则创建)
	 * 逐级检查，逐级创建
	 * @param batchSrvLogsPath
	 */
	public final static void checkAndCreate(String filePath) {
		filePath = filePath.replace('\\', '/');
		if (filePath.startsWith("/"))
			filePath = filePath.substring(1);
		String dirs[] = filePath.split("/");		
		String curPath = "";
		for (int i = 0; i < dirs.length; i++) {
			curPath += dirs[i] + "/";
			File file = new File(curPath.substring(0, curPath.length() - 1));
			if (!file.exists()) {
				file.mkdir();
			}
		}
	}
}
