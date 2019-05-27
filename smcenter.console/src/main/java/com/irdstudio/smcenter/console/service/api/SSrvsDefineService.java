package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.SSrvsDefineVO;

/**
 * <p>Description:01.服务组件信息				<p>
 * @author ligm
 * @date 2018-06-09
 */
public interface SSrvsDefineService {
	
	public List<SSrvsDefineVO> queryAllOwner(SSrvsDefineVO sSrvsDefineVo);
	
	public List<SSrvsDefineVO> queryAllCurrOrg(SSrvsDefineVO sSrvsDefineVo);
	
	public List<SSrvsDefineVO> queryAllCurrDownOrg(SSrvsDefineVO sSrvsDefineVo);
	
	public int insertSSrvsDefine(SSrvsDefineVO inSSrvsDefineVo);
	
	public int deleteByPk(SSrvsDefineVO sSrvsDefineVo);
	
	public int updateByPk(SSrvsDefineVO sSrvsDefineVo);
	
	public SSrvsDefineVO queryByPk(SSrvsDefineVO sSrvsDefineVo);

}
