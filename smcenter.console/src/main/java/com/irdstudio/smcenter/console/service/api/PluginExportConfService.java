package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.PluginExportConfVO;

/**
 * <p>Description:导出为文本文件插件				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginExportConfService {
	
	public List<PluginExportConfVO> queryAllOwner(PluginExportConfVO pluginExportConfVo);
	
	public List<PluginExportConfVO> queryAllCurrOrg(PluginExportConfVO pluginExportConfVo);
	
	public List<PluginExportConfVO> queryAllCurrDownOrg(PluginExportConfVO pluginExportConfVo);
	
	public int insertPluginExportConf(PluginExportConfVO inPluginExportConfVo);
	
	public int deleteByPk(PluginExportConfVO pluginExportConfVo);
	
	public int updateByPk(PluginExportConfVO pluginExportConfVo);
	
	public PluginExportConfVO queryByPk(PluginExportConfVO pluginExportConfVo);

}
