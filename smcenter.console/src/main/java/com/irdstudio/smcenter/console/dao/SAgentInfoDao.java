package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.SAgentInfo;
import com.irdstudio.smcenter.console.service.vo.SAgentInfoVO;
/**
 * <p>DAO interface:代理节点信息				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface SAgentInfoDao {
	
	public int insertSAgentInfo(SAgentInfo sAgentInfo);
	
	public int deleteByPk(SAgentInfo sAgentInfo);
	
	public int updateByPk(SAgentInfo sAgentInfo);
	
	public SAgentInfo queryByPk(SAgentInfo sAgentInfo);
	
	public List<SAgentInfo> queryAllOwnerByPage(SAgentInfoVO sAgentInfo);
	
	public List<SAgentInfo> queryAllCurrOrgByPage(SAgentInfoVO sAgentInfo);
	
	public List<SAgentInfo> queryAllCurrDownOrgByPage(SAgentInfoVO sAgentInfo);

}
