package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.BatInstBatchService;
import com.irdstudio.smcenter.console.dao.BatInstBatchDao;
import com.irdstudio.smcenter.console.dao.domain.BatInstBatch;
import com.irdstudio.smcenter.console.service.vo.BatInstBatchVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 批次实例信息				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("instBatchService")
public class BatInstBatchServiceImpl implements BatInstBatchService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(BatInstBatchServiceImpl.class);

	@Autowired
	private BatInstBatchDao batInstBatchDao;
	
	/**
	 * 新增操作
	 */
	public int insertBatInstBatch(BatInstBatchVO inBatInstBatchVo) {
		logger.debug("当前新增数据为:"+ inBatInstBatchVo.toString());
		int num = 0;
		try {
			BatInstBatch batInstBatch = new BatInstBatch();
			beanCopy(inBatInstBatchVo, batInstBatch);
			num = batInstBatchDao.insertBatInstBatch(batInstBatch);
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
	public int deleteByPk(BatInstBatchVO inBatInstBatchVo) {
		logger.debug("当前删除数据条件为:"+ inBatInstBatchVo);
		int num = 0;
		try {
			BatInstBatch batInstBatch = new BatInstBatch();
			beanCopy(inBatInstBatchVo, batInstBatch);
			num = batInstBatchDao.deleteByPk(batInstBatch);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inBatInstBatchVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(BatInstBatchVO inBatInstBatchVo) {
		logger.debug("当前修改数据为:"+ inBatInstBatchVo.toString());
		int num = 0;
		try {
			BatInstBatch batInstBatch = new BatInstBatch();
			beanCopy(inBatInstBatchVo, batInstBatch);
			num = batInstBatchDao.updateByPk(batInstBatch);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inBatInstBatchVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public BatInstBatchVO queryByPk(BatInstBatchVO inBatInstBatchVo) {
		
		logger.debug("当前查询参数信息为:"+ inBatInstBatchVo);
		try {
			BatInstBatch queryBatInstBatch = new BatInstBatch();
			beanCopy(inBatInstBatchVo, queryBatInstBatch);
			BatInstBatch queryRslBatInstBatch = batInstBatchDao.queryByPk(queryBatInstBatch);
			if (Objects.nonNull(queryRslBatInstBatch)) {
				BatInstBatchVO outBatInstBatchVo = beanCopy(queryRslBatInstBatch, new BatInstBatchVO());
				logger.debug("当前查询结果为:"+ outBatInstBatchVo.toString());
				return outBatInstBatchVo;
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
	public List<BatInstBatchVO> queryAllOwner(BatInstBatchVO batInstBatchVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<BatInstBatchVO> list = null;
		try {
			List<BatInstBatch> batInstBatchs = batInstBatchDao.queryAllOwnerByPage(batInstBatchVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ batInstBatchs.size());
			pageSet(batInstBatchs, batInstBatchVo);
			list = (List<BatInstBatchVO>) beansCopy(batInstBatchs, BatInstBatchVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<BatInstBatchVO> queryAllCurrOrg(BatInstBatchVO batInstBatchVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<BatInstBatch> batInstBatchs = batInstBatchDao.queryAllCurrOrgByPage(batInstBatchVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+batInstBatchs.size());
		List<BatInstBatchVO> list = null;
		try {
			pageSet(batInstBatchs, batInstBatchVo);
			list = (List<BatInstBatchVO>) beansCopy(batInstBatchs, BatInstBatchVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<BatInstBatchVO> queryAllCurrDownOrg(BatInstBatchVO batInstBatchVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<BatInstBatch> batInstBatchs = batInstBatchDao.queryAllCurrDownOrgByPage(batInstBatchVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ batInstBatchs.size());
		List<BatInstBatchVO> list = null;
		try {
			pageSet(batInstBatchs, batInstBatchVo);
			list = (List<BatInstBatchVO>) beansCopy(batInstBatchs, BatInstBatchVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}

	@Override
	public int updateBatInstBatchStatus(BatInstBatchVO batInstBatchVo) {
		logger.debug("当前修改状态数据为:"+ batInstBatchVo.toString());
		int num = 0;
		try {
			BatInstBatch batInstBatch = new BatInstBatch();
			beanCopy(batInstBatchVo, batInstBatch);
			num = batInstBatchDao.updateBatInstBatchStatus(batInstBatch);
		} catch (Exception e) {
			logger.error("修改状态数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ batInstBatchVo +"修改的状态数据条数为"+ num);
		return num;
	}
}
