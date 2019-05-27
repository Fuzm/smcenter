package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.PluginServiceConfService;
import com.irdstudio.smcenter.console.dao.PluginServiceConfDao;
import com.irdstudio.smcenter.console.dao.domain.PluginServiceConf;
import com.irdstudio.smcenter.console.service.vo.PluginServiceConfVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 服务调用插件				<p>
 * @author fuzm
 * @date 2018-06-16
 */
@Service("batPluginServiceConfService")
public class PluginServiceConfServiceImpl implements PluginServiceConfService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(PluginServiceConfServiceImpl.class);

	@Autowired
	private PluginServiceConfDao pluginServiceConfDao;
	
	/**
	 * 新增操作
	 */
	public int insertPluginServiceConf(PluginServiceConfVO inPluginServiceConfVo) {
		logger.debug("当前新增数据为:"+ inPluginServiceConfVo.toString());
		int num = 0;
		try {
			PluginServiceConf pluginServiceConf = new PluginServiceConf();
			beanCopy(inPluginServiceConfVo, pluginServiceConf);
			num = pluginServiceConfDao.insertPluginServiceConf(pluginServiceConf);
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
	public int deleteByPk(PluginServiceConfVO inPluginServiceConfVo) {
		logger.debug("当前删除数据条件为:"+ inPluginServiceConfVo);
		int num = 0;
		try {
			PluginServiceConf pluginServiceConf = new PluginServiceConf();
			beanCopy(inPluginServiceConfVo, pluginServiceConf);
			num = pluginServiceConfDao.deleteByPk(pluginServiceConf);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginServiceConfVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(PluginServiceConfVO inPluginServiceConfVo) {
		logger.debug("当前修改数据为:"+ inPluginServiceConfVo.toString());
		int num = 0;
		try {
			PluginServiceConf pluginServiceConf = new PluginServiceConf();
			beanCopy(inPluginServiceConfVo, pluginServiceConf);
			num = pluginServiceConfDao.updateByPk(pluginServiceConf);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginServiceConfVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public PluginServiceConfVO queryByPk(PluginServiceConfVO inPluginServiceConfVo) {
		
		logger.debug("当前查询参数信息为:"+ inPluginServiceConfVo);
		try {
			PluginServiceConf queryPluginServiceConf = new PluginServiceConf();
			beanCopy(inPluginServiceConfVo, queryPluginServiceConf);
			PluginServiceConf queryRslPluginServiceConf = pluginServiceConfDao.queryByPk(queryPluginServiceConf);
			if (Objects.nonNull(queryRslPluginServiceConf)) {
				PluginServiceConfVO outPluginServiceConfVo = beanCopy(queryRslPluginServiceConf, new PluginServiceConfVO());
				logger.debug("当前查询结果为:"+ outPluginServiceConfVo.toString());
				return outPluginServiceConfVo;
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
	public List<PluginServiceConfVO> queryAllOwner(PluginServiceConfVO pluginServiceConfVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<PluginServiceConfVO> list = null;
		try {
			List<PluginServiceConf> pluginServiceConfs = pluginServiceConfDao.queryAllOwnerByPage(pluginServiceConfVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ pluginServiceConfs.size());
			pageSet(pluginServiceConfs, pluginServiceConfVo);
			list = (List<PluginServiceConfVO>) beansCopy(pluginServiceConfs, PluginServiceConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginServiceConfVO> queryAllCurrOrg(PluginServiceConfVO pluginServiceConfVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<PluginServiceConf> pluginServiceConfs = pluginServiceConfDao.queryAllCurrOrgByPage(pluginServiceConfVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+pluginServiceConfs.size());
		List<PluginServiceConfVO> list = null;
		try {
			pageSet(pluginServiceConfs, pluginServiceConfVo);
			list = (List<PluginServiceConfVO>) beansCopy(pluginServiceConfs, PluginServiceConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginServiceConfVO> queryAllCurrDownOrg(PluginServiceConfVO pluginServiceConfVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<PluginServiceConf> pluginServiceConfs = pluginServiceConfDao.queryAllCurrDownOrgByPage(pluginServiceConfVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ pluginServiceConfs.size());
		List<PluginServiceConfVO> list = null;
		try {
			pageSet(pluginServiceConfs, pluginServiceConfVo);
			list = (List<PluginServiceConfVO>) beansCopy(pluginServiceConfs, PluginServiceConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
