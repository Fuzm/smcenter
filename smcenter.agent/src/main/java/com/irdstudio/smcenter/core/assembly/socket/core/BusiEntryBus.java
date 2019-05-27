package com.irdstudio.smcenter.core.assembly.socket.core;
import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;

import com.irdstudio.smcenter.core.assembly.socket.element.Business;
import com.irdstudio.smcenter.core.assembly.socket.element.BusinessConf;
import com.irdstudio.smcenter.core.assembly.socket.log.BusiLog;
import com.irdstudio.smcenter.core.assembly.socket.log.BusiLogService;
import com.irdstudio.smcenter.core.assembly.socket.util.PackUtil;
import com.irdstudio.smcenter.core.util.vo.UniKeyValueObject;
/**
 * 交易执行核心类,根据接收到的信息判断如何执行
 * 总入口总线类,将不同的交易转到不同的总线去执行
 * @author 李广明
 * @version 1.0
 * @date 2008-05-06
 */
public class BusiEntryBus{
	
	//单例模式
	private static BusiEntryBus bus = new BusiEntryBus();
	
	private static Map instFactory = new Hashtable();
	private Logger logger  =  Logger.getLogger("[交易接口]:[交易总线]");
	
	/**
	 * 构造函数,以子总线类作为参数进行构造
	 * @param inUvo
	 */
	private BusiEntryBus() {
	}

	/**
	 * 单例模式,用于返回本类的实例
	 */
	public static BusiEntryBus getInstance() {
		return bus;
	}
	/**
	 * 核心函数,由此函数进行判断交易该如何执行
	 * @return
	 */
	public UniKeyValueObject doExecute(UniKeyValueObject inUvo){
		
		UniKeyValueObject outUvo = new UniKeyValueObject();
		Object base = BusinessConf.busiTypeMap
				.get(inUvo.getValue("JYM").trim());
		
		//如果此交易码没有找到对应的处理类,说明不支持对该交易的处理
		if(base == null){
			outUvo = PackUtil.backErrorPack(inUvo.getValue("JYM"),
					PackUtil.NO_BUSINESS);
			return outUvo;
		}
		
		//根据交易基本信息,进行初始化动作,将其分发给相应的交易类进行处理
		IBusinessBus busiInst = null;
		Business busi = (Business)base;
		Object instance = instFactory.get(busi.getId());
		
		//记录日志
		BusiLog busiLog = new BusiLog();
		busiLog.setBusiElement(inUvo);

		//如果是第一次调用,则进行初始化并将其实例对象存入MAP中
		if (instance == null) {
			try {
				busiInst = (IBusinessBus) Class.forName(busi.getDealClass())
						.newInstance();
				busiInst.doInitBus(busi);
				instFactory.put(busi.getId(), busiInst);
				outUvo = busiInst.doExecute(inUvo);
			} catch (InstantiationException e) {
				outUvo = PackUtil.backErrorPack(inUvo.getValue("JYM"),
						PackUtil.INSTANCE_ERROR);
				logger.error(e.getMessage());
			} catch (IllegalAccessException e) {
				outUvo = PackUtil.backErrorPack(inUvo.getValue("JYM"),
						PackUtil.INSTANCE_ERROR);
				logger.error(e.getMessage());
			} catch (ClassNotFoundException e) {
				outUvo = PackUtil.backErrorPack(inUvo.getValue("JYM"),
						PackUtil.CLASS_NOT_FOUND);
				logger.error(e.getMessage());
			} catch (Exception ne) {
				ne.printStackTrace();
				outUvo = PackUtil.backErrorPack(inUvo.getValue("JYM"), ne
						.getMessage());
				logger.error(ne.getMessage());
			}
		} else {
			busiInst = (IBusinessBus) instance;
			try {
				outUvo = busiInst.doExecute(inUvo);
			} catch (Exception e) {
				outUvo = PackUtil.backErrorPack(inUvo.getValue("JYM"), e
						.getMessage());
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
		busiLog.setBusiResult(outUvo);
		BusiLogService.addLog(busiLog);
		return outUvo;
	}
}
