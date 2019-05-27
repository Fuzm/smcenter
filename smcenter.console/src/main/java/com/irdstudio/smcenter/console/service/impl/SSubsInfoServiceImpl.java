package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.SSubsInfoService;
import com.irdstudio.smcenter.console.dao.SSubsInfoDao;
import com.irdstudio.smcenter.console.dao.domain.SSubsInfo;
import com.irdstudio.smcenter.console.service.vo.SSubsInfoVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 子系统基础信息表				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("sSubsInfoService")
public class SSubsInfoServiceImpl implements SSubsInfoService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(SSubsInfoServiceImpl.class);

	@Autowired
	private SSubsInfoDao sSubsInfoDao;
	
	/**
	 * 新增操作
	 */
	public int insertSSubsInfo(SSubsInfoVO inSSubsInfoVo) {
		logger.debug("当前新增数据为:"+ inSSubsInfoVo.toString());
		int num = 0;
		try {
			SSubsInfo sSubsInfo = new SSubsInfo();
			beanCopy(inSSubsInfoVo, sSubsInfo);
			num = sSubsInfoDao.insertSSubsInfo(sSubsInfo);
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
	public int deleteByPk(SSubsInfoVO inSSubsInfoVo) {
		logger.debug("当前删除数据条件为:"+ inSSubsInfoVo);
		int num = 0;
		try {
			SSubsInfo sSubsInfo = new SSubsInfo();
			beanCopy(inSSubsInfoVo, sSubsInfo);
			num = sSubsInfoDao.deleteByPk(sSubsInfo);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inSSubsInfoVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(SSubsInfoVO inSSubsInfoVo) {
		logger.debug("当前修改数据为:"+ inSSubsInfoVo.toString());
		int num = 0;
		try {
			SSubsInfo sSubsInfo = new SSubsInfo();
			beanCopy(inSSubsInfoVo, sSubsInfo);
			num = sSubsInfoDao.updateByPk(sSubsInfo);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inSSubsInfoVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public SSubsInfoVO queryByPk(SSubsInfoVO inSSubsInfoVo) {
		
		logger.debug("当前查询参数信息为:"+ inSSubsInfoVo);
		try {
			SSubsInfo querySSubsInfo = new SSubsInfo();
			beanCopy(inSSubsInfoVo, querySSubsInfo);
			SSubsInfo queryRslSSubsInfo = sSubsInfoDao.queryByPk(querySSubsInfo);
			if (Objects.nonNull(queryRslSSubsInfo)) {
				SSubsInfoVO outSSubsInfoVo = beanCopy(queryRslSSubsInfo, new SSubsInfoVO());
				logger.debug("当前查询结果为:"+ outSSubsInfoVo.toString());
				return outSSubsInfoVo;
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
	public List<SSubsInfoVO> queryAllOwner(SSubsInfoVO sSubsInfoVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<SSubsInfoVO> list = null;
		try {
			List<SSubsInfo> sSubsInfos = sSubsInfoDao.queryAllOwnerByPage(sSubsInfoVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ sSubsInfos.size());
			pageSet(sSubsInfos, sSubsInfoVo);
			list = (List<SSubsInfoVO>) beansCopy(sSubsInfos, SSubsInfoVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<SSubsInfoVO> queryAllCurrOrg(SSubsInfoVO sSubsInfoVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<SSubsInfo> sSubsInfos = sSubsInfoDao.queryAllCurrOrgByPage(sSubsInfoVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+sSubsInfos.size());
		List<SSubsInfoVO> list = null;
		try {
			pageSet(sSubsInfos, sSubsInfoVo);
			list = (List<SSubsInfoVO>) beansCopy(sSubsInfos, SSubsInfoVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<SSubsInfoVO> queryAllCurrDownOrg(SSubsInfoVO sSubsInfoVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<SSubsInfo> sSubsInfos = sSubsInfoDao.queryAllCurrDownOrgByPage(sSubsInfoVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ sSubsInfos.size());
		List<SSubsInfoVO> list = null;
		try {
			pageSet(sSubsInfos, sSubsInfoVo);
			list = (List<SSubsInfoVO>) beansCopy(sSubsInfos, SSubsInfoVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
