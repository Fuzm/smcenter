package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.SAgentInfoVO;

/**
 * <p>Description:代理节点信息				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface SAgentInfoService {
	
	public List<SAgentInfoVO> queryAllOwner(SAgentInfoVO sAgentInfoVo);
	
	public List<SAgentInfoVO> queryAllCurrOrg(SAgentInfoVO sAgentInfoVo);
	
	public List<SAgentInfoVO> queryAllCurrDownOrg(SAgentInfoVO sAgentInfoVo);
	
	public int insertSAgentInfo(SAgentInfoVO inSAgentInfoVo);
	
	public int deleteByPk(SAgentInfoVO sAgentInfoVo);
	
	public int updateByPk(SAgentInfoVO sAgentInfoVo);
	
	public SAgentInfoVO queryByPk(SAgentInfoVO sAgentInfoVo);

}
