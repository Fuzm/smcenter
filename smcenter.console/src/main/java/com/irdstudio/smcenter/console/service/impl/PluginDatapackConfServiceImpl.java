package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.PluginDatapackConfService;
import com.irdstudio.smcenter.console.dao.PluginDatapackConfDao;
import com.irdstudio.smcenter.console.dao.domain.PluginDatapackConf;
import com.irdstudio.smcenter.console.service.vo.PluginDatapackConfVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 数据报文文件生成配置表				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("datapackConfService")
public class PluginDatapackConfServiceImpl implements PluginDatapackConfService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(PluginDatapackConfServiceImpl.class);

	@Autowired
	private PluginDatapackConfDao pluginDatapackConfDao;
	
	/**
	 * 新增操作
	 */
	public int insertPluginDatapackConf(PluginDatapackConfVO inPluginDatapackConfVo) {
		logger.debug("当前新增数据为:"+ inPluginDatapackConfVo.toString());
		int num = 0;
		try {
			PluginDatapackConf pluginDatapackConf = new PluginDatapackConf();
			beanCopy(inPluginDatapackConfVo, pluginDatapackConf);
			num = pluginDatapackConfDao.insertPluginDatapackConf(pluginDatapackConf);
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
	public int deleteByPk(PluginDatapackConfVO inPluginDatapackConfVo) {
		logger.debug("当前删除数据条件为:"+ inPluginDatapackConfVo);
		int num = 0;
		try {
			PluginDatapackConf pluginDatapackConf = new PluginDatapackConf();
			beanCopy(inPluginDatapackConfVo, pluginDatapackConf);
			num = pluginDatapackConfDao.deleteByPk(pluginDatapackConf);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginDatapackConfVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(PluginDatapackConfVO inPluginDatapackConfVo) {
		logger.debug("当前修改数据为:"+ inPluginDatapackConfVo.toString());
		int num = 0;
		try {
			PluginDatapackConf pluginDatapackConf = new PluginDatapackConf();
			beanCopy(inPluginDatapackConfVo, pluginDatapackConf);
			num = pluginDatapackConfDao.updateByPk(pluginDatapackConf);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginDatapackConfVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public PluginDatapackConfVO queryByPk(PluginDatapackConfVO inPluginDatapackConfVo) {
		
		logger.debug("当前查询参数信息为:"+ inPluginDatapackConfVo);
		try {
			PluginDatapackConf queryPluginDatapackConf = new PluginDatapackConf();
			beanCopy(inPluginDatapackConfVo, queryPluginDatapackConf);
			PluginDatapackConf queryRslPluginDatapackConf = pluginDatapackConfDao.queryByPk(queryPluginDatapackConf);
			if (Objects.nonNull(queryRslPluginDatapackConf)) {
				PluginDatapackConfVO outPluginDatapackConfVo = beanCopy(queryRslPluginDatapackConf, new PluginDatapackConfVO());
				logger.debug("当前查询结果为:"+ outPluginDatapackConfVo.toString());
				return outPluginDatapackConfVo;
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
	public List<PluginDatapackConfVO> queryAllOwner(PluginDatapackConfVO pluginDatapackConfVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<PluginDatapackConfVO> list = null;
		try {
			List<PluginDatapackConf> pluginDatapackConfs = pluginDatapackConfDao.queryAllOwnerByPage(pluginDatapackConfVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ pluginDatapackConfs.size());
			pageSet(pluginDatapackConfs, pluginDatapackConfVo);
			list = (List<PluginDatapackConfVO>) beansCopy(pluginDatapackConfs, PluginDatapackConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginDatapackConfVO> queryAllCurrOrg(PluginDatapackConfVO pluginDatapackConfVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<PluginDatapackConf> pluginDatapackConfs = pluginDatapackConfDao.queryAllCurrOrgByPage(pluginDatapackConfVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+pluginDatapackConfs.size());
		List<PluginDatapackConfVO> list = null;
		try {
			pageSet(pluginDatapackConfs, pluginDatapackConfVo);
			list = (List<PluginDatapackConfVO>) beansCopy(pluginDatapackConfs, PluginDatapackConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginDatapackConfVO> queryAllCurrDownOrg(PluginDatapackConfVO pluginDatapackConfVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<PluginDatapackConf> pluginDatapackConfs = pluginDatapackConfDao.queryAllCurrDownOrgByPage(pluginDatapackConfVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ pluginDatapackConfs.size());
		List<PluginDatapackConfVO> list = null;
		try {
			pageSet(pluginDatapackConfs, pluginDatapackConfVo);
			list = (List<PluginDatapackConfVO>) beansCopy(pluginDatapackConfs, PluginDatapackConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
