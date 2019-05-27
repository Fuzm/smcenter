package com.irdstudio.smcenter.console.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.PluginWtConditionService;
import com.irdstudio.smcenter.console.dao.PluginWtConditionDao;
import com.irdstudio.smcenter.console.dao.domain.PluginWtCondition;
import com.irdstudio.smcenter.console.service.vo.PluginWtConditionVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 等待条件表				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("wtConditionService")
public class PluginWtConditionServiceImpl implements PluginWtConditionService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(PluginWtConditionServiceImpl.class);

	@Autowired
	private PluginWtConditionDao pluginWtConditionDao;
	
	/**
	 * 新增操作
	 */
	public int insertPluginWtCondition(PluginWtConditionVO inPluginWtConditionVo) {
		logger.debug("当前新增数据为:"+ inPluginWtConditionVo.toString());
		int num = 0;
		try {
			PluginWtCondition pluginWtCondition = new PluginWtCondition();
			beanCopy(inPluginWtConditionVo, pluginWtCondition);
			num = pluginWtConditionDao.insertPluginWtCondition(pluginWtCondition);
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
	public int deleteByPk(PluginWtConditionVO inPluginWtConditionVo) {
		logger.debug("当前删除数据条件为:"+ inPluginWtConditionVo);
		int num = 0;
		try {
			PluginWtCondition pluginWtCondition = new PluginWtCondition();
			beanCopy(inPluginWtConditionVo, pluginWtCondition);
			num = pluginWtConditionDao.deleteByPk(pluginWtCondition);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginWtConditionVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(PluginWtConditionVO inPluginWtConditionVo) {
		logger.debug("当前修改数据为:"+ inPluginWtConditionVo.toString());
		int num = 0;
		try {
			PluginWtCondition pluginWtCondition = new PluginWtCondition();
			beanCopy(inPluginWtConditionVo, pluginWtCondition);
			num = pluginWtConditionDao.updateByPk(pluginWtCondition);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inPluginWtConditionVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public PluginWtConditionVO queryByPk(PluginWtConditionVO inPluginWtConditionVo) {
		
		logger.debug("当前查询参数信息为:"+ inPluginWtConditionVo);
		try {
			PluginWtCondition queryPluginWtCondition = new PluginWtCondition();
			beanCopy(inPluginWtConditionVo, queryPluginWtCondition);
			PluginWtCondition queryRslPluginWtCondition = pluginWtConditionDao.queryByPk(queryPluginWtCondition);
			if (Objects.nonNull(queryRslPluginWtCondition)) {
				PluginWtConditionVO outPluginWtConditionVo = beanCopy(queryRslPluginWtCondition, new PluginWtConditionVO());
				logger.debug("当前查询结果为:"+ outPluginWtConditionVo.toString());
				return outPluginWtConditionVo;
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
	public List<PluginWtConditionVO> queryAllOwner(PluginWtConditionVO pluginWtConditionVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<PluginWtConditionVO> list = null;
		try {
			List<PluginWtCondition> pluginWtConditions = pluginWtConditionDao.queryAllOwnerByPage(pluginWtConditionVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ pluginWtConditions.size());
			pageSet(pluginWtConditions, pluginWtConditionVo);
			list = (List<PluginWtConditionVO>) beansCopy(pluginWtConditions, PluginWtConditionVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginWtConditionVO> queryAllCurrOrg(PluginWtConditionVO pluginWtConditionVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<PluginWtCondition> pluginWtConditions = pluginWtConditionDao.queryAllCurrOrgByPage(pluginWtConditionVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+pluginWtConditions.size());
		List<PluginWtConditionVO> list = null;
		try {
			pageSet(pluginWtConditions, pluginWtConditionVo);
			list = (List<PluginWtConditionVO>) beansCopy(pluginWtConditions, PluginWtConditionVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<PluginWtConditionVO> queryAllCurrDownOrg(PluginWtConditionVO pluginWtConditionVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<PluginWtCondition> pluginWtConditions = pluginWtConditionDao.queryAllCurrDownOrgByPage(pluginWtConditionVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ pluginWtConditions.size());
		List<PluginWtConditionVO> list = null;
		try {
			pageSet(pluginWtConditions, pluginWtConditionVo);
			list = (List<PluginWtConditionVO>) beansCopy(pluginWtConditions, PluginWtConditionVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
}
