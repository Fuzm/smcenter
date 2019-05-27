package com.irdstudio.smcenter.agent.test;

import org.junit.Test;

public class LightApiTest {
	
	/*static {
		ServiceFactory factory = HsfServiceFactory.getServiceFactory();
		
		factory.consumer("pubSysInfoService")// 参数是一个标识，初始化后，下次只需调用consumer("helloConsumer")即可直接拿出对应服务
		.service("com.gdrcu.lp.framework.api.PubSysInfoService")// 接口全类名
		.version("1.0.0")// 版本号
		.group("HSF")// 组别
		.timeout(50000)
		.subscribe();

		try {
			factory.consumer("pubSysInfoService").sync();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 同步等待地址推送，最多6秒。
	}
*/
	@Test
	public void testApi() throws Exception {
//		ServiceFactory factory = HsfServiceFactory.getServiceFactory();
//		
//		factory.consumer("pubSysInfoService")// 参数是一个标识，初始化后，下次只需调用consumer("helloConsumer")即可直接拿出对应服务
//		.service("com.gdrcu.lp.framework.api.PubSysInfoService")// 接口全类名
//		.version("1.0.0")// 版本号
//		.group("HSF")// 组别
//		.timeout(50000)
//		.subscribe();
//
//		factory.consumer("pubSysInfoService").sync();// 同步等待地址推送，最多6秒。
		
		/*PubSysInfoService service = (PubSysInfoService) factory.consumer("pubSysInfoService").subscribe();
		String openday = service.getOpenday("1001");
		System.out.println("系统日期：" + openday);*/
	}
}
