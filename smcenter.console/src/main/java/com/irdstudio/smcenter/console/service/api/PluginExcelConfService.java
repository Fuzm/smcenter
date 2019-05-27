package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.PluginExcelConfVO;

/**
 * <p>Description:Excel操作配置表				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginExcelConfService {
	
	public List<PluginExcelConfVO> queryAllOwner(PluginExcelConfVO pluginExcelConfVo);
	
	public List<PluginExcelConfVO> queryAllCurrOrg(PluginExcelConfVO pluginExcelConfVo);
	
	public List<PluginExcelConfVO> queryAllCurrDownOrg(PluginExcelConfVO pluginExcelConfVo);
	
	public int insertPluginExcelConf(PluginExcelConfVO inPluginExcelConfVo);
	
	public int deleteByPk(PluginExcelConfVO pluginExcelConfVo);
	
	public int updateByPk(PluginExcelConfVO pluginExcelConfVo);
	
	public PluginExcelConfVO queryByPk(PluginExcelConfVO pluginExcelConfVo);

}
