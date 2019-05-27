package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.SSrvsCronInstService;
import com.irdstudio.smcenter.console.dao.SSrvsCronInstDao;
import com.irdstudio.smcenter.console.dao.domain.SSrvsCronInst;
import com.irdstudio.smcenter.console.service.vo.SSrvsCronInstVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 定时调度实例				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("sSrvsCronInstService")
public class SSrvsCronInstServiceImpl implements SSrvsCronInstService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(SSrvsCronInstServiceImpl.class);

	@Autowired
	private SSrvsCronInstDao sSrvsCronInstDao;
	
	/**
	 * 新增操作
	 */
	public int insertSSrvsCronInst(SSrvsCronInstVO inSSrvsCronInstVo) {
		logger.debug("当前新增数据为:"+ inSSrvsCronInstVo.toString());
		int num = 0;
		try {
			SSrvsCronInst sSrvsCronInst = new SSrvsCronInst();
			beanCopy(inSSrvsCronInstVo, sSrvsCronInst);
			num = sSrvsCronInstDao.insertSSrvsCronInst(sSrvsCronInst);
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
	public int deleteByPk(SSrvsCronInstVO inSSrvsCronInstVo) {
		logger.debug("当前删除数据条件为:"+ inSSrvsCronInstVo);
		int num = 0;
		try {
			SSrvsCronInst sSrvsCronInst = new SSrvsCronInst();
			beanCopy(inSSrvsCronInstVo, sSrvsCronInst);
			num = sSrvsCronInstDao.deleteByPk(sSrvsCronInst);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inSSrvsCronInstVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(SSrvsCronInstVO inSSrvsCronInstVo) {
		logger.debug("当前修改数据为:"+ inSSrvsCronInstVo.toString());
		int num = 0;
		try {
			SSrvsCronInst sSrvsCronInst = new SSrvsCronInst();
			beanCopy(inSSrvsCronInstVo, sSrvsCronInst);
			num = sSrvsCronInstDao.updateByPk(sSrvsCronInst);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inSSrvsCronInstVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public SSrvsCronInstVO queryByPk(SSrvsCronInstVO inSSrvsCronInstVo) {
		
		logger.debug("当前查询参数信息为:"+ inSSrvsCronInstVo);
		try {
			SSrvsCronInst querySSrvsCronInst = new SSrvsCronInst();
			beanCopy(inSSrvsCronInstVo, querySSrvsCronInst);
			SSrvsCronInst queryRslSSrvsCronInst = sSrvsCronInstDao.queryByPk(querySSrvsCronInst);
			if (Objects.nonNull(queryRslSSrvsCronInst)) {
				SSrvsCronInstVO outSSrvsCronInstVo = beanCopy(queryRslSSrvsCronInst, new SSrvsCronInstVO());
				logger.debug("当前查询结果为:"+ outSSrvsCronInstVo.toString());
				return outSSrvsCronInstVo;
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
	public List<SSrvsCronInstVO> queryAllOwner(SSrvsCronInstVO sSrvsCronInstVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<SSrvsCronInstVO> list = null;
		try {
			List<SSrvsCronInst> sSrvsCronInsts = sSrvsCronInstDao.queryAllOwnerByPage(sSrvsCronInstVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ sSrvsCronInsts.size());
			pageSet(sSrvsCronInsts, sSrvsCronInstVo);
			list = (List<SSrvsCronInstVO>) beansCopy(sSrvsCronInsts, SSrvsCronInstVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<SSrvsCronInstVO> queryAllCurrOrg(SSrvsCronInstVO sSrvsCronInstVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<SSrvsCronInst> sSrvsCronInsts = sSrvsCronInstDao.queryAllCurrOrgByPage(sSrvsCronInstVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+sSrvsCronInsts.size());
		List<SSrvsCronInstVO> list = null;
		try {
			pageSet(sSrvsCronInsts, sSrvsCronInstVo);
			list = (List<SSrvsCronInstVO>) beansCopy(sSrvsCronInsts, SSrvsCronInstVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<SSrvsCronInstVO> queryAllCurrDownOrg(SSrvsCronInstVO sSrvsCronInstVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<SSrvsCronInst> sSrvsCronInsts = sSrvsCronInstDao.queryAllCurrDownOrgByPage(sSrvsCronInstVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ sSrvsCronInsts.size());
		List<SSrvsCronInstVO> list = null;
		try {
			pageSet(sSrvsCronInsts, sSrvsCronInstVo);
			list = (List<SSrvsCronInstVO>) beansCopy(sSrvsCronInsts, SSrvsCronInstVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
