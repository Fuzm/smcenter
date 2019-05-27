package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.PluginWtConfService;
import com.irdstudio.smcenter.console.dao.PluginWtConfDao;
import com.irdstudio.smcenter.console.dao.domain.PluginWtConf;
import com.irdstudio.smcenter.console.service.vo.PluginWtConfVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 等待指定条件配置表				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("wtConfService")
public class PluginWtConfServiceImpl implements PluginWtConfService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(PluginWtConfServiceImpl.class);

	@Autowired
	private PluginWtConfDao pluginWtConfDao;
	
	/**
	 * 新增操作
	 */
	public int insertPluginWtConf(PluginWtConfVO inPluginWtConfVo) {
		logger.debug("当前新增数据为:"+ inPluginWtConfVo.toString());
		int num = 0;
		try {
			PluginWtConf pluginWtConf = new PluginWtConf();
			beanCopy(inPluginWtConfVo, pluginWtConf);
			num = pluginWtConfDao.insertPluginWtConf(pluginWtConf);
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
	public int deleteByPk(PluginWtConfVO inPluginWtConfVo) {
		logger.debug("当前删除数据条件为:"+ inPluginWtConfVo);
		int num = 0;
		try {
			PluginWtConf pluginWtConf = new PluginWtConf();
			beanCopy(inPluginWtConfVo, pluginWtConf);
			num = pluginWtConfDao.deleteByPk(pluginWtConf);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginWtConfVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(PluginWtConfVO inPluginWtConfVo) {
		logger.debug("当前修改数据为:"+ inPluginWtConfVo.toString());
		int num = 0;
		try {
			PluginWtConf pluginWtConf = new PluginWtConf();
			beanCopy(inPluginWtConfVo, pluginWtConf);
			num = pluginWtConfDao.updateByPk(pluginWtConf);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginWtConfVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public PluginWtConfVO queryByPk(PluginWtConfVO inPluginWtConfVo) {
		
		logger.debug("当前查询参数信息为:"+ inPluginWtConfVo);
		try {
			PluginWtConf queryPluginWtConf = new PluginWtConf();
			beanCopy(inPluginWtConfVo, queryPluginWtConf);
			PluginWtConf queryRslPluginWtConf = pluginWtConfDao.queryByPk(queryPluginWtConf);
			if (Objects.nonNull(queryRslPluginWtConf)) {
				PluginWtConfVO outPluginWtConfVo = beanCopy(queryRslPluginWtConf, new PluginWtConfVO());
				logger.debug("当前查询结果为:"+ outPluginWtConfVo.toString());
				return outPluginWtConfVo;
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
	public List<PluginWtConfVO> queryAllOwner(PluginWtConfVO pluginWtConfVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<PluginWtConfVO> list = null;
		try {
			List<PluginWtConf> pluginWtConfs = pluginWtConfDao.queryAllOwnerByPage(pluginWtConfVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ pluginWtConfs.size());
			pageSet(pluginWtConfs, pluginWtConfVo);
			list = (List<PluginWtConfVO>) beansCopy(pluginWtConfs, PluginWtConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginWtConfVO> queryAllCurrOrg(PluginWtConfVO pluginWtConfVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<PluginWtConf> pluginWtConfs = pluginWtConfDao.queryAllCurrOrgByPage(pluginWtConfVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+pluginWtConfs.size());
		List<PluginWtConfVO> list = null;
		try {
			pageSet(pluginWtConfs, pluginWtConfVo);
			list = (List<PluginWtConfVO>) beansCopy(pluginWtConfs, PluginWtConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginWtConfVO> queryAllCurrDownOrg(PluginWtConfVO pluginWtConfVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<PluginWtConf> pluginWtConfs = pluginWtConfDao.queryAllCurrDownOrgByPage(pluginWtConfVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ pluginWtConfs.size());
		List<PluginWtConfVO> list = null;
		try {
			pageSet(pluginWtConfs, pluginWtConfVo);
			list = (List<PluginWtConfVO>) beansCopy(pluginWtConfs, PluginWtConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
