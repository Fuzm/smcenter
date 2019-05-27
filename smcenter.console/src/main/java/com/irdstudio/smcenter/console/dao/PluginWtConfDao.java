package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.PluginWtConf;
import com.irdstudio.smcenter.console.service.vo.PluginWtConfVO;
/**
 * <p>DAO interface:等待指定条件配置表				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginWtConfDao {
	
	public int insertPluginWtConf(PluginWtConf pluginWtConf);
	
	public int deleteByPk(PluginWtConf pluginWtConf);
	
	public int updateByPk(PluginWtConf pluginWtConf);
	
	public PluginWtConf queryByPk(PluginWtConf pluginWtConf);
	
	public List<PluginWtConf> queryAllOwnerByPage(PluginWtConfVO pluginWtConf);
	
	public List<PluginWtConf> queryAllCurrOrgByPage(PluginWtConfVO pluginWtConf);
	
	public List<PluginWtConf> queryAllCurrDownOrgByPage(PluginWtConfVO pluginWtConf);

}
