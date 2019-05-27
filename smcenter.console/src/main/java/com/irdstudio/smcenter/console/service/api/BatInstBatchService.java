package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.BatInstBatchVO;

/**
 * <p>Description:批次实例信息				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface BatInstBatchService {
	
	public List<BatInstBatchVO> queryAllOwner(BatInstBatchVO batInstBatchVo);
	
	public List<BatInstBatchVO> queryAllCurrOrg(BatInstBatchVO batInstBatchVo);
	
	public List<BatInstBatchVO> queryAllCurrDownOrg(BatInstBatchVO batInstBatchVo);
	
	public int insertBatInstBatch(BatInstBatchVO inBatInstBatchVo);
	
	public int deleteByPk(BatInstBatchVO batInstBatchVo);
	
	public int updateByPk(BatInstBatchVO batInstBatchVo);
	
	public BatInstBatchVO queryByPk(BatInstBatchVO batInstBatchVo);
	
	public int updateBatInstBatchStatus(BatInstBatchVO batInstBatchVo);

}
