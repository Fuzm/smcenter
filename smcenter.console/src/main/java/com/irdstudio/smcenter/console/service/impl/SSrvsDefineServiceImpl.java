package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.SSrvsDefineService;
import com.irdstudio.smcenter.console.dao.SSrvsDefineDao;
import com.irdstudio.smcenter.console.dao.domain.SSrvsDefine;
import com.irdstudio.smcenter.console.service.vo.SSrvsDefineVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 01.服务组件信息				<p>
 * @author ligm
 * @date 2018-06-09
 */
@Service("sSrvsDefineService")
public class SSrvsDefineServiceImpl implements SSrvsDefineService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(SSrvsDefineServiceImpl.class);

	@Autowired
	private SSrvsDefineDao sSrvsDefineDao;
	
	/**
	 * 新增操作
	 */
	public int insertSSrvsDefine(SSrvsDefineVO inSSrvsDefineVo) {
		logger.debug("当前新增数据为:"+ inSSrvsDefineVo.toString());
		int num = 0;
		try {
			SSrvsDefine sSrvsDefine = new SSrvsDefine();
			beanCopy(inSSrvsDefineVo, sSrvsDefine);
			num = sSrvsDefineDao.insertSSrvsDefine(sSrvsDefine);
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
	public int deleteByPk(SSrvsDefineVO inSSrvsDefineVo) {
		logger.debug("当前删除数据条件为:"+ inSSrvsDefineVo);
		int num = 0;
		try {
			SSrvsDefine sSrvsDefine = new SSrvsDefine();
			beanCopy(inSSrvsDefineVo, sSrvsDefine);
			num = sSrvsDefineDao.deleteByPk(sSrvsDefine);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inSSrvsDefineVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(SSrvsDefineVO inSSrvsDefineVo) {
		logger.debug("当前修改数据为:"+ inSSrvsDefineVo.toString());
		int num = 0;
		try {
			SSrvsDefine sSrvsDefine = new SSrvsDefine();
			beanCopy(inSSrvsDefineVo, sSrvsDefine);
			num = sSrvsDefineDao.updateByPk(sSrvsDefine);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inSSrvsDefineVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public SSrvsDefineVO queryByPk(SSrvsDefineVO inSSrvsDefineVo) {
		
		logger.debug("当前查询参数信息为:"+ inSSrvsDefineVo);
		try {
			SSrvsDefine querySSrvsDefine = new SSrvsDefine();
			beanCopy(inSSrvsDefineVo, querySSrvsDefine);
			SSrvsDefine queryRslSSrvsDefine = sSrvsDefineDao.queryByPk(querySSrvsDefine);
			if (Objects.nonNull(queryRslSSrvsDefine)) {
				SSrvsDefineVO outSSrvsDefineVo = beanCopy(queryRslSSrvsDefine, new SSrvsDefineVO());
				logger.debug("当前查询结果为:"+ outSSrvsDefineVo.toString());
				return outSSrvsDefineVo;
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
	public List<SSrvsDefineVO> queryAllOwner(SSrvsDefineVO sSrvsDefineVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<SSrvsDefineVO> list = null;
		try {
			List<SSrvsDefine> sSrvsDefines = sSrvsDefineDao.queryAllOwnerByPage(sSrvsDefineVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ sSrvsDefines.size());
			pageSet(sSrvsDefines, sSrvsDefineVo);
			list = (List<SSrvsDefineVO>) beansCopy(sSrvsDefines, SSrvsDefineVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<SSrvsDefineVO> queryAllCurrOrg(SSrvsDefineVO sSrvsDefineVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<SSrvsDefine> sSrvsDefines = sSrvsDefineDao.queryAllCurrOrgByPage(sSrvsDefineVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+sSrvsDefines.size());
		List<SSrvsDefineVO> list = null;
		try {
			pageSet(sSrvsDefines, sSrvsDefineVo);
			list = (List<SSrvsDefineVO>) beansCopy(sSrvsDefines, SSrvsDefineVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<SSrvsDefineVO> queryAllCurrDownOrg(SSrvsDefineVO sSrvsDefineVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<SSrvsDefine> sSrvsDefines = sSrvsDefineDao.queryAllCurrDownOrgByPage(sSrvsDefineVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ sSrvsDefines.size());
		List<SSrvsDefineVO> list = null;
		try {
			pageSet(sSrvsDefines, sSrvsDefineVo);
			list = (List<SSrvsDefineVO>) beansCopy(sSrvsDefines, SSrvsDefineVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
