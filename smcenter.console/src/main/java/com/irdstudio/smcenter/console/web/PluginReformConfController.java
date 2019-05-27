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

import com.irdstudio.smcenter.console.service.api.PluginReformConfService;
import com.irdstudio.smcenter.console.service.vo.PluginReformConfVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class PluginReformConfController extends AbstractController  {
	
	@Autowired
	@Qualifier("reformConfService")
	private PluginReformConfService pluginReformConfService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/plugin/reform/confs", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<PluginReformConfVO>> queryPluginReformConfAll(
			PluginReformConfVO vo) {		
		List<PluginReformConfVO> outputVo = pluginReformConfService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/plugin/reform/conf/{pluginConfId}/{confSort}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<PluginReformConfVO> queryByPk(@PathVariable("pluginConfId") String pluginConfId,@PathVariable("confSort") String confSort) {		
		PluginReformConfVO inVo = new PluginReformConfVO();
				inVo.setPluginConfId(pluginConfId);
				inVo.setConfSort(Integer.valueOf(confSort));
		PluginReformConfVO outputVo = pluginReformConfService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param pluginReformConf
	 * @return
	 */
	@RequestMapping(value="/plugin/reform/conf", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody PluginReformConfVO inPluginReformConfVo) {		
		int outputVo = pluginReformConfService.deleteByPk(inPluginReformConfVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inPluginReformConfVo
	 * @return
	 */
	@RequestMapping(value="/plugin/reform/conf", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody PluginReformConfVO inPluginReformConfVo) {		
		int outputVo = pluginReformConfService.updateByPk(inPluginReformConfVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inPluginReformConfVo
	 * @return
	 */
	@RequestMapping(value="/plugin/reform/conf", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertPluginReformConf(@RequestBody PluginReformConfVO inPluginReformConfVo) {
		int outputVo = pluginReformConfService.insertPluginReformConf(inPluginReformConfVo);
		return getResponseData(outputVo);
		
	}
}
