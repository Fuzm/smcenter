package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.PluginJobParam;
import com.irdstudio.smcenter.console.service.vo.PluginJobParamVO;
/**
 * <p>DAO interface:数据作业参数配置				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginJobParamDao {
	
	public int insertPluginJobParam(PluginJobParam pluginJobParam);
	
	public int deleteByPk(PluginJobParam pluginJobParam);
	
	public int updateByPk(PluginJobParam pluginJobParam);
	
	public PluginJobParam queryByPk(PluginJobParam pluginJobParam);
	
	public List<PluginJobParam> queryAllOwnerByPage(PluginJobParamVO pluginJobParam);
	
	public List<PluginJobParam> queryAllCurrOrgByPage(PluginJobParamVO pluginJobParam);
	
	public List<PluginJobParam> queryAllCurrDownOrgByPage(PluginJobParamVO pluginJobParam);

}
