package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.BatTaskLocaleConfigService;
import com.irdstudio.smcenter.console.dao.BatTaskLocaleConfigDao;
import com.irdstudio.smcenter.console.dao.domain.BatTaskLocaleConfig;
import com.irdstudio.smcenter.console.service.vo.BatTaskLocaleConfigVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 执行场所配置				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("localeConfigService")
public class BatTaskLocaleConfigServiceImpl implements BatTaskLocaleConfigService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(BatTaskLocaleConfigServiceImpl.class);

	@Autowired
	private BatTaskLocaleConfigDao batTaskLocaleConfigDao;
	
	/**
	 * 新增操作
	 */
	public int insertBatTaskLocaleConfig(BatTaskLocaleConfigVO inBatTaskLocaleConfigVo) {
		logger.debug("当前新增数据为:"+ inBatTaskLocaleConfigVo.toString());
		int num = 0;
		try {
			BatTaskLocaleConfig batTaskLocaleConfig = new BatTaskLocaleConfig();
			beanCopy(inBatTaskLocaleConfigVo, batTaskLocaleConfig);
			num = batTaskLocaleConfigDao.insertBatTaskLocaleConfig(batTaskLocaleConfig);
		} catch (Exception e) {
			logger.error("新增数据发生异常!", e);
			num = -1;
		}
		logger.debug("当前新增数据条数为:"+ num);
		return num;
	}

	/**
	 * 删除操作
	 */
	public int deleteByPk(BatTaskLocaleConfigVO inBatTaskLocaleConfigVo) {
		logger.debug("当前删除数据条件为:"+ inBatTaskLocaleConfigVo);
		int num = 0;
		try {
			BatTaskLocaleConfig batTaskLocaleConfig = new BatTaskLocaleConfig();
			beanCopy(inBatTaskLocaleConfigVo, batTaskLocaleConfig);
			num = batTaskLocaleConfigDao.deleteByPk(batTaskLocaleConfig);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inBatTaskLocaleConfigVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(BatTaskLocaleConfigVO inBatTaskLocaleConfigVo) {
		logger.debug("当前修改数据为:"+ inBatTaskLocaleConfigVo.toString());
		int num = 0;
		try {
			BatTaskLocaleConfig batTaskLocaleConfig = new BatTaskLocaleConfig();
			beanCopy(inBatTaskLocaleConfigVo, batTaskLocaleConfig);
			num = batTaskLocaleConfigDao.updateByPk(batTaskLocaleConfig);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inBatTaskLocaleConfigVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public BatTaskLocaleConfigVO queryByPk(BatTaskLocaleConfigVO inBatTaskLocaleConfigVo) {
		
		logger.debug("当前查询参数信息为:"+ inBatTaskLocaleConfigVo);
		try {
			BatTaskLocaleConfig queryBatTaskLocaleConfig = new BatTaskLocaleConfig();
			beanCopy(inBatTaskLocaleConfigVo, queryBatTaskLocaleConfig);
			BatTaskLocaleConfig queryRslBatTaskLocaleConfig = batTaskLocaleConfigDao.queryByPk(queryBatTaskLocaleConfig);
			if (Objects.nonNull(queryRslBatTaskLocaleConfig)) {
				BatTaskLocaleConfigVO outBatTaskLocaleConfigVo = beanCopy(queryRslBatTaskLocaleConfig, new BatTaskLocaleConfigVO());
				logger.debug("当前查询结果为:"+ outBatTaskLocaleConfigVo.toString());
				return outBatTaskLocaleConfigVo;
			} else {
				logger.debug("当前查询结果为空!");
			}
		} catch (Exception e) {
			logger.error("查询数据发生异常!", e);
		}
		return null;
	}


	/**
	 * 查询用户权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<BatTaskLocaleConfigVO> queryAllOwner(BatTaskLocaleConfigVO batTaskLocaleConfigVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<BatTaskLocaleConfigVO> list = null;
		try {
			List<BatTaskLocaleConfig> batTaskLocaleConfigs = batTaskLocaleConfigDao.queryAllOwnerByPage(batTaskLocaleConfigVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ batTaskLocaleConfigs.size());
			pageSet(batTaskLocaleConfigs, batTaskLocaleConfigVo);
			list = (List<BatTaskLocaleConfigVO>) beansCopy(batTaskLocaleConfigs, BatTaskLocaleConfigVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<BatTaskLocaleConfigVO> queryAllCurrOrg(BatTaskLocaleConfigVO batTaskLocaleConfigVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<BatTaskLocaleConfig> batTaskLocaleConfigs = batTaskLocaleConfigDao.queryAllCurrOrgByPage(batTaskLocaleConfigVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+batTaskLocaleConfigs.size());
		List<BatTaskLocaleConfigVO> list = null;
		try {
			pageSet(batTaskLocaleConfigs, batTaskLocaleConfigVo);
			list = (List<BatTaskLocaleConfigVO>) beansCopy(batTaskLocaleConfigs, BatTaskLocaleConfigVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<BatTaskLocaleConfigVO> queryAllCurrDownOrg(BatTaskLocaleConfigVO batTaskLocaleConfigVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<BatTaskLocaleConfig> batTaskLocaleConfigs = batTaskLocaleConfigDao.queryAllCurrDownOrgByPage(batTaskLocaleConfigVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ batTaskLocaleConfigs.size());
		List<BatTaskLocaleConfigVO> list = null;
		try {
			pageSet(batTaskLocaleConfigs, batTaskLocaleConfigVo);
			list = (List<BatTaskLocaleConfigVO>) beansCopy(batTaskLocaleConfigs, BatTaskLocaleConfigVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
