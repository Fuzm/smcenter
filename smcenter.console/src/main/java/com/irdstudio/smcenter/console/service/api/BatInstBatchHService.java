package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.BatInstBatchHVO;

/**
 * <p>Description:批次历史信息				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface BatInstBatchHService {
	
	public List<BatInstBatchHVO> queryAllOwner(BatInstBatchHVO batInstBatchHVo);
	
	public List<BatInstBatchHVO> queryAllCurrOrg(BatInstBatchHVO batInstBatchHVo);
	
	public List<BatInstBatchHVO> queryAllCurrDownOrg(BatInstBatchHVO batInstBatchHVo);
	
	public int insertBatInstBatchH(BatInstBatchHVO inBatInstBatchHVo);
	
	public int deleteByPk(BatInstBatchHVO batInstBatchHVo);
	
	public int updateByPk(BatInstBatchHVO batInstBatchHVo);
	
	public BatInstBatchHVO queryByPk(BatInstBatchHVO batInstBatchHVo);

}
