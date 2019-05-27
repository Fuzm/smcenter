package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.PluginJobConf;
import com.irdstudio.smcenter.console.service.vo.PluginJobConfVO;
/**
 * <p>DAO interface:数据作业配置				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginJobConfDao {
	
	public int insertPluginJobConf(PluginJobConf pluginJobConf);
	
	public int deleteByPk(PluginJobConf pluginJobConf);
	
	public int updateByPk(PluginJobConf pluginJobConf);
	
	public PluginJobConf queryByPk(PluginJobConf pluginJobConf);
	
	public List<PluginJobConf> queryAllOwnerByPage(PluginJobConfVO pluginJobConf);
	
	public List<PluginJobConf> queryAllCurrOrgByPage(PluginJobConfVO pluginJobConf);
	
	public List<PluginJobConf> queryAllCurrDownOrgByPage(PluginJobConfVO pluginJobConf);

}
