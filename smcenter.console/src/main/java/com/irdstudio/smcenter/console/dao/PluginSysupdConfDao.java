package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.PluginSysupdConf;
import com.irdstudio.smcenter.console.service.vo.PluginSysupdConfVO;
/**
 * <p>DAO interface:系统信息更新配置表				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginSysupdConfDao {
	
	public int insertPluginSysupdConf(PluginSysupdConf pluginSysupdConf);
	
	public int deleteByPk(PluginSysupdConf pluginSysupdConf);
	
	public int updateByPk(PluginSysupdConf pluginSysupdConf);
	
	public PluginSysupdConf queryByPk(PluginSysupdConf pluginSysupdConf);
	
	public List<PluginSysupdConf> queryAllOwnerByPage(PluginSysupdConfVO pluginSysupdConf);
	
	public List<PluginSysupdConf> queryAllCurrOrgByPage(PluginSysupdConfVO pluginSysupdConf);
	
	public List<PluginSysupdConf> queryAllCurrDownOrgByPage(PluginSysupdConfVO pluginSysupdConf);

}
