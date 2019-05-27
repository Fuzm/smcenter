package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.PluginFileopConf;
import com.irdstudio.smcenter.console.service.vo.PluginFileopConfVO;
/**
 * <p>DAO interface:文件操作配置				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginFileopConfDao {
	
	public int insertPluginFileopConf(PluginFileopConf pluginFileopConf);
	
	public int deleteByPk(PluginFileopConf pluginFileopConf);
	
	public int updateByPk(PluginFileopConf pluginFileopConf);
	
	public PluginFileopConf queryByPk(PluginFileopConf pluginFileopConf);
	
	public List<PluginFileopConf> queryAllOwnerByPage(PluginFileopConfVO pluginFileopConf);
	
	public List<PluginFileopConf> queryAllCurrOrgByPage(PluginFileopConfVO pluginFileopConf);
	
	public List<PluginFileopConf> queryAllCurrDownOrgByPage(PluginFileopConfVO pluginFileopConf);

}
