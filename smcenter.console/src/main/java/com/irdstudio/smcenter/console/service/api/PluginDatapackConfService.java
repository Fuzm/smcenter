package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.PluginDatapackConfVO;

/**
 * <p>Description:数据报文文件生成配置表				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginDatapackConfService {
	
	public List<PluginDatapackConfVO> queryAllOwner(PluginDatapackConfVO pluginDatapackConfVo);
	
	public List<PluginDatapackConfVO> queryAllCurrOrg(PluginDatapackConfVO pluginDatapackConfVo);
	
	public List<PluginDatapackConfVO> queryAllCurrDownOrg(PluginDatapackConfVO pluginDatapackConfVo);
	
	public int insertPluginDatapackConf(PluginDatapackConfVO inPluginDatapackConfVo);
	
	public int deleteByPk(PluginDatapackConfVO pluginDatapackConfVo);
	
	public int updateByPk(PluginDatapackConfVO pluginDatapackConfVo);
	
	public PluginDatapackConfVO queryByPk(PluginDatapackConfVO pluginDatapackConfVo);

}
