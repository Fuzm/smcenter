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

import com.irdstudio.smcenter.console.service.api.PluginSysupdConfService;
import com.irdstudio.smcenter.console.service.vo.PluginSysupdConfVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class PluginSysupdConfController extends AbstractController  {
	
	@Autowired
	@Qualifier("sysupdConfService")
	private PluginSysupdConfService pluginSysupdConfService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/plugin/sysupd/confs", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<PluginSysupdConfVO>> queryPluginSysupdConfAll(
			PluginSysupdConfVO vo) {		
		List<PluginSysupdConfVO> outputVo = pluginSysupdConfService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/plugin/sysupd/conf/{pluginConfId}/{confSort}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<PluginSysupdConfVO> queryByPk(@PathVariable("pluginConfId") String pluginConfId,@PathVariable("confSort") String confSort) {		
		PluginSysupdConfVO inVo = new PluginSysupdConfVO();
				inVo.setPluginConfId(pluginConfId);
				inVo.setConfSort(Integer.valueOf(confSort));
		PluginSysupdConfVO outputVo = pluginSysupdConfService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param pluginSysupdConf
	 * @return
	 */
	@RequestMapping(value="/plugin/sysupd/conf", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody PluginSysupdConfVO inPluginSysupdConfVo) {		
		int outputVo = pluginSysupdConfService.deleteByPk(inPluginSysupdConfVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inPluginSysupdConfVo
	 * @return
	 */
	@RequestMapping(value="/plugin/sysupd/conf", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody PluginSysupdConfVO inPluginSysupdConfVo) {		
		int outputVo = pluginSysupdConfService.updateByPk(inPluginSysupdConfVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inPluginSysupdConfVo
	 * @return
	 */
	@RequestMapping(value="/plugin/sysupd/conf", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertPluginSysupdConf(@RequestBody PluginSysupdConfVO inPluginSysupdConfVo) {
		int outputVo = pluginSysupdConfService.insertPluginSysupdConf(inPluginSysupdConfVo);
		return getResponseData(outputVo);
		
	}
}
