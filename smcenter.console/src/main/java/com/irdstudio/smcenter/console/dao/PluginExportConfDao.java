package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.PluginExportConf;
import com.irdstudio.smcenter.console.service.vo.PluginExportConfVO;
/**
 * <p>DAO interface:导出为文本文件插件				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginExportConfDao {
	
	public int insertPluginExportConf(PluginExportConf pluginExportConf);
	
	public int deleteByPk(PluginExportConf pluginExportConf);
	
	public int updateByPk(PluginExportConf pluginExportConf);
	
	public PluginExportConf queryByPk(PluginExportConf pluginExportConf);
	
	public List<PluginExportConf> queryAllOwnerByPage(PluginExportConfVO pluginExportConf);
	
	public List<PluginExportConf> queryAllCurrOrgByPage(PluginExportConfVO pluginExportConf);
	
	public List<PluginExportConf> queryAllCurrDownOrgByPage(PluginExportConfVO pluginExportConf);

}
