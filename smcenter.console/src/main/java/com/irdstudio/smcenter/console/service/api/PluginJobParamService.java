package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.PluginJobParamVO;

/**
 * <p>Description:数据作业参数配置				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface PluginJobParamService {
	
	public List<PluginJobParamVO> queryAllOwner(PluginJobParamVO pluginJobParamVo);
	
	public List<PluginJobParamVO> queryAllCurrOrg(PluginJobParamVO pluginJobParamVo);
	
	public List<PluginJobParamVO> queryAllCurrDownOrg(PluginJobParamVO pluginJobParamVo);
	
	public int insertPluginJobParam(PluginJobParamVO inPluginJobParamVo);
	
	public int deleteByPk(PluginJobParamVO pluginJobParamVo);
	
	public int updateByPk(PluginJobParamVO pluginJobParamVo);
	
	public PluginJobParamVO queryByPk(PluginJobParamVO pluginJobParamVo);

}
