package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.BatTaskLocaleConfig;
import com.irdstudio.smcenter.console.service.vo.BatTaskLocaleConfigVO;
/**
 * <p>DAO interface:执行场所配置				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface BatTaskLocaleConfigDao {
	
	public int insertBatTaskLocaleConfig(BatTaskLocaleConfig batTaskLocaleConfig);
	
	public int deleteByPk(BatTaskLocaleConfig batTaskLocaleConfig);
	
	public int updateByPk(BatTaskLocaleConfig batTaskLocaleConfig);
	
	public BatTaskLocaleConfig queryByPk(BatTaskLocaleConfig batTaskLocaleConfig);
	
	public List<BatTaskLocaleConfig> queryAllOwnerByPage(BatTaskLocaleConfigVO batTaskLocaleConfig);
	
	public List<BatTaskLocaleConfig> queryAllCurrOrgByPage(BatTaskLocaleConfigVO batTaskLocaleConfig);
	
	public List<BatTaskLocaleConfig> queryAllCurrDownOrgByPage(BatTaskLocaleConfigVO batTaskLocaleConfig);

}
