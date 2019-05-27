package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.BatInstTaskHVO;

/**
 * <p>Description:批次任务历史				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface BatInstTaskHService {
	
	public List<BatInstTaskHVO> queryAllOwner(BatInstTaskHVO batInstTaskHVo);
	
	public List<BatInstTaskHVO> queryAllCurrOrg(BatInstTaskHVO batInstTaskHVo);
	
	public List<BatInstTaskHVO> queryAllCurrDownOrg(BatInstTaskHVO batInstTaskHVo);
	
	public int insertBatInstTaskH(BatInstTaskHVO inBatInstTaskHVo);
	
	public int deleteByPk(BatInstTaskHVO batInstTaskHVo);
	
	public int updateByPk(BatInstTaskHVO batInstTaskHVo);
	
	public BatInstTaskHVO queryByPk(BatInstTaskHVO batInstTaskHVo);

}
