package com.irdstudio.smcenter.core.assembly.socket.core;

import org.apache.log4j.Logger;

import com.irdstudio.smcenter.core.assembly.socket.element.Business;
import com.irdstudio.smcenter.core.util.vo.UniKeyValueObject;

/**
 * (基础类)可做所有中间业务类的顶级父类,使用Template模式实现
 * @author 李广明
 * @version 1.0
 * @date 2008-07-07
 */
public abstract class AbstractBusinessBus implements IBusinessBus{
	
	protected Business busi = null;
	protected Logger logger  =  null;
	protected UniKeyValueObject conf = null;
	protected String KEY_FIELD = "JYM";
	
	/**
	 * 默认构造函数,需要接收中间业务类的基础信息
	 * @param busi
	 */
	public void doInitBus(Business busi){
		this.busi = busi;
		this.logger = Logger.getLogger("[交易接口]:[" + busi.getDesc() + "]");
		this.conf = new UniKeyValueObject();
		this.conf = busi.getEnv();
	}
	
	/**
	 * 获取当前中间业务类的配置参数
	 */
	public UniKeyValueObject getBusiBusConf(){
		return conf;
	}

	/**
	 * 执行方法(由子类实现)
	 * @param inUvo
	 * @return
	 */
	public abstract UniKeyValueObject doExecute(UniKeyValueObject inUvo) throws Exception;

}