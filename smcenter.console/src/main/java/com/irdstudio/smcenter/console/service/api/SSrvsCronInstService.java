package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.SSrvsCronInstVO;

/**
 * <p>Description:定时调度实例				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface SSrvsCronInstService {
	
	public List<SSrvsCronInstVO> queryAllOwner(SSrvsCronInstVO sSrvsCronInstVo);
	
	public List<SSrvsCronInstVO> queryAllCurrOrg(SSrvsCronInstVO sSrvsCronInstVo);
	
	public List<SSrvsCronInstVO> queryAllCurrDownOrg(SSrvsCronInstVO sSrvsCronInstVo);
	
	public int insertSSrvsCronInst(SSrvsCronInstVO inSSrvsCronInstVo);
	
	public int deleteByPk(SSrvsCronInstVO sSrvsCronInstVo);
	
	public int updateByPk(SSrvsCronInstVO sSrvsCronInstVo);
	
	public SSrvsCronInstVO queryByPk(SSrvsCronInstVO sSrvsCronInstVo);

}
