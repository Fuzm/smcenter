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

import com.irdstudio.smcenter.console.service.api.PluginJobConfService;
import com.irdstudio.smcenter.console.service.vo.PluginJobConfVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class PluginJobConfController extends AbstractController  {
	
	@Autowired
	@Qualifier("jobConfService")
	private PluginJobConfService pluginJobConfService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/plugin/job/confs", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<PluginJobConfVO>> queryPluginJobConfAll(
			PluginJobConfVO vo) {		
		List<PluginJobConfVO> outputVo = pluginJobConfService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/plugin/job/conf/{pluginConfId}/{confSort}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<PluginJobConfVO> queryByPk(@PathVariable("pluginConfId") String pluginConfId,@PathVariable("confSort") String confSort) {		
		PluginJobConfVO inVo = new PluginJobConfVO();
				inVo.setPluginConfId(pluginConfId);
				inVo.setConfSort(Integer.valueOf(confSort));
		PluginJobConfVO outputVo = pluginJobConfService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param pluginJobConf
	 * @return
	 */
	@RequestMapping(value="/plugin/job/conf", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody PluginJobConfVO inPluginJobConfVo) {		
		int outputVo = pluginJobConfService.deleteByPk(inPluginJobConfVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inPluginJobConfVo
	 * @return
	 */
	@RequestMapping(value="/plugin/job/conf", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody PluginJobConfVO inPluginJobConfVo) {		
		int outputVo = pluginJobConfService.updateByPk(inPluginJobConfVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inPluginJobConfVo
	 * @return
	 */
	@RequestMapping(value="/plugin/job/conf", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertPluginJobConf(@RequestBody PluginJobConfVO inPluginJobConfVo) {
		int outputVo = pluginJobConfService.insertPluginJobConf(inPluginJobConfVo);
		return getResponseData(outputVo);
		
	}
}
