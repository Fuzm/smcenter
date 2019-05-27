package com.irdstudio.smcenter.console.web;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.irdstudio.smcenter.console.service.api.PluginServiceConfService;
import com.irdstudio.smcenter.console.service.vo.PluginServiceConfVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class PluginServiceConfController extends AbstractController  {
	
	@Autowired
	@Qualifier("batPluginServiceConfService")
	private PluginServiceConfService pluginServiceConfService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/plugin/service/confs", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<PluginServiceConfVO>> queryPluginServiceConfAll(
			PluginServiceConfVO vo) {		
		List<PluginServiceConfVO> outputVo = pluginServiceConfService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/plugin/service/conf/{pluginConfId}/{confSort}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<PluginServiceConfVO> queryByPk(@PathVariable("pluginConfId") String pluginConfId,@PathVariable("confSort") String confSort) {		
		PluginServiceConfVO inVo = new PluginServiceConfVO();
				inVo.setPluginConfId(pluginConfId);
				inVo.setConfSort(new BigDecimal(confSort));
		PluginServiceConfVO outputVo = pluginServiceConfService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param pluginServiceConf
	 * @return
	 */
	@RequestMapping(value="/plugin/service/conf", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody PluginServiceConfVO inPluginServiceConfVo) {		
		int outputVo = pluginServiceConfService.deleteByPk(inPluginServiceConfVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inPluginServiceConfVo
	 * @return
	 */
	@RequestMapping(value="/plugin/service/conf", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody PluginServiceConfVO inPluginServiceConfVo) {		
		int outputVo = pluginServiceConfService.updateByPk(inPluginServiceConfVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inPluginServiceConfVo
	 * @return
	 */
	@RequestMapping(value="/plugin/service/conf", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertPluginServiceConf(@RequestBody PluginServiceConfVO inPluginServiceConfVo) {
		int outputVo = pluginServiceConfService.insertPluginServiceConf(inPluginServiceConfVo);
		return getResponseData(outputVo);
		
	}
}
