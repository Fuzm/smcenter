package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.SSubsInfoVO;

/**
 * <p>Description:子系统基础信息表				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface SSubsInfoService {
	
	public List<SSubsInfoVO> queryAllOwner(SSubsInfoVO sSubsInfoVo);
	
	public List<SSubsInfoVO> queryAllCurrOrg(SSubsInfoVO sSubsInfoVo);
	
	public List<SSubsInfoVO> queryAllCurrDownOrg(SSubsInfoVO sSubsInfoVo);
	
	public int insertSSubsInfo(SSubsInfoVO inSSubsInfoVo);
	
	public int deleteByPk(SSubsInfoVO sSubsInfoVo);
	
	public int updateByPk(SSubsInfoVO sSubsInfoVo);
	
	public SSubsInfoVO queryByPk(SSubsInfoVO sSubsInfoVo);

}
