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

import com.irdstudio.smcenter.console.service.api.PluginCheckConfService;
import com.irdstudio.smcenter.console.service.vo.PluginCheckConfVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class PluginCheckConfController extends AbstractController  {
	
	@Autowired
	@Qualifier("checkConfService")
	private PluginCheckConfService pluginCheckConfService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/plugin/check/confs", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<PluginCheckConfVO>> queryPluginCheckConfAll(
			PluginCheckConfVO vo) {		
		List<PluginCheckConfVO> outputVo = pluginCheckConfService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/plugin/check/conf/{pluginConfId}/{confSort}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<PluginCheckConfVO> queryByPk(@PathVariable("pluginConfId") String pluginConfId,@PathVariable("confSort") String confSort) {		
		PluginCheckConfVO inVo = new PluginCheckConfVO();
				inVo.setPluginConfId(pluginConfId);
				inVo.setConfSort(Integer.valueOf(confSort));
		PluginCheckConfVO outputVo = pluginCheckConfService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param pluginCheckConf
	 * @return
	 */
	@RequestMapping(value="/plugin/check/conf", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody PluginCheckConfVO inPluginCheckConfVo) {		
		int outputVo = pluginCheckConfService.deleteByPk(inPluginCheckConfVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inPluginCheckConfVo
	 * @return
	 */
	@RequestMapping(value="/plugin/check/conf", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody PluginCheckConfVO inPluginCheckConfVo) {		
		int outputVo = pluginCheckConfService.updateByPk(inPluginCheckConfVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inPluginCheckConfVo
	 * @return
	 */
	@RequestMapping(value="/plugin/check/conf", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertPluginCheckConf(@RequestBody PluginCheckConfVO inPluginCheckConfVo) {
		int outputVo = pluginCheckConfService.insertPluginCheckConf(inPluginCheckConfVo);
		return getResponseData(outputVo);
		
	}
}
