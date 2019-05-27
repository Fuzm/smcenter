package com.irdstudio.ssm.framework.web;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.irdstudio.ssm.framework.vo.BaseInfo;
import com.irdstudio.ssm.framework.vo.UserInfo;

/**
 * 
 * Spring mvc Controller父类<p/>
 * 该类主要为了解决通用权限问题，通过权限类型去判断服务提供方调用不同的数据方法
 * 
 * @author Cytus_
 * @since 2018-04-24 18:29:23
 * @version 1.0
 */
public abstract class AbstractController implements IController {
	
	//ServletRequest请求对象
	@Autowired
	protected HttpServletRequest httpRequest;
	
	@Autowired
	protected HttpServletResponse httpResponse;

	protected static Logger logger = LoggerFactory.getLogger(AbstractController.class);
	
	/**
	 * 主要提供数据权限的查询控制, 通过通用的方法，使用hsf的泛型调用来进行查询
	 * @param methodName 代用方法
	 * @param serviceClass 服务的实例对象
	 * @param params 方法的参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected final <T> T executeQueryList(String methodName, Object serviceClass, Object[] params) {
		
		String uri = this.httpRequest.getRequestURI();
		logger.debug("当前请求的URI为:"+ uri);
		UserInfo userInfo = (UserInfo) this.httpRequest.getSession().getAttribute(UserInfo.SEESION_USER_KEY);
//		String dataRuleType = (String) ((GenericService) SpringContextUtils.getBean("sRoleDataRuleService")).$invoke("getDataRuleType", 
//				new String[] {"java.lang.String", "java.util.List"}, new Object[] {uri, roles});
		try {
			Method method = getCallMethod(serviceClass, methodName, "Owner");
			String[] paramsType = Arrays.stream(method.getParameters()).map(s -> s.getParameterizedType().getTypeName()).collect(Collectors.toList()).toArray(new String[params.length]);

			setUserInfoToVO(userInfo, params);
			//GenericService gs = (GenericService) serviceClass;
			
			logger.debug("当前调用的服务接口为:"+ serviceClass.getClass()+ ", 调用的方法为:"+ method.getName());
			//Object hsfRtnObj = gs.$invoke(method.getName(), paramsType, params);
			//Object rtnObj = HsfGenericUtils.exchangeRtnData(hsfRtnObj);
			//return (T) rtnObj;
		} catch (Exception e) {
			logger.error("当前调用数据权限服务出现异常!", e);
		}
		
		return null;
		
	}
	
	/**
	 * 设置用户相关信息到BaseInfo中
	 * @param userInfo
	 * @param params
	 */
	private final void setUserInfoToVO(UserInfo userInfo, Object[] params) {
		for (Object object : params) {
			if (object instanceof BaseInfo) {
				BaseInfo baseInfo = (BaseInfo) object;
				baseInfo.setLoginUserId(userInfo.getUserId());
				baseInfo.setLoginUserOrgLocation(userInfo.getOrgInfo().getOrgLocation());
				baseInfo.setLoginUserOrgCode(userInfo.getOrgInfo().getOrgCode());
				baseInfo.setLoginUserLeageOrgCode(userInfo.getLegalOrg().getOrgCode());
			}
		}
	}
	
	/**
	 * 获得调用方法
	 * @param serviceClass
	 * @param methodName
	 * @param dataRuleType
	 * @return
	 */
	private final Method getCallMethod(Object serviceClass, String methodName, String dataRuleType) throws Exception {
		try {
			Method[] methods = serviceClass.getClass().getMethods();
			String mtdName = getCallMethodByDataRuleType(methodName, dataRuleType);
			List<Method> lists = Arrays.stream(methods).filter(m -> m.getName().equals(mtdName)).collect(Collectors.toList());
			if (Objects.nonNull(lists) && lists.size() > 0) {
				return lists.get(0);
			}
		} catch (Exception e) {
			logger.error("当前获取调用方法出现异常!", e);
			throw e;
		}
		return null;
	}
	
	/**
	 * 获得调用方法名通过权限类型, 该方法dataRuleType不存在或者为空时默认为10
	 * @param methodName
	 * @param dataRuleType
	 * @return
	 */
	private final String getCallMethodByDataRuleType(String methodName, String dataRuleType) {
		if (StringUtils.isEmpty(dataRuleType)) {
			dataRuleType = "10";
		}
		String suffix = "";
		if (StringUtils.isNotEmpty(dataRuleType)) {
			for (DataRuleType type : DataRuleType.values()) {
				if (dataRuleType.equals(type.getValue())) {
					suffix = type.toString();
					break;
				}
			}
		}
		return methodName+suffix;
	}
	
	/**
	 * 设置用户信息对象到VO中
	 * @param baseInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public final <T> T setUserInfoToVO(BaseInfo baseInfo) {
		UserInfo userInfo = (UserInfo) this.httpRequest.getSession().getAttribute(UserInfo.SEESION_USER_KEY);
		Object[] obj = new Object[] {baseInfo};
		setUserInfoToVO(userInfo, obj);
		return (T) obj[0];
	}
	
	
	/**
	 * 数据权限类型
	 * @author Cytus_
	 *
	 */
	enum DataRuleType {
		Owner("10"), CurrOrg("20"), CurrDownOrg("30");
		
		private String value;
		
		private DataRuleType(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return this.value;
		}
		
	}
	
	/**
	 * 获得上下文中用户对象
	 * @return
	 */
	public final UserInfo getUserInfo() {
		UserInfo userInfo = (UserInfo) this.httpRequest.getSession().getAttribute(UserInfo.SEESION_USER_KEY);
		return userInfo;
	}


}
