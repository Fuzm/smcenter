package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.SSubsDatasourceService;
import com.irdstudio.smcenter.console.dao.SSubsDatasourceDao;
import com.irdstudio.smcenter.console.dao.domain.SSubsDatasource;
import com.irdstudio.smcenter.console.service.vo.SSubsDatasourceVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 子系统数据源信息表				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("sSubsDatasourceService")
public class SSubsDatasourceServiceImpl implements SSubsDatasourceService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(SSubsDatasourceServiceImpl.class);

	@Autowired
	private SSubsDatasourceDao sSubsDatasourceDao;
	
	/**
	 * 新增操作
	 */
	public int insertSSubsDatasource(SSubsDatasourceVO inSSubsDatasourceVo) {
		logger.debug("当前新增数据为:"+ inSSubsDatasourceVo.toString());
		int num = 0;
		try {
			SSubsDatasource sSubsDatasource = new SSubsDatasource();
			beanCopy(inSSubsDatasourceVo, sSubsDatasource);
			num = sSubsDatasourceDao.insertSSubsDatasource(sSubsDatasource);
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
	public int deleteByPk(SSubsDatasourceVO inSSubsDatasourceVo) {
		logger.debug("当前删除数据条件为:"+ inSSubsDatasourceVo);
		int num = 0;
		try {
			SSubsDatasource sSubsDatasource = new SSubsDatasource();
			beanCopy(inSSubsDatasourceVo, sSubsDatasource);
			num = sSubsDatasourceDao.deleteByPk(sSubsDatasource);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inSSubsDatasourceVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(SSubsDatasourceVO inSSubsDatasourceVo) {
		logger.debug("当前修改数据为:"+ inSSubsDatasourceVo.toString());
		int num = 0;
		try {
			SSubsDatasource sSubsDatasource = new SSubsDatasource();
			beanCopy(inSSubsDatasourceVo, sSubsDatasource);
			num = sSubsDatasourceDao.updateByPk(sSubsDatasource);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inSSubsDatasourceVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public SSubsDatasourceVO queryByPk(SSubsDatasourceVO inSSubsDatasourceVo) {
		
		logger.debug("当前查询参数信息为:"+ inSSubsDatasourceVo);
		try {
			SSubsDatasource querySSubsDatasource = new SSubsDatasource();
			beanCopy(inSSubsDatasourceVo, querySSubsDatasource);
			SSubsDatasource queryRslSSubsDatasource = sSubsDatasourceDao.queryByPk(querySSubsDatasource);
			if (Objects.nonNull(queryRslSSubsDatasource)) {
				SSubsDatasourceVO outSSubsDatasourceVo = beanCopy(queryRslSSubsDatasource, new SSubsDatasourceVO());
				logger.debug("当前查询结果为:"+ outSSubsDatasourceVo.toString());
				return outSSubsDatasourceVo;
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
	public List<SSubsDatasourceVO> queryAllOwner(SSubsDatasourceVO sSubsDatasourceVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<SSubsDatasourceVO> list = null;
		try {
			List<SSubsDatasource> sSubsDatasources = sSubsDatasourceDao.queryAllOwnerByPage(sSubsDatasourceVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ sSubsDatasources.size());
			pageSet(sSubsDatasources, sSubsDatasourceVo);
			list = (List<SSubsDatasourceVO>) beansCopy(sSubsDatasources, SSubsDatasourceVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<SSubsDatasourceVO> queryAllCurrOrg(SSubsDatasourceVO sSubsDatasourceVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<SSubsDatasource> sSubsDatasources = sSubsDatasourceDao.queryAllCurrOrgByPage(sSubsDatasourceVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+sSubsDatasources.size());
		List<SSubsDatasourceVO> list = null;
		try {
			pageSet(sSubsDatasources, sSubsDatasourceVo);
			list = (List<SSubsDatasourceVO>) beansCopy(sSubsDatasources, SSubsDatasourceVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<SSubsDatasourceVO> queryAllCurrDownOrg(SSubsDatasourceVO sSubsDatasourceVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<SSubsDatasource> sSubsDatasources = sSubsDatasourceDao.queryAllCurrDownOrgByPage(sSubsDatasourceVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ sSubsDatasources.size());
		List<SSubsDatasourceVO> list = null;
		try {
			pageSet(sSubsDatasources, sSubsDatasourceVo);
			list = (List<SSubsDatasourceVO>) beansCopy(sSubsDatasources, SSubsDatasourceVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
