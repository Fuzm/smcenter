package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.PluginExecLogService;
import com.irdstudio.smcenter.console.dao.PluginExecLogDao;
import com.irdstudio.smcenter.console.dao.domain.PluginExecLog;
import com.irdstudio.smcenter.console.service.vo.PluginExecLogVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 应用插件执行日志				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("execLogService")
public class PluginExecLogServiceImpl implements PluginExecLogService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(PluginExecLogServiceImpl.class);

	@Autowired
	private PluginExecLogDao pluginExecLogDao;
	
	/**
	 * 新增操作
	 */
	public int insertPluginExecLog(PluginExecLogVO inPluginExecLogVo) {
		logger.debug("当前新增数据为:"+ inPluginExecLogVo.toString());
		int num = 0;
		try {
			PluginExecLog pluginExecLog = new PluginExecLog();
			beanCopy(inPluginExecLogVo, pluginExecLog);
			num = pluginExecLogDao.insertPluginExecLog(pluginExecLog);
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
	public int deleteByPk(PluginExecLogVO inPluginExecLogVo) {
		logger.debug("当前删除数据条件为:"+ inPluginExecLogVo);
		int num = 0;
		try {
			PluginExecLog pluginExecLog = new PluginExecLog();
			beanCopy(inPluginExecLogVo, pluginExecLog);
			num = pluginExecLogDao.deleteByPk(pluginExecLog);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginExecLogVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(PluginExecLogVO inPluginExecLogVo) {
		logger.debug("当前修改数据为:"+ inPluginExecLogVo.toString());
		int num = 0;
		try {
			PluginExecLog pluginExecLog = new PluginExecLog();
			beanCopy(inPluginExecLogVo, pluginExecLog);
			num = pluginExecLogDao.updateByPk(pluginExecLog);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginExecLogVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public PluginExecLogVO queryByPk(PluginExecLogVO inPluginExecLogVo) {
		
		logger.debug("当前查询参数信息为:"+ inPluginExecLogVo);
		try {
			PluginExecLog queryPluginExecLog = new PluginExecLog();
			beanCopy(inPluginExecLogVo, queryPluginExecLog);
			PluginExecLog queryRslPluginExecLog = pluginExecLogDao.queryByPk(queryPluginExecLog);
			if (Objects.nonNull(queryRslPluginExecLog)) {
				PluginExecLogVO outPluginExecLogVo = beanCopy(queryRslPluginExecLog, new PluginExecLogVO());
				logger.debug("当前查询结果为:"+ outPluginExecLogVo.toString());
				return outPluginExecLogVo;
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
	public List<PluginExecLogVO> queryAllOwner(PluginExecLogVO pluginExecLogVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<PluginExecLogVO> list = null;
		try {
			List<PluginExecLog> pluginExecLogs = pluginExecLogDao.queryAllOwnerByPage(pluginExecLogVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ pluginExecLogs.size());
			pageSet(pluginExecLogs, pluginExecLogVo);
			list = (List<PluginExecLogVO>) beansCopy(pluginExecLogs, PluginExecLogVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginExecLogVO> queryAllCurrOrg(PluginExecLogVO pluginExecLogVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<PluginExecLog> pluginExecLogs = pluginExecLogDao.queryAllCurrOrgByPage(pluginExecLogVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+pluginExecLogs.size());
		List<PluginExecLogVO> list = null;
		try {
			pageSet(pluginExecLogs, pluginExecLogVo);
			list = (List<PluginExecLogVO>) beansCopy(pluginExecLogs, PluginExecLogVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginExecLogVO> queryAllCurrDownOrg(PluginExecLogVO pluginExecLogVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<PluginExecLog> pluginExecLogs = pluginExecLogDao.queryAllCurrDownOrgByPage(pluginExecLogVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ pluginExecLogs.size());
		List<PluginExecLogVO> list = null;
		try {
			pageSet(pluginExecLogs, pluginExecLogVo);
			list = (List<PluginExecLogVO>) beansCopy(pluginExecLogs, PluginExecLogVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
