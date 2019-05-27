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

import com.irdstudio.smcenter.console.service.api.PluginJobParamService;
import com.irdstudio.smcenter.console.service.vo.PluginJobParamVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class PluginJobParamController extends AbstractController  {
	
	@Autowired
	@Qualifier("jobParamService")
	private PluginJobParamService pluginJobParamService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/plugin/job/params", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<PluginJobParamVO>> queryPluginJobParamAll(
			PluginJobParamVO vo) {		
		List<PluginJobParamVO> outputVo = pluginJobParamService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/plugin/job/param/{paramGroupId}/{jobParamName}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<PluginJobParamVO> queryByPk(@PathVariable("paramGroupId") String paramGroupId,@PathVariable("jobParamName") String jobParamName) {		
		PluginJobParamVO inVo = new PluginJobParamVO();
				inVo.setParamGroupId(paramGroupId);
				inVo.setJobParamName(jobParamName);
		PluginJobParamVO outputVo = pluginJobParamService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param pluginJobParam
	 * @return
	 */
	@RequestMapping(value="/plugin/job/param", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody PluginJobParamVO inPluginJobParamVo) {		
		int outputVo = pluginJobParamService.deleteByPk(inPluginJobParamVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inPluginJobParamVo
	 * @return
	 */
	@RequestMapping(value="/plugin/job/param", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody PluginJobParamVO inPluginJobParamVo) {		
		int outputVo = pluginJobParamService.updateByPk(inPluginJobParamVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inPluginJobParamVo
	 * @return
	 */
	@RequestMapping(value="/plugin/job/param", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertPluginJobParam(@RequestBody PluginJobParamVO inPluginJobParamVo) {
		int outputVo = pluginJobParamService.insertPluginJobParam(inPluginJobParamVo);
		return getResponseData(outputVo);
		
	}
}
