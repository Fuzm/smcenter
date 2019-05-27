package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.PluginServiceConfVO;

/**
 * <p>Description:服务调用插件				<p>
 * @author fuzm
 * @date 2018-06-16
 */
public interface PluginServiceConfService {
	
	public List<PluginServiceConfVO> queryAllOwner(PluginServiceConfVO pluginServiceConfVo);
	
	public List<PluginServiceConfVO> queryAllCurrOrg(PluginServiceConfVO pluginServiceConfVo);
	
	public List<PluginServiceConfVO> queryAllCurrDownOrg(PluginServiceConfVO pluginServiceConfVo);
	
	public int insertPluginServiceConf(PluginServiceConfVO inPluginServiceConfVo);
	
	public int deleteByPk(PluginServiceConfVO pluginServiceConfVo);
	
	public int updateByPk(PluginServiceConfVO pluginServiceConfVo);
	
	public PluginServiceConfVO queryByPk(PluginServiceConfVO pluginServiceConfVo);

}
