package com.irdstudio.smcenter.core.tinycore.log;
/**
 * 日志记录者工厂，产生各种日志记录者
 * @author guangming.li
 * @version 1.0
 * @date 2014-04-17
 */
public class LoggerFactory {
	
	/**
	 * 产生文本文件日志记录者(自定义)
	 * @param fullLogFileName
	 * @param isStillOnConsole 是否还输出到控制台
	 * @return
	 */
	public static ILogger makeTxtFileLogger(String fullLogFileName,
			boolean isStillOnConsole) {
		return new TextFileLogger(fullLogFileName, isStillOnConsole);
	}
	
	/**
	 * 产生Log4j日志记录者(需要Log4j的jar包支持)
	 * @return
	 */
	public static ILogger makeLog4jLogger(){
		return new Log4jLogger();
	}

}
