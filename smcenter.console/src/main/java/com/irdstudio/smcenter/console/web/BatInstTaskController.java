package com.irdstudio.smcenter.console.web;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.irdstudio.smcenter.console.service.api.BatInstTaskService;
import com.irdstudio.smcenter.console.service.api.BatTaskUnitConfigService;
import com.irdstudio.smcenter.console.service.vo.BatInstTaskTree;
import com.irdstudio.smcenter.console.service.vo.BatInstTaskVO;
import com.irdstudio.smcenter.console.service.vo.BatTaskUnitConfigVO;
import com.irdstudio.ssm.framework.constant.BaseConstance;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class BatInstTaskController extends AbstractController  {
	
	@Autowired
	@Qualifier("instTaskService")
	private BatInstTaskService batInstTaskService;
	
	@Autowired
	@Qualifier("taskConfigService")
	private BatTaskUnitConfigService batTaskUnitConfigService;
	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/bat/inst/tasks", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<BatInstTaskVO>> queryBatInstTaskAll(
			BatInstTaskVO vo) {		
		List<BatInstTaskVO> outputVo = batInstTaskService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/bat/inst/task/{batchId}/{batchOrder}/{taskId}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<BatInstTaskVO> queryByPk(@PathVariable("batchId") String batchId,@PathVariable("batchOrder") String batchOrder,@PathVariable("taskId") String taskId) {		
		BatInstTaskVO inVo = new BatInstTaskVO();
				inVo.setBatchId(batchId);
				inVo.setBatchOrder(Integer.valueOf(batchOrder));
				inVo.setTaskId(taskId);
		BatInstTaskVO outputVo = batInstTaskService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param batInstTask
	 * @return
	 */
	@RequestMapping(value="/bat/inst/task", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody BatInstTaskVO inBatInstTaskVo) {		
		int outputVo = batInstTaskService.deleteByPk(inBatInstTaskVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inBatInstTaskVo
	 * @return
	 */
	@RequestMapping(value="/bat/inst/task", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody BatInstTaskVO inBatInstTaskVo) {		
		int outputVo = batInstTaskService.updateByPk(inBatInstTaskVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inBatInstTaskVo
	 * @return
	 */
	@RequestMapping(value="/bat/inst/task", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertBatInstTask(@RequestBody BatInstTaskVO inBatInstTaskVo) {
		int outputVo = batInstTaskService.insertBatInstTask(inBatInstTaskVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据查询批次下的任务实例树
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/bat/inst/task/tree/{batchId}", method=RequestMethod.GET)
	public @ResponseBody List<BatInstTaskTree> queryInstTaskTree(@PathVariable("batchId") String batchId) {		
		BatInstTaskVO inVo = new BatInstTaskVO();
		inVo.setBatchId(batchId);
		List<BatInstTaskTree> outputVo = batInstTaskService.queryBatInstTaskTree(inVo);
		
		//Map<String, List<BatInstTaskTree>> result = new HashMap<>();
		//result.put("data", outputVo);
		return outputVo;
	}
	
	/**
	 * 挂起
	 * @param inBatInstTaskVo
	 * @return
	 */
	@RequestMapping(value="/bat/inst/task/hangup", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> hangUp(@RequestBody BatInstTaskVO inBatInstTaskVo) {	
		inBatInstTaskVo.setTaskInterveneState(BaseConstance.TASK_INT_STATE_SUSPEND);
		int outputVo = batInstTaskService.updateTaskInterveneState(inBatInstTaskVo);
		return getResponseData(outputVo);
	}
	
	/**
	 * 解挂
	 * @param inBatInstTaskVo
	 * @return
	 */
	@RequestMapping(value="/bat/inst/task/hangdown", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> hangDown(@RequestBody BatInstTaskVO inBatInstTaskVo) {	
		inBatInstTaskVo.setTaskInterveneState(BaseConstance.TASK_INT_STATE_RUN);
		int outputVo = batInstTaskService.updateTaskInterveneState(inBatInstTaskVo);
		return getResponseData(outputVo);
	}
	
	/**
	 * 置过
	 * @param inBatInstTaskVo
	 * @return
	 */
	@RequestMapping(value="/bat/inst/task/skip", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> skip(@RequestBody BatInstTaskVO inBatInstTaskVo) {
		
		BatTaskUnitConfigVO params = new BatTaskUnitConfigVO();
		params.setTaskId(inBatInstTaskVo.getTaskId());
		BatTaskUnitConfigVO configVO = batTaskUnitConfigService.queryByPk(params);
		
		ResponseData<Integer> data = null;
		//判断失败策略不是禁止
		if(configVO != null && StringUtils.isNotEmpty(configVO.getTaskSkipTactic()) && 
				!configVO.getTaskSkipTactic().equals(BaseConstance.TASK_SKIP_TACTIC) ) {
			
			inBatInstTaskVo.setTaskInterveneState(BaseConstance.TASK_INT_STATE_SKIP);
			int outputVo = batInstTaskService.updateTaskInterveneState(inBatInstTaskVo);
			
			data = getResponseData(outputVo);
		} else {
			data = getResponseData(-1);
			data.setMessage("该任务不允许置过！");
		}
		
		return data;
	}
}
