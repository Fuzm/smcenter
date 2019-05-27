package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.SParamInfoService;
import com.irdstudio.smcenter.console.dao.SParamInfoDao;
import com.irdstudio.smcenter.console.dao.domain.SParamInfo;
import com.irdstudio.smcenter.console.service.vo.SParamInfoVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 参数信息表				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("sParamInfoService")
public class SParamInfoServiceImpl implements SParamInfoService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(SParamInfoServiceImpl.class);

	@Autowired
	private SParamInfoDao sParamInfoDao;
	
	/**
	 * 新增操作
	 */
	public int insertSParamInfo(SParamInfoVO inSParamInfoVo) {
		logger.debug("当前新增数据为:"+ inSParamInfoVo.toString());
		int num = 0;
		try {
			SParamInfo sParamInfo = new SParamInfo();
			beanCopy(inSParamInfoVo, sParamInfo);
			num = sParamInfoDao.insertSParamInfo(sParamInfo);
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
	public int deleteByPk(SParamInfoVO inSParamInfoVo) {
		logger.debug("当前删除数据条件为:"+ inSParamInfoVo);
		int num = 0;
		try {
			SParamInfo sParamInfo = new SParamInfo();
			beanCopy(inSParamInfoVo, sParamInfo);
			num = sParamInfoDao.deleteByPk(sParamInfo);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inSParamInfoVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(SParamInfoVO inSParamInfoVo) {
		logger.debug("当前修改数据为:"+ inSParamInfoVo.toString());
		int num = 0;
		try {
			SParamInfo sParamInfo = new SParamInfo();
			beanCopy(inSParamInfoVo, sParamInfo);
			num = sParamInfoDao.updateByPk(sParamInfo);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inSParamInfoVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public SParamInfoVO queryByPk(SParamInfoVO inSParamInfoVo) {
		
		logger.debug("当前查询参数信息为:"+ inSParamInfoVo);
		try {
			SParamInfo querySParamInfo = new SParamInfo();
			beanCopy(inSParamInfoVo, querySParamInfo);
			SParamInfo queryRslSParamInfo = sParamInfoDao.queryByPk(querySParamInfo);
			if (Objects.nonNull(queryRslSParamInfo)) {
				SParamInfoVO outSParamInfoVo = beanCopy(queryRslSParamInfo, new SParamInfoVO());
				logger.debug("当前查询结果为:"+ outSParamInfoVo.toString());
				return outSParamInfoVo;
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
	public List<SParamInfoVO> queryAllOwner(SParamInfoVO sParamInfoVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<SParamInfoVO> list = null;
		try {
			List<SParamInfo> sParamInfos = sParamInfoDao.queryAllOwnerByPage(sParamInfoVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ sParamInfos.size());
			pageSet(sParamInfos, sParamInfoVo);
			list = (List<SParamInfoVO>) beansCopy(sParamInfos, SParamInfoVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<SParamInfoVO> queryAllCurrOrg(SParamInfoVO sParamInfoVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<SParamInfo> sParamInfos = sParamInfoDao.queryAllCurrOrgByPage(sParamInfoVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+sParamInfos.size());
		List<SParamInfoVO> list = null;
		try {
			pageSet(sParamInfos, sParamInfoVo);
			list = (List<SParamInfoVO>) beansCopy(sParamInfos, SParamInfoVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<SParamInfoVO> queryAllCurrDownOrg(SParamInfoVO sParamInfoVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<SParamInfo> sParamInfos = sParamInfoDao.queryAllCurrDownOrgByPage(sParamInfoVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ sParamInfos.size());
		List<SParamInfoVO> list = null;
		try {
			pageSet(sParamInfos, sParamInfoVo);
			list = (List<SParamInfoVO>) beansCopy(sParamInfos, SParamInfoVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
