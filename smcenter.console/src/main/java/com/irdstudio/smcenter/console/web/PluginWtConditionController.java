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

import com.irdstudio.smcenter.console.service.api.PluginWtConditionService;
import com.irdstudio.smcenter.console.service.vo.PluginWtConditionVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class PluginWtConditionController extends AbstractController  {
	
	@Autowired
	@Qualifier("wtConditionService")
	private PluginWtConditionService pluginWtConditionService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/plugin/wt/conditions", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<PluginWtConditionVO>> queryPluginWtConditionAll(
			PluginWtConditionVO vo) {		
		List<PluginWtConditionVO> outputVo = pluginWtConditionService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/plugin/wt/condition/{pluginConfId}/{confSort}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<PluginWtConditionVO> queryByPk(@PathVariable("pluginConfId") String pluginConfId,@PathVariable("confSort") String confSort) {		
		PluginWtConditionVO inVo = new PluginWtConditionVO();
				inVo.setPluginConfId(pluginConfId);
				inVo.setConfSort(Integer.valueOf(confSort));
		PluginWtConditionVO outputVo = pluginWtConditionService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param pluginWtCondition
	 * @return
	 */
	@RequestMapping(value="/plugin/wt/condition", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody PluginWtConditionVO inPluginWtConditionVo) {		
		int outputVo = pluginWtConditionService.deleteByPk(inPluginWtConditionVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inPluginWtConditionVo
	 * @return
	 */
	@RequestMapping(value="/plugin/wt/condition", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody PluginWtConditionVO inPluginWtConditionVo) {		
		int outputVo = pluginWtConditionService.updateByPk(inPluginWtConditionVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inPluginWtConditionVo
	 * @return
	 */
	@RequestMapping(value="/plugin/wt/condition", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertPluginWtCondition(@RequestBody PluginWtConditionVO inPluginWtConditionVo) {
		int outputVo = pluginWtConditionService.insertPluginWtCondition(inPluginWtConditionVo);
		return getResponseData(outputVo);
		
	}
}
