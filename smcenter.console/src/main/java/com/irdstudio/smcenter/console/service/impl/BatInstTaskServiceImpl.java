package com.irdstudio.smcenter.console.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.smcenter.console.service.api.BatInstTaskService;
import com.irdstudio.smcenter.console.dao.BatInstTaskDao;
import com.irdstudio.smcenter.console.dao.domain.BatInstTask;
import com.irdstudio.smcenter.console.service.vo.BatInstTaskTree;
import com.irdstudio.smcenter.console.service.vo.BatInstTaskVO;
import com.irdstudio.ssm.framework.service.FrameworkService;
/**
 * <p>ServiceImpl: 批次任务实例				<p>
 * @author ligm
 * @date 2018-06-13
 */
@Service("instTaskService")
public class BatInstTaskServiceImpl implements BatInstTaskService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(BatInstTaskServiceImpl.class);

	@Autowired
	private BatInstTaskDao batInstTaskDao;
	
	/**
	 * 新增操作
	 */
	public int insertBatInstTask(BatInstTaskVO inBatInstTaskVo) {
		logger.debug("当前新增数据为:"+ inBatInstTaskVo.toString());
		int num = 0;
		try {
			BatInstTask batInstTask = new BatInstTask();
			beanCopy(inBatInstTaskVo, batInstTask);
			num = batInstTaskDao.insertBatInstTask(batInstTask);
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
	public int deleteByPk(BatInstTaskVO inBatInstTaskVo) {
		logger.debug("当前删除数据条件为:"+ inBatInstTaskVo);
		int num = 0;
		try {
			BatInstTask batInstTask = new BatInstTask();
			beanCopy(inBatInstTaskVo, batInstTask);
			num = batInstTaskDao.deleteByPk(batInstTask);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inBatInstTaskVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(BatInstTaskVO inBatInstTaskVo) {
		logger.debug("当前修改数据为:"+ inBatInstTaskVo.toString());
		int num = 0;
		try {
			BatInstTask batInstTask = new BatInstTask();
			beanCopy(inBatInstTaskVo, batInstTask);
			num = batInstTaskDao.updateByPk(batInstTask);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inBatInstTaskVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public BatInstTaskVO queryByPk(BatInstTaskVO inBatInstTaskVo) {
		
		logger.debug("当前查询参数信息为:"+ inBatInstTaskVo);
		try {
			BatInstTask queryBatInstTask = new BatInstTask();
			beanCopy(inBatInstTaskVo, queryBatInstTask);
			BatInstTask queryRslBatInstTask = batInstTaskDao.queryByPk(queryBatInstTask);
			if (Objects.nonNull(queryRslBatInstTask)) {
				BatInstTaskVO outBatInstTaskVo = beanCopy(queryRslBatInstTask, new BatInstTaskVO());
				logger.debug("当前查询结果为:"+ outBatInstTaskVo.toString());
				return outBatInstTaskVo;
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
	public List<BatInstTaskVO> queryAllOwner(BatInstTaskVO batInstTaskVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<BatInstTaskVO> list = null;
		try {
			List<BatInstTask> batInstTasks = batInstTaskDao.queryAllOwnerByPage(batInstTaskVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ batInstTasks.size());
			pageSet(batInstTasks, batInstTaskVo);
			list = (List<BatInstTaskVO>) beansCopy(batInstTasks, BatInstTaskVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<BatInstTaskVO> queryAllCurrOrg(BatInstTaskVO batInstTaskVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<BatInstTask> batInstTasks = batInstTaskDao.queryAllCurrOrgByPage(batInstTaskVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+batInstTasks.size());
		List<BatInstTaskVO> list = null;
		try {
			pageSet(batInstTasks, batInstTaskVo);
			list = (List<BatInstTaskVO>) beansCopy(batInstTasks, BatInstTaskVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<BatInstTaskVO> queryAllCurrDownOrg(BatInstTaskVO batInstTaskVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<BatInstTask> batInstTasks = batInstTaskDao.queryAllCurrDownOrgByPage(batInstTaskVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ batInstTasks.size());
		List<BatInstTaskVO> list = null;
		try {
			pageSet(batInstTasks, batInstTaskVo);
			list = (List<BatInstTaskVO>) beansCopy(batInstTasks, BatInstTaskVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}
	
	/**
	 * 查询批次任务实例树
	 * @param batInstTaskVo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<BatInstTaskTree> queryBatInstTaskTree(BatInstTaskVO batInstTaskVo) {
		//任务实例树
		List<BatInstTaskTree> taskTree = new ArrayList<>();
		BatInstTaskTree<Object> root = new BatInstTaskTree<Object>(batInstTaskVo.getBatchId(), batInstTaskVo.getBatchId());
		//初始化根目录
		taskTree.add(root);
		
		Map<String, BatInstTaskTree> stageMap = new HashMap<>();
		//Map<String, BatInstTaskTree> taskMap = new HashMap<>();
		//查询所有的任务实例
		List<BatInstTask> batInstTasks = batInstTaskDao.queryAllInstTask(batInstTaskVo);
		for(int i=0; i<batInstTasks.size(); i++) {
			BatInstTask task = batInstTasks.get(i);
			BatInstTaskTree<BatInstTask> taskNode = new BatInstTaskTree<BatInstTask>(task.getTaskId(), task.getTaskName());
			taskNode.setAttributes(task);
			taskNode.setIconCls("task-tree-icon");
			
			//判断阶段是否存在
			if(!stageMap.containsKey(task.getStageId())) {
				BatInstTaskTree<Object> stageNode = new BatInstTaskTree<Object>(task.getStageId(), task.getStageName());
				stageNode.getChildren().add(taskNode);
				
				root.getChildren().add(stageNode);
				stageMap.put(task.getStageId(), stageNode);
			} else {
				//存在就直接加入到阶段下
				@SuppressWarnings("unchecked")
				BatInstTaskTree<Object> stageNode = stageMap.get(task.getStageId());
				stageNode.getChildren().add(taskNode);
			}
		}
		
		return taskTree;
	}

	@Override
	public int updateTaskInterveneState(BatInstTaskVO batInstTaskVo) {
		logger.debug("当前修改人工干预状态数据为:"+ batInstTaskVo.toString());
		int num = 0;
		try {
			BatInstTask batInstTask = new BatInstTask();
			beanCopy(batInstTaskVo, batInstTask);
			num = batInstTaskDao.updateTaskInterveneState(batInstTask);
		} catch (Exception e) {
			logger.error("修改人工干预状态数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ batInstTaskVo +"修改的人工干预状态数据条数为"+ num);
		return num;
	}
	
}
