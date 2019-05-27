package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.BatInstTaskHService;
import com.irdstudio.smcenter.console.dao.BatInstTaskHDao;
import com.irdstudio.smcenter.console.dao.domain.BatInstTaskH;
import com.irdstudio.smcenter.console.service.vo.BatInstTaskHVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 批次任务历史				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("instTaskHService")
public class BatInstTaskHServiceImpl implements BatInstTaskHService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(BatInstTaskHServiceImpl.class);

	@Autowired
	private BatInstTaskHDao batInstTaskHDao;
	
	/**
	 * 新增操作
	 */
	public int insertBatInstTaskH(BatInstTaskHVO inBatInstTaskHVo) {
		logger.debug("当前新增数据为:"+ inBatInstTaskHVo.toString());
		int num = 0;
		try {
			BatInstTaskH batInstTaskH = new BatInstTaskH();
			beanCopy(inBatInstTaskHVo, batInstTaskH);
			num = batInstTaskHDao.insertBatInstTaskH(batInstTaskH);
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
	public int deleteByPk(BatInstTaskHVO inBatInstTaskHVo) {
		logger.debug("当前删除数据条件为:"+ inBatInstTaskHVo);
		int num = 0;
		try {
			BatInstTaskH batInstTaskH = new BatInstTaskH();
			beanCopy(inBatInstTaskHVo, batInstTaskH);
			num = batInstTaskHDao.deleteByPk(batInstTaskH);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inBatInstTaskHVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(BatInstTaskHVO inBatInstTaskHVo) {
		logger.debug("当前修改数据为:"+ inBatInstTaskHVo.toString());
		int num = 0;
		try {
			BatInstTaskH batInstTaskH = new BatInstTaskH();
			beanCopy(inBatInstTaskHVo, batInstTaskH);
			num = batInstTaskHDao.updateByPk(batInstTaskH);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inBatInstTaskHVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public BatInstTaskHVO queryByPk(BatInstTaskHVO inBatInstTaskHVo) {
		
		logger.debug("当前查询参数信息为:"+ inBatInstTaskHVo);
		try {
			BatInstTaskH queryBatInstTaskH = new BatInstTaskH();
			beanCopy(inBatInstTaskHVo, queryBatInstTaskH);
			BatInstTaskH queryRslBatInstTaskH = batInstTaskHDao.queryByPk(queryBatInstTaskH);
			if (Objects.nonNull(queryRslBatInstTaskH)) {
				BatInstTaskHVO outBatInstTaskHVo = beanCopy(queryRslBatInstTaskH, new BatInstTaskHVO());
				logger.debug("当前查询结果为:"+ outBatInstTaskHVo.toString());
				return outBatInstTaskHVo;
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
	public List<BatInstTaskHVO> queryAllOwner(BatInstTaskHVO batInstTaskHVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<BatInstTaskHVO> list = null;
		try {
			List<BatInstTaskH> batInstTaskHs = batInstTaskHDao.queryAllOwnerByPage(batInstTaskHVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ batInstTaskHs.size());
			pageSet(batInstTaskHs, batInstTaskHVo);
			list = (List<BatInstTaskHVO>) beansCopy(batInstTaskHs, BatInstTaskHVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<BatInstTaskHVO> queryAllCurrOrg(BatInstTaskHVO batInstTaskHVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<BatInstTaskH> batInstTaskHs = batInstTaskHDao.queryAllCurrOrgByPage(batInstTaskHVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+batInstTaskHs.size());
		List<BatInstTaskHVO> list = null;
		try {
			pageSet(batInstTaskHs, batInstTaskHVo);
			list = (List<BatInstTaskHVO>) beansCopy(batInstTaskHs, BatInstTaskHVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<BatInstTaskHVO> queryAllCurrDownOrg(BatInstTaskHVO batInstTaskHVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<BatInstTaskH> batInstTaskHs = batInstTaskHDao.queryAllCurrDownOrgByPage(batInstTaskHVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ batInstTaskHs.size());
		List<BatInstTaskHVO> list = null;
		try {
			pageSet(batInstTaskHs, batInstTaskHVo);
			list = (List<BatInstTaskHVO>) beansCopy(batInstTaskHs, BatInstTaskHVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
