package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.SSrvsCronConfVO;

/**
 * <p>Description:定时调度配置				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface SSrvsCronConfService {
	
	public List<SSrvsCronConfVO> queryAllOwner(SSrvsCronConfVO sSrvsCronConfVo);
	
	public List<SSrvsCronConfVO> queryAllCurrOrg(SSrvsCronConfVO sSrvsCronConfVo);
	
	public List<SSrvsCronConfVO> queryAllCurrDownOrg(SSrvsCronConfVO sSrvsCronConfVo);
	
	public int insertSSrvsCronConf(SSrvsCronConfVO inSSrvsCronConfVo);
	
	public int deleteByPk(SSrvsCronConfVO sSrvsCronConfVo);
	
	public int updateByPk(SSrvsCronConfVO sSrvsCronConfVo);
	
	public SSrvsCronConfVO queryByPk(SSrvsCronConfVO sSrvsCronConfVo);

}
