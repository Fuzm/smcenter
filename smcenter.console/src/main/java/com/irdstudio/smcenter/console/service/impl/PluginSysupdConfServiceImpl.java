package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.PluginSysupdConfService;
import com.irdstudio.smcenter.console.dao.PluginSysupdConfDao;
import com.irdstudio.smcenter.console.dao.domain.PluginSysupdConf;
import com.irdstudio.smcenter.console.service.vo.PluginSysupdConfVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 系统信息更新配置表				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("sysupdConfService")
public class PluginSysupdConfServiceImpl implements PluginSysupdConfService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(PluginSysupdConfServiceImpl.class);

	@Autowired
	private PluginSysupdConfDao pluginSysupdConfDao;
	
	/**
	 * 新增操作
	 */
	public int insertPluginSysupdConf(PluginSysupdConfVO inPluginSysupdConfVo) {
		logger.debug("当前新增数据为:"+ inPluginSysupdConfVo.toString());
		int num = 0;
		try {
			PluginSysupdConf pluginSysupdConf = new PluginSysupdConf();
			beanCopy(inPluginSysupdConfVo, pluginSysupdConf);
			num = pluginSysupdConfDao.insertPluginSysupdConf(pluginSysupdConf);
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
	public int deleteByPk(PluginSysupdConfVO inPluginSysupdConfVo) {
		logger.debug("当前删除数据条件为:"+ inPluginSysupdConfVo);
		int num = 0;
		try {
			PluginSysupdConf pluginSysupdConf = new PluginSysupdConf();
			beanCopy(inPluginSysupdConfVo, pluginSysupdConf);
			num = pluginSysupdConfDao.deleteByPk(pluginSysupdConf);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginSysupdConfVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(PluginSysupdConfVO inPluginSysupdConfVo) {
		logger.debug("当前修改数据为:"+ inPluginSysupdConfVo.toString());
		int num = 0;
		try {
			PluginSysupdConf pluginSysupdConf = new PluginSysupdConf();
			beanCopy(inPluginSysupdConfVo, pluginSysupdConf);
			num = pluginSysupdConfDao.updateByPk(pluginSysupdConf);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginSysupdConfVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public PluginSysupdConfVO queryByPk(PluginSysupdConfVO inPluginSysupdConfVo) {
		
		logger.debug("当前查询参数信息为:"+ inPluginSysupdConfVo);
		try {
			PluginSysupdConf queryPluginSysupdConf = new PluginSysupdConf();
			beanCopy(inPluginSysupdConfVo, queryPluginSysupdConf);
			PluginSysupdConf queryRslPluginSysupdConf = pluginSysupdConfDao.queryByPk(queryPluginSysupdConf);
			if (Objects.nonNull(queryRslPluginSysupdConf)) {
				PluginSysupdConfVO outPluginSysupdConfVo = beanCopy(queryRslPluginSysupdConf, new PluginSysupdConfVO());
				logger.debug("当前查询结果为:"+ outPluginSysupdConfVo.toString());
				return outPluginSysupdConfVo;
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
	public List<PluginSysupdConfVO> queryAllOwner(PluginSysupdConfVO pluginSysupdConfVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<PluginSysupdConfVO> list = null;
		try {
			List<PluginSysupdConf> pluginSysupdConfs = pluginSysupdConfDao.queryAllOwnerByPage(pluginSysupdConfVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ pluginSysupdConfs.size());
			pageSet(pluginSysupdConfs, pluginSysupdConfVo);
			list = (List<PluginSysupdConfVO>) beansCopy(pluginSysupdConfs, PluginSysupdConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginSysupdConfVO> queryAllCurrOrg(PluginSysupdConfVO pluginSysupdConfVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<PluginSysupdConf> pluginSysupdConfs = pluginSysupdConfDao.queryAllCurrOrgByPage(pluginSysupdConfVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+pluginSysupdConfs.size());
		List<PluginSysupdConfVO> list = null;
		try {
			pageSet(pluginSysupdConfs, pluginSysupdConfVo);
			list = (List<PluginSysupdConfVO>) beansCopy(pluginSysupdConfs, PluginSysupdConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginSysupdConfVO> queryAllCurrDownOrg(PluginSysupdConfVO pluginSysupdConfVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<PluginSysupdConf> pluginSysupdConfs = pluginSysupdConfDao.queryAllCurrDownOrgByPage(pluginSysupdConfVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ pluginSysupdConfs.size());
		List<PluginSysupdConfVO> list = null;
		try {
			pageSet(pluginSysupdConfs, pluginSysupdConfVo);
			list = (List<PluginSysupdConfVO>) beansCopy(pluginSysupdConfs, PluginSysupdConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
