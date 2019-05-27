package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.PluginReformConfService;
import com.irdstudio.smcenter.console.dao.PluginReformConfDao;
import com.irdstudio.smcenter.console.dao.domain.PluginReformConf;
import com.irdstudio.smcenter.console.service.vo.PluginReformConfVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 数据表重整配置表				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("reformConfService")
public class PluginReformConfServiceImpl implements PluginReformConfService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(PluginReformConfServiceImpl.class);

	@Autowired
	private PluginReformConfDao pluginReformConfDao;
	
	/**
	 * 新增操作
	 */
	public int insertPluginReformConf(PluginReformConfVO inPluginReformConfVo) {
		logger.debug("当前新增数据为:"+ inPluginReformConfVo.toString());
		int num = 0;
		try {
			PluginReformConf pluginReformConf = new PluginReformConf();
			beanCopy(inPluginReformConfVo, pluginReformConf);
			num = pluginReformConfDao.insertPluginReformConf(pluginReformConf);
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
	public int deleteByPk(PluginReformConfVO inPluginReformConfVo) {
		logger.debug("当前删除数据条件为:"+ inPluginReformConfVo);
		int num = 0;
		try {
			PluginReformConf pluginReformConf = new PluginReformConf();
			beanCopy(inPluginReformConfVo, pluginReformConf);
			num = pluginReformConfDao.deleteByPk(pluginReformConf);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginReformConfVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(PluginReformConfVO inPluginReformConfVo) {
		logger.debug("当前修改数据为:"+ inPluginReformConfVo.toString());
		int num = 0;
		try {
			PluginReformConf pluginReformConf = new PluginReformConf();
			beanCopy(inPluginReformConfVo, pluginReformConf);
			num = pluginReformConfDao.updateByPk(pluginReformConf);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginReformConfVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public PluginReformConfVO queryByPk(PluginReformConfVO inPluginReformConfVo) {
		
		logger.debug("当前查询参数信息为:"+ inPluginReformConfVo);
		try {
			PluginReformConf queryPluginReformConf = new PluginReformConf();
			beanCopy(inPluginReformConfVo, queryPluginReformConf);
			PluginReformConf queryRslPluginReformConf = pluginReformConfDao.queryByPk(queryPluginReformConf);
			if (Objects.nonNull(queryRslPluginReformConf)) {
				PluginReformConfVO outPluginReformConfVo = beanCopy(queryRslPluginReformConf, new PluginReformConfVO());
				logger.debug("当前查询结果为:"+ outPluginReformConfVo.toString());
				return outPluginReformConfVo;
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
	public List<PluginReformConfVO> queryAllOwner(PluginReformConfVO pluginReformConfVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<PluginReformConfVO> list = null;
		try {
			List<PluginReformConf> pluginReformConfs = pluginReformConfDao.queryAllOwnerByPage(pluginReformConfVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ pluginReformConfs.size());
			pageSet(pluginReformConfs, pluginReformConfVo);
			list = (List<PluginReformConfVO>) beansCopy(pluginReformConfs, PluginReformConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginReformConfVO> queryAllCurrOrg(PluginReformConfVO pluginReformConfVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<PluginReformConf> pluginReformConfs = pluginReformConfDao.queryAllCurrOrgByPage(pluginReformConfVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+pluginReformConfs.size());
		List<PluginReformConfVO> list = null;
		try {
			pageSet(pluginReformConfs, pluginReformConfVo);
			list = (List<PluginReformConfVO>) beansCopy(pluginReformConfs, PluginReformConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginReformConfVO> queryAllCurrDownOrg(PluginReformConfVO pluginReformConfVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<PluginReformConf> pluginReformConfs = pluginReformConfDao.queryAllCurrDownOrgByPage(pluginReformConfVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ pluginReformConfs.size());
		List<PluginReformConfVO> list = null;
		try {
			pageSet(pluginReformConfs, pluginReformConfVo);
			list = (List<PluginReformConfVO>) beansCopy(pluginReformConfs, PluginReformConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
