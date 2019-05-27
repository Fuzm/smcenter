package com.irdstudio.smcenter.core.tinycore.log;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
/**
 * Top Logger
 * 1.管理所有的日志记录者与分类的关系
 * 2.提供直接的日志访问方法(自动寻找相应的日志记录者)
 * 3.可按分类设置不同的日志记录者
 * 4.如果没有设置分类日志记录者,默认使用Log4j记录日志
 * @author guangming.li
 * @version 1.0
 * @date 2014-04-27
 */
public class TLogger {
	
	/* 日志记录者管理 (Java 5中支持高并发高吞吐量的线程安全的HashMap实现) */
	private static ConcurrentMap<String, ILogger> loggerFactory = new ConcurrentHashMap<String, ILogger>();
	
	/**
	 * 获得日志记录者(根据分类名称/模块名称)
	 * @param catagoryName
	 * @return
	 */
	public static ILogger getLogger(String categoryName) {
		// 如果未找到对应的日志记录者,则返回默认的
		if (loggerFactory.get(categoryName) == null) {
			ILogger defaultLogger = LoggerFactory.makeLog4jLogger();
			defaultLogger.setName(categoryName);
			registerCategoryLogger(categoryName, defaultLogger);
			return defaultLogger;
		} else {
			ILogger theLogger = loggerFactory.get(categoryName);
			return theLogger;
		}
	}
	
	/**
	 * 为指定的日志分类设置对应的日志记录者
	 * @param categoryName
	 * @param ILogger
	 */
	public static void registerCategoryLogger(String categoryName,
			ILogger ILogger) {
		loggerFactory.put(categoryName, ILogger);
	}
	
	/**
	 * 记录一条日志讯息。
	 * @param categoryName 分类名称 
	 * @param type 类型
	 * @param level 等级
	 * @param message 文本描述信息
	 */
	public static void log(String categoryName, int type, int level,
			String message) {
		log(categoryName, type, level, message, null);
	}	

	/**
	 * 记录一条带异常的日志讯息。
	 * @param categoryName 分类名称
	 * @param type 类型
	 * @param level 等级
	 * @param message 文本描述信息
	 * @param te 可抛出的异常对象
	 */
	public static void log(String categoryName, int type, int level,
			String message, Throwable te) {
		// 取得对应的日志记录者
		ILogger logger = getLogger(categoryName);
		switch (type) {
			case TLogger.DEBUG:
				logger.debug(message, te);
				break;
			case TLogger.TRACE:
				logger.debug(message, te);
				break;
			case TLogger.INFO:
				logger.info(message, te);
				break;
			case TLogger.WARNING:
				logger.warn(message, te);
				break;
			case TLogger.ERROR:
				logger.error(message, te);
				break;
		}
	}
	
	/* 参数type的取值之一，代表日志类别为DEBUG */
	public final static int DEBUG = 0;
	/* 参数type的取值之一，代表日志类别为TRACE */
	public final static int TRACE = 1;
	/* 参数type的取值之一，代表日志类别为INFO */
	public final static int INFO = 2;
	/* 参数type的取值之一，代表日志类别为WARNING */
	public final static int WARNING = 3;
	/* 参数type的取值之一，代表日志类别为ERROR */
	public final static int ERROR = 4;
	/* 参数type的取值之一，代表日志类别为FATAL（等价于ERROR） */
	public final static int FATAL = 4;
	/* 参数type的对应字符串表现 */
	public static String[] typeInfo = new String[] { "DEBUG", "TRACE", "INFO",
			"WARNING", "ERROR" };
	
}
