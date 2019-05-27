package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.PluginDefineService;
import com.irdstudio.smcenter.console.dao.PluginDefineDao;
import com.irdstudio.smcenter.console.dao.domain.PluginDefine;
import com.irdstudio.smcenter.console.service.vo.PluginDefineVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 应用插件信息				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("defineService")
public class PluginDefineServiceImpl implements PluginDefineService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(PluginDefineServiceImpl.class);

	@Autowired
	private PluginDefineDao pluginDefineDao;
	
	/**
	 * 新增操作
	 */
	public int insertPluginDefine(PluginDefineVO inPluginDefineVo) {
		logger.debug("当前新增数据为:"+ inPluginDefineVo.toString());
		int num = 0;
		try {
			PluginDefine pluginDefine = new PluginDefine();
			beanCopy(inPluginDefineVo, pluginDefine);
			num = pluginDefineDao.insertPluginDefine(pluginDefine);
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
	public int deleteByPk(PluginDefineVO inPluginDefineVo) {
		logger.debug("当前删除数据条件为:"+ inPluginDefineVo);
		int num = 0;
		try {
			PluginDefine pluginDefine = new PluginDefine();
			beanCopy(inPluginDefineVo, pluginDefine);
			num = pluginDefineDao.deleteByPk(pluginDefine);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginDefineVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(PluginDefineVO inPluginDefineVo) {
		logger.debug("当前修改数据为:"+ inPluginDefineVo.toString());
		int num = 0;
		try {
			PluginDefine pluginDefine = new PluginDefine();
			beanCopy(inPluginDefineVo, pluginDefine);
			num = pluginDefineDao.updateByPk(pluginDefine);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginDefineVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public PluginDefineVO queryByPk(PluginDefineVO inPluginDefineVo) {
		
		logger.debug("当前查询参数信息为:"+ inPluginDefineVo);
		try {
			PluginDefine queryPluginDefine = new PluginDefine();
			beanCopy(inPluginDefineVo, queryPluginDefine);
			PluginDefine queryRslPluginDefine = pluginDefineDao.queryByPk(queryPluginDefine);
			if (Objects.nonNull(queryRslPluginDefine)) {
				PluginDefineVO outPluginDefineVo = beanCopy(queryRslPluginDefine, new PluginDefineVO());
				logger.debug("当前查询结果为:"+ outPluginDefineVo.toString());
				return outPluginDefineVo;
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
	public List<PluginDefineVO> queryAllOwner(PluginDefineVO pluginDefineVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<PluginDefineVO> list = null;
		try {
			List<PluginDefine> pluginDefines = pluginDefineDao.queryAllOwnerByPage(pluginDefineVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ pluginDefines.size());
			pageSet(pluginDefines, pluginDefineVo);
			list = (List<PluginDefineVO>) beansCopy(pluginDefines, PluginDefineVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginDefineVO> queryAllCurrOrg(PluginDefineVO pluginDefineVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<PluginDefine> pluginDefines = pluginDefineDao.queryAllCurrOrgByPage(pluginDefineVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+pluginDefines.size());
		List<PluginDefineVO> list = null;
		try {
			pageSet(pluginDefines, pluginDefineVo);
			list = (List<PluginDefineVO>) beansCopy(pluginDefines, PluginDefineVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginDefineVO> queryAllCurrDownOrg(PluginDefineVO pluginDefineVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<PluginDefine> pluginDefines = pluginDefineDao.queryAllCurrDownOrgByPage(pluginDefineVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ pluginDefines.size());
		List<PluginDefineVO> list = null;
		try {
			pageSet(pluginDefines, pluginDefineVo);
			list = (List<PluginDefineVO>) beansCopy(pluginDefines, PluginDefineVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
