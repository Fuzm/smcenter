package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.PluginLoadConf;
import com.irdstudio.smcenter.console.service.vo.PluginLoadConfVO;
/**
 * <p>DAO interface:数据装载配置表				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginLoadConfDao {
	
	public int insertPluginLoadConf(PluginLoadConf pluginLoadConf);
	
	public int deleteByPk(PluginLoadConf pluginLoadConf);
	
	public int updateByPk(PluginLoadConf pluginLoadConf);
	
	public PluginLoadConf queryByPk(PluginLoadConf pluginLoadConf);
	
	public List<PluginLoadConf> queryAllOwnerByPage(PluginLoadConfVO pluginLoadConf);
	
	public List<PluginLoadConf> queryAllCurrOrgByPage(PluginLoadConfVO pluginLoadConf);
	
	public List<PluginLoadConf> queryAllCurrDownOrgByPage(PluginLoadConfVO pluginLoadConf);

}
