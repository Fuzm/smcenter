package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.PluginCheckConfVO;

/**
 * <p>Description:数据检查配置表				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginCheckConfService {
	
	public List<PluginCheckConfVO> queryAllOwner(PluginCheckConfVO pluginCheckConfVo);
	
	public List<PluginCheckConfVO> queryAllCurrOrg(PluginCheckConfVO pluginCheckConfVo);
	
	public List<PluginCheckConfVO> queryAllCurrDownOrg(PluginCheckConfVO pluginCheckConfVo);
	
	public int insertPluginCheckConf(PluginCheckConfVO inPluginCheckConfVo);
	
	public int deleteByPk(PluginCheckConfVO pluginCheckConfVo);
	
	public int updateByPk(PluginCheckConfVO pluginCheckConfVo);
	
	public PluginCheckConfVO queryByPk(PluginCheckConfVO pluginCheckConfVo);

}
