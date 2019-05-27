package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.PluginCheckConfService;
import com.irdstudio.smcenter.console.dao.PluginCheckConfDao;
import com.irdstudio.smcenter.console.dao.domain.PluginCheckConf;
import com.irdstudio.smcenter.console.service.vo.PluginCheckConfVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 数据检查配置表				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("checkConfService")
public class PluginCheckConfServiceImpl implements PluginCheckConfService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(PluginCheckConfServiceImpl.class);

	@Autowired
	private PluginCheckConfDao pluginCheckConfDao;
	
	/**
	 * 新增操作
	 */
	public int insertPluginCheckConf(PluginCheckConfVO inPluginCheckConfVo) {
		logger.debug("当前新增数据为:"+ inPluginCheckConfVo.toString());
		int num = 0;
		try {
			PluginCheckConf pluginCheckConf = new PluginCheckConf();
			beanCopy(inPluginCheckConfVo, pluginCheckConf);
			num = pluginCheckConfDao.insertPluginCheckConf(pluginCheckConf);
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
	public int deleteByPk(PluginCheckConfVO inPluginCheckConfVo) {
		logger.debug("当前删除数据条件为:"+ inPluginCheckConfVo);
		int num = 0;
		try {
			PluginCheckConf pluginCheckConf = new PluginCheckConf();
			beanCopy(inPluginCheckConfVo, pluginCheckConf);
			num = pluginCheckConfDao.deleteByPk(pluginCheckConf);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginCheckConfVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(PluginCheckConfVO inPluginCheckConfVo) {
		logger.debug("当前修改数据为:"+ inPluginCheckConfVo.toString());
		int num = 0;
		try {
			PluginCheckConf pluginCheckConf = new PluginCheckConf();
			beanCopy(inPluginCheckConfVo, pluginCheckConf);
			num = pluginCheckConfDao.updateByPk(pluginCheckConf);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginCheckConfVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public PluginCheckConfVO queryByPk(PluginCheckConfVO inPluginCheckConfVo) {
		
		logger.debug("当前查询参数信息为:"+ inPluginCheckConfVo);
		try {
			PluginCheckConf queryPluginCheckConf = new PluginCheckConf();
			beanCopy(inPluginCheckConfVo, queryPluginCheckConf);
			PluginCheckConf queryRslPluginCheckConf = pluginCheckConfDao.queryByPk(queryPluginCheckConf);
			if (Objects.nonNull(queryRslPluginCheckConf)) {
				PluginCheckConfVO outPluginCheckConfVo = beanCopy(queryRslPluginCheckConf, new PluginCheckConfVO());
				logger.debug("当前查询结果为:"+ outPluginCheckConfVo.toString());
				return outPluginCheckConfVo;
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
	public List<PluginCheckConfVO> queryAllOwner(PluginCheckConfVO pluginCheckConfVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<PluginCheckConfVO> list = null;
		try {
			List<PluginCheckConf> pluginCheckConfs = pluginCheckConfDao.queryAllOwnerByPage(pluginCheckConfVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ pluginCheckConfs.size());
			pageSet(pluginCheckConfs, pluginCheckConfVo);
			list = (List<PluginCheckConfVO>) beansCopy(pluginCheckConfs, PluginCheckConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginCheckConfVO> queryAllCurrOrg(PluginCheckConfVO pluginCheckConfVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<PluginCheckConf> pluginCheckConfs = pluginCheckConfDao.queryAllCurrOrgByPage(pluginCheckConfVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+pluginCheckConfs.size());
		List<PluginCheckConfVO> list = null;
		try {
			pageSet(pluginCheckConfs, pluginCheckConfVo);
			list = (List<PluginCheckConfVO>) beansCopy(pluginCheckConfs, PluginCheckConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginCheckConfVO> queryAllCurrDownOrg(PluginCheckConfVO pluginCheckConfVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<PluginCheckConf> pluginCheckConfs = pluginCheckConfDao.queryAllCurrDownOrgByPage(pluginCheckConfVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ pluginCheckConfs.size());
		List<PluginCheckConfVO> list = null;
		try {
			pageSet(pluginCheckConfs, pluginCheckConfVo);
			list = (List<PluginCheckConfVO>) beansCopy(pluginCheckConfs, PluginCheckConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
