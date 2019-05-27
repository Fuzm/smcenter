package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.PluginWtConfVO;

/**
 * <p>Description:等待指定条件配置表				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginWtConfService {
	
	public List<PluginWtConfVO> queryAllOwner(PluginWtConfVO pluginWtConfVo);
	
	public List<PluginWtConfVO> queryAllCurrOrg(PluginWtConfVO pluginWtConfVo);
	
	public List<PluginWtConfVO> queryAllCurrDownOrg(PluginWtConfVO pluginWtConfVo);
	
	public int insertPluginWtConf(PluginWtConfVO inPluginWtConfVo);
	
	public int deleteByPk(PluginWtConfVO pluginWtConfVo);
	
	public int updateByPk(PluginWtConfVO pluginWtConfVo);
	
	public PluginWtConfVO queryByPk(PluginWtConfVO pluginWtConfVo);

}
