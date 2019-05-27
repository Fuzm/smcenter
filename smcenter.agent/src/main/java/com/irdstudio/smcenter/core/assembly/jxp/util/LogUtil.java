package com.irdstudio.smcenter.core.assembly.jxp.util;

import com.irdstudio.smcenter.core.tinycore.log.ILogger;
import com.irdstudio.smcenter.core.tinycore.log.TLogger;

/**
 * @docRoot:
 * 输出日志
 * @author 李广明
 * @version 1.0
 * @modify:
 * 		李广明,增加atLine方法
 * 		去掉以前的方法,改为使用Log4j
 */
public class LogUtil {

	/* 默认日志输出对象 */
	private static ILogger logger  =  TLogger.getLogger("[XML管理]:[数据生成]");
	
	/**
	 * 用于指定日志输出对象
	 * @param log
	 */
	public static void setLogger(ILogger log) {
		logger = log;
	}
	
	public static void debug(Object msg){
		logger.debug(msg);
	}
	
	public static void info(Object msg){
		logger.info(msg);
	}
	
	public static void warn(Object msg){
		logger.warn(msg);
	}
	
	public static void fatal(Object msg){
		logger.fatal(msg);
	}
	
	public static void out(String msg){
		logger.debug(msg);
	}
	
	public static void log(String msg){
		logger.debug(msg);
	}
	
	public static void writeError(Object msg){
		logger.debug(msg);
	}
	
	public static void atLine(String msg){
		logger.debug(msg);
	}
	
	public static void log(String msg,String charset){
		logger.debug(msg);
	}
}
