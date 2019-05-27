package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.PluginFileopConfService;
import com.irdstudio.smcenter.console.dao.PluginFileopConfDao;
import com.irdstudio.smcenter.console.dao.domain.PluginFileopConf;
import com.irdstudio.smcenter.console.service.vo.PluginFileopConfVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 文件操作配置				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("fileopConfService")
public class PluginFileopConfServiceImpl implements PluginFileopConfService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(PluginFileopConfServiceImpl.class);

	@Autowired
	private PluginFileopConfDao pluginFileopConfDao;
	
	/**
	 * 新增操作
	 */
	public int insertPluginFileopConf(PluginFileopConfVO inPluginFileopConfVo) {
		logger.debug("当前新增数据为:"+ inPluginFileopConfVo.toString());
		int num = 0;
		try {
			PluginFileopConf pluginFileopConf = new PluginFileopConf();
			beanCopy(inPluginFileopConfVo, pluginFileopConf);
			num = pluginFileopConfDao.insertPluginFileopConf(pluginFileopConf);
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
	public int deleteByPk(PluginFileopConfVO inPluginFileopConfVo) {
		logger.debug("当前删除数据条件为:"+ inPluginFileopConfVo);
		int num = 0;
		try {
			PluginFileopConf pluginFileopConf = new PluginFileopConf();
			beanCopy(inPluginFileopConfVo, pluginFileopConf);
			num = pluginFileopConfDao.deleteByPk(pluginFileopConf);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginFileopConfVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(PluginFileopConfVO inPluginFileopConfVo) {
		logger.debug("当前修改数据为:"+ inPluginFileopConfVo.toString());
		int num = 0;
		try {
			PluginFileopConf pluginFileopConf = new PluginFileopConf();
			beanCopy(inPluginFileopConfVo, pluginFileopConf);
			num = pluginFileopConfDao.updateByPk(pluginFileopConf);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginFileopConfVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public PluginFileopConfVO queryByPk(PluginFileopConfVO inPluginFileopConfVo) {
		
		logger.debug("当前查询参数信息为:"+ inPluginFileopConfVo);
		try {
			PluginFileopConf queryPluginFileopConf = new PluginFileopConf();
			beanCopy(inPluginFileopConfVo, queryPluginFileopConf);
			PluginFileopConf queryRslPluginFileopConf = pluginFileopConfDao.queryByPk(queryPluginFileopConf);
			if (Objects.nonNull(queryRslPluginFileopConf)) {
				PluginFileopConfVO outPluginFileopConfVo = beanCopy(queryRslPluginFileopConf, new PluginFileopConfVO());
				logger.debug("当前查询结果为:"+ outPluginFileopConfVo.toString());
				return outPluginFileopConfVo;
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
	public List<PluginFileopConfVO> queryAllOwner(PluginFileopConfVO pluginFileopConfVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<PluginFileopConfVO> list = null;
		try {
			List<PluginFileopConf> pluginFileopConfs = pluginFileopConfDao.queryAllOwnerByPage(pluginFileopConfVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ pluginFileopConfs.size());
			pageSet(pluginFileopConfs, pluginFileopConfVo);
			list = (List<PluginFileopConfVO>) beansCopy(pluginFileopConfs, PluginFileopConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginFileopConfVO> queryAllCurrOrg(PluginFileopConfVO pluginFileopConfVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<PluginFileopConf> pluginFileopConfs = pluginFileopConfDao.queryAllCurrOrgByPage(pluginFileopConfVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+pluginFileopConfs.size());
		List<PluginFileopConfVO> list = null;
		try {
			pageSet(pluginFileopConfs, pluginFileopConfVo);
			list = (List<PluginFileopConfVO>) beansCopy(pluginFileopConfs, PluginFileopConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginFileopConfVO> queryAllCurrDownOrg(PluginFileopConfVO pluginFileopConfVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<PluginFileopConf> pluginFileopConfs = pluginFileopConfDao.queryAllCurrDownOrgByPage(pluginFileopConfVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ pluginFileopConfs.size());
		List<PluginFileopConfVO> list = null;
		try {
			pageSet(pluginFileopConfs, pluginFileopConfVo);
			list = (List<PluginFileopConfVO>) beansCopy(pluginFileopConfs, PluginFileopConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
