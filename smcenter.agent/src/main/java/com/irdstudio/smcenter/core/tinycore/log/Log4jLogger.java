package com.irdstudio.smcenter.core.tinycore.log;

import org.apache.log4j.Logger;

/**
 * 使用Log4j来记录日志 
 * @author guangming.li
 * @version 1.0
 * @date 2014-04-27
 */
public class Log4jLogger implements ILogger {
	
	/* 分类/模块名称 */
	private String categoryName = "";	
	/* Log4j的日志记录者 */
	private Logger logger = null;

	public void debug(Object message) {
		logger.debug(message);
	}

	public void debug(Object message, Throwable t) {
		logger.debug(message,t);
	}

	public void error(Object message) {
		logger.error(message);
	}

	public void error(Object message, Throwable t) {
		logger.error(message,t);
	}

	public void fatal(Object message) {
		logger.fatal(message);
	}

	public void fatal(Object message, Throwable t) {
		logger.fatal(message,t);
	}

	public void info(Object message) {
		logger.info(message);
	}

	public void info(Object message, Throwable t) {
		logger.info(message,t);
	}

	public void setName(String categoryName) {
		this.categoryName = categoryName;
		logger = Logger.getLogger(categoryName);
	}

	public void warn(Object message) {
		logger.warn(message);
	}

	public void warn(Object message, Throwable t) {
		logger.warn(message,t);

	}
	
	/**
	 * 产生子级的日志记录者
	 */
	public ILogger makeSubLogger(String subCategoryName) {
		ILogger subLogger = null;
		try {
			subLogger = (ILogger) this.clone();
			subLogger.setName(this.categoryName + "-" + subCategoryName);
			return subLogger;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return subLogger;
	}
}
