package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.PluginFileopConfVO;

/**
 * <p>Description:文件操作配置				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginFileopConfService {
	
	public List<PluginFileopConfVO> queryAllOwner(PluginFileopConfVO pluginFileopConfVo);
	
	public List<PluginFileopConfVO> queryAllCurrOrg(PluginFileopConfVO pluginFileopConfVo);
	
	public List<PluginFileopConfVO> queryAllCurrDownOrg(PluginFileopConfVO pluginFileopConfVo);
	
	public int insertPluginFileopConf(PluginFileopConfVO inPluginFileopConfVo);
	
	public int deleteByPk(PluginFileopConfVO pluginFileopConfVo);
	
	public int updateByPk(PluginFileopConfVO pluginFileopConfVo);
	
	public PluginFileopConfVO queryByPk(PluginFileopConfVO pluginFileopConfVo);

}
