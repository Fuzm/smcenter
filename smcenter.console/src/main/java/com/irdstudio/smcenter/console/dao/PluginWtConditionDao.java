package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.PluginWtCondition;
import com.irdstudio.smcenter.console.service.vo.PluginWtConditionVO;
/**
 * <p>DAO interface:等待条件表				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginWtConditionDao {
	
	public int insertPluginWtCondition(PluginWtCondition pluginWtCondition);
	
	public int deleteByPk(PluginWtCondition pluginWtCondition);
	
	public int updateByPk(PluginWtCondition pluginWtCondition);
	
	public PluginWtCondition queryByPk(PluginWtCondition pluginWtCondition);
	
	public List<PluginWtCondition> queryAllOwnerByPage(PluginWtConditionVO pluginWtCondition);
	
	public List<PluginWtCondition> queryAllCurrOrgByPage(PluginWtConditionVO pluginWtCondition);
	
	public List<PluginWtCondition> queryAllCurrDownOrgByPage(PluginWtConditionVO pluginWtCondition);

}
