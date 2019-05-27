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

import com.irdstudio.smcenter.console.service.api.PluginExcelConfService;
import com.irdstudio.smcenter.console.service.vo.PluginExcelConfVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class PluginExcelConfController extends AbstractController  {
	
	@Autowired
	@Qualifier("excelConfService")
	private PluginExcelConfService pluginExcelConfService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/plugin/excel/confs", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<PluginExcelConfVO>> queryPluginExcelConfAll(
			PluginExcelConfVO vo) {		
		List<PluginExcelConfVO> outputVo = pluginExcelConfService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/plugin/excel/conf/{pluginConfId}/{confSort}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<PluginExcelConfVO> queryByPk(@PathVariable("pluginConfId") String pluginConfId,@PathVariable("confSort") String confSort) {		
		PluginExcelConfVO inVo = new PluginExcelConfVO();
				inVo.setPluginConfId(pluginConfId);
				inVo.setConfSort(Integer.valueOf(confSort));
		PluginExcelConfVO outputVo = pluginExcelConfService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param pluginExcelConf
	 * @return
	 */
	@RequestMapping(value="/plugin/excel/conf", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody PluginExcelConfVO inPluginExcelConfVo) {		
		int outputVo = pluginExcelConfService.deleteByPk(inPluginExcelConfVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inPluginExcelConfVo
	 * @return
	 */
	@RequestMapping(value="/plugin/excel/conf", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody PluginExcelConfVO inPluginExcelConfVo) {		
		int outputVo = pluginExcelConfService.updateByPk(inPluginExcelConfVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inPluginExcelConfVo
	 * @return
	 */
	@RequestMapping(value="/plugin/excel/conf", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertPluginExcelConf(@RequestBody PluginExcelConfVO inPluginExcelConfVo) {
		int outputVo = pluginExcelConfService.insertPluginExcelConf(inPluginExcelConfVo);
		return getResponseData(outputVo);
		
	}
}
