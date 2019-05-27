package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.PluginExecLog;
import com.irdstudio.smcenter.console.service.vo.PluginExecLogVO;
/**
 * <p>DAO interface:应用插件执行日志				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginExecLogDao {
	
	public int insertPluginExecLog(PluginExecLog pluginExecLog);
	
	public int deleteByPk(PluginExecLog pluginExecLog);
	
	public int updateByPk(PluginExecLog pluginExecLog);
	
	public PluginExecLog queryByPk(PluginExecLog pluginExecLog);
	
	public List<PluginExecLog> queryAllOwnerByPage(PluginExecLogVO pluginExecLog);
	
	public List<PluginExecLog> queryAllCurrOrgByPage(PluginExecLogVO pluginExecLog);
	
	public List<PluginExecLog> queryAllCurrDownOrgByPage(PluginExecLogVO pluginExecLog);

}
