package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.PluginDatapackConf;
import com.irdstudio.smcenter.console.service.vo.PluginDatapackConfVO;
/**
 * <p>DAO interface:数据报文文件生成配置表				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginDatapackConfDao {
	
	public int insertPluginDatapackConf(PluginDatapackConf pluginDatapackConf);
	
	public int deleteByPk(PluginDatapackConf pluginDatapackConf);
	
	public int updateByPk(PluginDatapackConf pluginDatapackConf);
	
	public PluginDatapackConf queryByPk(PluginDatapackConf pluginDatapackConf);
	
	public List<PluginDatapackConf> queryAllOwnerByPage(PluginDatapackConfVO pluginDatapackConf);
	
	public List<PluginDatapackConf> queryAllCurrOrgByPage(PluginDatapackConfVO pluginDatapackConf);
	
	public List<PluginDatapackConf> queryAllCurrDownOrgByPage(PluginDatapackConfVO pluginDatapackConf);

}
