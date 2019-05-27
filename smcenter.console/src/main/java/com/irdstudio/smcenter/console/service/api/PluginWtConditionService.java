package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.PluginWtConditionVO;

/**
 * <p>Description:等待条件表				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginWtConditionService {
	
	public List<PluginWtConditionVO> queryAllOwner(PluginWtConditionVO pluginWtConditionVo);
	
	public List<PluginWtConditionVO> queryAllCurrOrg(PluginWtConditionVO pluginWtConditionVo);
	
	public List<PluginWtConditionVO> queryAllCurrDownOrg(PluginWtConditionVO pluginWtConditionVo);
	
	public int insertPluginWtCondition(PluginWtConditionVO inPluginWtConditionVo);
	
	public int deleteByPk(PluginWtConditionVO pluginWtConditionVo);
	
	public int updateByPk(PluginWtConditionVO pluginWtConditionVo);
	
	public PluginWtConditionVO queryByPk(PluginWtConditionVO pluginWtConditionVo);

}
