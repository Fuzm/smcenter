package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.BatBatchInfoConfigService;
import com.irdstudio.smcenter.console.dao.BatBatchInfoConfigDao;
import com.irdstudio.smcenter.console.dao.domain.BatBatchInfoConfig;
import com.irdstudio.smcenter.console.service.vo.BatBatchInfoConfigVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 批次信息配置				<p>
 * @author ligm
 * @date 2018-06-14
 */
@Service("batchConfigService")
public class BatBatchInfoConfigServiceImpl implements BatBatchInfoConfigService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(BatBatchInfoConfigServiceImpl.class);

	@Autowired
	private BatBatchInfoConfigDao batBatchInfoConfigDao;
	
	/**
	 * 新增操作
	 */
	public int insertBatBatchInfoConfig(BatBatchInfoConfigVO inBatBatchInfoConfigVo) {
		logger.debug("当前新增数据为:"+ inBatBatchInfoConfigVo.toString());
		int num = 0;
		try {
			BatBatchInfoConfig batBatchInfoConfig = new BatBatchInfoConfig();
			beanCopy(inBatBatchInfoConfigVo, batBatchInfoConfig);
			num = batBatchInfoConfigDao.insertBatBatchInfoConfig(batBatchInfoConfig);
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
	public int deleteByPk(BatBatchInfoConfigVO inBatBatchInfoConfigVo) {
		logger.debug("当前删除数据条件为:"+ inBatBatchInfoConfigVo);
		int num = 0;
		try {
			BatBatchInfoConfig batBatchInfoConfig = new BatBatchInfoConfig();
			beanCopy(inBatBatchInfoConfigVo, batBatchInfoConfig);
			num = batBatchInfoConfigDao.deleteByPk(batBatchInfoConfig);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inBatBatchInfoConfigVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(BatBatchInfoConfigVO inBatBatchInfoConfigVo) {
		logger.debug("当前修改数据为:"+ inBatBatchInfoConfigVo.toString());
		int num = 0;
		try {
			BatBatchInfoConfig batBatchInfoConfig = new BatBatchInfoConfig();
			beanCopy(inBatBatchInfoConfigVo, batBatchInfoConfig);
			num = batBatchInfoConfigDao.updateByPk(batBatchInfoConfig);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inBatBatchInfoConfigVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public BatBatchInfoConfigVO queryByPk(BatBatchInfoConfigVO inBatBatchInfoConfigVo) {
		
		logger.debug("当前查询参数信息为:"+ inBatBatchInfoConfigVo);
		try {
			BatBatchInfoConfig queryBatBatchInfoConfig = new BatBatchInfoConfig();
			beanCopy(inBatBatchInfoConfigVo, queryBatBatchInfoConfig);
			BatBatchInfoConfig queryRslBatBatchInfoConfig = batBatchInfoConfigDao.queryByPk(queryBatBatchInfoConfig);
			if (Objects.nonNull(queryRslBatBatchInfoConfig)) {
				BatBatchInfoConfigVO outBatBatchInfoConfigVo = beanCopy(queryRslBatBatchInfoConfig, new BatBatchInfoConfigVO());
				logger.debug("当前查询结果为:"+ outBatBatchInfoConfigVo.toString());
				return outBatBatchInfoConfigVo;
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
	public List<BatBatchInfoConfigVO> queryAllOwner(BatBatchInfoConfigVO batBatchInfoConfigVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<BatBatchInfoConfigVO> list = null;
		try {
			List<BatBatchInfoConfig> batBatchInfoConfigs = batBatchInfoConfigDao.queryAllOwnerByPage(batBatchInfoConfigVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ batBatchInfoConfigs.size());
			pageSet(batBatchInfoConfigs, batBatchInfoConfigVo);
			list = (List<BatBatchInfoConfigVO>) beansCopy(batBatchInfoConfigs, BatBatchInfoConfigVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<BatBatchInfoConfigVO> queryAllCurrOrg(BatBatchInfoConfigVO batBatchInfoConfigVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<BatBatchInfoConfig> batBatchInfoConfigs = batBatchInfoConfigDao.queryAllCurrOrgByPage(batBatchInfoConfigVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+batBatchInfoConfigs.size());
		List<BatBatchInfoConfigVO> list = null;
		try {
			pageSet(batBatchInfoConfigs, batBatchInfoConfigVo);
			list = (List<BatBatchInfoConfigVO>) beansCopy(batBatchInfoConfigs, BatBatchInfoConfigVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<BatBatchInfoConfigVO> queryAllCurrDownOrg(BatBatchInfoConfigVO batBatchInfoConfigVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<BatBatchInfoConfig> batBatchInfoConfigs = batBatchInfoConfigDao.queryAllCurrDownOrgByPage(batBatchInfoConfigVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ batBatchInfoConfigs.size());
		List<BatBatchInfoConfigVO> list = null;
		try {
			pageSet(batBatchInfoConfigs, batBatchInfoConfigVo);
			list = (List<BatBatchInfoConfigVO>) beansCopy(batBatchInfoConfigs, BatBatchInfoConfigVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
