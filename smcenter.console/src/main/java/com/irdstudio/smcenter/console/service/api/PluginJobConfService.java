package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.PluginJobConfVO;

/**
 * <p>Description:数据作业配置				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginJobConfService {
	
	public List<PluginJobConfVO> queryAllOwner(PluginJobConfVO pluginJobConfVo);
	
	public List<PluginJobConfVO> queryAllCurrOrg(PluginJobConfVO pluginJobConfVo);
	
	public List<PluginJobConfVO> queryAllCurrDownOrg(PluginJobConfVO pluginJobConfVo);
	
	public int insertPluginJobConf(PluginJobConfVO inPluginJobConfVo);
	
	public int deleteByPk(PluginJobConfVO pluginJobConfVo);
	
	public int updateByPk(PluginJobConfVO pluginJobConfVo);
	
	public PluginJobConfVO queryByPk(PluginJobConfVO pluginJobConfVo);

}
