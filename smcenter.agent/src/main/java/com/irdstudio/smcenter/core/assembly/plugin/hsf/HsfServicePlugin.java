package com.irdstudio.smcenter.core.assembly.plugin.hsf;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.irdstudio.smcenter.core.assembly.plugin.AbstractPlugin;
import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.TConnPool;
import com.irdstudio.smcenter.core.tinycore.log.ILogger;
import com.irdstudio.smcenter.core.tinycore.log.TLogger;

public class HsfServicePlugin extends AbstractPlugin {

	protected ILogger logger = TLogger.getLogger(HsfServicePlugin.class.getSimpleName());

	private List<PluginServiceConf> serviceList;

	@Override
	public boolean execute() {
		Connection conn = null;
		try {
			// 获取HSF服务工厂
			// ServiceFactory factory = HsfServiceFactory.getServiceFactory();
			conn = this.getPluginConnection();
			/*PluginServiceParamDao paramDao = new PluginServiceParamDao(conn);
			if (serviceList != null) {
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
						
						MethodSpecial special = new MethodSpecial();
						special.setMethodName(conf.getServiceMethod());
						special.setClientTimeout(conf.getTimeout().intValue());
						consumerBean.setMethodSpecials(new MethodSpecial[] {special});
					}

					//初始化
					consumerBean.syncInit();
					GenericService svc = (GenericService) consumerBean.getObject(); // 强转接口接口为 GenericService

					// 查询所需参数
					List<PluginServiceParam> paramList = null;
					if (StringUtils.isNotEmpty(conf.getPluginConfId())) {
						paramList = paramDao.queryWithCond("where param_group_id = '" + conf.getParamGroupId() + "'",
								" order by service_param_name");
					}

					// 泛化接口调用
					String[] paramTypeArr = null;
					Object[] paramValueArr = null;
					if (paramList != null && paramList.size() > 0) {
						paramTypeArr = new String[paramList.size()];
						paramValueArr = new Object[paramList.size()];
						for (int index = 0; index < paramList.size(); index++) {
							PluginServiceParam param = paramList.get(index);

							paramTypeArr[index] = param.getServiceParamType();
							paramValueArr[index] = param.getServiceParamValue();
						}
					}

					//调用执行
					logger.info("service id: " + conf.getServiceId() + ", interface: " + conf.getServiceInterface()
							+ ",version: " + conf.getVersion() + ", group: " + conf.getGroup() + ",timeout: " + conf.getTimeout()
							+ ", param type: " + paramTypeArr + ", param value: " + paramValueArr);
					Object result = svc.$invoke(conf.getServiceMethod(), paramTypeArr, paramValueArr);
					logger.info("service call result: " + result);
					
					if(result != null) {
						logger.info("service call result class: " + result.getClass());
						if(result instanceof Boolean) {
							logger.info("service return result: " + result);
							Boolean resultBoolean = (Boolean) result;
							if(!resultBoolean) {
								throw new Exception("service: " + conf.getServiceInterface() + " call error");
							}
						} else if(result instanceof Map) {
							@SuppressWarnings("rawtypes")
							Map resultMap = (Map) result;
							if(resultMap.containsKey("class") && resultMap.get("class") != null 
										&& resultMap.get("class").toString().contains("Exception")) {
								
								logger.debug("result map key class: " + resultMap.get("class").getClass());
								//判断是否报错
								throw new Exception(result.toString());
							}
						}
					}

					
					 * //查找是否存在 if(factory.consumer(conf.getServiceId()) != null) { // 进行服务消费
					 * ConsumerService service = factory.consumer(conf.getServiceId())//
					 * 参数是一个标识，初始化后，下次只需调用 consumer("helloConsumer")即可直接拿出对应服务
					 * .service(conf.getServiceInterface())// 接口全类名 .version(conf.getVersion())//
					 * 版本号 .group(conf.getGroup()); // 组别
					 * 
					 * // 设置超时 if (conf.getTimeout() != null) {
					 * service.timeout(conf.getTimeout().intValue()); } // 发布 service.subscribe(); }
					 * 
					 * // 同步等待地址推送，最多6秒。 factory.consumer(conf.getServiceId()).sync();
					 * 
					 * 
					 * PubSysInfoService infoService = (PubSysInfoService)
					 * factory.consumer(conf.getServiceId()).subscribe(); logger.info("openday : " +
					 * infoService.getOpenday("1001"));
					 * 
					 * 
					 * // 查询所需参数 List<PluginServiceParam> paramList = null; if
					 * (StringUtils.isNotEmpty(conf.getPluginConfId())) { paramList =
					 * paramDao.queryWithCond("where param_group_id = '" + conf.getParamGroupId() +
					 * "'", " order by service_param_name"); }
					 * 
					 * // 泛化接口调用 GenericMethod method = new GenericMethod(conf.getServiceMethod());
					 * if (paramList != null && paramList.size() > 0) { String[] paramTypeArr = new
					 * String[paramList.size()]; Object[] paramValueArr = new
					 * Object[paramList.size()]; for (int index = 0; index < paramList.size();
					 * index++) { PluginServiceParam param = paramList.get(index);
					 * 
					 * paramTypeArr[index] = param.getServiceParamType(); paramValueArr[index] =
					 * param.getServiceParamValue(); }
					 * method.argsType(paramTypeArr).argsVal(paramValueArr); } // 执行调用
					 * logger.info("service id: " + conf.getServiceId() + ", interface: " +
					 * conf.getServiceInterface() + ",version: " + conf.getVersion() + ", group: " +
					 * conf.getGroup()); Object result =
					 * factory.consumer(conf.getServiceId()).genericInvoke(method);
					 * logger.info("service call result: " + result);
					 
				}
			}*/

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				TConnPool.getDefaultPool().releaseConnection(conn);
			}
		}

		return false;
	}

	@Override
	protected boolean doReadConfigureFromDB(Connection conn, String szConfIdentify) throws SQLException {
		PluginServiceConfDao plcDao = new PluginServiceConfDao(conn);
		serviceList = plcDao.queryWithCond(" where plugin_conf_id='" + szConfIdentify + "'", "order by conf_sort");
		if (serviceList.size() < 1) {
			context.szLastErrorMsg = "未读取到配置标识为：" + szConfIdentify + "的服务配置!";
			return false;
		} else {
			return true;
		}
	}

}
