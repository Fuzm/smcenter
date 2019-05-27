package com.irdstudio.smcenter.core.assembly.plugin.dubbo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.dubbo.rpc.service.GenericService;
import com.irdstudio.smcenter.core.assembly.plugin.AbstractPlugin;
import com.irdstudio.smcenter.core.assembly.plugin.hsf.PluginServiceConf;
import com.irdstudio.smcenter.core.assembly.plugin.hsf.PluginServiceConfDao;
import com.irdstudio.smcenter.core.assembly.plugin.hsf.PluginServiceParam;
import com.irdstudio.smcenter.core.assembly.plugin.hsf.PluginServiceParamDao;
import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.TConnPool;
import com.irdstudio.smcenter.core.tinycore.log.ILogger;
import com.irdstudio.smcenter.core.tinycore.log.TLogger;

public class DubboServicePlugin extends AbstractPlugin {

	protected ILogger logger = TLogger.getLogger(DubboServicePlugin.class.getSimpleName());

	private List<PluginServiceConf> serviceList;

	@Override
	public boolean execute() {
		Connection conn = null;
		try {
			conn = this.getPluginConnection();
			PluginServiceParamDao paramDao = new PluginServiceParamDao(conn);
			if (serviceList != null) {
				for (int i = 0; i < serviceList.size(); i++) {
					PluginServiceConf conf = serviceList.get(i);
					
					// 强转接口接口为 GenericService
					GenericService svc = null;
					if(conf.getTimeout() == null) {
						//如果不设置超时时间，则采用system.propeties配置的超时时间
						svc = DubboClient.getDubboClient().buildGenericService(conf.getServiceInterface(), conf.getGroup());
					} else {
						svc = DubboClient.getDubboClient().buildGenericService(conf.getServiceInterface(), conf.getTimeout().intValue(), conf.getGroup());
					}
					
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
				}
			}

			return true;
		} catch (Exception e) {
			logger.error("Dubbo插件执行失败：" + e.getMessage(), e);
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
