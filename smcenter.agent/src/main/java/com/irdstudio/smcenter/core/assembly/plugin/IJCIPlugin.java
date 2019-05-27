package com.irdstudio.smcenter.core.assembly.plugin;
/**
 * 应用插件接口定义
 * @author guangming.li
 * @version 1.1
 * @since 1.0 2013-09-30 
 * @modify 2014-05-06
 * 		精简应用插件接口中的方法
 */
public interface IJCIPlugin {
	
	/*执行结果：成功*/
	public final static String EXEC_RESULT_SUCCESS = "1";
	
	/*执行结果：失败*/
	public final static String EXEC_RESULT_FAILD = "2";	
	
	/**
	 * 设置插件上下文对象
	 * 包括插件基本信息、日志、连接、错误信息存储
	 * @param context
	 */
	public void setPluginContext(PluginContext context);
	
	/**
	 * 读取应用插件的配置数据(从数据库)
	 */
	public boolean readPluginConfigureFromDB(String szConfIdentify);
	
	/**
	 * 读取应用插件的配置数据(从配置文件)
	 * 配置文件可以是XML或属性文件等,由实现类判断
	 */
	public boolean readPluginConfigureFromFile(String szConfIdentify);

	/**
	 * 执行插件功能
	 * @return
	 */
	public boolean execute();
		
}
