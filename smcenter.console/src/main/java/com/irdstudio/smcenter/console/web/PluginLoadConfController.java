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

import com.irdstudio.smcenter.console.service.api.PluginLoadConfService;
import com.irdstudio.smcenter.console.service.vo.PluginLoadConfVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class PluginLoadConfController extends AbstractController  {
	
	@Autowired
	@Qualifier("loadConfService")
	private PluginLoadConfService pluginLoadConfService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/plugin/load/confs", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<PluginLoadConfVO>> queryPluginLoadConfAll(
			PluginLoadConfVO vo) {		
		List<PluginLoadConfVO> outputVo = pluginLoadConfService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/plugin/load/conf/{tableName}/{pluginConfId}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<PluginLoadConfVO> queryByPk(@PathVariable("tableName") String tableName,@PathVariable("pluginConfId") String pluginConfId) {		
		PluginLoadConfVO inVo = new PluginLoadConfVO();
				inVo.setTableName(tableName);
				inVo.setPluginConfId(pluginConfId);
		PluginLoadConfVO outputVo = pluginLoadConfService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param pluginLoadConf
	 * @return
	 */
	@RequestMapping(value="/plugin/load/conf", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody PluginLoadConfVO inPluginLoadConfVo) {		
		int outputVo = pluginLoadConfService.deleteByPk(inPluginLoadConfVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inPluginLoadConfVo
	 * @return
	 */
	@RequestMapping(value="/plugin/load/conf", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody PluginLoadConfVO inPluginLoadConfVo) {		
		int outputVo = pluginLoadConfService.updateByPk(inPluginLoadConfVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inPluginLoadConfVo
	 * @return
	 */
	@RequestMapping(value="/plugin/load/conf", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertPluginLoadConf(@RequestBody PluginLoadConfVO inPluginLoadConfVo) {
		int outputVo = pluginLoadConfService.insertPluginLoadConf(inPluginLoadConfVo);
		return getResponseData(outputVo);
		
	}
}
