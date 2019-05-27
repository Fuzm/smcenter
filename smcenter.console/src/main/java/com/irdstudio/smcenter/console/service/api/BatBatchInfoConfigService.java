package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.BatBatchInfoConfigVO;

/**
 * <p>Description:批次信息配置				<p>
 * @author ligm
 * @date 2018-06-14
 */
public interface BatBatchInfoConfigService {
	
	public List<BatBatchInfoConfigVO> queryAllOwner(BatBatchInfoConfigVO batBatchInfoConfigVo);
	
	public List<BatBatchInfoConfigVO> queryAllCurrOrg(BatBatchInfoConfigVO batBatchInfoConfigVo);
	
	public List<BatBatchInfoConfigVO> queryAllCurrDownOrg(BatBatchInfoConfigVO batBatchInfoConfigVo);
	
	public int insertBatBatchInfoConfig(BatBatchInfoConfigVO inBatBatchInfoConfigVo);
	
	public int deleteByPk(BatBatchInfoConfigVO batBatchInfoConfigVo);
	
	public int updateByPk(BatBatchInfoConfigVO batBatchInfoConfigVo);
	
	public BatBatchInfoConfigVO queryByPk(BatBatchInfoConfigVO batBatchInfoConfigVo);

}
