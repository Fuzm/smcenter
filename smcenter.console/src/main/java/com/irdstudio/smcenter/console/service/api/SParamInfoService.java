package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.SParamInfoVO;

/**
 * <p>Description:参数信息表				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface SParamInfoService {
	
	public List<SParamInfoVO> queryAllOwner(SParamInfoVO sParamInfoVo);
	
	public List<SParamInfoVO> queryAllCurrOrg(SParamInfoVO sParamInfoVo);
	
	public List<SParamInfoVO> queryAllCurrDownOrg(SParamInfoVO sParamInfoVo);
	
	public int insertSParamInfo(SParamInfoVO inSParamInfoVo);
	
	public int deleteByPk(SParamInfoVO sParamInfoVo);
	
	public int updateByPk(SParamInfoVO sParamInfoVo);
	
	public SParamInfoVO queryByPk(SParamInfoVO sParamInfoVo);

}
