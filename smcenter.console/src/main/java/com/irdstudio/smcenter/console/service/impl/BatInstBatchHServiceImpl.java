package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.BatInstBatchHService;
import com.irdstudio.smcenter.console.dao.BatInstBatchHDao;
import com.irdstudio.smcenter.console.dao.domain.BatInstBatchH;
import com.irdstudio.smcenter.console.service.vo.BatInstBatchHVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 批次历史信息				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("instBatchHService")
public class BatInstBatchHServiceImpl implements BatInstBatchHService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(BatInstBatchHServiceImpl.class);

	@Autowired
	private BatInstBatchHDao batInstBatchHDao;
	
	/**
	 * 新增操作
	 */
	public int insertBatInstBatchH(BatInstBatchHVO inBatInstBatchHVo) {
		logger.debug("当前新增数据为:"+ inBatInstBatchHVo.toString());
		int num = 0;
		try {
			BatInstBatchH batInstBatchH = new BatInstBatchH();
			beanCopy(inBatInstBatchHVo, batInstBatchH);
			num = batInstBatchHDao.insertBatInstBatchH(batInstBatchH);
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
	public int deleteByPk(BatInstBatchHVO inBatInstBatchHVo) {
		logger.debug("当前删除数据条件为:"+ inBatInstBatchHVo);
		int num = 0;
		try {
			BatInstBatchH batInstBatchH = new BatInstBatchH();
			beanCopy(inBatInstBatchHVo, batInstBatchH);
			num = batInstBatchHDao.deleteByPk(batInstBatchH);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inBatInstBatchHVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(BatInstBatchHVO inBatInstBatchHVo) {
		logger.debug("当前修改数据为:"+ inBatInstBatchHVo.toString());
		int num = 0;
		try {
			BatInstBatchH batInstBatchH = new BatInstBatchH();
			beanCopy(inBatInstBatchHVo, batInstBatchH);
			num = batInstBatchHDao.updateByPk(batInstBatchH);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inBatInstBatchHVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public BatInstBatchHVO queryByPk(BatInstBatchHVO inBatInstBatchHVo) {
		
		logger.debug("当前查询参数信息为:"+ inBatInstBatchHVo);
		try {
			BatInstBatchH queryBatInstBatchH = new BatInstBatchH();
			beanCopy(inBatInstBatchHVo, queryBatInstBatchH);
			BatInstBatchH queryRslBatInstBatchH = batInstBatchHDao.queryByPk(queryBatInstBatchH);
			if (Objects.nonNull(queryRslBatInstBatchH)) {
				BatInstBatchHVO outBatInstBatchHVo = beanCopy(queryRslBatInstBatchH, new BatInstBatchHVO());
				logger.debug("当前查询结果为:"+ outBatInstBatchHVo.toString());
				return outBatInstBatchHVo;
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
	public List<BatInstBatchHVO> queryAllOwner(BatInstBatchHVO batInstBatchHVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<BatInstBatchHVO> list = null;
		try {
			List<BatInstBatchH> batInstBatchHs = batInstBatchHDao.queryAllOwnerByPage(batInstBatchHVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ batInstBatchHs.size());
			pageSet(batInstBatchHs, batInstBatchHVo);
			list = (List<BatInstBatchHVO>) beansCopy(batInstBatchHs, BatInstBatchHVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<BatInstBatchHVO> queryAllCurrOrg(BatInstBatchHVO batInstBatchHVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<BatInstBatchH> batInstBatchHs = batInstBatchHDao.queryAllCurrOrgByPage(batInstBatchHVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+batInstBatchHs.size());
		List<BatInstBatchHVO> list = null;
		try {
			pageSet(batInstBatchHs, batInstBatchHVo);
			list = (List<BatInstBatchHVO>) beansCopy(batInstBatchHs, BatInstBatchHVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<BatInstBatchHVO> queryAllCurrDownOrg(BatInstBatchHVO batInstBatchHVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<BatInstBatchH> batInstBatchHs = batInstBatchHDao.queryAllCurrDownOrgByPage(batInstBatchHVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ batInstBatchHs.size());
		List<BatInstBatchHVO> list = null;
		try {
			pageSet(batInstBatchHs, batInstBatchHVo);
			list = (List<BatInstBatchHVO>) beansCopy(batInstBatchHs, BatInstBatchHVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
