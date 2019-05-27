package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.PluginLoadConfVO;

/**
 * <p>Description:数据装载配置表				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginLoadConfService {
	
	public List<PluginLoadConfVO> queryAllOwner(PluginLoadConfVO pluginLoadConfVo);
	
	public List<PluginLoadConfVO> queryAllCurrOrg(PluginLoadConfVO pluginLoadConfVo);
	
	public List<PluginLoadConfVO> queryAllCurrDownOrg(PluginLoadConfVO pluginLoadConfVo);
	
	public int insertPluginLoadConf(PluginLoadConfVO inPluginLoadConfVo);
	
	public int deleteByPk(PluginLoadConfVO pluginLoadConfVo);
	
	public int updateByPk(PluginLoadConfVO pluginLoadConfVo);
	
	public PluginLoadConfVO queryByPk(PluginLoadConfVO pluginLoadConfVo);

}
