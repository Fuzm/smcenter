package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.PluginReformConfVO;

/**
 * <p>Description:数据表重整配置表				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginReformConfService {
	
	public List<PluginReformConfVO> queryAllOwner(PluginReformConfVO pluginReformConfVo);
	
	public List<PluginReformConfVO> queryAllCurrOrg(PluginReformConfVO pluginReformConfVo);
	
	public List<PluginReformConfVO> queryAllCurrDownOrg(PluginReformConfVO pluginReformConfVo);
	
	public int insertPluginReformConf(PluginReformConfVO inPluginReformConfVo);
	
	public int deleteByPk(PluginReformConfVO pluginReformConfVo);
	
	public int updateByPk(PluginReformConfVO pluginReformConfVo);
	
	public PluginReformConfVO queryByPk(PluginReformConfVO pluginReformConfVo);

}
