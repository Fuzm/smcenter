package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.SAgentInfoService;
import com.irdstudio.smcenter.console.dao.SAgentInfoDao;
import com.irdstudio.smcenter.console.dao.domain.SAgentInfo;
import com.irdstudio.smcenter.console.service.vo.SAgentInfoVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 代理节点信息				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("sAgentInfoService")
public class SAgentInfoServiceImpl implements SAgentInfoService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(SAgentInfoServiceImpl.class);

	@Autowired
	private SAgentInfoDao sAgentInfoDao;
	
	/**
	 * 新增操作
	 */
	public int insertSAgentInfo(SAgentInfoVO inSAgentInfoVo) {
		logger.debug("当前新增数据为:"+ inSAgentInfoVo.toString());
		int num = 0;
		try {
			SAgentInfo sAgentInfo = new SAgentInfo();
			beanCopy(inSAgentInfoVo, sAgentInfo);
			num = sAgentInfoDao.insertSAgentInfo(sAgentInfo);
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
	public int deleteByPk(SAgentInfoVO inSAgentInfoVo) {
		logger.debug("当前删除数据条件为:"+ inSAgentInfoVo);
		int num = 0;
		try {
			SAgentInfo sAgentInfo = new SAgentInfo();
			beanCopy(inSAgentInfoVo, sAgentInfo);
			num = sAgentInfoDao.deleteByPk(sAgentInfo);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inSAgentInfoVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(SAgentInfoVO inSAgentInfoVo) {
		logger.debug("当前修改数据为:"+ inSAgentInfoVo.toString());
		int num = 0;
		try {
			SAgentInfo sAgentInfo = new SAgentInfo();
			beanCopy(inSAgentInfoVo, sAgentInfo);
			num = sAgentInfoDao.updateByPk(sAgentInfo);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inSAgentInfoVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public SAgentInfoVO queryByPk(SAgentInfoVO inSAgentInfoVo) {
		
		logger.debug("当前查询参数信息为:"+ inSAgentInfoVo);
		try {
			SAgentInfo querySAgentInfo = new SAgentInfo();
			beanCopy(inSAgentInfoVo, querySAgentInfo);
			SAgentInfo queryRslSAgentInfo = sAgentInfoDao.queryByPk(querySAgentInfo);
			if (Objects.nonNull(queryRslSAgentInfo)) {
				SAgentInfoVO outSAgentInfoVo = beanCopy(queryRslSAgentInfo, new SAgentInfoVO());
				logger.debug("当前查询结果为:"+ outSAgentInfoVo.toString());
				return outSAgentInfoVo;
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
	public List<SAgentInfoVO> queryAllOwner(SAgentInfoVO sAgentInfoVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<SAgentInfoVO> list = null;
		try {
			List<SAgentInfo> sAgentInfos = sAgentInfoDao.queryAllOwnerByPage(sAgentInfoVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ sAgentInfos.size());
			pageSet(sAgentInfos, sAgentInfoVo);
			list = (List<SAgentInfoVO>) beansCopy(sAgentInfos, SAgentInfoVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<SAgentInfoVO> queryAllCurrOrg(SAgentInfoVO sAgentInfoVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<SAgentInfo> sAgentInfos = sAgentInfoDao.queryAllCurrOrgByPage(sAgentInfoVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+sAgentInfos.size());
		List<SAgentInfoVO> list = null;
		try {
			pageSet(sAgentInfos, sAgentInfoVo);
			list = (List<SAgentInfoVO>) beansCopy(sAgentInfos, SAgentInfoVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<SAgentInfoVO> queryAllCurrDownOrg(SAgentInfoVO sAgentInfoVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<SAgentInfo> sAgentInfos = sAgentInfoDao.queryAllCurrDownOrgByPage(sAgentInfoVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ sAgentInfos.size());
		List<SAgentInfoVO> list = null;
		try {
			pageSet(sAgentInfos, sAgentInfoVo);
			list = (List<SAgentInfoVO>) beansCopy(sAgentInfos, SAgentInfoVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
