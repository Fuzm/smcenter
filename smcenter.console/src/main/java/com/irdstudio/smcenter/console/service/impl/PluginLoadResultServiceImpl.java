package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.PluginLoadResultService;
import com.irdstudio.smcenter.console.dao.PluginLoadResultDao;
import com.irdstudio.smcenter.console.dao.domain.PluginLoadResult;
import com.irdstudio.smcenter.console.service.vo.PluginLoadResultVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 数据装载结果表				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("loadResultService")
public class PluginLoadResultServiceImpl implements PluginLoadResultService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(PluginLoadResultServiceImpl.class);

	@Autowired
	private PluginLoadResultDao pluginLoadResultDao;
	
	/**
	 * 新增操作
	 */
	public int insertPluginLoadResult(PluginLoadResultVO inPluginLoadResultVo) {
		logger.debug("当前新增数据为:"+ inPluginLoadResultVo.toString());
		int num = 0;
		try {
			PluginLoadResult pluginLoadResult = new PluginLoadResult();
			beanCopy(inPluginLoadResultVo, pluginLoadResult);
			num = pluginLoadResultDao.insertPluginLoadResult(pluginLoadResult);
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
	public int deleteByPk(PluginLoadResultVO inPluginLoadResultVo) {
		logger.debug("当前删除数据条件为:"+ inPluginLoadResultVo);
		int num = 0;
		try {
			PluginLoadResult pluginLoadResult = new PluginLoadResult();
			beanCopy(inPluginLoadResultVo, pluginLoadResult);
			num = pluginLoadResultDao.deleteByPk(pluginLoadResult);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginLoadResultVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(PluginLoadResultVO inPluginLoadResultVo) {
		logger.debug("当前修改数据为:"+ inPluginLoadResultVo.toString());
		int num = 0;
		try {
			PluginLoadResult pluginLoadResult = new PluginLoadResult();
			beanCopy(inPluginLoadResultVo, pluginLoadResult);
			num = pluginLoadResultDao.updateByPk(pluginLoadResult);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginLoadResultVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public PluginLoadResultVO queryByPk(PluginLoadResultVO inPluginLoadResultVo) {
		
		logger.debug("当前查询参数信息为:"+ inPluginLoadResultVo);
		try {
			PluginLoadResult queryPluginLoadResult = new PluginLoadResult();
			beanCopy(inPluginLoadResultVo, queryPluginLoadResult);
			PluginLoadResult queryRslPluginLoadResult = pluginLoadResultDao.queryByPk(queryPluginLoadResult);
			if (Objects.nonNull(queryRslPluginLoadResult)) {
				PluginLoadResultVO outPluginLoadResultVo = beanCopy(queryRslPluginLoadResult, new PluginLoadResultVO());
				logger.debug("当前查询结果为:"+ outPluginLoadResultVo.toString());
				return outPluginLoadResultVo;
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
	public List<PluginLoadResultVO> queryAllOwner(PluginLoadResultVO pluginLoadResultVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<PluginLoadResultVO> list = null;
		try {
			List<PluginLoadResult> pluginLoadResults = pluginLoadResultDao.queryAllOwnerByPage(pluginLoadResultVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ pluginLoadResults.size());
			pageSet(pluginLoadResults, pluginLoadResultVo);
			list = (List<PluginLoadResultVO>) beansCopy(pluginLoadResults, PluginLoadResultVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginLoadResultVO> queryAllCurrOrg(PluginLoadResultVO pluginLoadResultVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<PluginLoadResult> pluginLoadResults = pluginLoadResultDao.queryAllCurrOrgByPage(pluginLoadResultVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+pluginLoadResults.size());
		List<PluginLoadResultVO> list = null;
		try {
			pageSet(pluginLoadResults, pluginLoadResultVo);
			list = (List<PluginLoadResultVO>) beansCopy(pluginLoadResults, PluginLoadResultVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginLoadResultVO> queryAllCurrDownOrg(PluginLoadResultVO pluginLoadResultVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<PluginLoadResult> pluginLoadResults = pluginLoadResultDao.queryAllCurrDownOrgByPage(pluginLoadResultVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ pluginLoadResults.size());
		List<PluginLoadResultVO> list = null;
		try {
			pageSet(pluginLoadResults, pluginLoadResultVo);
			list = (List<PluginLoadResultVO>) beansCopy(pluginLoadResults, PluginLoadResultVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
