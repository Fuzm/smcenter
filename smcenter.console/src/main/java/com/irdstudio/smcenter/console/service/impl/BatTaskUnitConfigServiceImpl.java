package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.BatTaskUnitConfigService;
import com.irdstudio.smcenter.console.dao.BatTaskUnitConfigDao;
import com.irdstudio.smcenter.console.dao.domain.BatTaskUnitConfig;
import com.irdstudio.smcenter.console.service.vo.BatTaskUnitConfigVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 批次任务配置				<p>
 * @author ligm
 * @date 2018-06-14
 */
@Service("taskConfigService")
public class BatTaskUnitConfigServiceImpl implements BatTaskUnitConfigService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(BatTaskUnitConfigServiceImpl.class);

	@Autowired
	private BatTaskUnitConfigDao batTaskUnitConfigDao;
	
	/**
	 * 新增操作
	 */
	public int insertBatTaskUnitConfig(BatTaskUnitConfigVO inBatTaskUnitConfigVo) {
		logger.debug("当前新增数据为:"+ inBatTaskUnitConfigVo.toString());
		int num = 0;
		try {
			BatTaskUnitConfig batTaskUnitConfig = new BatTaskUnitConfig();
			beanCopy(inBatTaskUnitConfigVo, batTaskUnitConfig);
			num = batTaskUnitConfigDao.insertBatTaskUnitConfig(batTaskUnitConfig);
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
	public int deleteByPk(BatTaskUnitConfigVO inBatTaskUnitConfigVo) {
		logger.debug("当前删除数据条件为:"+ inBatTaskUnitConfigVo);
		int num = 0;
		try {
			BatTaskUnitConfig batTaskUnitConfig = new BatTaskUnitConfig();
			beanCopy(inBatTaskUnitConfigVo, batTaskUnitConfig);
			num = batTaskUnitConfigDao.deleteByPk(batTaskUnitConfig);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inBatTaskUnitConfigVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(BatTaskUnitConfigVO inBatTaskUnitConfigVo) {
		logger.debug("当前修改数据为:"+ inBatTaskUnitConfigVo.toString());
		int num = 0;
		try {
			BatTaskUnitConfig batTaskUnitConfig = new BatTaskUnitConfig();
			beanCopy(inBatTaskUnitConfigVo, batTaskUnitConfig);
			num = batTaskUnitConfigDao.updateByPk(batTaskUnitConfig);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inBatTaskUnitConfigVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public BatTaskUnitConfigVO queryByPk(BatTaskUnitConfigVO inBatTaskUnitConfigVo) {
		
		logger.debug("当前查询参数信息为:"+ inBatTaskUnitConfigVo);
		try {
			BatTaskUnitConfig queryBatTaskUnitConfig = new BatTaskUnitConfig();
			beanCopy(inBatTaskUnitConfigVo, queryBatTaskUnitConfig);
			BatTaskUnitConfig queryRslBatTaskUnitConfig = batTaskUnitConfigDao.queryByPk(queryBatTaskUnitConfig);
			if (Objects.nonNull(queryRslBatTaskUnitConfig)) {
				BatTaskUnitConfigVO outBatTaskUnitConfigVo = beanCopy(queryRslBatTaskUnitConfig, new BatTaskUnitConfigVO());
				logger.debug("当前查询结果为:"+ outBatTaskUnitConfigVo.toString());
				return outBatTaskUnitConfigVo;
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
	public List<BatTaskUnitConfigVO> queryAllOwner(BatTaskUnitConfigVO batTaskUnitConfigVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<BatTaskUnitConfigVO> list = null;
		try {
			List<BatTaskUnitConfig> batTaskUnitConfigs = batTaskUnitConfigDao.queryAllOwnerByPage(batTaskUnitConfigVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ batTaskUnitConfigs.size());
			pageSet(batTaskUnitConfigs, batTaskUnitConfigVo);
			list = (List<BatTaskUnitConfigVO>) beansCopy(batTaskUnitConfigs, BatTaskUnitConfigVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<BatTaskUnitConfigVO> queryAllCurrOrg(BatTaskUnitConfigVO batTaskUnitConfigVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<BatTaskUnitConfig> batTaskUnitConfigs = batTaskUnitConfigDao.queryAllCurrOrgByPage(batTaskUnitConfigVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+batTaskUnitConfigs.size());
		List<BatTaskUnitConfigVO> list = null;
		try {
			pageSet(batTaskUnitConfigs, batTaskUnitConfigVo);
			list = (List<BatTaskUnitConfigVO>) beansCopy(batTaskUnitConfigs, BatTaskUnitConfigVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<BatTaskUnitConfigVO> queryAllCurrDownOrg(BatTaskUnitConfigVO batTaskUnitConfigVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<BatTaskUnitConfig> batTaskUnitConfigs = batTaskUnitConfigDao.queryAllCurrDownOrgByPage(batTaskUnitConfigVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ batTaskUnitConfigs.size());
		List<BatTaskUnitConfigVO> list = null;
		try {
			pageSet(batTaskUnitConfigs, batTaskUnitConfigVo);
			list = (List<BatTaskUnitConfigVO>) beansCopy(batTaskUnitConfigs, BatTaskUnitConfigVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
