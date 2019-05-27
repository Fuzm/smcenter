package com.irdstudio.ssm.framework.service;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;
import com.irdstudio.ssm.framework.util.TimeUtil;
import com.irdstudio.ssm.framework.vo.BaseInfo;

/**
 * 框架基础服务接口，主要提供一些常用方法
 * @author Cytus_
 * @since 2018-04-25 09:54:21
 * @version 1.0
 */
public interface FrameworkService {
	
	/**
	 * bean对象之间互相转换, 采用spring的cglib下的BeanCopier进行转换
	 */
	@SuppressWarnings("unchecked")
	default <T> T beanCopy(Object srcBean, Object targetBean) {
		if (Objects.nonNull(targetBean) && Objects.nonNull(srcBean)) {
			BeanCopier beanCopier = BeanCopier.create(srcBean.getClass(), targetBean.getClass(), false);
			beanCopier.copy(srcBean, targetBean, null);
			return (T) targetBean;
		}
		return null;
	}
	
	/**
	 * bean对象之间互相转换, 采用spring的cglib下的BeanCopier进行转换
	 */
	default <T> Collection<? extends Object> beansCopy(Collection<? extends Object> srcBeans, Class<?> targetBean) throws Exception {
		if (Objects.isNull(srcBeans)) return null;
		return srcBeans.stream().map(s -> this.beanCopy(s, newInstance(targetBean))).collect(Collectors.toList());
	}
	
	/**
	 * 主要是setpage信息，在查询列表完成后，返回给服务消费者的是一个list，故将后台查询到的总页数放到list每一个bean中
	 */
	default void pageSet(Collection<? extends BaseInfo> queryRtnBean, BaseInfo queryCondBean) {
		if (Objects.nonNull(queryRtnBean) && !queryRtnBean.isEmpty() && Objects.nonNull(queryCondBean)) {
			queryRtnBean.stream().forEach(s -> {
				s.setLoginUserId(queryCondBean.getLoginUserId());
				s.setLoginUserLeageOrgCode(queryCondBean.getLoginUserLeageOrgCode());
				s.setLoginUserOrgCode(queryCondBean.getLoginUserOrgCode());
				s.setLoginUserOrgLocation(queryCondBean.getLoginUserOrgLocation());
				s.setPage(queryCondBean.getPage());
				s.setSize(queryCondBean.getSize());
				s.setTotal(queryCondBean.getTotal());
			});
		}
	}
	
	/**
	 * 通过class new 一个对象，该对象必须存在不带参的构造方法
	 */
	default Object newInstance(Class<?> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error("Class类对象:"+ clazz.getName() +"实例化时出现异常!", e);
		}
		return null;
	}

	/**
	 * 设置新增是对象默认相关属性
	 * @param baseInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default <T> T setInsertDefaultProperty(BaseInfo baseInfo) {
		try {
			BeanUtils.setProperty(baseInfo, "createTime", TimeUtil.getCurrentDateTime());
			BeanUtils.setProperty(baseInfo, "lastUpdateTime", TimeUtil.getCurrentDateTime());
			BeanUtils.setProperty(baseInfo, "createUser", baseInfo.getLoginUserId());
			BeanUtils.setProperty(baseInfo, "lastUpdateUser", baseInfo.getLoginUserId());
			BeanUtils.setProperty(baseInfo, "legalOrgCode", baseInfo.getLoginUserLeageOrgCode());
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error("设置新增默认属性时出现异常!", e);
		}
		return (T) baseInfo;
	}
	
	/**
	 * 设置修改时对象默认相关属性
	 * @param baseInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default <T> T setUpdateDefaultProperty(BaseInfo baseInfo) {
		try {
			BeanUtils.setProperty(baseInfo, "lastUpdateTime", TimeUtil.getCurrentDateTime());
			BeanUtils.setProperty(baseInfo, "lastUpdateUser", baseInfo.getLoginUserId());
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error("设置修改默认属性时出现异常!", e);
		}
		return (T) baseInfo;
	}
}
