package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.PluginLoadConfService;
import com.irdstudio.smcenter.console.dao.PluginLoadConfDao;
import com.irdstudio.smcenter.console.dao.domain.PluginLoadConf;
import com.irdstudio.smcenter.console.service.vo.PluginLoadConfVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 数据装载配置表				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("loadConfService")
public class PluginLoadConfServiceImpl implements PluginLoadConfService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(PluginLoadConfServiceImpl.class);

	@Autowired
	private PluginLoadConfDao pluginLoadConfDao;
	
	/**
	 * 新增操作
	 */
	public int insertPluginLoadConf(PluginLoadConfVO inPluginLoadConfVo) {
		logger.debug("当前新增数据为:"+ inPluginLoadConfVo.toString());
		int num = 0;
		try {
			PluginLoadConf pluginLoadConf = new PluginLoadConf();
			beanCopy(inPluginLoadConfVo, pluginLoadConf);
			num = pluginLoadConfDao.insertPluginLoadConf(pluginLoadConf);
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
	public int deleteByPk(PluginLoadConfVO inPluginLoadConfVo) {
		logger.debug("当前删除数据条件为:"+ inPluginLoadConfVo);
		int num = 0;
		try {
			PluginLoadConf pluginLoadConf = new PluginLoadConf();
			beanCopy(inPluginLoadConfVo, pluginLoadConf);
			num = pluginLoadConfDao.deleteByPk(pluginLoadConf);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginLoadConfVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(PluginLoadConfVO inPluginLoadConfVo) {
		logger.debug("当前修改数据为:"+ inPluginLoadConfVo.toString());
		int num = 0;
		try {
			PluginLoadConf pluginLoadConf = new PluginLoadConf();
			beanCopy(inPluginLoadConfVo, pluginLoadConf);
			num = pluginLoadConfDao.updateByPk(pluginLoadConf);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginLoadConfVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public PluginLoadConfVO queryByPk(PluginLoadConfVO inPluginLoadConfVo) {
		
		logger.debug("当前查询参数信息为:"+ inPluginLoadConfVo);
		try {
			PluginLoadConf queryPluginLoadConf = new PluginLoadConf();
			beanCopy(inPluginLoadConfVo, queryPluginLoadConf);
			PluginLoadConf queryRslPluginLoadConf = pluginLoadConfDao.queryByPk(queryPluginLoadConf);
			if (Objects.nonNull(queryRslPluginLoadConf)) {
				PluginLoadConfVO outPluginLoadConfVo = beanCopy(queryRslPluginLoadConf, new PluginLoadConfVO());
				logger.debug("当前查询结果为:"+ outPluginLoadConfVo.toString());
				return outPluginLoadConfVo;
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
	public List<PluginLoadConfVO> queryAllOwner(PluginLoadConfVO pluginLoadConfVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<PluginLoadConfVO> list = null;
		try {
			List<PluginLoadConf> pluginLoadConfs = pluginLoadConfDao.queryAllOwnerByPage(pluginLoadConfVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ pluginLoadConfs.size());
			pageSet(pluginLoadConfs, pluginLoadConfVo);
			list = (List<PluginLoadConfVO>) beansCopy(pluginLoadConfs, PluginLoadConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginLoadConfVO> queryAllCurrOrg(PluginLoadConfVO pluginLoadConfVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<PluginLoadConf> pluginLoadConfs = pluginLoadConfDao.queryAllCurrOrgByPage(pluginLoadConfVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+pluginLoadConfs.size());
		List<PluginLoadConfVO> list = null;
		try {
			pageSet(pluginLoadConfs, pluginLoadConfVo);
			list = (List<PluginLoadConfVO>) beansCopy(pluginLoadConfs, PluginLoadConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginLoadConfVO> queryAllCurrDownOrg(PluginLoadConfVO pluginLoadConfVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<PluginLoadConf> pluginLoadConfs = pluginLoadConfDao.queryAllCurrDownOrgByPage(pluginLoadConfVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ pluginLoadConfs.size());
		List<PluginLoadConfVO> list = null;
		try {
			pageSet(pluginLoadConfs, pluginLoadConfVo);
			list = (List<PluginLoadConfVO>) beansCopy(pluginLoadConfs, PluginLoadConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
