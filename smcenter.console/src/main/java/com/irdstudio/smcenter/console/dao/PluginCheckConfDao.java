package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.PluginCheckConf;
import com.irdstudio.smcenter.console.service.vo.PluginCheckConfVO;
/**
 * <p>DAO interface:数据检查配置表				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginCheckConfDao {
	
	public int insertPluginCheckConf(PluginCheckConf pluginCheckConf);
	
	public int deleteByPk(PluginCheckConf pluginCheckConf);
	
	public int updateByPk(PluginCheckConf pluginCheckConf);
	
	public PluginCheckConf queryByPk(PluginCheckConf pluginCheckConf);
	
	public List<PluginCheckConf> queryAllOwnerByPage(PluginCheckConfVO pluginCheckConf);
	
	public List<PluginCheckConf> queryAllCurrOrgByPage(PluginCheckConfVO pluginCheckConf);
	
	public List<PluginCheckConf> queryAllCurrDownOrgByPage(PluginCheckConfVO pluginCheckConf);

}
