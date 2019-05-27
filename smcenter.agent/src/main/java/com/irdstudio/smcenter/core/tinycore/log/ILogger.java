package com.irdstudio.smcenter.core.tinycore.log;

/**
 * 日志记录程序接口。继承该接口实现用户自定义的日志组件。
 * 
 * @author guangming.li
 * @version 1.0
 * @since 1.1 2014-04-27 
 */
public interface ILogger {

	/**
	 * 输出debug级别的日志
	 * @param message
	 */
	public void debug(Object message);
	
	/**
	 * 输出debug级别的日志
	 * @param message
	 * @param t
	 */
	public void debug(Object message, Throwable t);
	
	/**
	 * 输出error级别的日志
	 * @param message
	 */
	public void error(Object message);
	
	/**
	 * 输出error级别的日志
	 * @param message
	 */
	public void error(Object message, Throwable t);	
	
	/**
	 * 输出fatal级别的日志
	 * @param message
	 */	
	public void fatal(Object message);
	
	/**
	 * 输出fatal级别的日志
	 * @param message
	 */	
	public void fatal(Object message, Throwable t);
	
	/**
	 * 输出info级别的日志
	 * @param message
	 */
	public void info(Object message);
	
	/**
	 * 输出info级别的日志
	 * @param message
	 */
	public void info(Object message, Throwable t);
	
	/**
	 * 输出warn级别的日志
	 * @param message
	 */
	public void warn(Object message);

	/**
	 * 输出warn级别的日志
	 * @param message
	 */
	public void warn(Object message, Throwable t);

	/**
	 * 设置日志组件名称。
	 * @param name 日志分组名称
	 */
	public void setName(String categoryName);

	/**
	 * 基于本日志记录者产生一个子日志记录者
	 * @param subCategoryName
	 * @return
	 */
	public ILogger makeSubLogger(String subCategoryName);
}
