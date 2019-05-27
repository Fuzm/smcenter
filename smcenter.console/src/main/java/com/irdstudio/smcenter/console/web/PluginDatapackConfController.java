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

import com.irdstudio.smcenter.console.service.api.PluginDatapackConfService;
import com.irdstudio.smcenter.console.service.vo.PluginDatapackConfVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class PluginDatapackConfController extends AbstractController  {
	
	@Autowired
	@Qualifier("datapackConfService")
	private PluginDatapackConfService pluginDatapackConfService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/plugin/datapack/confs", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<PluginDatapackConfVO>> queryPluginDatapackConfAll(
			PluginDatapackConfVO vo) {		
		List<PluginDatapackConfVO> outputVo = pluginDatapackConfService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/plugin/datapack/conf/{pluginConfId}/{confSort}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<PluginDatapackConfVO> queryByPk(@PathVariable("pluginConfId") String pluginConfId,@PathVariable("confSort") String confSort) {		
		PluginDatapackConfVO inVo = new PluginDatapackConfVO();
				inVo.setPluginConfId(pluginConfId);
				inVo.setConfSort(Integer.valueOf(confSort));
		PluginDatapackConfVO outputVo = pluginDatapackConfService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param pluginDatapackConf
	 * @return
	 */
	@RequestMapping(value="/plugin/datapack/conf", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody PluginDatapackConfVO inPluginDatapackConfVo) {		
		int outputVo = pluginDatapackConfService.deleteByPk(inPluginDatapackConfVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inPluginDatapackConfVo
	 * @return
	 */
	@RequestMapping(value="/plugin/datapack/conf", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody PluginDatapackConfVO inPluginDatapackConfVo) {		
		int outputVo = pluginDatapackConfService.updateByPk(inPluginDatapackConfVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inPluginDatapackConfVo
	 * @return
	 */
	@RequestMapping(value="/plugin/datapack/conf", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertPluginDatapackConf(@RequestBody PluginDatapackConfVO inPluginDatapackConfVo) {
		int outputVo = pluginDatapackConfService.insertPluginDatapackConf(inPluginDatapackConfVo);
		return getResponseData(outputVo);
		
	}
}
