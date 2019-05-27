package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.PluginSysupdConfVO;

/**
 * <p>Description:系统信息更新配置表				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginSysupdConfService {
	
	public List<PluginSysupdConfVO> queryAllOwner(PluginSysupdConfVO pluginSysupdConfVo);
	
	public List<PluginSysupdConfVO> queryAllCurrOrg(PluginSysupdConfVO pluginSysupdConfVo);
	
	public List<PluginSysupdConfVO> queryAllCurrDownOrg(PluginSysupdConfVO pluginSysupdConfVo);
	
	public int insertPluginSysupdConf(PluginSysupdConfVO inPluginSysupdConfVo);
	
	public int deleteByPk(PluginSysupdConfVO pluginSysupdConfVo);
	
	public int updateByPk(PluginSysupdConfVO pluginSysupdConfVo);
	
	public PluginSysupdConfVO queryByPk(PluginSysupdConfVO pluginSysupdConfVo);

}
