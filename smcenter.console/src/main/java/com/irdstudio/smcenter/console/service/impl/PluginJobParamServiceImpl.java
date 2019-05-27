package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.PluginJobParamService;
import com.irdstudio.smcenter.console.dao.PluginJobParamDao;
import com.irdstudio.smcenter.console.dao.domain.PluginJobParam;
import com.irdstudio.smcenter.console.service.vo.PluginJobParamVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 数据作业参数配置				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("jobParamService")
public class PluginJobParamServiceImpl implements PluginJobParamService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(PluginJobParamServiceImpl.class);

	@Autowired
	private PluginJobParamDao pluginJobParamDao;
	
	/**
	 * 新增操作
	 */
	public int insertPluginJobParam(PluginJobParamVO inPluginJobParamVo) {
		logger.debug("当前新增数据为:"+ inPluginJobParamVo.toString());
		int num = 0;
		try {
			PluginJobParam pluginJobParam = new PluginJobParam();
			beanCopy(inPluginJobParamVo, pluginJobParam);
			num = pluginJobParamDao.insertPluginJobParam(pluginJobParam);
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
	public int deleteByPk(PluginJobParamVO inPluginJobParamVo) {
		logger.debug("当前删除数据条件为:"+ inPluginJobParamVo);
		int num = 0;
		try {
			PluginJobParam pluginJobParam = new PluginJobParam();
			beanCopy(inPluginJobParamVo, pluginJobParam);
			num = pluginJobParamDao.deleteByPk(pluginJobParam);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginJobParamVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(PluginJobParamVO inPluginJobParamVo) {
		logger.debug("当前修改数据为:"+ inPluginJobParamVo.toString());
		int num = 0;
		try {
			PluginJobParam pluginJobParam = new PluginJobParam();
			beanCopy(inPluginJobParamVo, pluginJobParam);
			num = pluginJobParamDao.updateByPk(pluginJobParam);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginJobParamVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public PluginJobParamVO queryByPk(PluginJobParamVO inPluginJobParamVo) {
		
		logger.debug("当前查询参数信息为:"+ inPluginJobParamVo);
		try {
			PluginJobParam queryPluginJobParam = new PluginJobParam();
			beanCopy(inPluginJobParamVo, queryPluginJobParam);
			PluginJobParam queryRslPluginJobParam = pluginJobParamDao.queryByPk(queryPluginJobParam);
			if (Objects.nonNull(queryRslPluginJobParam)) {
				PluginJobParamVO outPluginJobParamVo = beanCopy(queryRslPluginJobParam, new PluginJobParamVO());
				logger.debug("当前查询结果为:"+ outPluginJobParamVo.toString());
				return outPluginJobParamVo;
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
	public List<PluginJobParamVO> queryAllOwner(PluginJobParamVO pluginJobParamVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<PluginJobParamVO> list = null;
		try {
			List<PluginJobParam> pluginJobParams = pluginJobParamDao.queryAllOwnerByPage(pluginJobParamVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ pluginJobParams.size());
			pageSet(pluginJobParams, pluginJobParamVo);
			list = (List<PluginJobParamVO>) beansCopy(pluginJobParams, PluginJobParamVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginJobParamVO> queryAllCurrOrg(PluginJobParamVO pluginJobParamVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<PluginJobParam> pluginJobParams = pluginJobParamDao.queryAllCurrOrgByPage(pluginJobParamVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+pluginJobParams.size());
		List<PluginJobParamVO> list = null;
		try {
			pageSet(pluginJobParams, pluginJobParamVo);
			list = (List<PluginJobParamVO>) beansCopy(pluginJobParams, PluginJobParamVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginJobParamVO> queryAllCurrDownOrg(PluginJobParamVO pluginJobParamVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<PluginJobParam> pluginJobParams = pluginJobParamDao.queryAllCurrDownOrgByPage(pluginJobParamVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ pluginJobParams.size());
		List<PluginJobParamVO> list = null;
		try {
			pageSet(pluginJobParams, pluginJobParamVo);
			list = (List<PluginJobParamVO>) beansCopy(pluginJobParams, PluginJobParamVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
