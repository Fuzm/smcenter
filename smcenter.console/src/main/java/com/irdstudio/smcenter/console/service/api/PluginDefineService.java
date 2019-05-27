package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.PluginDefineVO;

/**
 * <p>Description:应用插件信息				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginDefineService {
	
	public List<PluginDefineVO> queryAllOwner(PluginDefineVO pluginDefineVo);
	
	public List<PluginDefineVO> queryAllCurrOrg(PluginDefineVO pluginDefineVo);
	
	public List<PluginDefineVO> queryAllCurrDownOrg(PluginDefineVO pluginDefineVo);
	
	public int insertPluginDefine(PluginDefineVO inPluginDefineVo);
	
	public int deleteByPk(PluginDefineVO pluginDefineVo);
	
	public int updateByPk(PluginDefineVO pluginDefineVo);
	
	public PluginDefineVO queryByPk(PluginDefineVO pluginDefineVo);

}
