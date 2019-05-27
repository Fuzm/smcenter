package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.PluginExcelConf;
import com.irdstudio.smcenter.console.service.vo.PluginExcelConfVO;
/**
 * <p>DAO interface:Excel操作配置表				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginExcelConfDao {
	
	public int insertPluginExcelConf(PluginExcelConf pluginExcelConf);
	
	public int deleteByPk(PluginExcelConf pluginExcelConf);
	
	public int updateByPk(PluginExcelConf pluginExcelConf);
	
	public PluginExcelConf queryByPk(PluginExcelConf pluginExcelConf);
	
	public List<PluginExcelConf> queryAllOwnerByPage(PluginExcelConfVO pluginExcelConf);
	
	public List<PluginExcelConf> queryAllCurrOrgByPage(PluginExcelConfVO pluginExcelConf);
	
	public List<PluginExcelConf> queryAllCurrDownOrgByPage(PluginExcelConfVO pluginExcelConf);

}
