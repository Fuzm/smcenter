package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.BatBatchStageConfigVO;

/**
 * <p>Description:批次任务阶段				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface BatBatchStageConfigService {
	
	public List<BatBatchStageConfigVO> queryAllOwner(BatBatchStageConfigVO batBatchStageConfigVo);
	
	public List<BatBatchStageConfigVO> queryAllCurrOrg(BatBatchStageConfigVO batBatchStageConfigVo);
	
	public List<BatBatchStageConfigVO> queryAllCurrDownOrg(BatBatchStageConfigVO batBatchStageConfigVo);
	
	public int insertBatBatchStageConfig(BatBatchStageConfigVO inBatBatchStageConfigVo);
	
	public int deleteByPk(BatBatchStageConfigVO batBatchStageConfigVo);
	
	public int updateByPk(BatBatchStageConfigVO batBatchStageConfigVo);
	
	public BatBatchStageConfigVO queryByPk(BatBatchStageConfigVO batBatchStageConfigVo);

}
