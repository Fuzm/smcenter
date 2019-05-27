package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.PluginExecLogVO;

/**
 * <p>Description:应用插件执行日志				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginExecLogService {
	
	public List<PluginExecLogVO> queryAllOwner(PluginExecLogVO pluginExecLogVo);
	
	public List<PluginExecLogVO> queryAllCurrOrg(PluginExecLogVO pluginExecLogVo);
	
	public List<PluginExecLogVO> queryAllCurrDownOrg(PluginExecLogVO pluginExecLogVo);
	
	public int insertPluginExecLog(PluginExecLogVO inPluginExecLogVo);
	
	public int deleteByPk(PluginExecLogVO pluginExecLogVo);
	
	public int updateByPk(PluginExecLogVO pluginExecLogVo);
	
	public PluginExecLogVO queryByPk(PluginExecLogVO pluginExecLogVo);

}
