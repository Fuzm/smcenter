package com.irdstudio.smcenter.agent.boot;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.irdstudio.smcenter.agent.utils.SpringContextUtils;
import com.irdstudio.smcenter.core.init.AgentInstInfo;
import com.irdstudio.smcenter.core.init.AgentResourceLoader;

/**
 * <p>Agent启动入口,根据属性文件中的参数分别支持dubbo,hsf及local模式的agent</p>
 * @author fuzm
 * @date 2018-06-15
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.irdstudio.smcenter"})
public class AgentApplicationBoot {

	public static void main(String[] args) throws SQLException {
		// 加载路径配置
		AgentResourceLoader.me().start();
		String agentMode = AgentInstInfo.AGENT_MODE;
		
		// 读取参数来确定Agent的启动模式
		if (AgentConstant.AGENT_MODE_HSF.equals(agentMode)) {
			/*System.out.println("正在以agent for hsf模式启动...");
			PandoraBootstrap.run(args);
			ApplicationContext context = SpringApplication.run(AgentApplicationBoot.class, args);
			SpringContextUtils.setApplicationContext(context);
			// 初始化hsf服务
			HsfServiceConfig.init();
			test();
			
			PandoraBootstrap.markStartupAndWait();*/
		} else if (AgentConstant.AGENT_MODE_DUBBO.equals(agentMode)) {
			System.out.println("正在以agent for dubbo模式启动...");
		} else {
			System.out.println("正在以agent for local模式启动...");
			ApplicationContext context = SpringApplication.run(AgentApplicationBoot.class, args);
			SpringContextUtils.setApplicationContext(context);
		}
	}
}
