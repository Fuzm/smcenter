package com.irdstudio.smcenter.core.init;

import java.io.File;
import java.util.ResourceBundle;

import com.irdstudio.smcenter.core.tinycore.log.ILogger;
import com.irdstudio.smcenter.core.tinycore.log.TLogger;
import com.irdstudio.smcenter.core.util.pub.PathUtil;

/**
 * Agent所需要属性配置加载
 * @author guangming.li
 * @version 1.0
 * @date 2014-05-07
 * @modify 2018-06-16 
 */
public class AgentResourceLoader {
	
	/* 本类实例 */
	private static AgentResourceLoader inst = null;
	/* 工程所在位置的绝对路径 */
	private String projectRootPath = "";
	/* 日志记录者 */
	private ILogger logger = null;
	
	/*
	 * 获得本身的实例
	 */
	public synchronized static AgentResourceLoader me() {
		if (inst == null) {
			inst = new AgentResourceLoader();
		}
		return inst;
	}
	
	/**
	 * 加载全局的配置属性
	 */
	public void loadGlobalConfigure(){
		
		this.projectRootPath = PathUtil.getProjectRootPath();
		ResourceBundle bundle = ResourceBundle.getBundle("application");	
		
		// 获取批次日志文件路径
		AgentInstInfo.BATCH_LOG_PATH = formatPath(bundle
				.getString("agent.batch.log.path"));
		
		// 获取Agent启动模式
		AgentInstInfo.AGENT_MODE = bundle.getString("agent.mode");
		
		// 获取Agent标识
		AgentInstInfo.AGENT_ID = bundle.getString("agent.id");
		
		//初始化目录
		File file = new File(AgentInstInfo.BATCH_LOG_PATH);
		if(!file.exists()) {
			file.mkdirs();
		}

		logger.info(" 批次服务日志路径 :" + AgentInstInfo.BATCH_LOG_PATH);
	}

	/**
	 * 格式化路径(如果是相对路径，则转换为绝对路径
	 * 如果是绝对路径,则直接返回)
	 * @param string
	 * @return
	 */
	private String formatPath(String path) {
		if (PathUtil.isAbsolutePath(path)) {
			return path;
		} else {
			return this.projectRootPath + path;
		}
	}

	/**
	 * 开始加载资源
	 */
	public void start() {
		
		// 初始化日志体系
		logger = TLogger.getLogger("resource");
		// 加载全局的配置文件
		loadGlobalConfigure();
	}
	

}
