package com.irdstudio.smcenter.agent.boot;
/**
 * <p>Agent常量定义</p>
 * @author ligm
 * @date 2018-06-16
 */
public class AgentConstant {

	/** Agent状态-未启动或未知 */
	public final static String AGENT_STATE_UNKNOW = "0";

	/** Agent状态-待激活 */
	public final static String AGENT_STATE_ACTIVE = "1";

	/** Agent状态-运行中 */
	public final static String AGENT_STATE_RUNING = "2";
	
	/** Agent状态-暂停中 */
	public final static String AGENT_STATE_PAUSE = "3";
	
	/* Agent启动模式-支持HSF*/
	public final static String AGENT_MODE_HSF = "hsf";
	
	/* Agent启动模式-支持DUBBO*/
	public final static String AGENT_MODE_DUBBO  = "dubbo";
	
	/* Agent启动模式-支持LOCAL*/
	public final static String AGENT_MODE_LOCAL = "local";
	
}
