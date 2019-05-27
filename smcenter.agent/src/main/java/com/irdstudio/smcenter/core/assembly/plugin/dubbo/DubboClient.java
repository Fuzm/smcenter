package com.irdstudio.smcenter.core.assembly.plugin.dubbo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.irdstudio.smcenter.agent.utils.PropertiesUtil;
import com.irdstudio.smcenter.agent.utils.SpringContextUtils;

/**
 * dobbo服务调用客户端
 */
@Component
public class DubboClient {
	
	private static Logger logger = Logger.getLogger(DubboClient.class);
	private Map<String, ReferenceConfig<GenericService>> referenceConfigMapping = new ConcurrentHashMap<String, ReferenceConfig<GenericService>>();
	private Object lock = new Object();

	private String protocol = PropertiesUtil.getApplicationKey("dubbo.registry.protocol");
	private String address = PropertiesUtil.getApplicationKey("dubbo.registry.address");
	private String cluster = PropertiesUtil.getApplicationKey("dubbo.consumer.cluster");
	private String retries = PropertiesUtil.getApplicationKey("dubbo.consumer.retries");
	private String timeout = PropertiesUtil.getApplicationKey("dubbo.consumer.timeout");
	private String applicationName = PropertiesUtil.getApplicationKey("spring.application.name");

	/**
	 * 创建dubbo连接
	 * @param serviceInterface
	 * @param group
	 * @return
	 */
	public GenericService buildGenericService(String serviceInterface, String group) {
		return this.buildGenericService(serviceInterface, Integer.valueOf(timeout), group);
	}
	
	/**
	 * 创建dubbo连接
	 * @param serviceInterface
	 * @param timeout
	 * @param group
	 * @return
	 */
	public GenericService buildGenericService(String serviceInterface, int timeout, String group) {
		ReferenceConfig<GenericService> reference = referenceConfigMapping.get(serviceInterface);
		if (reference == null || reference.get() == null) {
			synchronized (lock) {
				reference = referenceConfigMapping.get(serviceInterface);
				if (reference == null || reference.get() == null) {
					reference = new ReferenceConfig<GenericService>();
					RegistryConfig registry = new RegistryConfig();
					registry.setProtocol(protocol);
					registry.setAddress(address);
					registry.setFile(applicationName+".properties");
					reference.setRegistry(registry);
					reference.setInterface(serviceInterface);
					reference.setGeneric(Boolean.valueOf(true));
					reference.setSent(Boolean.valueOf(false));
					reference.setCluster(cluster);
					if(StringUtils.isNoneEmpty(group)) {
						reference.setGroup(group);
					}
					reference.setRetries(Integer.valueOf(retries));
					reference.setTimeout(Integer.valueOf(timeout));
					reference.setApplication(new ApplicationConfig(applicationName));

					referenceConfigMapping.put(serviceInterface, reference);
				}
			}
		}
		logger.info("获取服务成功！");
		return (GenericService) reference.get();
	}

	/**
	 * 获取dubbo客户端实例
	 * 
	 * @return
	 */
	public static DubboClient getDubboClient() {
		return (DubboClient) SpringContextUtils.getBean("dubboClient");
	}
}