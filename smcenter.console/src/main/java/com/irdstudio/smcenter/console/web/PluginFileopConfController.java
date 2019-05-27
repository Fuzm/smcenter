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

import com.irdstudio.smcenter.console.service.api.PluginFileopConfService;
import com.irdstudio.smcenter.console.service.vo.PluginFileopConfVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class PluginFileopConfController extends AbstractController  {
	
	@Autowired
	@Qualifier("fileopConfService")
	private PluginFileopConfService pluginFileopConfService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/plugin/fileop/confs", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<PluginFileopConfVO>> queryPluginFileopConfAll(
			PluginFileopConfVO vo) {		
		List<PluginFileopConfVO> outputVo = pluginFileopConfService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/plugin/fileop/conf/{pluginConfId}/{confSort}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<PluginFileopConfVO> queryByPk(@PathVariable("pluginConfId") String pluginConfId,@PathVariable("confSort") String confSort) {		
		PluginFileopConfVO inVo = new PluginFileopConfVO();
				inVo.setPluginConfId(pluginConfId);
				inVo.setConfSort(Integer.valueOf(confSort));
		PluginFileopConfVO outputVo = pluginFileopConfService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param pluginFileopConf
	 * @return
	 */
	@RequestMapping(value="/plugin/fileop/conf", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody PluginFileopConfVO inPluginFileopConfVo) {		
		int outputVo = pluginFileopConfService.deleteByPk(inPluginFileopConfVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inPluginFileopConfVo
	 * @return
	 */
	@RequestMapping(value="/plugin/fileop/conf", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody PluginFileopConfVO inPluginFileopConfVo) {		
		int outputVo = pluginFileopConfService.updateByPk(inPluginFileopConfVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inPluginFileopConfVo
	 * @return
	 */
	@RequestMapping(value="/plugin/fileop/conf", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertPluginFileopConf(@RequestBody PluginFileopConfVO inPluginFileopConfVo) {
		int outputVo = pluginFileopConfService.insertPluginFileopConf(inPluginFileopConfVo);
		return getResponseData(outputVo);
		
	}
}
