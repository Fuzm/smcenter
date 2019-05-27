package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.PluginJobConfService;
import com.irdstudio.smcenter.console.dao.PluginJobConfDao;
import com.irdstudio.smcenter.console.dao.domain.PluginJobConf;
import com.irdstudio.smcenter.console.service.vo.PluginJobConfVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 数据作业配置				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("jobConfService")
public class PluginJobConfServiceImpl implements PluginJobConfService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(PluginJobConfServiceImpl.class);

	@Autowired
	private PluginJobConfDao pluginJobConfDao;
	
	/**
	 * 新增操作
	 */
	public int insertPluginJobConf(PluginJobConfVO inPluginJobConfVo) {
		logger.debug("当前新增数据为:"+ inPluginJobConfVo.toString());
		int num = 0;
		try {
			PluginJobConf pluginJobConf = new PluginJobConf();
			beanCopy(inPluginJobConfVo, pluginJobConf);
			num = pluginJobConfDao.insertPluginJobConf(pluginJobConf);
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
	public int deleteByPk(PluginJobConfVO inPluginJobConfVo) {
		logger.debug("当前删除数据条件为:"+ inPluginJobConfVo);
		int num = 0;
		try {
			PluginJobConf pluginJobConf = new PluginJobConf();
			beanCopy(inPluginJobConfVo, pluginJobConf);
			num = pluginJobConfDao.deleteByPk(pluginJobConf);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginJobConfVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(PluginJobConfVO inPluginJobConfVo) {
		logger.debug("当前修改数据为:"+ inPluginJobConfVo.toString());
		int num = 0;
		try {
			PluginJobConf pluginJobConf = new PluginJobConf();
			beanCopy(inPluginJobConfVo, pluginJobConf);
			num = pluginJobConfDao.updateByPk(pluginJobConf);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginJobConfVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public PluginJobConfVO queryByPk(PluginJobConfVO inPluginJobConfVo) {
		
		logger.debug("当前查询参数信息为:"+ inPluginJobConfVo);
		try {
			PluginJobConf queryPluginJobConf = new PluginJobConf();
			beanCopy(inPluginJobConfVo, queryPluginJobConf);
			PluginJobConf queryRslPluginJobConf = pluginJobConfDao.queryByPk(queryPluginJobConf);
			if (Objects.nonNull(queryRslPluginJobConf)) {
				PluginJobConfVO outPluginJobConfVo = beanCopy(queryRslPluginJobConf, new PluginJobConfVO());
				logger.debug("当前查询结果为:"+ outPluginJobConfVo.toString());
				return outPluginJobConfVo;
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
	public List<PluginJobConfVO> queryAllOwner(PluginJobConfVO pluginJobConfVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<PluginJobConfVO> list = null;
		try {
			List<PluginJobConf> pluginJobConfs = pluginJobConfDao.queryAllOwnerByPage(pluginJobConfVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ pluginJobConfs.size());
			pageSet(pluginJobConfs, pluginJobConfVo);
			list = (List<PluginJobConfVO>) beansCopy(pluginJobConfs, PluginJobConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginJobConfVO> queryAllCurrOrg(PluginJobConfVO pluginJobConfVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<PluginJobConf> pluginJobConfs = pluginJobConfDao.queryAllCurrOrgByPage(pluginJobConfVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+pluginJobConfs.size());
		List<PluginJobConfVO> list = null;
		try {
			pageSet(pluginJobConfs, pluginJobConfVo);
			list = (List<PluginJobConfVO>) beansCopy(pluginJobConfs, PluginJobConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginJobConfVO> queryAllCurrDownOrg(PluginJobConfVO pluginJobConfVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<PluginJobConf> pluginJobConfs = pluginJobConfDao.queryAllCurrDownOrgByPage(pluginJobConfVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ pluginJobConfs.size());
		List<PluginJobConfVO> list = null;
		try {
			pageSet(pluginJobConfs, pluginJobConfVo);
			list = (List<PluginJobConfVO>) beansCopy(pluginJobConfs, PluginJobConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
