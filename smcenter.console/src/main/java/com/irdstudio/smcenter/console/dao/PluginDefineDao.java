package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.PluginDefine;
import com.irdstudio.smcenter.console.service.vo.PluginDefineVO;
/**
 * <p>DAO interface:应用插件信息				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginDefineDao {
	
	public int insertPluginDefine(PluginDefine pluginDefine);
	
	public int deleteByPk(PluginDefine pluginDefine);
	
	public int updateByPk(PluginDefine pluginDefine);
	
	public PluginDefine queryByPk(PluginDefine pluginDefine);
	
	public List<PluginDefine> queryAllOwnerByPage(PluginDefineVO pluginDefine);
	
	public List<PluginDefine> queryAllCurrOrgByPage(PluginDefineVO pluginDefine);
	
	public List<PluginDefine> queryAllCurrDownOrgByPage(PluginDefineVO pluginDefine);

}
