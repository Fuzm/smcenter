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

import com.irdstudio.smcenter.console.service.api.PluginExecLogService;
import com.irdstudio.smcenter.console.service.vo.PluginExecLogVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class PluginExecLogController extends AbstractController  {
	
	@Autowired
	@Qualifier("execLogService")
	private PluginExecLogService pluginExecLogService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/plugin/exec/logs", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<PluginExecLogVO>> queryPluginExecLogAll(
			PluginExecLogVO vo) {		
		List<PluginExecLogVO> outputVo = pluginExecLogService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/plugin/exec/log", method=RequestMethod.GET)
	public @ResponseBody ResponseData<PluginExecLogVO> queryByPk() {		
		PluginExecLogVO inVo = new PluginExecLogVO();
		PluginExecLogVO outputVo = pluginExecLogService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param pluginExecLog
	 * @return
	 */
	@RequestMapping(value="/plugin/exec/log", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody PluginExecLogVO inPluginExecLogVo) {		
		int outputVo = pluginExecLogService.deleteByPk(inPluginExecLogVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inPluginExecLogVo
	 * @return
	 */
	@RequestMapping(value="/plugin/exec/log", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody PluginExecLogVO inPluginExecLogVo) {		
		int outputVo = pluginExecLogService.updateByPk(inPluginExecLogVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inPluginExecLogVo
	 * @return
	 */
	@RequestMapping(value="/plugin/exec/log", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertPluginExecLog(@RequestBody PluginExecLogVO inPluginExecLogVo) {
		int outputVo = pluginExecLogService.insertPluginExecLog(inPluginExecLogVo);
		return getResponseData(outputVo);
		
	}
}
