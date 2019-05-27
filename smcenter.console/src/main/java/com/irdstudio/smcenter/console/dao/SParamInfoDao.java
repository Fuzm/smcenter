package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.SParamInfo;
import com.irdstudio.smcenter.console.service.vo.SParamInfoVO;
/**
 * <p>DAO interface:参数信息表				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface SParamInfoDao {
	
	public int insertSParamInfo(SParamInfo sParamInfo);
	
	public int deleteByPk(SParamInfo sParamInfo);
	
	public int updateByPk(SParamInfo sParamInfo);
	
	public SParamInfo queryByPk(SParamInfo sParamInfo);
	
	public List<SParamInfo> queryAllOwnerByPage(SParamInfoVO sParamInfo);
	
	public List<SParamInfo> queryAllCurrOrgByPage(SParamInfoVO sParamInfo);
	
	public List<SParamInfo> queryAllCurrDownOrgByPage(SParamInfoVO sParamInfo);

}
