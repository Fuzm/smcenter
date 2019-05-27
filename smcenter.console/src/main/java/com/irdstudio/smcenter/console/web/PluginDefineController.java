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

import com.irdstudio.smcenter.console.service.api.PluginDefineService;
import com.irdstudio.smcenter.console.service.vo.PluginDefineVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class PluginDefineController extends AbstractController  {
	
	@Autowired
	@Qualifier("defineService")
	private PluginDefineService pluginDefineService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/plugin/defines", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<PluginDefineVO>> queryPluginDefineAll(
			PluginDefineVO vo) {		
		List<PluginDefineVO> outputVo = pluginDefineService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/plugin/define/{pluginId}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<PluginDefineVO> queryByPk(@PathVariable("pluginId") String pluginId) {		
		PluginDefineVO inVo = new PluginDefineVO();
				inVo.setPluginId(Integer.valueOf(pluginId));
		PluginDefineVO outputVo = pluginDefineService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param pluginDefine
	 * @return
	 */
	@RequestMapping(value="/plugin/define", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody PluginDefineVO inPluginDefineVo) {		
		int outputVo = pluginDefineService.deleteByPk(inPluginDefineVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inPluginDefineVo
	 * @return
	 */
	@RequestMapping(value="/plugin/define", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody PluginDefineVO inPluginDefineVo) {		
		int outputVo = pluginDefineService.updateByPk(inPluginDefineVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inPluginDefineVo
	 * @return
	 */
	@RequestMapping(value="/plugin/define", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertPluginDefine(@RequestBody PluginDefineVO inPluginDefineVo) {
		int outputVo = pluginDefineService.insertPluginDefine(inPluginDefineVo);
		return getResponseData(outputVo);
		
	}
}
