package com.irdstudio.smcenter.core.assembly.plugin.datajob;

import com.irdstudio.smcenter.core.assembly.plugin.PluginContext;

/**
 * Java数据作业接口类
 * 所有实现Java作业的类须实现该接口
 * @author guangming.li
 * @version 1.0
 * @date 2014-05-04
 */
public interface IJavaJob {
	
	/**
	 * 执行作业
	 * @param context
	 * @return
	 */
	public boolean execute(PluginContext context) throws Exception;

}
