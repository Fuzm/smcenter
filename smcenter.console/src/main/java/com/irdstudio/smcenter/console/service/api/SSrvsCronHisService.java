package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.SSrvsCronHisVO;

/**
 * <p>Description:定时调度历史				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface SSrvsCronHisService {
	
	public List<SSrvsCronHisVO> queryAllOwner(SSrvsCronHisVO sSrvsCronHisVo);
	
	public List<SSrvsCronHisVO> queryAllCurrOrg(SSrvsCronHisVO sSrvsCronHisVo);
	
	public List<SSrvsCronHisVO> queryAllCurrDownOrg(SSrvsCronHisVO sSrvsCronHisVo);
	
	public int insertSSrvsCronHis(SSrvsCronHisVO inSSrvsCronHisVo);
	
	public int deleteByPk(SSrvsCronHisVO sSrvsCronHisVo);
	
	public int updateByPk(SSrvsCronHisVO sSrvsCronHisVo);
	
	public SSrvsCronHisVO queryByPk(SSrvsCronHisVO sSrvsCronHisVo);

}
