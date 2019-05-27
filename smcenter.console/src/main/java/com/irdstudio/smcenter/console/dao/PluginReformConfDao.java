package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.PluginReformConf;
import com.irdstudio.smcenter.console.service.vo.PluginReformConfVO;
/**
 * <p>DAO interface:数据表重整配置表				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginReformConfDao {
	
	public int insertPluginReformConf(PluginReformConf pluginReformConf);
	
	public int deleteByPk(PluginReformConf pluginReformConf);
	
	public int updateByPk(PluginReformConf pluginReformConf);
	
	public PluginReformConf queryByPk(PluginReformConf pluginReformConf);
	
	public List<PluginReformConf> queryAllOwnerByPage(PluginReformConfVO pluginReformConf);
	
	public List<PluginReformConf> queryAllCurrOrgByPage(PluginReformConfVO pluginReformConf);
	
	public List<PluginReformConf> queryAllCurrDownOrgByPage(PluginReformConfVO pluginReformConf);

}
