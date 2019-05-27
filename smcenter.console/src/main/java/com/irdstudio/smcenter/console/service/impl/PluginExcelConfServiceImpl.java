package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.PluginExcelConfService;
import com.irdstudio.smcenter.console.dao.PluginExcelConfDao;
import com.irdstudio.smcenter.console.dao.domain.PluginExcelConf;
import com.irdstudio.smcenter.console.service.vo.PluginExcelConfVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: Excel操作配置表				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("excelConfService")
public class PluginExcelConfServiceImpl implements PluginExcelConfService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(PluginExcelConfServiceImpl.class);

	@Autowired
	private PluginExcelConfDao pluginExcelConfDao;
	
	/**
	 * 新增操作
	 */
	public int insertPluginExcelConf(PluginExcelConfVO inPluginExcelConfVo) {
		logger.debug("当前新增数据为:"+ inPluginExcelConfVo.toString());
		int num = 0;
		try {
			PluginExcelConf pluginExcelConf = new PluginExcelConf();
			beanCopy(inPluginExcelConfVo, pluginExcelConf);
			num = pluginExcelConfDao.insertPluginExcelConf(pluginExcelConf);
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
	public int deleteByPk(PluginExcelConfVO inPluginExcelConfVo) {
		logger.debug("当前删除数据条件为:"+ inPluginExcelConfVo);
		int num = 0;
		try {
			PluginExcelConf pluginExcelConf = new PluginExcelConf();
			beanCopy(inPluginExcelConfVo, pluginExcelConf);
			num = pluginExcelConfDao.deleteByPk(pluginExcelConf);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginExcelConfVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(PluginExcelConfVO inPluginExcelConfVo) {
		logger.debug("当前修改数据为:"+ inPluginExcelConfVo.toString());
		int num = 0;
		try {
			PluginExcelConf pluginExcelConf = new PluginExcelConf();
			beanCopy(inPluginExcelConfVo, pluginExcelConf);
			num = pluginExcelConfDao.updateByPk(pluginExcelConf);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginExcelConfVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public PluginExcelConfVO queryByPk(PluginExcelConfVO inPluginExcelConfVo) {
		
		logger.debug("当前查询参数信息为:"+ inPluginExcelConfVo);
		try {
			PluginExcelConf queryPluginExcelConf = new PluginExcelConf();
			beanCopy(inPluginExcelConfVo, queryPluginExcelConf);
			PluginExcelConf queryRslPluginExcelConf = pluginExcelConfDao.queryByPk(queryPluginExcelConf);
			if (Objects.nonNull(queryRslPluginExcelConf)) {
				PluginExcelConfVO outPluginExcelConfVo = beanCopy(queryRslPluginExcelConf, new PluginExcelConfVO());
				logger.debug("当前查询结果为:"+ outPluginExcelConfVo.toString());
				return outPluginExcelConfVo;
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
	public List<PluginExcelConfVO> queryAllOwner(PluginExcelConfVO pluginExcelConfVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<PluginExcelConfVO> list = null;
		try {
			List<PluginExcelConf> pluginExcelConfs = pluginExcelConfDao.queryAllOwnerByPage(pluginExcelConfVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ pluginExcelConfs.size());
			pageSet(pluginExcelConfs, pluginExcelConfVo);
			list = (List<PluginExcelConfVO>) beansCopy(pluginExcelConfs, PluginExcelConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginExcelConfVO> queryAllCurrOrg(PluginExcelConfVO pluginExcelConfVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<PluginExcelConf> pluginExcelConfs = pluginExcelConfDao.queryAllCurrOrgByPage(pluginExcelConfVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+pluginExcelConfs.size());
		List<PluginExcelConfVO> list = null;
		try {
			pageSet(pluginExcelConfs, pluginExcelConfVo);
			list = (List<PluginExcelConfVO>) beansCopy(pluginExcelConfs, PluginExcelConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginExcelConfVO> queryAllCurrDownOrg(PluginExcelConfVO pluginExcelConfVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<PluginExcelConf> pluginExcelConfs = pluginExcelConfDao.queryAllCurrDownOrgByPage(pluginExcelConfVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ pluginExcelConfs.size());
		List<PluginExcelConfVO> list = null;
		try {
			pageSet(pluginExcelConfs, pluginExcelConfVo);
			list = (List<PluginExcelConfVO>) beansCopy(pluginExcelConfs, PluginExcelConfVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
