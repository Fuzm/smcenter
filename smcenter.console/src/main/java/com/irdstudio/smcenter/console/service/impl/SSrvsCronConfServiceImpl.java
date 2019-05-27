package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.SSrvsCronConfService;
import com.irdstudio.smcenter.console.dao.SSrvsCronConfDao;
import com.irdstudio.smcenter.console.dao.domain.SSrvsCronConf;
import com.irdstudio.smcenter.console.service.vo.SSrvsCronConfVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 定时调度配置				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("sSrvsCronConfService")
public class SSrvsCronConfServiceImpl implements SSrvsCronConfService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(SSrvsCronConfServiceImpl.class);

	@Autowired
	private SSrvsCronConfDao sSrvsCronConfDao;
	
	/**
	 * 新增操作
	 */
	public int insertSSrvsCronConf(SSrvsCronConfVO inSSrvsCronConfVo) {
		logger.debug("当前新增数据为:"+ inSSrvsCronConfVo.toString());
		int num = 0;
		try {
			SSrvsCronConf sSrvsCronConf = new SSrvsCronConf();
			beanCopy(inSSrvsCronConfVo, sSrvsCronConf);
			num = sSrvsCronConfDao.insertSSrvsCronConf(sSrvsCronConf);
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
	public int deleteByPk(SSrvsCronConfVO inSSrvsCronConfVo) {
		logger.debug("当前删除数据条件为:"+ inSSrvsCronConfVo);
		int num = 0;
		try {
			SSrvsCronConf sSrvsCronConf = new SSrvsCronConf();
			beanCopy(inSSrvsCronConfVo, sSrvsCronConf);
			num = sSrvsCronConfDao.deleteByPk(sSrvsCronConf);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inSSrvsCronConfVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(SSrvsCronConfVO inSSrvsCronConfVo) {
		logger.debug("当前修改数据为:"+ inSSrvsCronConfVo.toString());
		int num = 0;
		try {
			SSrvsCronConf sSrvsCronConf = new SSrvsCronConf();
			beanCopy(inSSrvsCronConfVo, sSrvsCronConf);
			num = sSrvsCronConfDao.updateByPk(sSrvsCronConf);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inSSrvsCronConfVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public SSrvsCronConfVO queryByPk(SSrvsCronConfVO inSSrvsCronConfVo) {
		
		logger.debug("当前查询参数信息为:"+ inSSrvsCronConfVo);
		try {
			SSrvsCronConf querySSrvsCronConf = new SSrvsCronConf();
			beanCopy(inSSrvsCronConfVo, querySSrvsCronConf);
			SSrvsCronConf queryRslSSrvsCronConf = sSrvsCronConfDao.queryByPk(querySSrvsCronConf);
			if (Objects.nonNull(queryRslSSrvsCronConf)) {
				SSrvsCronConfVO outSSrvsCronConfVo = beanCopy(queryRslSSrvsCronConf, new SSrvsCronConfVO());
				logger.debug("当前查询结果为:"+ outSSrvsCronConfVo.toString());
				return outSSrvsCronConfVo;
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
	public List<SSrvsCronConfVO> queryAllOwner(SSrvsCronConfVO sSrvsCronConfVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<SSrvsCronConfVO> list = null;
		try {
			List<SSrvsCronConf> sSrvsCronConfs = sSrvsCronConfDao.queryAllOwnerByPage(sSrvsCronConfVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ sSrvsCronConfs.size());
			pageSet(sSrvsCronConfs, sSrvsCronConfVo);
			list = (List<SSrvsCronConfVO>) beansCopy(sSrvsCronConfs, SSrvsCronConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<SSrvsCronConfVO> queryAllCurrOrg(SSrvsCronConfVO sSrvsCronConfVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<SSrvsCronConf> sSrvsCronConfs = sSrvsCronConfDao.queryAllCurrOrgByPage(sSrvsCronConfVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+sSrvsCronConfs.size());
		List<SSrvsCronConfVO> list = null;
		try {
			pageSet(sSrvsCronConfs, sSrvsCronConfVo);
			list = (List<SSrvsCronConfVO>) beansCopy(sSrvsCronConfs, SSrvsCronConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<SSrvsCronConfVO> queryAllCurrDownOrg(SSrvsCronConfVO sSrvsCronConfVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<SSrvsCronConf> sSrvsCronConfs = sSrvsCronConfDao.queryAllCurrDownOrgByPage(sSrvsCronConfVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ sSrvsCronConfs.size());
		List<SSrvsCronConfVO> list = null;
		try {
			pageSet(sSrvsCronConfs, sSrvsCronConfVo);
			list = (List<SSrvsCronConfVO>) beansCopy(sSrvsCronConfs, SSrvsCronConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
