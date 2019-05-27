package com.irdstudio.smcenter.console.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.irdstudio.smcenter.console.service.api.BatTaskUnitConfigService;
import com.irdstudio.smcenter.console.service.vo.BatTaskUnitConfigVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class BatTaskUnitConfigController extends AbstractController  {
	
	@Autowired
	@Qualifier("taskConfigService")
	private BatTaskUnitConfigService batTaskUnitConfigService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/bat/task/unit/configs", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<BatTaskUnitConfigVO>> queryBatTaskUnitConfigAll(
			BatTaskUnitConfigVO vo) {		
		List<BatTaskUnitConfigVO> outputVo = batTaskUnitConfigService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/bat/task/unit/config/{taskId}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<BatTaskUnitConfigVO> queryByPk(@PathVariable("taskId") String taskId) {		
		BatTaskUnitConfigVO inVo = new BatTaskUnitConfigVO();
				inVo.setTaskId(taskId);
		BatTaskUnitConfigVO outputVo = batTaskUnitConfigService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param batTaskUnitConfig
	 * @return
	 */
	@RequestMapping(value="/bat/task/unit/config", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody BatTaskUnitConfigVO inBatTaskUnitConfigVo) {		
		int outputVo = batTaskUnitConfigService.deleteByPk(inBatTaskUnitConfigVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inBatTaskUnitConfigVo
	 * @return
	 */
	@RequestMapping(value="/bat/task/unit/config", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody BatTaskUnitConfigVO inBatTaskUnitConfigVo) {		
		int outputVo = batTaskUnitConfigService.updateByPk(inBatTaskUnitConfigVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inBatTaskUnitConfigVo
	 * @return
	 */
	@RequestMapping(value="/bat/task/unit/config", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertBatTaskUnitConfig(@RequestBody BatTaskUnitConfigVO inBatTaskUnitConfigVo) {
		int outputVo = batTaskUnitConfigService.insertBatTaskUnitConfig(inBatTaskUnitConfigVo);
		return getResponseData(outputVo);
		
	}
}
