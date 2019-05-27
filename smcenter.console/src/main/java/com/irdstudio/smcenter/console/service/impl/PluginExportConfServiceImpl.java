package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.PluginExportConfService;
import com.irdstudio.smcenter.console.dao.PluginExportConfDao;
import com.irdstudio.smcenter.console.dao.domain.PluginExportConf;
import com.irdstudio.smcenter.console.service.vo.PluginExportConfVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 导出为文本文件插件				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("exportConfService")
public class PluginExportConfServiceImpl implements PluginExportConfService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(PluginExportConfServiceImpl.class);

	@Autowired
	private PluginExportConfDao pluginExportConfDao;
	
	/**
	 * 新增操作
	 */
	public int insertPluginExportConf(PluginExportConfVO inPluginExportConfVo) {
		logger.debug("当前新增数据为:"+ inPluginExportConfVo.toString());
		int num = 0;
		try {
			PluginExportConf pluginExportConf = new PluginExportConf();
			beanCopy(inPluginExportConfVo, pluginExportConf);
			num = pluginExportConfDao.insertPluginExportConf(pluginExportConf);
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
	public int deleteByPk(PluginExportConfVO inPluginExportConfVo) {
		logger.debug("当前删除数据条件为:"+ inPluginExportConfVo);
		int num = 0;
		try {
			PluginExportConf pluginExportConf = new PluginExportConf();
			beanCopy(inPluginExportConfVo, pluginExportConf);
			num = pluginExportConfDao.deleteByPk(pluginExportConf);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginExportConfVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(PluginExportConfVO inPluginExportConfVo) {
		logger.debug("当前修改数据为:"+ inPluginExportConfVo.toString());
		int num = 0;
		try {
			PluginExportConf pluginExportConf = new PluginExportConf();
			beanCopy(inPluginExportConfVo, pluginExportConf);
			num = pluginExportConfDao.updateByPk(pluginExportConf);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginExportConfVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public PluginExportConfVO queryByPk(PluginExportConfVO inPluginExportConfVo) {
		
		logger.debug("当前查询参数信息为:"+ inPluginExportConfVo);
		try {
			PluginExportConf queryPluginExportConf = new PluginExportConf();
			beanCopy(inPluginExportConfVo, queryPluginExportConf);
			PluginExportConf queryRslPluginExportConf = pluginExportConfDao.queryByPk(queryPluginExportConf);
			if (Objects.nonNull(queryRslPluginExportConf)) {
				PluginExportConfVO outPluginExportConfVo = beanCopy(queryRslPluginExportConf, new PluginExportConfVO());
				logger.debug("当前查询结果为:"+ outPluginExportConfVo.toString());
				return outPluginExportConfVo;
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
	public List<PluginExportConfVO> queryAllOwner(PluginExportConfVO pluginExportConfVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<PluginExportConfVO> list = null;
		try {
			List<PluginExportConf> pluginExportConfs = pluginExportConfDao.queryAllOwnerByPage(pluginExportConfVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ pluginExportConfs.size());
			pageSet(pluginExportConfs, pluginExportConfVo);
			list = (List<PluginExportConfVO>) beansCopy(pluginExportConfs, PluginExportConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginExportConfVO> queryAllCurrOrg(PluginExportConfVO pluginExportConfVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<PluginExportConf> pluginExportConfs = pluginExportConfDao.queryAllCurrOrgByPage(pluginExportConfVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+pluginExportConfs.size());
		List<PluginExportConfVO> list = null;
		try {
			pageSet(pluginExportConfs, pluginExportConfVo);
			list = (List<PluginExportConfVO>) beansCopy(pluginExportConfs, PluginExportConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginExportConfVO> queryAllCurrDownOrg(PluginExportConfVO pluginExportConfVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<PluginExportConf> pluginExportConfs = pluginExportConfDao.queryAllCurrDownOrgByPage(pluginExportConfVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ pluginExportConfs.size());
		List<PluginExportConfVO> list = null;
		try {
			pageSet(pluginExportConfs, pluginExportConfVo);
			list = (List<PluginExportConfVO>) beansCopy(pluginExportConfs, PluginExportConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
