package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.SSrvsCronHisService;
import com.irdstudio.smcenter.console.dao.SSrvsCronHisDao;
import com.irdstudio.smcenter.console.dao.domain.SSrvsCronHis;
import com.irdstudio.smcenter.console.service.vo.SSrvsCronHisVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 定时调度历史				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("sSrvsCronHisService")
public class SSrvsCronHisServiceImpl implements SSrvsCronHisService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(SSrvsCronHisServiceImpl.class);

	@Autowired
	private SSrvsCronHisDao sSrvsCronHisDao;
	
	/**
	 * 新增操作
	 */
	public int insertSSrvsCronHis(SSrvsCronHisVO inSSrvsCronHisVo) {
		logger.debug("当前新增数据为:"+ inSSrvsCronHisVo.toString());
		int num = 0;
		try {
			SSrvsCronHis sSrvsCronHis = new SSrvsCronHis();
			beanCopy(inSSrvsCronHisVo, sSrvsCronHis);
			num = sSrvsCronHisDao.insertSSrvsCronHis(sSrvsCronHis);
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
	public int deleteByPk(SSrvsCronHisVO inSSrvsCronHisVo) {
		logger.debug("当前删除数据条件为:"+ inSSrvsCronHisVo);
		int num = 0;
		try {
			SSrvsCronHis sSrvsCronHis = new SSrvsCronHis();
			beanCopy(inSSrvsCronHisVo, sSrvsCronHis);
			num = sSrvsCronHisDao.deleteByPk(sSrvsCronHis);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inSSrvsCronHisVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(SSrvsCronHisVO inSSrvsCronHisVo) {
		logger.debug("当前修改数据为:"+ inSSrvsCronHisVo.toString());
		int num = 0;
		try {
			SSrvsCronHis sSrvsCronHis = new SSrvsCronHis();
			beanCopy(inSSrvsCronHisVo, sSrvsCronHis);
			num = sSrvsCronHisDao.updateByPk(sSrvsCronHis);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inSSrvsCronHisVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public SSrvsCronHisVO queryByPk(SSrvsCronHisVO inSSrvsCronHisVo) {
		
		logger.debug("当前查询参数信息为:"+ inSSrvsCronHisVo);
		try {
			SSrvsCronHis querySSrvsCronHis = new SSrvsCronHis();
			beanCopy(inSSrvsCronHisVo, querySSrvsCronHis);
			SSrvsCronHis queryRslSSrvsCronHis = sSrvsCronHisDao.queryByPk(querySSrvsCronHis);
			if (Objects.nonNull(queryRslSSrvsCronHis)) {
				SSrvsCronHisVO outSSrvsCronHisVo = beanCopy(queryRslSSrvsCronHis, new SSrvsCronHisVO());
				logger.debug("当前查询结果为:"+ outSSrvsCronHisVo.toString());
				return outSSrvsCronHisVo;
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
	public List<SSrvsCronHisVO> queryAllOwner(SSrvsCronHisVO sSrvsCronHisVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<SSrvsCronHisVO> list = null;
		try {
			List<SSrvsCronHis> sSrvsCronHiss = sSrvsCronHisDao.queryAllOwnerByPage(sSrvsCronHisVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ sSrvsCronHiss.size());
			pageSet(sSrvsCronHiss, sSrvsCronHisVo);
			list = (List<SSrvsCronHisVO>) beansCopy(sSrvsCronHiss, SSrvsCronHisVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<SSrvsCronHisVO> queryAllCurrOrg(SSrvsCronHisVO sSrvsCronHisVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<SSrvsCronHis> sSrvsCronHiss = sSrvsCronHisDao.queryAllCurrOrgByPage(sSrvsCronHisVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+sSrvsCronHiss.size());
		List<SSrvsCronHisVO> list = null;
		try {
			pageSet(sSrvsCronHiss, sSrvsCronHisVo);
			list = (List<SSrvsCronHisVO>) beansCopy(sSrvsCronHiss, SSrvsCronHisVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<SSrvsCronHisVO> queryAllCurrDownOrg(SSrvsCronHisVO sSrvsCronHisVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<SSrvsCronHis> sSrvsCronHiss = sSrvsCronHisDao.queryAllCurrDownOrgByPage(sSrvsCronHisVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ sSrvsCronHiss.size());
		List<SSrvsCronHisVO> list = null;
		try {
			pageSet(sSrvsCronHiss, sSrvsCronHisVo);
			list = (List<SSrvsCronHisVO>) beansCopy(sSrvsCronHiss, SSrvsCronHisVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
