package com.irdstudio.smcenter.core.assembly.socket.core;

import com.irdstudio.smcenter.core.assembly.socket.element.Business;
import com.irdstudio.smcenter.core.util.vo.UniKeyValueObject;

/**
 * 所有中间业务类的接口类
 * @author 李广明
 * @version 1.0
 * @date 2008-07-07
 */
public interface IBusinessBus {
	
	/**
	 * 用于初始化中间业务类
	 * @param busi
	 */
	public void doInitBus(Business busi);
	
	/**
	 * 获取当前中间业务类的配置参数
	 * @return
	 */
	public UniKeyValueObject getBusiBusConf();
	
	/**
	 * 执行方法
	 * @param inUvo
	 * @return
	 */
	public UniKeyValueObject doExecute(UniKeyValueObject inUvo) throws Exception;

}
