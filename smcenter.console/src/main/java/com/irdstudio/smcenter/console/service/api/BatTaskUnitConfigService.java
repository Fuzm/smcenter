package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.BatTaskUnitConfigVO;

/**
 * <p>Description:批次任务配置				<p>
 * @author ligm
 * @date 2018-06-14
 */
public interface BatTaskUnitConfigService {
	
	public List<BatTaskUnitConfigVO> queryAllOwner(BatTaskUnitConfigVO batTaskUnitConfigVo);
	
	public List<BatTaskUnitConfigVO> queryAllCurrOrg(BatTaskUnitConfigVO batTaskUnitConfigVo);
	
	public List<BatTaskUnitConfigVO> queryAllCurrDownOrg(BatTaskUnitConfigVO batTaskUnitConfigVo);
	
	public int insertBatTaskUnitConfig(BatTaskUnitConfigVO inBatTaskUnitConfigVo);
	
	public int deleteByPk(BatTaskUnitConfigVO batTaskUnitConfigVo);
	
	public int updateByPk(BatTaskUnitConfigVO batTaskUnitConfigVo);
	
	public BatTaskUnitConfigVO queryByPk(BatTaskUnitConfigVO batTaskUnitConfigVo);

}
