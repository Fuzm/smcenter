package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.PluginLoadResultVO;

/**
 * <p>Description:数据装载结果表				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginLoadResultService {
	
	public List<PluginLoadResultVO> queryAllOwner(PluginLoadResultVO pluginLoadResultVo);
	
	public List<PluginLoadResultVO> queryAllCurrOrg(PluginLoadResultVO pluginLoadResultVo);
	
	public List<PluginLoadResultVO> queryAllCurrDownOrg(PluginLoadResultVO pluginLoadResultVo);
	
	public int insertPluginLoadResult(PluginLoadResultVO inPluginLoadResultVo);
	
	public int deleteByPk(PluginLoadResultVO pluginLoadResultVo);
	
	public int updateByPk(PluginLoadResultVO pluginLoadResultVo);
	
	public PluginLoadResultVO queryByPk(PluginLoadResultVO pluginLoadResultVo);

}
