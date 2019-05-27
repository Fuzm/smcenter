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

import com.irdstudio.smcenter.console.service.api.PluginWtConfService;
import com.irdstudio.smcenter.console.service.vo.PluginWtConfVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class PluginWtConfController extends AbstractController  {
	
	@Autowired
	@Qualifier("wtConfService")
	private PluginWtConfService pluginWtConfService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/plugin/wt/confs", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<PluginWtConfVO>> queryPluginWtConfAll(
			PluginWtConfVO vo) {		
		List<PluginWtConfVO> outputVo = pluginWtConfService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/plugin/wt/conf/{pluginConfId}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<PluginWtConfVO> queryByPk(@PathVariable("pluginConfId") String pluginConfId) {		
		PluginWtConfVO inVo = new PluginWtConfVO();
				inVo.setPluginConfId(pluginConfId);
		PluginWtConfVO outputVo = pluginWtConfService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param pluginWtConf
	 * @return
	 */
	@RequestMapping(value="/plugin/wt/conf", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody PluginWtConfVO inPluginWtConfVo) {		
		int outputVo = pluginWtConfService.deleteByPk(inPluginWtConfVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inPluginWtConfVo
	 * @return
	 */
	@RequestMapping(value="/plugin/wt/conf", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody PluginWtConfVO inPluginWtConfVo) {		
		int outputVo = pluginWtConfService.updateByPk(inPluginWtConfVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inPluginWtConfVo
	 * @return
	 */
	@RequestMapping(value="/plugin/wt/conf", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertPluginWtConf(@RequestBody PluginWtConfVO inPluginWtConfVo) {
		int outputVo = pluginWtConfService.insertPluginWtConf(inPluginWtConfVo);
		return getResponseData(outputVo);
		
	}
}
