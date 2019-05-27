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

import com.irdstudio.smcenter.console.service.api.PluginLoadResultService;
import com.irdstudio.smcenter.console.service.vo.PluginLoadResultVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class PluginLoadResultController extends AbstractController  {
	
	@Autowired
	@Qualifier("loadResultService")
	private PluginLoadResultService pluginLoadResultService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/plugin/load/results", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<PluginLoadResultVO>> queryPluginLoadResultAll(
			PluginLoadResultVO vo) {		
		List<PluginLoadResultVO> outputVo = pluginLoadResultService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/plugin/load/result/{batchSerialNo}/{tableName}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<PluginLoadResultVO> queryByPk(@PathVariable("batchSerialNo") String batchSerialNo,@PathVariable("tableName") String tableName) {		
		PluginLoadResultVO inVo = new PluginLoadResultVO();
				inVo.setBatchSerialNo(batchSerialNo);
				inVo.setTableName(tableName);
		PluginLoadResultVO outputVo = pluginLoadResultService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param pluginLoadResult
	 * @return
	 */
	@RequestMapping(value="/plugin/load/result", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody PluginLoadResultVO inPluginLoadResultVo) {		
		int outputVo = pluginLoadResultService.deleteByPk(inPluginLoadResultVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inPluginLoadResultVo
	 * @return
	 */
	@RequestMapping(value="/plugin/load/result", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody PluginLoadResultVO inPluginLoadResultVo) {		
		int outputVo = pluginLoadResultService.updateByPk(inPluginLoadResultVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inPluginLoadResultVo
	 * @return
	 */
	@RequestMapping(value="/plugin/load/result", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertPluginLoadResult(@RequestBody PluginLoadResultVO inPluginLoadResultVo) {
		int outputVo = pluginLoadResultService.insertPluginLoadResult(inPluginLoadResultVo);
		return getResponseData(outputVo);
		
	}
}
