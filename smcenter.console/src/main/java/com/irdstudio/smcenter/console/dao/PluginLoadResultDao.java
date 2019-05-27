package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.PluginLoadResult;
import com.irdstudio.smcenter.console.service.vo.PluginLoadResultVO;
/**
 * <p>DAO interface:数据装载结果表				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginLoadResultDao {
	
	public int insertPluginLoadResult(PluginLoadResult pluginLoadResult);
	
	public int deleteByPk(PluginLoadResult pluginLoadResult);
	
	public int updateByPk(PluginLoadResult pluginLoadResult);
	
	public PluginLoadResult queryByPk(PluginLoadResult pluginLoadResult);
	
	public List<PluginLoadResult> queryAllOwnerByPage(PluginLoadResultVO pluginLoadResult);
	
	public List<PluginLoadResult> queryAllCurrOrgByPage(PluginLoadResultVO pluginLoadResult);
	
	public List<PluginLoadResult> queryAllCurrDownOrgByPage(PluginLoadResultVO pluginLoadResult);

}
