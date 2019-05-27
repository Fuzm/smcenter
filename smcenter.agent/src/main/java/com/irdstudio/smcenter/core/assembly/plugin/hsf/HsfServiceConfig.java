package com.irdstudio.smcenter.core.assembly.plugin.hsf;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.irdstudio.smcenter.core.schedule.SSrvsCronConf;
import com.irdstudio.smcenter.core.schedule.SSrvsCronConfDao;
import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.TConnPool;
import com.irdstudio.smcenter.core.tinycore.log.ILogger;
import com.irdstudio.smcenter.core.tinycore.log.TLogger;

public class HsfServiceConfig {
	
	private static ILogger logger = TLogger.getLogger(HsfServiceConfig.class.getSimpleName());
	
	public static void init() throws SQLException {
		logger.info("HSF service init start");
		Connection conn = null;
		try {
			conn = TConnPool.getDefaultPool().getConnection();
			PluginServiceConfDao confDao = new PluginServiceConfDao(conn);
			
			/*List<PluginServiceConf> serviceList = confDao.queryWithCond("", "");
			for (int i = 0; i < serviceList.size(); i++) {
				PluginServiceConf conf = serviceList.get(i);

				HSFSpringConsumerBean consumerBean = new HSFSpringConsumerBean();
				consumerBean.setInterfaceName(conf.getServiceInterface());
				consumerBean.setVersion(conf.getVersion());
				consumerBean.setGeneric("true"); // 设置 generic 为 true
				// 设置组别
				if (StringUtils.isNotEmpty(conf.getGroup())) {
					consumerBean.setGroup(conf.getGroup());
				}
				// 设置超时
				if (conf.getTimeout() != null) {
					consumerBean.setClientTimeout(conf.getTimeout().intValue());
					
					//设置超时
					MethodSpecial special = new MethodSpecial();
					special.setMethodName(conf.getServiceMethod());
					special.setClientTimeout(conf.getTimeout().intValue());
					consumerBean.setMethodSpecials(new MethodSpecial[] {special});
				}

				//初始化
				consumerBean.init();
				logger.info("Plugin服务" + conf.getServiceInterface() + ",组别:" + conf.getGroup() + 
						",版本:" + conf.getVersion() + ",超时时间:" + conf.getTimeout() + ",初始化成功！");
			}
			
			//初始化定时调度任务hsf服务
			SSrvsCronConfDao cronDao = new SSrvsCronConfDao(conn);
			List<SSrvsCronConf> cronList = cronDao.querySSrvsCronConfWithCond(" where job_class_type = 'hsf' ", "");
			for (int i = 0; i < cronList.size(); i++) {
				SSrvsCronConf cron = cronList.get(i);

				HSFSpringConsumerBean consumerBean = new HSFSpringConsumerBean();
				consumerBean.setInterfaceName(cron.getJobClass());
				consumerBean.setVersion(cron.getServiceVersion());
				consumerBean.setGeneric("true"); // 设置 generic 为 true
				// 设置组别
				if (StringUtils.isNotEmpty(cron.getServiceGroup())) {
					consumerBean.setGroup(cron.getServiceGroup());
				}
				// 设置超时
				if (cron.getServiceTimeout() != null) {
					consumerBean.setClientTimeout(cron.getServiceTimeout().intValue());
					
					//设置超时
					MethodSpecial special = new MethodSpecial();
					special.setMethodName(cron.getJobMethod());
					special.setClientTimeout(cron.getServiceTimeout().intValue());
					consumerBean.setMethodSpecials(new MethodSpecial[] {special});
				}

				//初始化
				consumerBean.init();
				logger.info("Cron服务" + cron.getJobClass() + ",组别:" + cron.getServiceGroup() + 
						",版本:" + cron.getServiceVersion() + ",超时时间:" + cron.getServiceTimeout() + ",初始化成功！");
			}*/
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			logger.info("HSF service init complete");
			if(conn != null) {
				TConnPool.getDefaultPool().releaseConnection(conn);
			}
		}
		
	}
}
