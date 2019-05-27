package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.BatBatchStageConfigService;
import com.irdstudio.smcenter.console.dao.BatBatchStageConfigDao;
import com.irdstudio.smcenter.console.dao.domain.BatBatchStageConfig;
import com.irdstudio.smcenter.console.service.vo.BatBatchStageConfigVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 批次任务阶段				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("stageConfigService")
public class BatBatchStageConfigServiceImpl implements BatBatchStageConfigService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(BatBatchStageConfigServiceImpl.class);

	@Autowired
	private BatBatchStageConfigDao batBatchStageConfigDao;
	
	/**
	 * 新增操作
	 */
	public int insertBatBatchStageConfig(BatBatchStageConfigVO inBatBatchStageConfigVo) {
		logger.debug("当前新增数据为:"+ inBatBatchStageConfigVo.toString());
		int num = 0;
		try {
			BatBatchStageConfig batBatchStageConfig = new BatBatchStageConfig();
			beanCopy(inBatBatchStageConfigVo, batBatchStageConfig);
			num = batBatchStageConfigDao.insertBatBatchStageConfig(batBatchStageConfig);
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
	public int deleteByPk(BatBatchStageConfigVO inBatBatchStageConfigVo) {
		logger.debug("当前删除数据条件为:"+ inBatBatchStageConfigVo);
		int num = 0;
		try {
			BatBatchStageConfig batBatchStageConfig = new BatBatchStageConfig();
			beanCopy(inBatBatchStageConfigVo, batBatchStageConfig);
			num = batBatchStageConfigDao.deleteByPk(batBatchStageConfig);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inBatBatchStageConfigVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(BatBatchStageConfigVO inBatBatchStageConfigVo) {
		logger.debug("当前修改数据为:"+ inBatBatchStageConfigVo.toString());
		int num = 0;
		try {
			BatBatchStageConfig batBatchStageConfig = new BatBatchStageConfig();
			beanCopy(inBatBatchStageConfigVo, batBatchStageConfig);
			num = batBatchStageConfigDao.updateByPk(batBatchStageConfig);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inBatBatchStageConfigVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public BatBatchStageConfigVO queryByPk(BatBatchStageConfigVO inBatBatchStageConfigVo) {
		
		logger.debug("当前查询参数信息为:"+ inBatBatchStageConfigVo);
		try {
			BatBatchStageConfig queryBatBatchStageConfig = new BatBatchStageConfig();
			beanCopy(inBatBatchStageConfigVo, queryBatBatchStageConfig);
			BatBatchStageConfig queryRslBatBatchStageConfig = batBatchStageConfigDao.queryByPk(queryBatBatchStageConfig);
			if (Objects.nonNull(queryRslBatBatchStageConfig)) {
				BatBatchStageConfigVO outBatBatchStageConfigVo = beanCopy(queryRslBatBatchStageConfig, new BatBatchStageConfigVO());
				logger.debug("当前查询结果为:"+ outBatBatchStageConfigVo.toString());
				return outBatBatchStageConfigVo;
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
	public List<BatBatchStageConfigVO> queryAllOwner(BatBatchStageConfigVO batBatchStageConfigVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<BatBatchStageConfigVO> list = null;
		try {
			List<BatBatchStageConfig> batBatchStageConfigs = batBatchStageConfigDao.queryAllOwnerByPage(batBatchStageConfigVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ batBatchStageConfigs.size());
			pageSet(batBatchStageConfigs, batBatchStageConfigVo);
			list = (List<BatBatchStageConfigVO>) beansCopy(batBatchStageConfigs, BatBatchStageConfigVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<BatBatchStageConfigVO> queryAllCurrOrg(BatBatchStageConfigVO batBatchStageConfigVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<BatBatchStageConfig> batBatchStageConfigs = batBatchStageConfigDao.queryAllCurrOrgByPage(batBatchStageConfigVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+batBatchStageConfigs.size());
		List<BatBatchStageConfigVO> list = null;
		try {
			pageSet(batBatchStageConfigs, batBatchStageConfigVo);
			list = (List<BatBatchStageConfigVO>) beansCopy(batBatchStageConfigs, BatBatchStageConfigVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<BatBatchStageConfigVO> queryAllCurrDownOrg(BatBatchStageConfigVO batBatchStageConfigVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<BatBatchStageConfig> batBatchStageConfigs = batBatchStageConfigDao.queryAllCurrDownOrgByPage(batBatchStageConfigVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ batBatchStageConfigs.size());
		List<BatBatchStageConfigVO> list = null;
		try {
			pageSet(batBatchStageConfigs, batBatchStageConfigVo);
			list = (List<BatBatchStageConfigVO>) beansCopy(batBatchStageConfigs, BatBatchStageConfigVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
