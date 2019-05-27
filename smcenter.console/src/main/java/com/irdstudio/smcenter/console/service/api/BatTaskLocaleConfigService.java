package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.BatTaskLocaleConfigVO;

/**
 * <p>Description:执行场所配置				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface BatTaskLocaleConfigService {
	
	public List<BatTaskLocaleConfigVO> queryAllOwner(BatTaskLocaleConfigVO batTaskLocaleConfigVo);
	
	public List<BatTaskLocaleConfigVO> queryAllCurrOrg(BatTaskLocaleConfigVO batTaskLocaleConfigVo);
	
	public List<BatTaskLocaleConfigVO> queryAllCurrDownOrg(BatTaskLocaleConfigVO batTaskLocaleConfigVo);
	
	public int insertBatTaskLocaleConfig(BatTaskLocaleConfigVO inBatTaskLocaleConfigVo);
	
	public int deleteByPk(BatTaskLocaleConfigVO batTaskLocaleConfigVo);
	
	public int updateByPk(BatTaskLocaleConfigVO batTaskLocaleConfigVo);
	
	public BatTaskLocaleConfigVO queryByPk(BatTaskLocaleConfigVO batTaskLocaleConfigVo);

}
