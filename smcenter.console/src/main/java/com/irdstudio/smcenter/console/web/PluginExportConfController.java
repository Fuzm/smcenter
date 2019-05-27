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

import com.irdstudio.smcenter.console.service.api.PluginExportConfService;
import com.irdstudio.smcenter.console.service.vo.PluginExportConfVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class PluginExportConfController extends AbstractController  {
	
	@Autowired
	@Qualifier("exportConfService")
	private PluginExportConfService pluginExportConfService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/plugin/export/confs", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<PluginExportConfVO>> queryPluginExportConfAll(
			PluginExportConfVO vo) {		
		List<PluginExportConfVO> outputVo = pluginExportConfService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/plugin/export/conf/{pluginConfId}/{confSort}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<PluginExportConfVO> queryByPk(@PathVariable("pluginConfId") String pluginConfId,@PathVariable("confSort") String confSort) {		
		PluginExportConfVO inVo = new PluginExportConfVO();
				inVo.setPluginConfId(pluginConfId);
				inVo.setConfSort(Integer.valueOf(confSort));
		PluginExportConfVO outputVo = pluginExportConfService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param pluginExportConf
	 * @return
	 */
	@RequestMapping(value="/plugin/export/conf", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody PluginExportConfVO inPluginExportConfVo) {		
		int outputVo = pluginExportConfService.deleteByPk(inPluginExportConfVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inPluginExportConfVo
	 * @return
	 */
	@RequestMapping(value="/plugin/export/conf", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody PluginExportConfVO inPluginExportConfVo) {		
		int outputVo = pluginExportConfService.updateByPk(inPluginExportConfVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inPluginExportConfVo
	 * @return
	 */
	@RequestMapping(value="/plugin/export/conf", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertPluginExportConf(@RequestBody PluginExportConfVO inPluginExportConfVo) {
		int outputVo = pluginExportConfService.insertPluginExportConf(inPluginExportConfVo);
		return getResponseData(outputVo);
		
	}
}
