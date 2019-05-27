package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.PluginServiceConf;
import com.irdstudio.smcenter.console.service.vo.PluginServiceConfVO;
/**
 * <p>DAO interface:服务调用插件				<p>
 * @author fuzm
 * @date 2018-06-16
 */
public interface PluginServiceConfDao {
	
	public int insertPluginServiceConf(PluginServiceConf pluginServiceConf);
	
	public int deleteByPk(PluginServiceConf pluginServiceConf);
	
	public int updateByPk(PluginServiceConf pluginServiceConf);
	
	public PluginServiceConf queryByPk(PluginServiceConf pluginServiceConf);
	
	public List<PluginServiceConf> queryAllOwnerByPage(PluginServiceConfVO pluginServiceConf);
	
	public List<PluginServiceConf> queryAllCurrOrgByPage(PluginServiceConfVO pluginServiceConf);
	
	public List<PluginServiceConf> queryAllCurrDownOrgByPage(PluginServiceConfVO pluginServiceConf);

}
